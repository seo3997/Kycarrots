<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="resultMap" 	class="com.whomade.kycarrots.framework.common.object.DataMap" 	scope="request"/>
<jsp:useBean id="resultList"  	class="java.util.ArrayList"		  						scope="request"	type="java.util.List"/>
<jsp:useBean id="param" 		class="com.whomade.kycarrots.framework.common.object.DataMap" 	scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>

	<script type="text/javascript">
		$(function(){
		});
	</script>
	
</head>

<body class="hold-transition skin-green-light sidebar-mini">
<div class="content">
	<section class="content">
	      <div class="container-fluid">
	        <div class="row">
	          <div class="col-12">
	
	
	            <!-- Main content -->
	            <div class="invoice p-3 mb-3">
	              <!-- title row -->
	              <div class="row">
	                <div class="col-12">
	                  <h4>
	                    <i class="fas fa-globe"></i> 메일전송
	                  </h4>
	                </div>
	                <!-- /.col -->
	              </div>
	
	
	              <div class="row">
	                <!-- accepted payments column -->
	                <div class="col-12">
	                  <p class="lead">메일:</p>
	                  <input type="hidden" 	name="GROUP_ID"		id="GROUP_ID" 	value="<%=param.getString("GROUP_ID")%>" />
	                  <input type="hidden" 	name="MAIL_LIST"	id="MAIL_LIST"  />
	                  
	                  <p class="text-muted well well-sm shadow-none" style="margin-top: 10px;">
	                  	<ul id="ulmailId" style="list-style: none;">
							<li>
								<input type="text" class="form-control w-50" name="ed_mail" placeholder="메일아이디" value="" />
							</li>
						</ul>
	                  
	                  </p>
	                </div>
	              </div>
	              <!-- /.row -->
	
	              <!-- this row will not appear when printing -->
	              <div class="row no-print">
	                <div class="col-12">
	                  <button type="button" class="btn btn-success float-right" id="btnSendEmail">
	                  	<i class="far fa-credit-card"></i> 메일전송
	                  </button>
	                  <button type="button" class="btn btn-primary float-right" style="margin-right: 5px;" id="btnAddEmail">
	                    <i class="fas fa-download"></i> 메일추가
	                  </button>
	                </div>
	              </div>
	            </div>
	            <!-- /.invoice -->
	          </div><!-- /.col -->
	        </div><!-- /.row -->
	      </div><!-- /.container-fluid -->
	    </section>
</div>
	<script type="text/javascript">
		 //이메일 추가 
		 document.getElementById('btnAddEmail').addEventListener('click',function(e){
			 addEmailInput();
		 });

		 //이메일 전송 
		 document.getElementById('btnSendEmail').addEventListener('click',function(e){
			 fnSendEmail();
		 });
		 
        //이메일 추가  
		function addEmailInput() {
			  let sEmailAdd="";
			  let li = document.createElement('li');
			  sEmailAdd = "<input type='text' class='form-control w-50' name='ed_mail' placeholder='메일아이디'  />";
			  li.innerHTML=sEmailAdd;
		      let targetul = document.getElementById('ulmailId');
			  targetul.append(li);
		}


		function fnSendEmail(){

			let ed_mail 	 = document.querySelectorAll("[name=ed_mail]");
			let iIndex=0;
 			if (ed_mail.length > 0) {
 				ed_mail.forEach(function(node){
 					if( node.value!="")	{
 						iIndex++;
		 			}
 				})
 			}	
 			if(iIndex<1){
				alert('이메일을 입력해주세요.');
				return;
		 	}

			if(confirm("메일을 전송하시겠습니까?")){
				 fnMakeMailList();
				 
				 var sUrl 	= "/mgt/group/sendMailAjax.do";
				 var sParam ={'GROUP_ID'			: $('#GROUP_ID').val()
						     ,'MAIL_LIST' 		    : $('#MAIL_LIST').val()
						 	 };
				 var sType 	= "post";
                 console.log("sParam:",sParam);
				 fnCallbackAjax(sUrl,sParam,sType,function fncallback(pResponse){
							console.log(pResponse);
					 	var results=pResponse.resultStats.resultList;
						//alert(pResponse.resultStats.resultMsg);
					 	if(pResponse.resultStats.resultCode=="ok"){
	                		alert("메일이 전송되었습니다.");
	                		self.close();
					 	}else{
	                		alert("처리중 오류가 생겼습니다 \N 메일전송 버튼을 다시 클릭하세요.");
						}
				 });
				 
			}
		}

		function fnMakeMailList(){
			let planfJson 	= new Object();

			let mailArr 	= new Array();

			let ed_mail 	 = document.querySelectorAll("[name=ed_mail]");

			let iIndex=0;
 			if (ed_mail.length > 0) {
 				ed_mail.forEach(function(node){
 					if(node.value)	mailArr.push(node.value);
 				})
 			}	
			
 			planfJson.MAILS = mailArr;

			document.getElementById('MAIL_LIST').value = JSON.stringify(planfJson);

 			 
			
		}
	</script>



</body>
</html>
