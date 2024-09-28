<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>

<% 
	response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT"); 
	response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	response.addHeader("Cache-Control", "post-check=0, pre-check=0"); 
	response.setHeader("Pragma", "no-cache");
%>
<%@ include file="/common/inc/common.jspf" %>
<jsp:useBean id="param" 			class="com.whomade.kycarrots.framework.common.object.DataMap" 			scope="request"/>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>창업경영</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<meta http-equiv="Cache-Control" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/>
		<meta http-equiv="Pragma" content="no-cache"/>

		
		<%@ include file="/common/front/cssScript.jspf" %>
		<script type="text/javascript">
			function fnCheckId(){
				if($('#user_id').val()==""){
					alert("아디디 입력후 중복체크 하세요.");
		       		return;
				}
				 var sUrl 	= "/front/memberIdCheckAjax.do";
				 var sParam ={'user_id' : $('#user_id').val()};
				 var sType 	= "post";
				 var sReturn="N";
	
				 fnCallbackAjax(sUrl,sParam,sType,function fncallback(pResponse){
		   				console.log(pResponse);
					 	var results=pResponse.resultStats.resultList;
		               	if(pResponse.resultStats.resultCode=="ok"){
		               		if(pResponse.resultMap == 'Y'){
								//alert(pResponse.resultStats.resultMsg);
								$("#myDangerAlertS").show("slow").delay(2000).hide("3000");
								$("#spDangerAlert").html(pResponse.resultStats.resultMsg);
								$("#idcheck").val("N");
								return;
							}else{
								$("#idcheck").val("Y");
								//alert("사용가능한 아이디입니다.");
								$("#myDangerAlertS").hide("fast");
								$("#mySuccessAlertS").show("slow").delay(2000).hide("3000");
								$("#spSuccessAlert").html("사용가능한 아이디입니다.");
							}
		               	}
				 });
			}

		
			function fnGoInsert(){
				var bool = true;
	
				if(bool){
	
					if($("#user_id").val() == ""){
						alert("사용자 이메일을 입력해 주세요.");
						$("#user_id").focus();
						return;
					}
	
					
					if($("#user_id").val() != ""){
						var email = $("#user_id").val();  
						var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
						if(regex.test(email) === false) {  
							alert("이메일을 양식을 확인해주세요.");
						    return;  
						}  
					}
	
					if($("#idcheck").val() == "N"){
						alert("아이디 중복확인을 확인하세요");
						$("#user_id").focus();
						return;
					}

					if($("#password").val() == ""){
						alert("비밀번호를 입력해 주세요.");
	
						$("#password").focus();
						return;
					}
	
					if($("#password").val() != $("#password_con").val()){
						alert("비밀번호와 비밀번호 확인이 다릅니다.");
						$("#password").focus();
						return;
					}
					
					
					if(confirm("등록하시겠습니까?")){
						   var sUrl		= "/front/memberSaveAjax.do";
			               var fFrom  =$("#aform");
							fnCallbackFormAjax(sUrl,fFrom,function fncallback(pResponse){
			    				//var results=pResponse.resultStats.resultList;
			                	if(pResponse.resultStats.resultCode=="ok"){
			                   		if(pResponse.resultMap == 'Y'){
				                		alert("회원가입이 완료 되었습니다.");
				        				$("#mySuccessAlert").show("fast");
				        				$("#spSuccessAlert").html("회원가입이 완료 되었습니다.");
				                		location.href="/";
			    					}else{
				                		//alert("처리중 오류가 생겼습니다 \N 저장버튼을 다시 클릭하세요.");
				        				$("#myDangerAlertS").show("slow").delay(2000).hide("3000");
				        				$("#spDangerAlert").html("처리중 오류가 생겼습니다. 저장버튼을 다시 클릭하세요.");
	
			    					}
			                	}
						 	});
					}
					
				}
			}
		</script>
	</head>

	<body>	
	<header>
		<%@ include file="/common/front/headHtml.jspf" %>
	</header>

	
	<!-- Start page header -->
	<section id="page-header" style="padding-top: 100px;">
		<div class="container">
		  <div class="row">
			<div class="span12 aligncenter">
				<h3>회원가입</h3>
				<p></p>
			</div>
		  </div>
		</div>
	</section>
	<!-- End page header  -->
	<!-- Start modal -->
		<%@ include file="/common/front/loginHtml.jspf" %>
	<!-- End modal -->		
	
	
	<!-- Start contain -->
	<section id="contain">
		<div class="container">
		  <div class="row">
			<div class="span8">
				<h4 class="heading">회원가입<span></span></h4>
				<form id="aform" method="post">
				<input type="hidden" id="idcheck"  name="idcheck" value="N"/>
					<fieldset>
						<p>
						<label>이메일 :</label>
						<input type="email" name="user_id" id="user_id" maxlength="50" value="" class="span5" placeholder="Enter your unique email address." />
							<input  class="btn btn-dpgray" type="button" id="btn_idcheck" style="margin-top: -10px;" value="중복확인" />
						</p>
					</fieldset>
					<fieldset>
						<p>
						<label>패스워드 :</label>
						<input type="password" name="password" id="password" size="20" maxlength="50" value="" class="span5" placeholder="Enter a secure password." />
						</p>      
						<p>
						<label>패스워드 확인 :</label>
						<input type="password" name="password_con" id="password_con" value="" class="span5" placeholder="Confirm your secure password!"/>
						</p>      
					</fieldset>
					<fieldset>
						<p>
						<input class="btn btn-large btn-primary" type="button" id="btn_save" value="회원가입"/>
						</p>
					</fieldset>	
				</form>
			    <div class="controls alert alert-danger" id="myDangerAlertS" style="display:none">
					<button type="button" class="close" id="myDangerAlertH">×</button>
					<span id="spDangerAlert">실패</span>
				</div>						  
			    <div class="controls alert alert-info" id="mySuccessAlertS" style="display:none">
					<button type="button" class="close" id="mySuccessAlertH">×</button>
					<span id="spSuccessAlert">성공</span>
				</div>						  
								
			</div>
			<div class="span4">
				<div class="info-box">
					<p>
					회원가입을 무료입니다.<a href="#">Help</a>. 많이 사용해 주세요 <a href="#">Contact us</a>.
					</p>
					<span class="bg-bottom"></span>
				</div>
			</div>
		  </div>
		</div>
	</section>
	<!-- End contain -->
	
	<!-- Start footer -->
	<footer>
	<%@ include file="/common/front/footer.jspf" %>
	</footer>	
	<!-- End footer -->

    <!-- Javascript
    ================================================== -->
    <script src="/common/front/js/jquery.js"></script>
	<script src="/common/front/js/html5shiv.js"></script>	
	<script src="/common/front/js/jquery.easing.1.3.js"></script>	
    <script src="/common/front/js/bootstrap.js"></script>	

	<!-- slitslider -->
	<script src="/common/front/js/slitslider/jquery.ba-cond.min.js"></script>
	<script src="/common/front/js/slitslider/modernizr.custom.79639.js"></script>		
	<script src="/common/front/js/slitslider/jquery.slitslider.js"></script>	
	<script src="/common/front/js/slitslider/setting.js"></script>

	<!-- isotope -->
	<!--
    <script src="/common/front/js/filter/jquery.isotope.min.js"></script>
	<script src="/common/front/js/filter/setting.js"></script>
	-->
	<!-- prettyPhoto -->
	<script src="/common/front/js/prettyPhoto/jquery.prettyPhoto.js"></script>
	<script src="/common/front/js/prettyPhoto/setting.js"></script>

	<!-- to top -->
	<script src="/common/front/js/totop/jquery.ui.totop.js"></script>
	<script src="/common/front/js/totop/setting.js"></script>	
	
	<!-- custom js -->
	<script src="/common/front/js/custom.js"></script>
	<script type="text/javascript" src="/common/js/common.js?version=6.5"></script>

	<script type="text/javascript">
		document.getElementById('btn_save').addEventListener('click',function(e){
			fnGoInsert();
		});

		document.getElementById('btn_idcheck').addEventListener('click',function(e){
			fnCheckId();
		});
		
	</script>
	</body>
	
</html>


