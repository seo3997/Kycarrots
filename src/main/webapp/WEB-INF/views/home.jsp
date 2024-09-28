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

<jsp:useBean id="sectorComboStr"    type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="dayComboStr"    	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="invest1ComboStr"   type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="invest2ComboStr"   type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="invest3ComboStr"   type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="invest4ComboStr"   type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="loan1ComboStr"   	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="loan2ComboStr"   	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="bizCdComboStr"     type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="mCostComboStr"     type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="sCostComboStr"     type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" 			class="com.whomade.kycarrots.framework.common.object.DataMap" 			scope="request"/>
<jsp:useBean id="planFMap" 			class="com.whomade.kycarrots.framework.common.object.DataMap" 			scope="request"/>
<jsp:useBean id="pageNavigationVo" 	class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" 	class="java.lang.String" scope="request"/>
<jsp:useBean id="mCostStr" 			class="java.lang.String" scope="request"/>
<jsp:useBean id="sCostStr" 			class="java.lang.String" scope="request"/>
<jsp:useBean id="invest1Str" 		class="java.lang.String" scope="request"/>
<jsp:useBean id="invest2Str" 		class="java.lang.String" scope="request"/>


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
				<h3>소상공인 수익분석</h3>
				<p>
				간단한 수익분석으로 창업아이템의 수익성을 분석해 봅시다.
				</p>
			</div>
		  </div>
		</div>
	</section>
	<!-- End page header  -->
	
	<%@ include file="/common/front/loginHtml.jspf" %>
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
	<!-- Start modal -->
	<div id="myYMenuModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="myYModalLabel"><span id="modalTitle">연도별 매출등록</span></h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="inputYMenuTitle"><span id="modalYmenuTitle">메뉴명</span></label>
					<div class="controls">
						<label class="control-label" for="YmenuTitle" style="text-align:left">
						<span id="YmenuTitle"></span>
						</label>
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="inputYmenuCd">입력방법선택</label>
					<div class="controls">
						<select id="inputYmenuCd" name="inputYmenuCd">
						<option value="1">증감율</option>
						<option value="2">직접입력</option>
						</select>				
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px" id="modalYMenuQtyRate">
					<label class="control-label" for="inputYMenuQtyRate">판매수량 증감율</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputYMenuQtyRate" value="0" name="inputYMenuQtyRate" placeholder="판매수량 증감율을 입력하세요">%
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px" id="modalYMenuPriceRate">
					<label class="control-label" for="inputYMenuPriceRate">판매단가 증감율</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputYMenuPriceRate" value="0" name="inputYMenuPriceRate" placeholder="판매단가 증감율을 입력하세요">%
					</div>
				</div>
				<table  class="table table-bordered table-striped">
					<thead>
					<tr>
						<th>년도</th>
						<th width="25%">판매수량</th>
						<th width="25%">판매단가</th>
						<th width="25%">판매금액</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>1차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuQtyYear1" 	value="" name="inputMenuQtyYear1" 	placeholder="1차년도 판매수량을 입력하세요"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuPriceYear1" 	value="" name="inputMenuPriceYear1" placeholder="1차년도 판매단가를 입력하세요"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuAmtYear1" 	value="" name="inputMenuAmtYear1" 	readOnly></td>
					</tr>
					<tr>
						<td>2차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuQtyYear2" 	value="" name="inputMenuQtyYear2" 	placeholder="2차년도 판매수량을 입력하세요"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuPriceYear2" 	value="" name="inputMenuPriceYear2" placeholder="2차년도 판매단가를 입력하세요"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuAmtYear2" 	value="" name="inputMenuAmtYear2" 	readOnly></td>
					</tr>
					<tr>
						<td>3차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuQtyYear3" 	value="" name="inputMenuQtyYear3" 	placeholder="3차년도 판매수량을입력하세요"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuPriceYear3" 	value="" name="inputMenuPriceYear3" placeholder="3차년도 판매단가를 입력하세요"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputMenuAmtYear3" 	value="" name="inputMenuAmtYear3" 	readOnly></td>
					</tr>
			  	</table>
				
				
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddYMenu" class="btn btn-primary" value="수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myYCostModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="myYCostModalLabel"><span id="modalYCostTitle">연도별 제료비 등록</span></h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<input type="hidden"  id="inputYCostRate" name="inputYCostRate">
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="modalYCostTitle"><span id="modalYCostTitle">메뉴명</span></label>
					<div class="controls">
						<label class="control-label" for="YCostTitle" style="text-align:left">
						<span id="YCostTitle"></span>
						</label>
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px" id="modalYCostAmtRate">
					<label class="control-label" for="inputYCostAmtRate">재료비비율 증감율</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputYCostAmtRate" value="0" name="inputYCostAmtRate" placeholder="재료비비율 증감율을 입력하세요">%
					</div>
				</div>
				<table  class="table table-bordered table-striped">
					<thead>
					<tr>
						<th>년도</th>
						<th width="25%">판매금액</th>
						<th width="25%">재료비 비율</th>
						<th width="25%">재료비</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>1차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostMenuAmtYear1" value="" name="inputCostMenuAmtYear1" placeholder="1차년도 판매금액"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostAmtYearRate1" value="" name="inputCostAmtYearRate1" readOnly>%</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostAmtYear1" 	 value="" name="inputCostAmtYear1" placeholder="1차년도 재료비"></td>
					</tr>
					<tr>
						<td>2차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostMenuAmtYear2" value="" name="inputCostMenuAmtYear2" placeholder="2차년도 판매금액"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostAmtYearRate2" value="" name="inputCostAmtYearRate2" readOnly>%</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostAmtYear2" 	 value="" name="inputCostAmtYear2" placeholder="2차년도 재료비"></td>
					</tr>
					<tr>
						<td>3차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostMenuAmtYear3" value="" name="inputCostMenuAmtYear3" placeholder="3차년도 판매금액"></td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostAmtYearRate3" value="" name="inputCostAmtYearRate3" readOnly>%</td>
						<td style="text-align:left;"><input type="text" class="numeric span1" id="inputCostAmtYear3" 	 value="" name="inputCostAmtYear3" placeholder="3차년도 재료비"></td>
					</tr>
			  	</table>
				
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddYCost" class="btn btn-primary" value="수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myJobModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="myJobModalLabel"><span id="modalTitle">인건비등록</span></h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group margintop30">
					<label class="control-label" for="inputJobTitle"><span id="modalJobTitle">직위</span></label>
					<div class="controls">
						<input type="text" class="span3" id="inputJobTitle" value="" name="inputJobTitle" placeholder="직위를 입력하세요(ex 사원,대리,주방장등등)">
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputJobCnt"><span id="modalJopPrice">기준인원</span></label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputJobCnt" value="" name="inputJobCnt" placeholder="기준인원을 입려하세요">
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputJobQty">월 기준급여</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputJobQty" value="" name="inputJobQty" placeholder="월 기준급여를 입력하세요">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddJob" class="btn btn-primary" value="추가 및 수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myInvestModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="myInvestModalLabel"><span id="modalTitle">자산등록</span></h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<input type="hidden" id="inputInvestYn" name="inputInvestYn">
				<input type="hidden" id="inputInvestYearAmt" name="inputInvestYearAmt">
				<div class="control-group margintop30" style="display:none">
					<label class="control-label" for="inputInvestBizcdCodeM">제조/판매선택</label>
					<div class="controls">
						<select id="inputInvestBizcdCodeM" name="inputInvestBizcdCodeM">
						<%=CommboUtil.getComboStr(bizCdComboStr, "CODE", "CODE_NM", "" , "C")%>
						</select>				
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputInvestTitle">투자내역</label>
					<div class="controls">
						<select id="inputInvestTitle" name="inputInvestTitle">
						<%=CommboUtil.getComboStr(invest1ComboStr, "CODE", "CODE_NM", "" , "C")%>
						</select>				
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputInvestPirce">투자비용(단윈:천원)</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputInvestPirce" value="" name="inputInvestPirce" placeholder="투자비용을 입력하세요">
					</div>
				</div>
				<div class="control-group margintop30" id="dInvestYear">
					<label class="control-label" for="inputInvestYear">상각년수</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputInvestYear" value="" name="inputInvestYear" placeholder="상각년수를 입력하세요">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddInvest" class="btn btn-primary" value="추가 및 수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myLoanModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="myLoanModalLabel"><span id="modalTitle">자금등록</span></h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group margintop30">
					<label class="control-label" for="inputLoanTitle">자금구분</label>
					<div class="controls">
						<select id="inputLoanTitle" name="inputLoanTitle">
						<%=CommboUtil.getComboStr(loan1ComboStr, "CODE", "CODE_NM", "" , "C")%>
						</select>				
					</div>
				</div>
				
				<table id="modalLoan" class="table table-bordered table-striped">
					<!-- 
					<thead>
					<tr>
						<th>년도</th>
						<th width="35%">자본금액</th>
						<th width="35%">이자율</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>1차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputLoanYear1" value="" name="inputLoanYear1" placeholder="1차년도 자본금을 입력하세요"></td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputLoanRate1" value="" name="inputLoanRate1" placeholder="1차년도 이자율을 입력하세요"></td>
					</tr>
					<tr>
						<td>2차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputLoanYear2" value="" name="inputLoanYear2" placeholder="2차년도 자본금을 입력하세요"></td>
					</tr>
					<tr>
						<td>3차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputLoanYear3" value="" name="inputLoanYear3" placeholder="3차년도 자본금을 입력하세요"></td>
					</tr>
					</tbody>
					 -->
			  	</table>
				
				
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddLoan" class="btn btn-primary" value="추가 및 수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myMCostModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myMCostModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="myMCostModalLabel">제조경비등록</h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group margintop30">
					<label class="control-label" for="inputMcostcdCodeM">계정과목선택</label>
					<div class="controls">
						<select id="inputMcostcdCodeM" name="inputMcostcdCodeM">
						<%=CommboUtil.getComboStr(mCostComboStr, "CODE", "CODE_NM", "" , "C")%>
						</select>				
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="sMCostCd">기준항목</label>
					<div class="controls">
						<label class="control-label" for="sMCostCd" style="text-align:left">
						<span id="sMCostCd">매출액</span>
						</label>
					 </div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputMCostTVa">적용값</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputMCostTVa" value="" name="inputMCostTVa" placeholder="적용값을 입력하세요">
					    <span id="sMCostTVaLabel">원</span>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddMcost" class="btn btn-primary" value="추가 및 수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myYMCostModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4>연도별 제조경비등록</h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="YMCostTitle">계정과목</label>
					<div class="controls">
						<label class="control-label" for="YMCostTitle" style="text-align:left">
						<span id="YMCostTitle"></span>
						</label>
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="inputYMCostAmtCd">입력방법선택</label>
					<div class="controls">
						<select id="inputYMCostAmtCd" name="inputYMCostAmtCd">
						<option value="1">증감율</option>
						<option value="2">직접입력</option>
						</select>				
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px" id="modalYMCostRate">
					<label class="control-label" for="inputYMCostRate">재조경비 증감율</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputYMCostRate" value="0" name="inputYMCostRate" placeholder="판매수량 증감율을 입력하세요">%
					</div>
				</div>
				<table  class="table table-bordered table-striped">
					<thead>
					<tr>
						<th>년도</th>
						<th width="50%">재조경비</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>1차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYMCostYear1" value="" name="inputYMCostYear1" placeholder="1차년도 재조경비를 입력하세요"></td>
					</tr>
					<tr>
						<td>2차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYMCostYear2" value="" name="inputYMCostYear2" placeholder="2차년도 재조경비를  입력하세요"></td>
					</tr>
					<tr>
						<td>3차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYMCostYear3" value="" name="inputYMCostYear3" placeholder="3차년도 재조경비를 입력하세요"></td>
					</tr>
			  	</table>
				
				
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddYMCost" class="btn btn-primary" value="수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="mySCostModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="mySCostModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="mySCostModalLabel">판매관리비등록</h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group margintop30">
					<label class="control-label" for="inputSCostcdCodeM">계정과목선택</label>
					<div class="controls">
						<select id="inputSCostcdCodeM" name="inputSCostcdCodeM">
						<%=CommboUtil.getComboStr(sCostComboStr, "CODE", "CODE_NM", "" , "C")%>
						</select>				
					</div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="sSCostCd">기준항목</label>
					<div class="controls">
						<label class="control-label" for="sSCostCd" style="text-align:left">
						<span id="sSCostCd">매출액</span>
						</label>
					 </div>
				</div>
				<div class="control-group margintop30">
					<label class="control-label" for="inputSCostTVa">적용값</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputSCostTVa" value="" name="inputSCostTVa" placeholder="적용값을 입력하세요">
					    <span id="sSCostTVaLabel">원</span>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddSCost" class="btn btn-primary" value="추가 및 수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myYSCostModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4>연도별 판매관리비등록</h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="YSCostTitle">계정과목</label>
					<div class="controls">
						<label class="control-label" for="YSCostTitle" style="text-align:left">
						<span id="YSCostTitle"></span>
						</label>
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="inputYSCostAmtCd">입력방법선택</label>
					<div class="controls">
						<select id="inputYSCostAmtCd" name="inputYSCostAmtCd">
						<option value="1">증감율</option>
						<option value="2">직접입력</option>
						</select>				
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px" id="modalYSCostRate">
					<label class="control-label" for="inputYSCostRate">판매관리비 증감율</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputYSCostRate" value="0" name="inputYSCostRate" placeholder="판매관리비 증감율을 입력하세요">%
					</div>
				</div>
				<table  class="table table-bordered table-striped">
					<thead>
					<tr>
						<th>년도</th>
						<th width="50%">재조경비</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>1차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYSCostYear1" value="" name="inputYSCostYear1" placeholder="1차년도 재조경비를 입력하세요"></td>
					</tr>
					<tr>
						<td>2차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYSCostYear2" value="" name="inputYSCostYear2" placeholder="2차년도 재조경비를  입력하세요"></td>
					</tr>
					<tr>
						<td>3차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYSCostYear3" value="" name="inputYSCostYear3" placeholder="3차년도 재조경비를 입력하세요"></td>
					</tr>
			  	</table>
				
				
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddYSCost" class="btn btn-primary" value="수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myYJobModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4>연도별 인건비등록</h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<input type="hidden" id="inputYJobCd" name="inputYJobCd">
				<input type="hidden" id="inputYJobCnt" name="inputYJobCnt">
				<input type="hidden" id="inputYJobRateAmt1" name="inputYJobRateAmt1">
				<input type="hidden" id="inputYJobRateAmt2" name="inputYJobRateAmt2">
				<input type="hidden" id="inputYJobRateAmt3" name="inputYJobRateAmt3">
			
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="YJobTitle">계정과목</label>
					<div class="controls">
						<label class="control-label" for="YJobTitle" style="text-align:left">
						<span id="YJobTitle"></span>
						</label>
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px">
					<label class="control-label" for="inputYJobAmtCd">입력방법선택</label>
					<div class="controls">
						<select id="inputYJobAmtCd" name="inputYJobAmtCd" disabled>
						<option value="1">증감율</option>
						<option value="2">직접입력</option>
						</select>				
					</div>
				</div>
				<div class="control-group" style="margin-bottom:5px" id="modalYJobRate">
					<label class="control-label" for="inputYJobRate">인건비 증감율</label>
					<div class="controls">
						<input type="text" class="numeric span2" id="inputYJobRate" value="0" name="inputYJobRate" placeholder="인건비 증감율을 입력하세요">%
					</div>
				</div>
				<table  class="table table-bordered table-striped">
					<thead>
					<tr>
						<th>년도</th>
						<th width="50%">인건비</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>1차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYJobYear1" value="" name="inputYJobYear1" placeholder="1차년도 재조경비를 입력하세요"></td>
					</tr>
					<tr>
						<td>2차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYJobYear2" value="" name="inputYJobYear2" placeholder="2차년도 재조경비를  입력하세요"></td>
					</tr>
					<tr>
						<td>3차년도</td>
						<td style="text-align:left;"><input type="text" class="numeric span2" id="inputYJobYear3" value="" name="inputYJobYear3" placeholder="3차년도 재조경비를 입력하세요"></td>
					</tr>
			  	</table>
				
				
			</div>
			<div class="modal-footer">
				<input type="button" id="btnAddYJob" class="btn btn-primary" value="수정"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="mySaveModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="mySaveModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="mySaveModalLabel">수익분석 저장</h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<input type="hidden" name="GROUP_ID" id="GROUP_ID" value="<%=param.getString("GROUP_ID",planFMap.getString("GROUP_ID"))%>"/>
			<input type="hidden" name="OLD_PLANF_TITLE" id="OLD_PLANF_TITLE" value="<%=planFMap.getString("PLANF_TITLE")%>"/>
		
			<div class="modal-body">
				<div class="control-group margintop30">
					<label class="control-label" for="inputMenuTitle"><span id="modalmenuTitle">수익분석 타이틀</span></label>
					<div class="controls">
						<input type="text" class="span3" id="inputPlanTitle" value="<%=planFMap.getString("PLANF_TITLE") %>" name="inputPlanTitle" placeholder="수익분석타이틀을 입력하세요">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" id="btnPopMenuNewSave" class="btn btn-primary" value="새로저장"/>
				<input type="button" id="btnPopMenuSave" class="btn btn-primary" value="저장"/>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	<!-- Start modal -->
	<div id="myPlanFListModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myPlanFListModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 id="mySaveModalLabel">수익분석 불러오기</h4>
		</div>
		<form class="form-horizontal marginbot-clear">  
			<div class="modal-body">
				<table id="trModalPlanList" class="table table-bordered table-striped">
					<thead>
					<tr>
						<th width="5%">No</th>
						<th>타이틀</th>
						<th width="20%">등록자</th>
						<th width="20%">등록일시</th>
					</tr>
					</thead>
					<tbody>
		            <tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('1'); return false;">
		                  <td></td>
		                  <td></td>
		                  <td></td>
		                  <td></td>
		            </tr>
					</tbody>
			  	</table>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</form>
	</div>
	<!-- End modal -->		
	
	
	
	<!-- Start contain -->
	<section id="contain">
		<div class="container">
		  <div class="row">
			<div class="span2">
				<div class="navaside">
					<ul class="nav menunav">
						<li class="active"><a href="javascript:selectMenu(0)"><i class="icon-home"></i> Dashboard</a></li>
						<li><a href="javascript:selectMenu(1)"><i class="icon-pencil"></i> 예상매출액</a></li>
						<li><a href="javascript:selectMenu(2)"><i class="icon-food"></i> 재료비계획</a></li>
						<li><a href="javascript:selectMenu(3)"><i class="icon-bullhorn"></i> 인건비계획</a></li>
						<li><a href="javascript:selectMenu(4)"><i class="icon-calendar"></i> 투자계획</a></li>
						<li><a href="javascript:selectMenu(5)"><i class="icon-money"></i> 자금조달계획</a></li>
						<li><a href="javascript:selectMenu(6)"><i class="icon-folder-open"></i> 제조경비계획</a></li>
						<li><a href="javascript:selectMenu(7)"><i class=" icon-magic"></i> 판매관리비계획</a></li>
						<li><a href="javascript:selectMenu(8)"><i class="icon-signal"></i> 수익성검토</a></li>
					</ul>
				</div>
			</div>
			
			<div class="span10">

				<div id="diDash">
					<h4 class="heading">Member register<span></span></h4>
					<p>
					In stet aliquam consulatu quo, vocibus comprehensam per ne. Ut augue minim iuvaret eos, mei falli timeam ceteros ut. Qui et choro ubique denique, vim ei accumsan deseruisse argumentum. Usu cu dicant scripta dignissim, usu omnis paulo pertinacia id. Eos verear mediocrem ut, scripta gubergren eu vis. Te eos melius praesent, his nobis nostro diceret id.
					</p>
					<img src="img/register.png" class="thumbnail marginbot20" alt="" />
					<h5>Step 1 :</h5>
					<p>Eos verear mediocrem ut, <strong>scripta</strong> gubergren eu vis. Te eos <strong>melius praesent,</strong> his nobis nostro <a href="#">diceret.</a></p>
					<h5>Step 2 :</h5>
					<p>Quem movet iudicabit est eu, vix affert putant impedit id,  <strong>vivendo adipisci petentium </strong> et nec. Ea adipisci euripidis eos. Et ipsum altera singulis per, ullum  <strong>postulant </strong> persequeris ex vel. Cum eu sale movet delenit.</p>	
					<h5>Step 3 :</h5>
					<p>Solet ocurreret <strong>accommodare</strong> per et, te etiam docendi definitiones nec, duo ad <strong>suscipit conceptam</strong> adversarium. Ius case nusquam id, per labitur verterem eu, cum et mazim iudicabit. Ea alii nonumes nec, viris dicant imperdiet vis eu, ea cibo fugit <a href="#">laboramus</a> sit. Diam offendit no nec, eam in ornatus explicari, <strong>dolore melius menandri vel in.</strong> Ex nec tempor disputando.</p>				
					<div class="divider"></div>
					<h4 class="heading">validate your account<span></span></h4>
					<p>Saepe doctus sed in. Nec ad ocurreret consequuntur, graeco possim constituam pro ad.
					In stet aliquam consulatu quo, vocibus comprehensam per ne. Ut augue minim iuvaret eos, mei falli timeam ceteros ut. Qui et choro ubique denique, vim ei accumsan deseruisse argumentum. Usu cu dicant scripta dignissim, usu omnis paulo pertinacia id. Eos verear mediocrem ut, scripta gubergren eu vis. Te eos melius praesent, his nobis nostro diceret id.</p>
					<img src="img/confirm-email.png" class="thumbnail" alt="" />
				</div><!--diDash-->

				<div class="control-group"  id="btnDivInit" style="display:none;margin-bottom:30px;">
				    <div class="controls right-form">
 					  <input type="button" id="btnClearJson" class="btn btn-primary" value="초기화"/>
				      <%
						if(!ssUserNo.equals("")){ 
					  %>
				      <input type="button" id="btnGetPplan" 	class="btn btn-primary" value="불러오기"/>
				      <%
						} 
					  %>
				    </div>
				</div>
				
				<div id="diSale" style="display:none">
					<h4 class="heading">예상매출액 계산하기<span></span></h4>
					<p>
					본 자료는 사업계획서 작성과 사업성분석을 위한 실습자료로 사용자들의 이해를 돕기 위해 교육용 목적으로 만든 것으로 <br>기업회계 기준에서 정하는 내용과 부분적으로 상이 할 수 있습니다.
					</p>
					<form class="form-horizontal" id="aform" name="aform">
					<input type="hidden" id="PLANF_ID"  	name="PLANF_ID" 	value="<%=param.getString("PLANF_ID")%>"/>
					<input type="hidden" id="PLANF_SALE"  	name="PLANF_SALE" 	value="<%=param.getString("PLANF_SALE")%>"/>


					
					  <div class="control-group">
					    <label class="control-label">업종선택</label>
					    <div class="controls">
							<select id="sector_se_code_m" name="sector_se_code_m">
							<%=CommboUtil.getComboStr(sectorComboStr, "CODE", "CODE_NM", "3" , "C")%>
							</select>				
					    <button class="btn btn-mini btn-primary" type="button" id="btnHelpBizCdS">?</button>
					    </div>
					  </div>
					  <div class="controls alert alert-info" id="dBtnHelpBizCdS" style="display:none">
						<button type="button" class="close" id="btnHelpBizCdH">×</button>
						<strong>업종선택</strong><br />
						제조업: 사업장에서 제품을 제조하는 업종<br>
						외식업: 음식, 음료, 제과 등을 판매하는 업종<br>
						소매업: 식품, 의료, 생활, 문구 등을 판매하는 업종<br>
						시설서비스업: 식품, 의류, 생활, 문구 등을 판매하는 업종(예: PC방, 헬스클럽등)<br>
						용역서비스업: 미용, 교육 등의 용역등을 제공 하는 서비스 업종(예: 미용실, 학원등)
					  </div>						  
					  
					  <div class="control-group">
					    <label class="control-label">영업일수</label>
					    <div class="controls">
							<select id="day_se_code_m" name="day_se_code_m">
							<%=CommboUtil.getComboStr(dayComboStr, "CODE", "CODE_NM", "25" , "C")%>
							</select>				
					    </div>
					  </div>

					  <div class="control-group">
					    <label class="control-label">월목표이익</label>
					    <div class="controls">
						<input type="text" class="numeric" id="inTargetPrice" name="inTargetPrice" placeholder="500만원" value=1000 style="padding-right: 2px;"> 만원
					    </div>
					  </div>

					</form>				
	
					<h5>매출계획</h5>
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
						</tbody>
				  	</table>
	
					<h4 class="heading" style="clear: both;">예상매출액<span></span></h4>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="33%">일 예상 매출액</th>
							<th width="33%">월 예상 매출액 <button class="btn btn-mini"type="button" id="btnMMenuS">상세보기</button></th>
							<th>1차년도 연 예상 매출액 <button class="btn btn-mini btn-info"type="button" id="btnYMenuS">상세보기</button></th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td style="text-align:right;"><span id="daySaleAmt"></span>원</td>
							<td style="text-align:right;"><span id="monthSaleAmt"></span>원</td>
							<td style="text-align:right;"><span id="yearSaleAmt"></span>원</td>
						</tr>
						</tbody>
				  	</table>
					<div id="mMenu" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnMMenuH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					
					<table id="mmenuTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="5%">No</th>
							<th><span id="sMMenuTitle">메뉴명</span></th>
							<th width="8%">구분</th>
							<th width="6%">1월</th>
							<th width="6%">2월</th>
							<th width="6%">3월</th>
							<th width="6%">4월</th>
							<th width="6%">5월</th>
							<th width="6%">6월</th>
							<th width="6%">7월</th>
							<th width="6%">8월</th>
							<th width="6%">9월</th>
							<th width="6%">10월</th>
							<th width="6%">11월</th>
							<th width="6%">12월</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td rowspan="2" colspan="2">합계</td>
							<td>판매수량</td>
							<td style="text-align:right;"><span id="sMmenuQty01Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty02Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty03Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty04Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty05Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty06Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty07Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty08Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty09Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty10Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty11Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuQty12Tot"></span></td>
						</tr>
						<tr>
							<td>판매금액</td>
							<td style="text-align:right;"><span id="sMmenuAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt03Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt04Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt05Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt06Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt07Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt08Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt09Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt10Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt11Tot"></span></td>
							<td style="text-align:right;"><span id="sMmenuAmt12Tot"></span></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					<div id="yMenu" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnYMenuH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					<table id="ymenuTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="5%">No</th>
							<th><span id="sYMenuTitle">메뉴명</span></th>
							<th width="10%">내용</th>
							<th width="20%">1차년도</th>
							<th width="20%">2차년도</th>
							<th width="20%">3차년도</th>
							<th width="10%">수정</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td rowspan="2" colspan="2">합계</td>
							<td>판매수량</td>
							<td style="text-align:right;"><span id="sYmenuQty01Tot"></span></td>
							<td style="text-align:right;"><span id="sYmenuQty02Tot"></span></td>
							<td style="text-align:right;"><span id="sYmenuQty03Tot"></span></td>
							<td rowspan="2"></td>
						</tr>
						<tr>
							<td>판매금액</td>
							<td style="text-align:right;"><span id="sYmenuAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sYmenuAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sYmenuAmt03Tot"></span></td>
						</tr>
						<tr>
							<td colspan="3">매출율 증가율</td>
							<td style="text-align:right;"></span></td>
							<td style="text-align:right;"><span id="sYmenuAmtRate02Tot" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td style="text-align:right;"><span id="sYmenuAmtRate03Tot" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td style="text-align:right;"></span></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					<div class="divider"></div>
				</div><!--diSale-->
				
				<div id="diCost" style="display:none">
					<h4 class="heading">재료비 계획<span></span></h4>
	
					<table id="costTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="5%">No</th>
							<th><span id="scostTitle">메뉴명</span></th>
							<th width="20%"><span id="scostPrice">메뉴가격</span></th>
							<th width="20%"><span id="scostQty">재료비 비율</span></th>
							<th width="20%"><span id="scostAmt">재료비 금액</span></th>
						</tr>
						</thead>
						<tbody>
						</tbody>
				  	</table>
					<h4 class="heading" style="clear: both;">예상재료비<span></span></h4>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="50%">월 예상 재료비 <button id="btnMCostS" class="btn btn-mini" type="button">상세보기</button></th>
							<th>1차년도 연 예상 재료비 <button id="btnYCostS" class="btn btn-mini btn-info" type="button">상세보기</button></th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td style="text-align:right;"><span id="monthCostAmt"></span>원</td>
							<td style="text-align:right;"><span id="yearCostAmt"></span>원</td>
						</tr>
						</tbody>
				  	</table>
					<div id="mCost" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnMCostH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					
					<table id="mCostTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="5%">No</th>
							<th><span id="sMCostTitle">메뉴명/재료비율</span></th>
							<th width="8%">구분</th>
							<th width="6%">1월</th>
							<th width="6%">2월</th>
							<th width="6%">3월</th>
							<th width="6%">4월</th>
							<th width="6%">5월</th>
							<th width="6%">6월</th>
							<th width="6%">7월</th>
							<th width="6%">8월</th>
							<th width="6%">9월</th>
							<th width="6%">10월</th>
							<th width="6%">11월</th>
							<th width="6%">12월</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td rowspan="2" colspan="2">합계</td>
							<td>판매금액</td>
							<td style="text-align:right;"><span id="sMCostMenuAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt03Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt04Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt05Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt06Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt07Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt08Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt09Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt10Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt11Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostMenuAmt12Tot"></span></td>
						</tr>
						<tr>
							<td>재료비</td>
							<td style="text-align:right;"><span id="sMCostAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt03Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt04Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt05Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt06Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt07Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt08Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt09Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt10Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt11Tot"></span></td>
							<td style="text-align:right;"><span id="sMCostAmt12Tot"></span></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					<div id="yCost" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnYCostH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					<table id="ycostTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="5%">No</th>
							<th><span id="sYCostTitle">메뉴명</span></th>
							<th width="15%">내용</th>
							<th width="15%">1차년도</th>
							<th width="15%">2차년도</th>
							<th width="15%">3차년도</th>
							<th width="10%">수정</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td rowspan="2" colspan="2">합계</td>
							<td>판매금액</td>
							<td style="text-align:right;"><span id="sYCostMentAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sYCostMentAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sYCostMentAmt03Tot"></span></td>
							<td rowspan="2"></td>
						</tr>
						<tr>
							<td>판매재료비</td>
							<td style="text-align:right;"><span id="sYCostAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sYCostAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sYCostAmt03Tot"></span></td>
						</tr>
						<tr>
							<td colspan="2">재료비 비율</td>
							<td></td>
							<td style="text-align:right;"><span id="sYCostMentAmt01Rate" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td style="text-align:right;"><span id="sYCostMentAmt02Rate" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td style="text-align:right;"><span id="sYCostMentAmt03Rate" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					<div class="divider"></div>
				</div><!--diCost-->

				<div id="diJob" style="display:none">
					<h4 class="heading">인건비 계획<span></span></h4>
					<div class="tabbable">
						<ul class="nav nav-tabs tabber">
							<li class="active"><a data-toggle="tab" href="#tabJob1">제조</a></li>
							<li class=""><a data-toggle="tab" href="#tabJob2">판매관리</a></li>
						</ul>
						<div class="tab-content">
							<div id="tabJob1" class="tab-pane active">
								<h5 class="heading">제조<span></span></h5>
								<br>
								<div class="control-group login-register">
								    <div class="controls">
								     	(금액:원)
								        <button id="btnJob01" class="btn btn-small" type="button" onClick="addJobPop('1')">+</button>
								    </div>
								</div>
				
								<table id="jobTable01" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>직위</th>
										<th width="20%">기준인원</th>
										<th width="20%">월 기준급여</th>
										<th width="20%">월 인건비</th>
										<th width="10%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="sJob01TotCnt">1</span>명</td>
										<td style="text-align:right;"><span id="sJob01TotQty">3,000,000</span>원</td>
										<td style="text-align:right;"><span id="sJob01TotAmt">3,000,000</span>원</td>
										<td></td>
									</tr>
									<tfoot>
							  	</table>
							</div><!-- tabJob1 -->
							<div id="tabJob2" class="tab-pane">
								<h5 class="heading">판매관리<span></span></h5>
								<br>
								<div class="control-group login-register">
								    <div class="controls">
								        (금액:원)
								        <button id="btnJob02" class="btn btn-small" type="button" onClick="addJobPop('2')">+</button>
								    </div>
								</div>
				
								<table id="jobTable02" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>직위</th>
										<th width="20%">기준인원</th>
										<th width="20%">월 기준급여</th>
										<th width="20%">월 인건비</th>
										<th width="10%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="sJob02TotCnt">1</span>명</td>
										<td style="text-align:right;"><span id="sJob02TotQty">3,000,000</span>원</td>
										<td style="text-align:right;"><span id="sJob02TotAmt">3,000,000</span>원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>

							</div><!-- tabJob2 -->
						</div><!-- tab-content -->
					</div><!--tabbable-->						
					<br>
					<h4 class="heading" style="clear: both;">예상인건비<span></span></h4>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="50%">월 예상 인건비 <button class="btn btn-mini"	type="button"  id="btnMJobS">상세보기</button></th>
							<th>1차년도 연 예상 인건비 <button class="btn btn-mini btn-info"		type="button"  id="btnYJobS">상세보기</button></th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td style="text-align:right;"><span id="monthJobAmt">6,000,000</span>원</td>
							<td style="text-align:right;"><span id="yearJobAmt">72,000,000</span>원</td>
						</tr>
						</tbody>
				  	</table>
					<div id="mJob" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnMJobH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					
					<table id="mJobTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="6%">구분</th>
							<th>직위(부분)</th>
							<th width="8%">인원</th>
							<th width="6%">1월</th>
							<th width="6%">2월</th>
							<th width="6%">3월</th>
							<th width="6%">4월</th>
							<th width="6%">5월</th>
							<th width="6%">6월</th>
							<th width="6%">7월</th>
							<th width="6%">8월</th>
							<th width="6%">9월</th>
							<th width="6%">10월</th>
							<th width="6%">11월</th>
							<th width="6%">12월</th>
						</tr>
						</thead>
						<tbody id="tbMjob01">
						</tbody>
						<tbody id="tbMjob01Tot">
						<tr>
							<td>제조</td>
							<td>소계</td>
							<td style="text-align:right;"><span id="sMJobCnt01Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt01Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt02Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt03Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt04Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt05Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt06Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt07Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt08Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt09Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt10Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt11Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt12Sub1Tot"></span></td>
						</tr>
						</tbody>
						<tbody id="tbMjob02">
						</tbody>
						<tbody id="tbMjob02Tot">
						<tr>
							<td>판매</td>
							<td>소계</td>
							<td style="text-align:right;"><span id="sMJobCnt01Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt01Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt02Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt03Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt04Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt05Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt06Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt07Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt08Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt09Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt10Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt11Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt12Sub2Tot"></span></td>
						</tr>
						</tbody>

						<tfoot>
						<tr>
							<td colspan="2">합계</td>
							<td style="text-align:right;"><span id="sMJobCnt012Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt03Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt04Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt05Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt06Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt07Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt08Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt09Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt10Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt11Tot"></span></td>
							<td style="text-align:right;"><span id="sMJobAmt12Tot"></span></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					<div id="yJob" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnYJobH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					<table id="yJobTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="5%">구분</th>
							<th>직위(부분)</th>
							<th width="10%">기준급여</th>
							<th width="5%">인원</th>
							<th width="15%">1차년도</th>
							<th width="15%">2차년도</th>
							<th width="15%">3차년도</th>
							<th width="10%">수정</th>
						</tr>
						</thead>
						<tbody id="tbYjob01">
						</tbody>
						<tbody id="tbYjob01Tot">
						<tr>
							<td>제조</td>
							<td colspan="2">소계</td>
							<td style="text-align:right;"><span id="sYJobCntSub1Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt01Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt02Sub1Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt03Sub1Tot"></span></td>
							<td></td>
						</tr>
						</tbody>

						<tbody id="tbYjob02">
						</tbody>
						<tbody id="tbYjob02Tot">
						<tr>
							<td>판매</td>
							<td colspan="2">소계</td>
							<td style="text-align:right;"><span id="sYJobCntSub2Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt01Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt02Sub2Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt03Sub2Tot"></span></td>
							<td></td>
						</tr>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="3">합계</td>
							<td style="text-align:right;"><span id="sYJobCntTot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt03Tot"></span></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="3">인건비 비율</td>
							<td style="text-align:right;"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt01TotRate" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td style="text-align:right;"><span id="sYJobAmt02TotRate" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td style="text-align:right;"><span id="sYJobAmt03TotRate" style="color:#f97e76;font-weight:bold;"></span>%</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="3">1인당매출액</td>
							<td style="text-align:right;"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt01Tot1Amt" style="color:#f97e76;font-weight:bold;"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt02Tot1Amt" style="color:#f97e76;font-weight:bold;"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt03Tot1Amt"  style="color:#f97e76;font-weight:bold;"></span></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="3">1인당인건비</td>
							<td style="text-align:right;"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt01TotJob1Amt" style="color:#f97e76;font-weight:bold;"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt02TotJob1Amt" style="color:#f97e76;font-weight:bold;"></span></td>
							<td style="text-align:right;"><span id="sYJobAmt03TotJob1Amt" style="color:#f97e76;font-weight:bold;"></span></td>
							<td></td>
						</tr>
						</tfoot>
				  	</table>
					</div>

					<div class="divider"></div>
				</div><!--diJob-->

				<div id="diInvest" style="display:none">
					<h4 class="heading">
					투자 계획<button class="btn btn-mini btn-primary" type="button" id="btnHelpInvestCdS">?</button>
					<span></span>
					</h4>
				    <div class="controls alert alert-info" id="dBtnHelpInvestCd" style="display:none">
						<button type="button" class="close" id="btnHelpInvestCdH">×</button>
						<strong>투자계획</strong><br />
						유형자산: 토지, 건뭏, 시설공사비, 차량운반구, 비품구입비, 기타유형자산<br>
						무형자산: 영업권(권리금), 산업재산권, 개발비, 가맹비, 기타무형자산<br>
						기타비유동자산: 임차보증금, 가맹보증금<br>
						기타투자비용: 초등상품(재료)비, 개업전급료, 개업전임차료, 개업전홍보비용, 개업전 지급수수료, 기타투자비용<br>
				   </div>						  
					<div class="tabbable">
						<ul class="nav nav-tabs tabber">
							<li class="active"><a data-toggle="tab" href="#tabInvest1">제조부분</a></li>
							<li class=""><a data-toggle="tab" href="#tabInvest2">판매부분</a></li>
						</ul>
						<div class="tab-content">
							<div id="tabInvest1" class="tab-pane active">
								<h5 class="heading">제조부분<span></span></h5>
								<br>
								<h5>유형자산</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest01" class="btn btn-small" type="button" onClick="addInvestPop('1','1')">+</button>
								    </div>
								</div>
				
								<table id="investTable01" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="20%">제조부문</th>
										<th width="20%">상각년수</th>
										<th width="20%">월제조비용</th>
										<th width="10%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest01TotPrice"></span>천원</td>
										<td style="text-align:right;"></td>
										<td style="text-align:right;"><span id="dInvest01TotAmt"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>
								<h5>무형자산</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest02" class="btn btn-small" type="button" onClick="addInvestPop('2','1')">+</button>
								    </div>
								</div>
				
								<table id="investTable03" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="20%">제조부문</th>
										<th width="20%">상각년수</th>
										<th width="20%">월제조비용</th>
										<th width="10%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest03TotPrice"></span>천원</td>
										<td style="text-align:right;"></td>
										<td style="text-align:right;"><span id="dInvest03TotAmt"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>
								<h5>기타비유동자산</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest03" class="btn btn-small" type="button" onClick="addInvestPop('3','1')">+</button>
								    </div>
								</div>
				
								<table id="investTable05" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="30%">제조부문</th>
										<th width="30%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest05TotPrice"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>
								<h5>기타투자비용</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest04" class="btn btn-small" type="button" onClick="addInvestPop('4','1')">+</button>
								    </div>
								</div>
				
								<table id="investTable07" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="30%">제조부문</th>
										<th width="30%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest07TotPrice"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>


							</div><!-- tabJob1 -->
							<div id="tabInvest2" class="tab-pane">
								<h5 class="heading">판매부분<span></span></h5>
								<br>
								<h5>유형자산</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest05" class="btn btn-small" type="button" onClick="addInvestPop('1','2')">+</button>
								    </div>
								</div>
								<table id="investTable02" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="20%">판매부문</th>
										<th width="20%">상각년수</th>
										<th width="20%">월판관비용</th>
										<th width="10%">수정삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest02TotPrice"></span>천원</td>
										<td style="text-align:right;"></td>
										<td style="text-align:right;"><span id="dInvest02TotAmt"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>

								<h5>무형자산</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest06" class="btn btn-small" type="button" onClick="addInvestPop('2','2')">+</button>
								    </div>
								</div>
								<table id="investTable04" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="20%">판매부문</th>
										<th width="20%">상각년수</th>
										<th width="20%">월판관비용</th>
										<th width="10%">수정삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest04TotPrice"></span>천원</td>
										<td style="text-align:right;"></td>
										<td style="text-align:right;"><span id="dInvest04TotAmt"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>

								<h5>기타비유동자산</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest07" class="btn btn-small" type="button" onClick="addInvestPop('3','2')">+</button>
								    </div>
								</div>
								<table id="investTable06" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="30%">판매부문</th>
										<th width="30%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest06TotPrice"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>

								<h5>기타투자비용</h5>
								<div class="control-group login-register">
								    <div class="controls">
								        <button id="btnInvest08" class="btn btn-small" type="button" onClick="addInvestPop('4','2')">+</button>
								    </div>
								</div>
								<table id="investTable08" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>투자내역</th>
										<th width="30%">판매부문</th>
										<th width="30%">수정/삭제</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									<tr>
										<td>합계</td>
										<td style="text-align:right;"><span id="dInvest08TotPrice"></span>천원</td>
										<td></td>
									</tr>
									</tfoot>
							  	</table>

							</div><!-- tabJob2 -->
						</div><!-- tab-content -->
					</div><!--tabbable-->						
					<br>
					<h4 class="heading" style="clear: both;">합계<span></span></h4>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="25%">제조부분</th>
							<th width="25%">판매부분</th>
							<th width="25%">월제조비용</th>
							<th>월판관비용 </th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td style="text-align:right;"><span id="innvest01TotAmt"></span>천원</td>
							<td style="text-align:right;"><span id="innvest02TotAmt"></span>천원</td>
							<td style="text-align:right;"><span id="innvest03TotAmt"></span>천원</td>
							<td style="text-align:right;"><span id="innvest04TotAmt"></span>천원</td>
						</tr>
						</tbody>
				  	</table>

					<div class="divider"></div>
				</div><!--diInvest-->
				
				<div id="diLoan" style="display:none">
					<h4 class="heading">자금조달 계획
					<span></span>
					</h4>
					<h5>자기자금</h5>

					<div class="control-group login-register">
					    <div class="controls">
					        <button id="btnLoan01" class="btn btn-small" type="button" onClick="addLoanPop('1')">+</button>
					    </div>
					</div>
	
					<table id="loanTable01" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>항목</th>
							<th width="20%">1차년도</th>
							<th width="20%">2차년도</th>
							<th width="20%">3차년도</th>
							<th width="13%">수정/삭제</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td>합계</td>
							<td style="text-align:right;"><span id="dLoan01TotYear1"></span>천원</td>
							<td style="text-align:right;"><span id="dLoan01TotYear2"></span>천원</td>
							<td style="text-align:right;"><span id="dLoan01TotYear3"></span>천원</td>
							<td></td>
						</tr>
						</tfoot>
				  	</table>


					<h5>타인자금</h5>

					<div class="control-group login-register">
					    <div class="controls">
					        <button id="btnLoan01" class="btn btn-small" type="button" onClick="addLoanPop('2')">+</button>
					    </div>
					</div>
	
					<table id="loanTable02" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="12%">항목</th>
							<th>내용</th>
							<th width="20%">1차년도</th>
							<th width="20%">2차년도</th>
							<th width="20%">3차년도</th>
							<th width="12%">수정/삭제</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td rowspan="2">합계</td>
							<td>금액</td>
							<td style="text-align:right;"><span id="dLoan02TotYear1"></span>천원</td>
							<td style="text-align:right;"><span id="dLoan02TotYear2"></span>천원</td>
							<td style="text-align:right;"><span id="dLoan02TotYear3"></span>천원</td>
							<td rowspan="2"></td>
						</tr>
						<tr>
							<td>년이자비용</td>
							<td style="text-align:right;"><span id="dLoan02TotYearRate1"></span>천원</td>
							<td style="text-align:right;"><span id="dLoan02TotYearRate2"></span>천원</td>
							<td style="text-align:right;"><span id="dLoan02TotYearRate3"></span>천원</td>
						</tr>
						</tfoot>
				  	</table>


					<h4 class="heading" style="clear: both;">합계<span></span></h4>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>합계</th>
							<th width="20%">1차년도</th>
							<th width="20%">2차년도</th>
							<th width="20%">3차년도</th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td></td>
							<td style="text-align:right;"><span id="loanTotYear1Amt"></span>천원</td>
							<td style="text-align:right;"><span id="loanTotYear2Amt"></span>천원</td>
							<td style="text-align:right;"><span id="loanTotYear3Amt"></span>천원</td>
						</tr>
						</tbody>
				  	</table>

					<div class="divider"></div>
				</div><!--diLoan-->

				<div id="diMCost" style="display:none">
					<h4 class="heading">제조경비 계획<span></span></h4>
					<h5></h5>
					<br>
					<div class="control-group login-register">
					    <div class="controls">
					     	단위(천원)
					        <button id="btnMCost01" class="btn btn-small" type="button" onClick="addMCostPop()">+</button>
					    </div>
					</div>
	
					<table id="MCostTable01" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>계정과목</th>
							<th width="13%">기준항목</th>
							<th width="20%">적용값</th>
							<th width="20%">적용금액</th>
							<th width="13%">수정/삭제</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
				  	</table>
					<h4 class="heading" style="clear: both;">예상제조경비<span></span></h4>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="50%">월 예상제조경비 <button id="btnMMCostS" class="btn btn-mini" type="button">상세보기</button></th>
							<th>1차년도 연 예상제조경비 <button id="btnMYCostS" class="btn btn-mini btn-info" type="button">상세보기</button></th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td style="text-align:right;"><span id="monthMCostAmt"></span>원</td>
							<td style="text-align:right;"><span id="yearMCostAmt"></span>원</td>
						</tr>
						</tbody>
				  	</table>
					<div id="mMCost" style="display:block">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnMMCostH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					
					<table id="mMCostTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>계정과목</th>
							<th width="6%">항목</th>
							<th width="8%">적용값</th>
							<th width="6%">1월</th>
							<th width="6%">2월</th>
							<th width="6%">3월</th>
							<th width="6%">4월</th>
							<th width="6%">5월</th>
							<th width="6%">6월</th>
							<th width="6%">7월</th>
							<th width="6%">8월</th>
							<th width="6%">9월</th>
							<th width="6%">10월</th>
							<th width="6%">11월</th>
							<th width="6%">12월</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="3">합계</td>
							<td style="text-align:right;"><span id="sMMCostAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt03Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt04Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt05Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt06Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt07Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt08Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt09Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt10Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt11Tot"></span></td>
							<td style="text-align:right;"><span id="sMMCostAmt12Tot"></span></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					<div id="yMCost" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnYMCostH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					<table id="yMCostTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>계정과목</th>
							<th width="10%">기준항목</th>
							<th width="10%">적용값</th>
							<th width="15%">1차년도</th>
							<th width="15%">2차년도</th>
							<th width="15%">3차년도</th>
							<th width="10%">수정</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="3">합계</td>
							<td style="text-align:right;"><span id="sYMCostAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sYMCostAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sYMCostAmt03Tot"></span></td>
							<td></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					
					<div class="divider"></div>
				</div><!--diMCost-->

				<div id="diSCost" style="display:none">
					<h4 class="heading">판매관리비 계획<span></span></h4>
					<div class="divider"></div>
					<h5></h5>
					<br>
					<div class="control-group login-register">
					    <div class="controls">
					     	단위(천원)
					        <button id="btnSCost01" class="btn btn-small" type="button" onClick="addSCostPop()">+</button>
					    </div>
					</div>
	
					<table id="SCostTable01" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>계정과목</th>
							<th width="13%">기준항목</th>
							<th width="20%">적용값</th>
							<th width="20%">적용금액</th>
							<th width="13%">수정/삭제</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
				  	</table>
					<h4 class="heading" style="clear: both;">예상 판매관리비<span></span></h4>
					<table class="table table-bordered table-striped">
						<thead>
						<tr>
							<th width="50%">월 예상판매관리비 <button id="btnMSCostS" class="btn btn-mini" type="button">상세보기</button></th>
							<th>1차년도 연 예상판매관리비 <button id="btnYSCostS" class="btn btn-mini btn-info" type="button">상세보기</button></th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td style="text-align:right;"><span id="monthSCostAmt"></span>원</td>
							<td style="text-align:right;"><span id="yearSCostAmt"></span>원</td>
						</tr>
						</tbody>
				  	</table>
					<div id="mSCost" style="display:block">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnMSCostH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					
					<table id="mSCostTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>계정과목</th>
							<th width="6%">항목</th>
							<th width="8%">적용값</th>
							<th width="6%">1월</th>
							<th width="6%">2월</th>
							<th width="6%">3월</th>
							<th width="6%">4월</th>
							<th width="6%">5월</th>
							<th width="6%">6월</th>
							<th width="6%">7월</th>
							<th width="6%">8월</th>
							<th width="6%">9월</th>
							<th width="6%">10월</th>
							<th width="6%">11월</th>
							<th width="6%">12월</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="3">합계</td>
							<td style="text-align:right;"><span id="sMSCostAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt03Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt04Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt05Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt06Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt07Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt08Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt09Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt10Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt11Tot"></span></td>
							<td style="text-align:right;"><span id="sMSCostAmt12Tot"></span></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
					<div id="ySCost" style="display:none">
					<div class="control-group login-register" style="margin-top: 1px;">
					    <div class="controls" >
					              단위(천원)
					        <button id="btnYSCostH" class="btn btn-small" type="button">숨기기</button>
					    </div>
					</div>
					<table id="ySCostTable" class="table table-bordered table-striped">
						<thead>
						<tr>
							<th>계정과목</th>
							<th width="10%">기준항목</th>
							<th width="10%">적용값</th>
							<th width="15%">1차년도</th>
							<th width="15%">2차년도</th>
							<th width="15%">3차년도</th>
							<th width="10%">수정</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="3">합계</td>
							<td style="text-align:right;"><span id="sYSCostAmt01Tot"></span></td>
							<td style="text-align:right;"><span id="sYSCostAmt02Tot"></span></td>
							<td style="text-align:right;"><span id="sYSCostAmt03Tot"></span></td>
							<td></td>
						</tr>
						</tfoot>
				  	</table>
					</div>
				</div><!--diSCost-->

				<div id="diPlan" style="display:none">
					<h4 class="heading">수익성 검토<span></span></h4>
					<div class="divider"></div>
					<div class="tabbable">
						<ul class="nav nav-tabs tabber">
							<li class="active"><a data-toggle="tab" href="#tab3">손익계산서</a></li>
							<li><a data-toggle="tab" href="#tab4">재무상태표</a></li>
							<li><a data-toggle="tab" href="#tab5">손익분기점</a></li>
							<li><a data-toggle="tab" href="#tab1">수익성 지표</a></li>
							<li><a data-toggle="tab" href="#tab2">Chart</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab3" class="tab-pane active">
								<h5 class="heading">손익계산서<span></span></h5>
								<br>
								<div class="login-register">
								    <div class="controls">
								        (금액:천원)
								    </div>
								</div>
								<table id="totalTable03" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>계정항목</th>
										<th width="25%">1차년도</th>
										<th width="25%">2차년도</th>
										<th width="25%">3차년도</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td>매출액</td>
										<td style="text-align:right;"><span id="sInCome1Year1"></span></td>
										<td style="text-align:right;"><span id="sInCome1Year2"></span></td>
										<td style="text-align:right;"><span id="sInCome1Year3"></span></td>
									</tr>
									<tr>
										<td>매출원가</td>
										<td style="text-align:right;"><span id="sInCome2Year1"></span></td>
										<td style="text-align:right;"><span id="sInCome2Year2"></span></td>
										<td style="text-align:right;"><span id="sInCome2Year3"></span></td>
									</tr>
									<tr>
										<td>매출총이익</td>
										<td style="text-align:right;"><span id="sInCome3Year1"></span></td>
										<td style="text-align:right;"><span id="sInCome3Year2"></span></td>
										<td style="text-align:right;"><span id="sInCome3Year3"></span></td>
									</tr>
									<tr>
										<td>매출액총이익율</td>
										<td style="text-align:right;"><span id="sInCome4Year1"></span>%</td>
										<td style="text-align:right;"><span id="sInCome4Year2"></span>%</td>
										<td style="text-align:right;"><span id="sInCome4Year3"></span>%</td>
									</tr>
									<tr>
										<td>판매관리비</td>
										<td style="text-align:right;"><span id="sInCome5Year1"></span></td>
										<td style="text-align:right;"><span id="sInCome5Year2"></span></td>
										<td style="text-align:right;"><span id="sInCome5Year3"></span></td>
									</tr>
									<tr>
										<td>영업이익</td>
										<td style="text-align:right;"><span id="sInCome6Year1"></span></td>
										<td style="text-align:right;"><span id="sInCome6Year2"></span></td>
										<td style="text-align:right;"><span id="sInCome6Year3"></span></td>
									</tr>
									<tr>
										<td>매출액 영업이익률</td>
										<td style="text-align:right;"><span id="sInCome7Year1"></span>%</td>
										<td style="text-align:right;"><span id="sInCome7Year2"></span>%</td>
										<td style="text-align:right;"><span id="sInCome7Year3"></span>%</td>
									</tr>
									<tr>
										<td>영업외비용</td>
										<td style="text-align:right;"><span id="sInCome8Year1"></span></td>
										<td style="text-align:right;"><span id="sInCome8Year2"></span></td>
										<td style="text-align:right;"><span id="sInCome8Year3"></span></td>
									</tr>
									<tr>
										<td>경상이익</td>
										<td style="text-align:right;"><span id="sInCome9Year1"></span></td>
										<td style="text-align:right;"><span id="sInCome9Year2"></span></td>
										<td style="text-align:right;"><span id="sInCome9Year3"></span></td>
									</tr>
									<tr>
										<td>매출액 경상이익률</td>
										<td style="text-align:right;"><span id="sInCome10Year1"></span>%</td>
										<td style="text-align:right;"><span id="sInCome10Year2"></span>%</td>
										<td style="text-align:right;"><span id="sInCome10Year3"></span>%</td>
									</tr>
									</tbody>
								</table>	
							</div><!-- tab4 -->
							<div id="tab4" class="tab-pane">
								<h5 class="heading">재무상태표<span></span></h5>
								<br>
								<div class="login-register">
								    <div class="controls">
								        (금액:천원)
								    </div>
								</div>
								<table id="totalTable03" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th width="9%">구분</th>
										<th>계정항목</th>
										<th width="22%">1차년도</th>
										<th width="22%">2차년도</th>
										<th width="22%">3차년도</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td rowspan="3">자산</td>
										<td>유동자산</td>
										<td style="text-align:right;"><span id="sFinancial1Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancial1Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancial1Year3"></span></td>
									</tr>
									<tr>
										<td>비유동자산</td>
										<td style="text-align:right;"><span id="sFinancial2Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancial2Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancial2Year3"></span></td>
									</tr>
									<tr>
										<td >자산총계</td>
										<td style="text-align:right;"><span id="sFinancialT1Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancialT1Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancialT1Year3"></span></td>
									</tr>
									<tr>
										<td>부채</td>
										<td>비유동부채</td>
										<td style="text-align:right;"><span id="sFinancial3Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancial3Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancial3Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="3">자본</td>
										<td>자본금</td>
										<td style="text-align:right;"><span id="sFinancial4Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancial4Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancial4Year3"></span></td>
									</tr>
									<tr>
										<td>이익잉여금</td>
										<td style="text-align:right;"><span id="sFinancial5Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancial5Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancial5Year3"></span></td>
									</tr>
									<tr>
										<td>자본총계</td>
										<td style="text-align:right;"><span id="sFinancialT2Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancialT2Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancialT2Year3"></span></td>
									</tr>
									<tr>
										<td colspan="2">부채와자본 총계</td>
										<td style="text-align:right;"><span id="sFinancialT3Year1"></span></td>
										<td style="text-align:right;"><span id="sFinancialT3Year2"></span></td>
										<td style="text-align:right;"><span id="sFinancialT3Year3"></span></td>
									</tr>
									</tbody>
								</table>	
							</div><!-- tab5 -->
							<div id="tab5" class="tab-pane">
								<h5 class="heading">손익분기점<span></span></h5>
								<br>
								<div class="login-register">
								    <div class="controls">
								        (금액:천원)
								    </div>
								</div>
								<table id="totalTable03" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th width="15%">구분</th>
										<th>산출식</th>
										<th width="22%">1차년도</th>
										<th width="22%">2차년도</th>
										<th width="22%">3차년도</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td>매출액</td>
										<td>①</td>
										<td style="text-align:right;"><span id="sBreakPoint1Year1"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint1Year2"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint1Year3"></span></td>
									</tr>
									<tr>
										<td>변동비</td>
										<td>②</td>
										<td style="text-align:right;"><span id="sBreakPoint2Year1"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint2Year2"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint2Year3"></span></td>
									</tr>
									<tr>
										<td >한계이익</td>
										<td >③ = ① - ②</td>
										<td style="text-align:right;"><span id="sBreakPoint3Year1"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint3Year2"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint3Year3"></span></td>
									</tr>
									<tr>
										<td>한계이익률</td>
										<td>④ = ③ / ①</td>
										<td style="text-align:right;"><span id="sBreakPoint4Year1"></span>%</td>
										<td style="text-align:right;"><span id="sBreakPoint4Year2"></span>%</td>
										<td style="text-align:right;"><span id="sBreakPoint4Year3"></span>%</td>
									</tr>
									<tr>
										<td>고정비</td>
										<td>⑤</td>
										<td style="text-align:right;"><span id="sBreakPoint5Year1"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint5Year2"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint5Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="2">손익분기점</td>
										<td>⑥ = ⑤ / ④</td>
										<td style="text-align:right;"><span id="sBreakPoint6Year1"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint6Year2"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint6Year3"></span></td>
									</tr>
									<tr>
										<td></td>
										<td style="text-align:right;"><span id="sBreakPoint7Year1"></span>%</td>
										<td style="text-align:right;"><span id="sBreakPoint7Year2"></span>%</td>
										<td style="text-align:right;"><span id="sBreakPoint7Year3"></span>%</td>
									</tr>
									<tr>
										<td>목표이익</td>
										<td>⑦</td>
										<td style="text-align:right;"><span id="sBreakPoint8Year1"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint8Year2"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint8Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="2">목표매출액</td>
										<td>⑧ = (⑤ + ⑦) / ④</td>
										<td style="text-align:right;"><span id="sBreakPoint9Year1"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint9Year2"></span></td>
										<td style="text-align:right;"><span id="sBreakPoint9Year3"></span></td>
									</tr>
									<tr>
										<td></td>
										<td style="text-align:right;"><span id="sBreakPoint10Year1"></span>%</td>
										<td style="text-align:right;"><span id="sBreakPoint10Year2"></span>%</td>
										<td style="text-align:right;"><span id="sBreakPoint10Year3"></span>%</td>
									</tr>
									</tbody>
								</table>	
							</div><!-- tab5 -->
							<div id="tab1" class="tab-pane">
								<h5 class="heading">수익성 지표<span></span></h5>
								<br>
								<div class="login-register">
								    <div class="controls">
								        (금액:천원)
								    </div>
								</div>
								<table id="totalTable01" class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>구분</th>
										<th width="12%">내용</th>
										<th width="12%">1차년도</th>
										<th width="12%">검토의견</th>
										<th width="12%">2차년도</th>
										<th width="12%">검토의견</th>
										<th width="12%">3차년도</th>
										<th width="12%">검토의견</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td rowspan="3">1.투자금액회전율</td>
										<td>산출값</td>
										<td style="text-align:right;"><span id="sBiz1C1Year1"></span>회전</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz1C2Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz1C1Year2"></span>회전</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz1C2Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz1C1Year3"></span>회전</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz1C2Year3"></span></td>						
									</tr>
									<tr>
										<td>매출액</td>
										<td style="text-align:right;"><span id="sBiz1C3Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz1C3Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz1C3Year3"></span></td>
									</tr>
									<tr>
										<td>투자금액</td>
										<td style="text-align:right;"><span id="sBiz1C4Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz1C4Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz1C4Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="3">2.매출액영업이익률</td>
										<td>산출값</td>
										<td style="text-align:right;"><span id="sBiz2C1Year1"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz2C2Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz2C1Year2"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz2C2Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz2C1Year3"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz2C2Year3"></span></td>						
									</tr>
									<tr>
										<td>매출액</td>
										<td style="text-align:right;"><span id="sBiz2C3Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz2C3Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz2C3Year3"></span></td>
									</tr>
									<tr>
										<td>영억이익</td>
										<td style="text-align:right;"><span id="sBiz2C4Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz2C4Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz2C4Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="3">3.총자산영업이익률</td>
										<td>산출값</td>
										<td style="text-align:right;"><span id="sBiz3C1Year1"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz3C2Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz3C1Year2"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz3C2Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz3C1Year3"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz3C2Year3"></span></td>						
									</tr>
									<tr>
										<td>투자금액</td>
										<td style="text-align:right;"><span id="sBiz3C3Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz3C3Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz3C3Year3"></span></td>
									</tr>
									<tr>
										<td>영억이익</td>
										<td style="text-align:right;"><span id="sBiz3C4Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz3C4Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz3C4Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="3">4.인건비비율</td>
										<td>산출값</td>
										<td style="text-align:right;"><span id="sBiz4C1Year1"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz4C2Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz4C1Year2"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz4C2Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz4C1Year3"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz4C2Year3"></span></td>						
									</tr>
									<tr>
										<td>매출액</td>
										<td style="text-align:right;"><span id="sBiz4C3Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz4C3Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz4C3Year3"></span></td>
									</tr>
									<tr>
										<td>인건비</td>
										<td style="text-align:right;"><span id="sBiz4C4Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz4C4Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz4C4Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="3">5.임차율비율</td>
										<td>산출값</td>
										<td style="text-align:right;"><span id="sBiz5C1Year1"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz5C2Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz5C1Year2"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz5C2Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz5C1Year3"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz5C2Year3"></span></td>						
									</tr>
									<tr>
										<td>월매출액</td>
										<td style="text-align:right;"><span id="sBiz5C3Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz5C3Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz5C3Year3"></span></td>
									</tr>
									<tr>
										<td>월임차료</td>
										<td style="text-align:right;"><span id="sBiz5C4Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz5C4Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz5C4Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="3">6.자기자금비율</td>
										<td>산출값</td>
										<td style="text-align:right;"><span id="sBiz6C1Year1"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz6C2Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz6C1Year2"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz6C2Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz6C1Year3"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz6C2Year3"></span></td>						
									</tr>
									<tr>
										<td>총자산</td>
										<td style="text-align:right;"><span id="sBiz6C3Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz6C3Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz6C3Year3"></span></td>
									</tr>
									<tr>
										<td>자기자본</td>
										<td style="text-align:right;"><span id="sBiz6C4Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz6C4Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz6C4Year3"></span></td>
									</tr>
									<tr>
										<td rowspan="3">7.손익분기점률</td>
										<td>산출값</td>
										<td style="text-align:right;"><span id="sBiz7C1Year1"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz7C2Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz7C1Year2"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz7C2Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz7C1Year3"></span>%</td>
										<td rowspan="3" style="text-align:right;"><span id="sBiz7C2Year3"></span></td>						
									</tr>
									<tr>
										<td>년매출액</td>
										<td style="text-align:right;"><span id="sBiz7C3Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz7C3Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz7C3Year3"></span></td>
									</tr>
									<tr>
										<td>손익분기점</td>
										<td style="text-align:right;"><span id="sBiz7C4Year1"></span></td>
										<td style="text-align:right;"><span id="sBiz7C4Year2"></span></td>
										<td style="text-align:right;"><span id="sBiz7C4Year3"></span></td>
									</tr>
									</tbody>
							  	</table>
							</div><!-- tab1 -->
							<div id="tab2" class="tab-pane">
								<h5 class="heading">Chart<span></span></h5>
								<canvas id="myChart" width="300" height="300">
							</div><!-- tab2 -->
						</div><!-- tab-content -->
					</div><!--tabbable-->						
				</div><!--diPlan-->

				<div class="control-group" id="btnDivSave" style="display:none">
				    <div class="controls right-form">
				      <%
						if(!ssUserNo.equals("")){ 
					  %>
				      <input type="button" id="btnMenuSave" 	class="btn btn-primary" value="저장"/>
				      <%
						} 
					  %>
				    </div>
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
	<script type="text/javascript" src="/common/js/common.js?version=7.52"></script>
	<script>
	  $(document).ready(function() {
	    // 모달이 나타날 때 이벤트 핸들러 추가
	    $('#myLoginModal').on('show.bs.modal', function (e) {
	    	document.getElementById('popUserId').value = "";
	    	document.getElementById('popUserPw').value = "";
	    });
	  });
	</script>
	
	<script type="text/javascript">
	    let invest1ComboStr="<%=CommboUtil.getComboStr(invest1ComboStr, "CODE", "CODE_NM", "" , "C")%>";
	    let invest2ComboStr="<%=CommboUtil.getComboStr(invest2ComboStr, "CODE", "CODE_NM", "" , "C")%>";
	    let invest3ComboStr="<%=CommboUtil.getComboStr(invest3ComboStr, "CODE", "CODE_NM", "" , "C")%>";
	    let invest4ComboStr="<%=CommboUtil.getComboStr(invest4ComboStr, "CODE", "CODE_NM", "" , "C")%>";
	    let loan1ComboStr="<%=CommboUtil.getComboStr(loan1ComboStr, "CODE", "CODE_NM", "" , "C")%>";
	    let loan2ComboStr="<%=CommboUtil.getComboStr(loan2ComboStr, "CODE", "CODE_NM", "" , "C")%>";
	    
	    let mCostJson	=  JSON.parse('<%=mCostStr%>');
	    let sCostJson	=  JSON.parse('<%=sCostStr%>');
	    let invest1Json	=  JSON.parse('<%=invest1Str%>');
	    let invest2Json	=  JSON.parse('<%=invest2Str%>');

		 var ctx = document.getElementById('myChart');
			
		 let chartDataYear1 = [100, 59, 90, 81, 55, 40, 100];
		 let chartDataYear2 = [28, 48, 40, 19, 96, 100, 50];
		 let chartDataYear3 = [50, 100, 27, 96, 19, 40, 28];
		 
		 let data = {
				  labels: [
				    '투자금액회전율',
				    '매출액영업이익율',
				    '총자산영업이익율',
				    '인건비비율',
				    '임자료 비율',
				    '자기자금비율',
				    '손익분기점률'
				  ],
				  datasets: [{
					    label: '1차년도',
					    data: chartDataYear1,
					    fill: true,
					    backgroundColor: 'rgba(255, 99, 132, 0.2)',
					    borderColor: 'rgb(255, 99, 132)',
					    pointBackgroundColor: 'rgb(255, 99, 132)',
					    pointBorderColor: '#fff',
					    pointHoverBackgroundColor: '#fff',
					    pointHoverBorderColor: 'rgb(255, 99, 132)'
				   }
				  ,{
					    label: '2차년도',
					    data: chartDataYear2,
					    fill: true,
					    backgroundColor: 'rgba(54, 162, 235, 0.2)',
					    borderColor: 'rgb(54, 162, 235)',
					    pointBackgroundColor: 'rgb(54, 162, 235)',
					    pointBorderColor: '#fff',
					    pointHoverBackgroundColor: '#fff',
					    pointHoverBorderColor: 'rgb(54, 162, 235)'
						}
				  ,{
					    label: '3차년도',
					    data: chartDataYear3,
					    fill: true,
					    backgroundColor: 'rgba(153, 102, 255, 0.2)',
					    borderColor: 'rgb(153, 102, 255)',
					    pointBackgroundColor: 'rgb(153, 102, 255)',
					    pointBorderColor: '#fff',
					    pointHoverBackgroundColor: '#fff',
					    pointHoverBorderColor: 'rgb(153, 102, 255)'
						}
				  ]
		 };
		 var chartOptions = {
		     elements: {
			      line: {
			        borderWidth: 3
			      }
			  },
			  scales: {
				    r: {
	                   pointLabels:{
	                       font: {
	                           size: 14,
	                       }
	                   }
	               }				    
				  },
			  plugins: {
		            legend: {
		                labels: {
		                    font: {
		                        size: 15
		                    }
		                }
		            }
		        }			  
		};
	
			
	  const config = {
			  type: 'radar',
			  data: data,
			  options: chartOptions
	  };		 
	  var myChart = new Chart(ctx, config);

	 <%
			if(!ssUserNo.equals("")){ 
	  %>
	  document.getElementById('btnMenuSave').addEventListener('click',function(e){
		document.getElementById('inputPlanTitle').value = "<%=planFMap.getString("PLANF_TITLE")%>";
		console.log("*****btnMenuSave*******");
		$('#mySaveModal').modal('toggle');
	  });
	 <%
			} 
	  %>
	  
	</script>
	<script type="text/javascript" src="/common/js/home.js?version=2.63"></script>

	<script>
         let PLANF_ID   ="<%=param.getString("PLANF_ID")%>";
         let PLANF_SALE ="";

		 let PLANF_SALE_JSON = new Object();
         let SALE;
         let COST;
         let JOB;
         let INVEST;
         let LOAN;
         let MCOST;
         let SCOST;

         SALE 	= new Object();
         COST 	= new Object();
         JOB  	= new Object();
         INVEST = new Object();
         LOAN  	= new Object();
         MCOST  = new Object();
         SCOST  = new Object();
         
         if(""==PLANF_ID){
	         industryCd   = "3";
			 workDay      = 25;	
			 monTargetPrice="";	
			 PLANF_SALE    ="";
	         PLANF_SALE_JSON.SALE = {"menu":[
										 {"MenuTitle":"A메뉴","MenuPrice":20000,"MenuQty":30}
										,{"MenuTitle":"B메뉴","MenuPrice":7000,"MenuQty":40}
										,{"MenuTitle":"C메뉴","MenuPrice":3000,"MenuQty":50}
										,{"MenuTitle":"D메뉴","MenuPrice":1000,"MenuQty":50}
									]
						     ,"menuM":[
										 {"MMenuQty":[750,750,750,750,750,750,750,750,750,750,750,750]
										 ,"MMenuAmt":[15000,15000,15000,15000,15000,15000,15000,15000,15000,15000,15000,15000]}
										,{"MMenuQty":[1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000]
										 ,"MMenuAmt":[7000,7000,7000,7000,7000,7000,7000,7000,7000,7000,7000,7000]}
										,{"MMenuQty":[1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250]
										 ,"MMenuAmt":[3750,3750,3750,3750,3750,3750,3750,3750,3750,3750,3750,3750]}
										,{"MMenuQty":[1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250]
										 ,"MMenuAmt":[1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250,1250]}
									]
						      ,"menuY":[
										 {"YMenuQty":[9000,9000,9000]
										 ,"YMenuPrice":[20000,20000,20000]
										 ,"YMenuAmt":[180000,180000,180000]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":0
										 ,"YMenuPriceRate":0}
										,{"YMenuQty":[12000,12000,12000]
										 ,"YMenuPrice":[7000,7000,7000]
										 ,"YMenuAmt":[84000,84000,84000]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":0
										 ,"YMenuPriceRate":0}
										,{"YMenuQty":[15000,15000,15000]
										 ,"YMenuPrice":[3000,3000,3000]
										 ,"YMenuAmt":[45000,45000,45000]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":0
										 ,"YMenuPriceRate":0}
										,{"YMenuQty":[15000,15000,15000]
										 ,"YMenuPrice":[1000,1000,1000]
										 ,"YMenuAmt":[15000,15000,15000]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":0
										 ,"YMenuPriceRate":0}
									]
					   };
	
	         PLANF_SALE_JSON.COST = {"cost":[
									 {"costRate":40}
									,{"costRate":40}
									,{"costRate":30}
									,{"costRate":30}
									]
							,"costM":[
								 	 {"MCostAmt":[6000,6000,6000,6000,6000,6000,6000,6000,6000,6000,6000,6000]}
									,{"MCostAmt":[2800,2800,2800,2800,2800,2800,2800,2800,2800,2800,2800,2800]}
									,{"MCostAmt":[1125,1125,1125,1125,1125,1125,1125,1125,1125,1125,1125,1125]}
									,{"MCostAmt":[375,375,375,375,375,375,375,375,375,375,375,375]}
									 ]
							,"costY":[
									 {"YCostAmt":[72000,72000,72000]
									 ,"YCostRate":[40,40,40]
						     		 ,"YCostCd":"1"
							   		 ,"YCostAmtRate":0	
									 }
									,{"YCostAmt":[33600,33600,33600]
									 ,"YCostRate":[40,40,40]
									 ,"YCostCd":"1"
							   		 ,"YCostAmtRate":0	
									}
									,{"YCostAmt":[13500,13500,13500]
									 ,"YCostRate":[30,30,30]
									 ,"YCostCd":"1"
							   		 ,"YCostAmtRate":0	
									}
									,{"YCostAmt":[4500,4500,4500]
									 ,"YCostRate":[30,30,30]
									 ,"YCostCd":"1"
							   		 ,"YCostAmtRate":0	
									}
								   ]
							};
	         PLANF_SALE_JSON.JOB = {"job01":[]
							,"job02":[
	 								  {"JobTitle":"A담당","JobCnt":"1","JobQty":3000000}
									 ,{"JobTitle":"B담당","JobCnt":"1","JobQty":1500000}
									 ,{"JobTitle":"C담당","JobCnt":"1","JobQty":800000}
									 ]
							,"job01M":[]
							,"job02M":[
									 	  {"MJobAmt":[3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000]}
									 	 ,{"MJobAmt":[1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500]}
									 	 ,{"MJobAmt":[800,800,800,800,800,800,800,800,800,800,800,800]}
									 ]
							,"job01Y":[]
							,"job02Y":[
										 {"YJobAmt":[36000,36000,36000]
										 ,"YJobRateAmt":[3000000,3000000,3000000]
										 ,"YJobAmtCd":"1"
								   		 ,"YJobAmtRate":0
										 }
										 ,{"YJobAmt":[18000,18000,18000]
										 ,"YJobRateAmt":[1500000,1500000,1500000]
										 ,"YJobAmtCd":"1"
								   		 ,"YJobAmtRate":0
										 }
										 ,{"YJobAmt":[9600,9600,9600]
										 ,"YJobRateAmt":[800000,800000,800000]
										 ,"YJobAmtCd":"1"
								   		 ,"YJobAmtRate":0
										 }
									   ]
							};
	         PLANF_SALE_JSON.INVEST = {"invest01":[]
							,"invest02":[
							  			  {"investTitle":"시설공사비","investTitleCd":"30","investPrice":40000,"investYear":5,"investYearAmt":667,"investYn":'Y'}
							  			 ,{"investTitle":"비품구입비","investTitleCd":"60","investPrice":16000,"investYear":5,"investYearAmt":267,"investYn":'Y'}
							  			 ,{"investTitle":"기타 유형자산","investTitleCd":"70","investPrice":2000,"investYear":5,"investYearAmt":33,"investYn":'Y'}
							 		   ]
							,"invest03":[]
							,"invest04":[
							  			 {"investTitle":"영업권(권리금)","investTitleCd":"10","investPrice":50000,"investYear":0,"investYearAmt":0,"investYn":'N'}
							 		   ]
							,"invest05":[]
							,"invest06":[
								   		 {"investTitle":"임차보증금","investTitleCd":"10","investPrice":50000,"investYn":'N'}
							     	   ]
							,"invest07":[]
							,"invest08":[
								   		 {"investTitle":"개업전 홍보비용","investTitleCd":"40","investPrice":2000,"investYn":'N'}
							     	   ]
							};
	
	         PLANF_SALE_JSON.LOAN = {"loan01":[
										 {"loanTitle":"자본금","loanTitleCd":"10","loanYear":[100000]}
								   	]
									,"loan02":[
									  			 {"loanTitle":"소상공인융자","loanTitleCd":"10","loanYear":[50000,0,0],"loanRate":[3.0,3.0,3.0],"loanRateAmt":[1500,1500,1500]}
									  			 ,{"loanTitle":"은행융자","loanTitleCd":"20","loanYear":[10000,0,0],"loanRate":[6.0,6.0,6.0],"loanRateAmt":[600,600,600]}
									 		 ]
									};
	
	         PLANF_SALE_JSON.MCOST = {"MCost":[
										 {"MCostTitle":"전력비","MCostTitleCd":"1","MCostCd":"매출액","MCostTVa":"10","MCostTVaLabel":"%","MCostTVaAmt":2700}
								    ]
									,"MCostM":[
											 	 {"MMCostAmt":[2700,2700,2700,2700,2700,2700,2700,2700,2700,2700,2700,2700]}
											 ]
									,"MCostY":[
												 {"YMCostAmtYear":["32400","32400","32400"]
												 ,"YMCostAmtCd":"1"
									   		 	 ,"YMCostAmtRate":10
									   			 
												 }
											   ]
									};
	         PLANF_SALE_JSON.SCOST = {"SCost":[
										 			{"SCostTitle":"지급임차료","SCostTitleCd":"12","SCostCd":"월정액","SCostTVa":2700,"SCostTVaLabel":"천원","SCostTVaAmt":2700}
											    ]
									,"SCostM":[
											 	 {"MSCostAmt":[2700,2700,2700,2700,2700,2700,2700,2700,2700,2700,2700,2700]}
											 ]
									,"SCostY":[
												 {"YSCostAmtYear":[32400,32400,32400]
												 ,"YSCostAmtCd":"1"
									   		 	 ,"YSCostAmtRate":0
									   			 
												 }
											   ]
										};
         } else {
	         industryCd   = "<%=planFMap.getString("INDUSTRY_CD")%>";
			 workDay      = "<%=planFMap.getString("WORK_DAY_CNT")%>";	
			 monTargetPrice="<%=planFMap.getString("MON_TARGET_PROFIT")%>";	
			 PLANF_SALE    ='<%=planFMap.getString("PLANF_SALE")%>';
			 PLANF_SALE_JSON = JSON.parse(PLANF_SALE);
         }

 		//년도별 매출 가져오기
 		let aMenuYamtArrFn = function fnGetMenuYAmt(){
 			let returnArr = [];
 			
 			let aMenuY = PLANF_SALE_JSON.SALE.menuY;
 			
 			if(aMenuY.length < 1){
 				returnArr[0]=0;
 				returnArr[1]=0;
 				returnArr[2]=0;
 				return returnArr;
 			}
 			
 			let iYMenuAmt1 = aMenuY.map(function(yMenu) { 
 				let iReturn =0;
 				return getNumber(yMenu.YMenuAmt[0]); 
 			}).reduce((pre, val) => pre + val);

 			let iYMenuAmt2 = aMenuY.map(function(yMenu) { 
 				let iReturn =0;
 				return getNumber(yMenu.YMenuAmt[1]); 
 			}).reduce((pre, val) => pre + val);

 			let iYMenuAmt3 = aMenuY.map(function(yMenu) { 
 				let iReturn =0;
 				return getNumber(yMenu.YMenuAmt[2]); 
 			}).reduce((pre, val) => pre + val);
 			
 			returnArr[0] = iYMenuAmt1;
 			returnArr[1] = iYMenuAmt2;
 			returnArr[2] = iYMenuAmt3;
 			return returnArr;
 			
 		};

		//월 매출액,월인건비 
		//사용볍 aItemAmtMon(PLANF_SALE_JSON.SALE.menuM,'MMenuAmt')
		let aItemAmtMon = function fnGetMenuMAmt(pObject,pStr){
			let returnAmt = 0;
 			
 			if(pObject == null || pObject.length==0){
 				return returnAmt;
 			}
 			
 			let iItemAmt = pObject.map(function(item) { 
 				let iReturn =0;
 				return getNumber(item[pStr][0]); 
 			}).reduce((pre, val) => pre + val);
 			
 			returnAmt = iItemAmt;
 			return returnAmt;
 		};

 		//제조비용,판관비용
		let aInvestUseAmt = function fnGetInvestUseAmt(pCd){
			let returnAmt = 0;

			let returnAmt1 = 0;
			let returnAmt2 = 0;
			let returnAmt3 = 0;
			let returnAmt4 = 0;

			let lObject = PLANF_SALE_JSON.INVEST;
 			
 			if(lObject == null ){
 				return returnAmt;
 			}

 			//재조비용 합계
 			if(pCd==1) {
 	 			if (lObject.invest01.length >0) {
		 			returnAmt1 = lObject.invest01.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}
 	 			if (lObject.invest03.length >0) {
	 	 			returnAmt2 = lObject.invest03.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}	
 	 			if (lObject.invest05.length >0) {
		 			returnAmt3 = lObject.invest05.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}	

 	 			if (lObject.invest07.length >0) {
		 			returnAmt4 = lObject.invest07.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}
 	 			
 			} else {
 	 			if (lObject.invest02.length >0) {
		 			returnAmt1 = lObject.invest02.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}	
 	 			if (lObject.invest04.length >0) {
		 			returnAmt2 = lObject.invest04.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}
 	 			if (lObject.invest06.length >0) {
		 			returnAmt3 = lObject.invest06.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}
 	 			if (lObject.invest08.length >0) {
		 			returnAmt4 = lObject.invest08.map(function(item) { 
		 				let iReturn =0;
		 				return getNumber(item.investYearAmt); 
		 			}).reduce((pre, val) => pre + val);
 	 			}
 	 	 	}
 			
 			returnAmt = returnAmt1+returnAmt2+returnAmt3+returnAmt4;
 			
 			return returnAmt;
 		};

 		
		initUpdate(industryCd,workDay,monTargetPrice);


	</script>
	
	</body>
	
</html>


