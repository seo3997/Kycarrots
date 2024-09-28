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


<jsp:useBean id="sectorComboStr"    type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="dayComboStr"    type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" 			class="com.whomade.kycarrots.framework.common.object.DataMap" 			scope="request"/>
<jsp:useBean id="pageNavigationVo" 	class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" 	class="java.lang.String" scope="request"/>


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
				<h3>간이재무계획</h3>
				<p>
				간단한 재무분석 이므로 편안하고 부담없이 작성해 봅시다.
				</p>
			</div>
		  </div>
		</div>
	</section>
	<!-- End page header  -->
	<!-- Start modal -->
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="myModalLabel"><span id="modalTitle">메뉴등록</span></h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group margintop30">
					<label class="control-label" for="inputMenuTitle"><span id="modalmenuTitle">메뉴명</span></label>
					<div class="controls">
						<input type="text" class="span3" id="inputMenuTitle" value="" name="inputMenuTitle" placeholder="제품및 메뉴명을 입력하세요">
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputMenuPrice"><span id="modalmenuPrice">메뉴가격</span></label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputMenuPrice" value="" name="inputMenuPrice" placeholder="가격을 입력하세요">
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputMenuQty">판매수량</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputMenuQty" value="" name="inputMenuQty" placeholder="판매수량을 입력하세요">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddMenu" class="btn btn-primary" value="추가 및 수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	
	
	<!-- Start contain -->
	<section id="contain">
		<div class="container">
		  <div class="row">
			<div class="span3">
				<div class="navaside">
					<ul class="nav">
						<li><a href="/"><i class="icon-home"></i> Dashboard</a></li>
						<li class="active"><a href="/front/salePlan.do"><i class="icon-pencil"></i> 예상매출액</a></li>
						<li><a href="change-password.html"><i class="icon-lock"></i> 재료비계획</a></li>
						<li><a href="upload.html"><i class="icon-cloud-upload"></i> 인건비계획</a></li>
						<li><a href="member-porfolio.html"><i class="icon-picture"></i> 투자계획</a></li>
						<li><a href="member-collection.html"><i class="icon-folder-open"></i> 자금조달계획</a></li>
						<li><a href="help.html"><i class="icon-bullhorn"></i> 사업타당성검토</a></li>
					</ul>
				</div>
			</div>
			<div class="span9">
				<h4 class="heading">예상매출액 계산하기<span></span></h4>
				<p>
				본 자료는 사업계획서 작성과 사업성분석을 위한 실습자료로 이지플랜컨설팅이 참석자들의 이해를 돕기 위해 교육용 목적으로 만든 것으로 <br>기업회계 기준에서 정하는 내용과 부분적으로 상이 할 수 있습니다.
				</p>
				<form class="form-horizontal">
				  <div class="control-group">
				    <label class="control-label">업종선택</label>
				    <div class="controls">
						<select id="sector_se_code_m" name="sector_se_code_m">
						<%=CommboUtil.getComboStr(sectorComboStr, "CODE", "CODE_NM", "3" , "C")%>
						</select>				
				    </div>
				  </div>
				  <div class="control-group">
				    <label class="control-label">영업일수</label>
				    <div class="controls">
						<select id="day_se_code_m" name="day_se_code_m">
						<%=CommboUtil.getComboStr(dayComboStr, "CODE", "CODE_NM", "25" , "C")%>
						</select>				
				    </div>
				  </div>
				</form>				

				<h5>상품계획</h5>
				<p>업종선택에 따라 <strong class="text-primary">명칭</strong>이 다르게 보일수 있습니다.</p>
				<div class="control-group login-register">
				    <div class="controls">
				        <button id="btnMenu" class="btn btn-small" type="button">+</button>
				    </div>
				</div>

				<table id="menuTable" class="table table-bordered table-striped">
					<thead>
					<tr>
						<th width="5%">No</th>
						<th><span id="smenuTitle">메뉴명</span></th>
						<th width="17%"><span id="smenuPrice">메뉴가격</span></th>
						<th width="17%"><span id="smenuQty">일 판매 기준수량</span></th>
						<th width="17%"><span id="smenuAmt">일 판매 금액</span></th>
						<th width="10%">수정/삭제</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td class="menuIndex"><span name="dmenuIndex">1</span></td>
						<td><span name="dmenuTitle">A메뉴</span></td>
						<td style="text-align:right;"><span name="dmenuPrice">20,000</span>원</td>
						<td style="text-align:right;"><span name="dmenuQty">25</span></td>
						<td style="text-align:right;"><span name="dmenuAmt">500,000</span>원</td>
						<td style="text-align:center;"><input name="btnUpdMenu" class="btn btn-mini" value="수정" type="button" />
							<input name="btnDelMenu" class="btn btn-mini btn-danger" value="삭제" type="button" />
						</td>
					</tr>
					<tr>
						<td class="menuIndex"><span name="dmenuIndex">2</span></td>
						<td><span name="dmenuTitle">B메뉴</span></td>
						<td style="text-align:right;"><span name="dmenuPrice">10,000</span>원</td>
						<td style="text-align:right;"><span name="dmenuQty">25</span></td>
						<td style="text-align:right;"><span name="dmenuAmt">250,000</span>원</td>
						<td style="text-align:center;"><input name="btnUpdMenu" class="btn btn-mini" value="수정" type="button" />
							<input name="btnDelMenu" class="btn btn-mini btn-danger" value="삭제" type="button" />
						</td>
					</tr>
					</tbody>
			  	</table>
		
				<div class="control-group">
				    <div class="controls right-form">
				      <input type="button" id="btnCalSele" class="btn btn-primary" value="Update Your Plan"/>
				    </div>
				</div>

				<h4 class="heading" style="clear: both;">예상매출액<span></span></h4>
				<table class="table table-bordered table-striped">
					<thead>
					<tr>
						<th width="33%">일 예상 매출액</th>
						<th width="33%">월 예상 매출액 <button class="btn btn-mini"type="button">상세보기</button></th>
						<th>연 예상 매출액 <button class="btn btn-mini btn-info"type="button">상세보기</button></th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td style="text-align:right;"><span id="daySaleAmt">750,000</span>원</td>
						<td style="text-align:right;"><span id="monthSaleAmt">18,750,000</span>원</td>
						<td style="text-align:right;"><span id="yearSaleAmt">225,000,000</span>원</td>
					</tr>
					</tbody>
			  	</table>
				<div class="divider"></div>
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

	
	<script type="text/javascript">
	//<![CDATA[
		var browser			= navigator.userAgent;
		var browserRegex	= /(Android|BlackBerry|IEMobile|Nokia|iP(ad|hone|od)|Opera M(obi|ini))/;
		var isMobile		= false;
		if(browser.match(browserRegex)) {
			isMobile			= true;
			addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
			function hideURLbar(){
				window.scrollTo(0,1);
			}
		}
	//]]>
	
	</script>		
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
	    var saleAddFlag="ADD";
	    var saleIndexNo=0;
		    
		$('#sector_se_code_m').on('change', function(e) {
	   		if("1"==this.value){
		   	    document.getElementById('smenuTitle').innerHTML  = "제품명";
		   	    document.getElementById('smenuPrice').innerHTML  = "제품가격";
		   	    document.getElementById('modalTitle').innerHTML  = "제품등록";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "제품명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "제품가격";
	   		}else if("2"==this.value){
		   	    document.getElementById('smenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('smenuPrice').innerHTML  = "상품가격";
		   	    document.getElementById('modalTitle').innerHTML  = "상품등록";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "상품가격";
	   		}else if("3"==this.value){
		   	    document.getElementById('smenuTitle').innerHTML  = "메뉴명";
		   	    document.getElementById('smenuPrice').innerHTML  = "메뉴가격";
		   	    document.getElementById('modalTitle').innerHTML  = "메뉴등록";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "메뉴명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "메뉴가격";
	   		}else if("4"==this.value){
		   	    document.getElementById('smenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('smenuPrice').innerHTML  = "서비스가격";
		   	    document.getElementById('modalTitle').innerHTML  = "서비스등록";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "서비스가격";
	   		}else if("5"==this.value){
		   	    document.getElementById('smenuTitle').innerHTML  = "물건명";
		   	    document.getElementById('smenuPrice').innerHTML  = "물건가격";
		   	    document.getElementById('modalTitle').innerHTML  = "물건등록";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "물건명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "물건가격";
			}else{
		   	    document.getElementById('modalTitle').innerHTML  = "상품등록";
		   	    document.getElementById('smenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('smenuPrice').innerHTML  = "상품가격";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "상품가격";
			}
		});

		 document.getElementById('btnAddMenu').addEventListener('click',function(e){
				console.log("*****btnAddMenu*******");
				if (document.getElementById('inputMenuTitle').value == "") {
					alert("명칭을 입력하세요");
					 document.getElementById("inputMenuTitle").focus();
					return;
				}
				if (document.getElementById('inputMenuPrice').value == "") {
					alert("가격을 입력하세요");
					 document.getElementById("inputMenuPrice").focus();
					return;
				}
				if (document.getElementById('inputMenuQty').value == "") {
					alert("수량을 입력하세요");
					 document.getElementById("inputMenuQty").focus();
					return;
				}
				
				var inputMenuIndex = "";
				var inputMenuTitle = "";
				var inputMenuPrice = "";
				var inputMenuQty = "";
				var inputMenuAmt = 0;
				var inputMenuTitleValue = document.getElementById('inputMenuTitle').value;
				var inputMenuPriceValue = document.getElementById('inputMenuPrice').value;
				var inputMenuQtyValue 	= document.getElementById('inputMenuQty').value;

				inputMenuAmt = eval(inputMenuPriceValue * inputMenuQtyValue);
				console.log("inputMenuAmt["+inputMenuAmt+"]");
				

				if(saleAddFlag=="ADD") {							//추가버튼 클릭시
					inputMenuIndex = "<span name=\"dmenuIndex\"></span>";
					inputMenuTitle = "<span name=\"dmenuTitle\">"+inputMenuTitleValue+"</span>";
					inputMenuPrice = "<span name=\"dmenuPrice\">"+setComma(inputMenuPriceValue)+"</span>원";
					inputMenuQty   = "<span name=\"dmenuQty\">"+setComma(inputMenuQtyValue)+"</span>";
					inputMenuAmt   = "<span name=\"dmenuAmt\">"+setComma(inputMenuAmt+"")+"</span>원";

					
					
	         		var trStr="";
					trStr="<tr>";
					trStr += "<td>"+inputMenuIndex+"</td>";
					trStr += "<td>"+inputMenuTitle+"</td>";
					trStr += "<td style=\"text-align:right;\">"+inputMenuPrice+"</td>";
					trStr += "<td style=\"text-align:right;\">"+inputMenuQty+"</td>";
					trStr += "<td style=\"text-align:right;\">"+inputMenuAmt+"</td>";
					trStr += "<td style=\"text-align:right;\">"+"<input name=\"btnUpdMenu\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
														+" <input name=\"btnDelMenu\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
						   +"</td>";
					console.log(trStr);
					$('#menuTable').append(trStr);
				} else{
					$('[name=dmenuTitle]')[saleIndexNo].innerHTML 	= inputMenuTitleValue;
					$('[name=dmenuPrice]')[saleIndexNo].innerHTML 	= setComma(inputMenuPriceValue);
					$('[name=dmenuQty]')[saleIndexNo].innerHTML 	= setComma(inputMenuQtyValue);
					$('[name=dmenuAmt]')[saleIndexNo].innerHTML 	= setComma(inputMenuAmt+"");
				}

				calSale();
				$('#myModal').modal('toggle');
		 });

			
		 $('#btnMenu').on('click', function(e) {
			 	saleAddFlag="ADD";
				document.getElementById('btnAddMenu').value="추가";
				document.getElementById('inputMenuTitle').value ="";
				document.getElementById('inputMenuPrice').value ="";
				document.getElementById('inputMenuQty').value 	="";
				$('#myModal').modal('toggle');
		 });

		 $(document).on('click', 'input[name=btnUpdMenu]', function() {
			 	saleAddFlag="UPD";
				document.getElementById('btnAddMenu').value="수정";
			 	saleIndexNo = $('input[name=btnUpdMenu]').index(this);
			 	console.log(removeComma($('[name=dmenuPrice]')[saleIndexNo].innerHTML));
				
				document.getElementById('inputMenuTitle').value = $('[name=dmenuTitle]')[saleIndexNo].innerHTML;
				document.getElementById('inputMenuPrice').value = removeComma($('[name=dmenuPrice]')[saleIndexNo].innerHTML);
				document.getElementById('inputMenuQty').value 	= removeComma($('[name=dmenuQty]')[saleIndexNo].innerHTML);
				$('#myModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelMenu]', function() {
				if(!confirm('메뉴항목을 삭제하시겠습니까?')){
					return;
				}

				saleIndexNo = $('input[name=btnDelMenu]').index(this);
			 	console.log(saleIndexNo);
				document.getElementById("menuTable").deleteRow(saleIndexNo+1);
				calSale();
			 	
		});

		 $('#btnCalSele').on('click', function(e) {
			 calSale();
		 });
		 

		 //매출액 계산
		 function calSale() {
			 //일매출금액
            let daySaleAmt = 0;
            let monthSaleAmt = 0;
            let yearSaleAmt = 0;

 			let menuAmt = document.querySelectorAll("[name=dmenuAmt]");

 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){
	        		daySaleAmt = daySaleAmt + eval(removeComma(node.innerHTML));
	  	      });
	         }	

			 if (daySaleAmt > 0) {
	             
				 //월간매출금액
		         let day_se_code_m = document.getElementById('day_se_code_m');        
				 let workDay = eval(day_se_code_m.options[day_se_code_m.selectedIndex].value);
	             monthSaleAmt = daySaleAmt * workDay
	             
				 //연간 매출금액
	             yearSaleAmt = monthSaleAmt * 12
	             document.getElementById('yearSaleAmt').innerHTML = setComma(yearSaleAmt+"");
			 }else{

			 }
			 document.getElementById('daySaleAmt').innerHTML = setComma(daySaleAmt+"");
             document.getElementById('monthSaleAmt').innerHTML = setComma(monthSaleAmt+"");
             document.getElementById('yearSaleAmt').innerHTML = setComma(yearSaleAmt+"");

             calNo();

		 }

	     //메뉴 No 재계산 			
		 function calNo() {

 			let menuIndex = document.querySelectorAll("[name=dmenuIndex]");

 			if (menuIndex.length > 0) {
 	 			let i = 0;
 	 			menuIndex.forEach(function(node){
		        	i++;
	        		node.innerHTML = i;
	  	      });
	         }	

	     }
		 
			
	</script>

	</body>
	
</html>


