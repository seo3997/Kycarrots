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

<jsp:useBean id="resultList" 		class="java.util.ArrayList" 									scope="request" type="java.util.List"  />
<jsp:useBean id="param" 			class="com.whomade.kycarrots.framework.common.object.DataMap" 			scope="request"/>
<jsp:useBean id="pageNavigationVo" 	class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" 	class="java.lang.String" 										scope="request"/>


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
			// 상세조회
			function fnSelect(pPLANF_ID){
				$('[name=PLANF_ID]').val(pPLANF_ID);
				$('#aform').attr({ action : '/front/selectPlanF.do', method : 'post' }).submit();
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
				<h3>Mypage</h3>
				<p></p>
			</div>
		  </div>
		</div>
	</section>
	<!-- End page header  -->
	
	<!-- Start modal -->
	<%@ include file="/common/front/loginHtml.jspf" %>
	<!-- End modal -->		
	<form role="form" id="aform" method="post" action="">
		<input type="hidden" id="PLANF_ID"  name="PLANF_ID" value=""/>
	</form>
	<!-- Start contain -->
	<section id="contain">
		<div class="container">
		  <div class="row">
			<div class="span2">
				<div class="navaside">
					<ul class="nav menunav">
						<li class="active"><a href="/front/myProfit.do"><i class="icon-home"></i> 수익분석 목록</a></li>
						<li><a href="/front/myPage.do"><i class="icon-pencil"></i> Mypage</a></li>
					</ul>
				</div>
			</div>
			
			<div class="span10">
				<div>
					<h4 class="heading">수익분석목록<span></span></h4>
					<p></p>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="5%">No</th>
							<th>타이틀</th>
							<th width="20%">등록자</th>
							<th width="10%">등록일시</th>
						</tr>
						</thead>
						<tbody>
							<%
							int dataNo = pageNavigationVo.getCurrDataNo();
							for(int i = 0; i < resultList.size(); i++){
								DataMap dataMap = (DataMap) resultList.get(i);
							%>
			                <tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("PLANF_ID")%>'); return false;">
			                  <td><%=(i+1)%></td>
			                  <td><%=dataMap.getString("PLANF_TITLE") %></td>
			                  <td><%=dataMap.getString("USER_ID") %></td>
			                  <td><%=dataMap.getString("REGIST_DT") %></td>
			                </tr>
							<%}%>
							<%if(resultList.size() == 0){%>
								<tr>
									<td style="text-align:center;" colspan="4">데이타가 없습니다.</td>
								</tr>
							<%}%>
						</tbody>
				  	</table>

				</div><!--diDash-->
		    </div><!--span10-->
		    
		  </div><!--row-->
		</div><!--container-->
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
	<script src="/common/js/chart/chart.js"></script>
	<script type="text/javascript" src="/common/js/common.js?version=6.5"></script>

	<script type="text/javascript">
	</script>

	<script>
	</script>
	
	</body>
	
</html>


