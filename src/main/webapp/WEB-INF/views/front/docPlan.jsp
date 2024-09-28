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

<jsp:useBean id="typeBizComboStr"    type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="cateBizComboStr"    type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" 			class="com.whomade.kycarrots.framework.common.object.DataMap" 			scope="request"/>

<%
	System.out.println(param.getString("bizComboStr"));
 
%>
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
				<h3>사업계획수립</h3>
				<p>
				간단한 선택으로 사업계획서를 작성해 보세요.
				</p>
			</div>
		  </div>
		</div>
	</section>
	<!-- End page header  -->
	
	<%@ include file="/common/front/loginHtml.jspf" %>
	<div id="app">
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
		  	<div class="span2">
				<div class="navaside">
					<ul class="nav menunav">
						<li :class="{active:slideIndex==1}" style="cursor: pointer;"><a @click="slideClick(1)"><i class="icon-pencil"></i> 기본정보선택</a></li>
						<li :class="{active:slideIndex==2}" style="cursor: pointer;"><a @click="slideClick(2)"><i class="icon-bullhorn"></i> 사업계왹 요약항목</a></li>
						<li :class="{active:slideIndex==3}" style="cursor: pointer;"><a @click="slideClick(3)"><i class="icon-calendar"></i> 사업계획 준비검토</a></li>
					</ul>
				</div>
			</div>
		  
		  
			<div class="span10">
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
				
				<div v-show="slideIndex==1">
					<h4 class="heading">기본정보 선택<span></span></h4>
					<p>
						본  자료는 사업계획 수립과 작성을 위한 실습자료로 교육용 목적으로 개발한 것으로 개인별 용도의 내용과 부분적으로 상이할 수 있습니다.
					</p>
					<form class="form-horizontal" id="aform" name="aform">
					<input type="hidden" id="PLANF_ID"  	name="PLANF_ID" 	value="<%=param.getString("PLANF_ID")%>"/>
					<input type="hidden" id="PLANF_SALE"  	name="PLANF_SALE" 	value="<%=param.getString("PLANF_SALE")%>"/>


					
					  <div class="control-group">
					    <label class="control-label">사업자유형</label>
					    <div class="controls">
							<select id="sector_type_biz" name="sector_type_biz" v-model="typeBizVal">
							<%=CommboUtil.getComboStr(typeBizComboStr, "CODE", "CODE_NM", "1" , "")%>
							</select>				
					    </div>
					  </div>
					  
					  <div class="control-group">
					    <label class="control-label">업종선택</label>
					    <div class="controls">
							<select id="cate_biz_m" name="cate_biz_m" v-model="cateBizMVal">
							<%=CommboUtil.getComboStr(cateBizComboStr, "CODE", "CODE_NM", "3" , "")%>
							</select>				
					    </div>
					  </div>

					  <div class="control-group">
					    <label class="control-label">업종유형</label>
					    <div class="controls">
							<select id="cate_biz_s" name="cate_biz_s" v-model="cateBizSVal">
							<option v-for="item in cateBizSs" :value="item.SCLAS_CODE">{{item.SCLAS_NM}}</option>
							</select>			
					    </div>
					  </div>

					</form>				
					<div class="divider"></div>
				</div><!--diSale-->
				
				<div v-show="slideIndex==1">
					<h4 class="heading">사업계획 구성목차<span></span></h4>
					<p><h6>사업계획 구성은 {{cateBizMsLabel}}종의 {{cateBizSValLabel}}관련 아이템으로 {{typeBizValLabel}}의 사업계획목차 유형입니다.</h6></p>

					<div class="control-group"  style="margin-bottom:100px;">
					    <div class="controls right-form">
	 					  <button id="btnMenu" class="btn" type="button" @click="fileDownClick()">사업계획서 다운로드</button>
					    </div>
					</div>

				    <div class="accordion" id="accordion1">
				        <div class="accordion-group" v-for="(item, index) in attrb1Obj" :key="index">
				            <div class="accordion-heading">
				                <a class="accordion-toggle" data-toggle="collapse" :data-parent="'#accordion1'" :href="'#collapse1' + index">
				                    {{ item.section }}
				                </a>
				            </div>
							<div :id="'collapse1' +index" class="accordion-body collapse" :class="{ 'in': index === 0 }">
								<div  v-for="(subSection, subIndex) in item.subsections" class="accordion-inner">
								  {{subIndex+1}}.{{subSection}}:{{getAttrb1ByCodeNm(subSection)}}
								</div>
							</div>
				        </div>
				    </div>
	
					
					<div class="divider"></div>
				</div><!--diSale-->

				<div v-show="slideIndex==2">
					<h4 class="heading">사업계획 요약항목<span></span></h4>
					<p><h6>사업계획 요약은 {{cateBizMsLabel}}종의 {{cateBizSValLabel}}관련 아이템으로 {{typeBizValLabel}}의 사업계획 요약항목과 향후 학습해야 할 내용입니다</h6></p>
	
				    <div class="accordion" id="accordion2">
				        <div class="accordion-group" v-for="(item, index) in attrb2Obj" :key="index">
				            <div class="accordion-heading">
				                <a class="accordion-toggle" data-toggle="collapse" :data-parent="'#accordion2'" :href="'#collapse2' + index">
				                    {{ item.section }}
				                </a>
				            </div>
							<div :id="'collapse2' +index" class="accordion-body collapse in">
								<div  v-for="(subSection, subIndex) in item.subsections" class="accordion-inner">
								  {{subIndex+1}}.{{subSection}}:{{getAttrb1ByCodeNm(subSection)}}
								</div>
							</div>
				        </div>
				    </div>
	
	
					<div class="divider"></div>
				</div><!--diSale-->
				<div v-show="slideIndex==3">
					<h4 class="heading">사업계획  준비검토<span></span></h4>

					<div class="tabbable">
						<ul class="nav nav-tabs tabber">
							<li class="active"><a data-toggle="tab" href="#tab1">{{sections1.title}}</a></li>
							<li class=""><a data-toggle="tab" href="#tab2">{{sections2.title}}</a></li>
							<li class=""><a data-toggle="tab" href="#tab3">{{sections3.title}}</a></li>
							<li class=""><a data-toggle="tab" href="#tab4">사업계획 시뮬레이션</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab1" class="tab-pane active">
								<h5 class="heading">Ⅰ. {{sections1.title}} <span></span></h5>
								<form>
								<fieldset v-for="(item,index) in sections1.questions" :key="item.number">
									<p>
									<label>{{item.number}}.{{item.title}} </label>
									<select class="span5" v-model="sections1selectVal[index]">
										<option value="" selected disabled>선택하세요</option>
										<option v-for="subitem in item.options" :value="subitem.score">{{subitem.description}}</option>
									</select>
									</p>
								</fieldset>	
								</form>
								<h6><span>평가점수 집계</span></h6>
								<div class="span6" style="margin-left: 0px;">
								<table class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>구분</th>
										<th width="25%">인지ㆍ이해 수준</th>
										<th width="25%">계획ㆍ준비 수준</th>
										<th width="25%">합계</th>
									</tr>
									</thead>
									<tbody>
										<tr>
										<td>Ⅰ. {{sections1.title}}</td>
										<td style="text-align:right;">{{section11Score}}</td>
										<td style="text-align:right;">{{section12Score}}</td>
										<td style="text-align:right;">{{section1Score}}</td>
										</tr>
										<tr>
									</tbody>
							  	</table>
								</div>
								<div class="span6" style="margin-left: 0px;">
								<h6><span>평가결과 설명</span></h6>
								<p v-html="section1ScoreValueStr"></p>
								</div>

							</div>
							<div id="tab2" class="tab-pane">
								<h5 class="heading">Ⅱ. {{sections2.title}} <span></span></h5>
								<form>
								<fieldset v-for="(item,index) in sections2.questions" :key="item.number">
									<p>
									<label>{{item.number}}.{{item.title}} </label>
									<select class="span5" v-model="sections2selectVal[index]">
										<option value="">선택하세요</option>
										<option v-for="subitem in item.options" :value="subitem.score">{{subitem.description}}</option>
									</select>
									</p>
								</fieldset>	
								</form>
								<h6><span>평가점수 집계</span></h6>
								<div class="span6" style="margin-left: 0px;">
								<table class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>구분</th>
										<th width="25%">인지ㆍ이해 수준</th>
										<th width="25%">계획ㆍ준비 수준</th>
										<th width="25%">합계</th>
									</tr>
									</thead>
									<tbody>
										<tr>
										<td>Ⅱ. {{sections2.title}}</td>
										<td style="text-align:right;">{{section21Score}}</td>
										<td style="text-align:right;">{{section22Score}}</td>
										<td style="text-align:right;">{{section2Score}}</td>
										</tr>
									</tbody>
							  	</table>
								</div>
								<div class="span6" style="margin-left: 0px;">
								<h6><span>평가결과 설명</span></h6>
								<p v-html="section2ScoreValueStr"></p>
								</div>
							</div>
							<div id="tab3" class="tab-pane">
								<h5 class="heading">Ⅲ. {{sections3.title}} <span></span></h5>
								<form>
								<fieldset v-for="(item,index) in sections3.questions" :key="item.number">
									<p>
									<label>{{item.number}}.{{item.title}} </label>
									<select class="span5"  v-model="sections3selectVal[index]">
										<option value="">선택하세요</option>
										<option v-for="subitem in item.options" :value="subitem.score">{{subitem.description}}</option>
									</select>
									</p>
								</fieldset>	
								</form>
								<h6><span>평가점수 집계</span></h6>
								<div class="span6" style="margin-left: 0px;">
								<table class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>구분</th>
										<th width="25%">인지ㆍ이해 수준</th>
										<th width="25%">계획ㆍ준비 수준</th>
										<th width="25%">합계</th>
									</tr>
									</thead>
									<tbody>
										<td>Ⅲ. {{sections3.title}}</td>
										<td style="text-align:right;">{{section31Score}}</td>
										<td style="text-align:right;">{{section32Score}}</td>
										<td style="text-align:right;">{{section3Score}}</td>
									</tbody>
							  	</table>
								</div>
								<div class="span6" style="margin-left: 0px;">
								<h6><span>평가결과 설명</span></h6>
								<p v-html="section2ScoreValueStr"></p>
								</div>

							</div>								
							<div id="tab4" class="tab-pane">
								<h5 class="heading">Ⅳ. 사업계획 시뮬레이션 <span></span></h5>
								<h6><span>평가점수 집계</span></h6>
								<div class="span7" style="margin-left: 0px;">
								<table class="table table-bordered table-striped">
									<thead>
									<tr>
										<th>구분</th>
										<th width="25%">인지ㆍ이해 수준</th>
										<th width="25%">계획ㆍ준비 수준</th>
										<th width="25%">합계</th>
									</tr>
									</thead>
									<tbody>
										<tr>
										<td>Ⅰ. {{sections1.title}}</td>
										<td style="text-align:right;">{{section11Score}}</td>
										<td style="text-align:right;">{{section12Score}}</td>
										<td style="text-align:right;">{{section1Score}}</td>
										</tr>
										<tr>
										<td>Ⅱ. {{sections2.title}}</td>
										<td style="text-align:right;">{{section21Score}}</td>
										<td style="text-align:right;">{{section22Score}}</td>
										<td style="text-align:right;">{{section2Score}}</td>
										</tr>
										<tr>
										<td>Ⅲ. {{sections3.title}}</td>
										<td style="text-align:right;">{{section31Score}}</td>
										<td style="text-align:right;">{{section32Score}}</td>
										<td style="text-align:right;">{{section3Score}}</td>
										</tr>
									</tbody>
									<tfoot>
									<tr>
										<td>평균</td>
										<td style="text-align:right;">{{averageScore1}}</td>
										<td style="text-align:right;">{{averageScore2}}</td>
										<td style="text-align:right;">{{averageScore}}</td>
									</tr>
									<tr>
										<td>평가유형</td>
										<td colspan=3 style="text-align:center;">({{resultEvalType}}형)</td>
									</tr>
									</tfoot>
									
							  	</table>
								</div>
								
								<div class="span6" style="margin-left: 0px;height: 300px;">
								<h6><span>평가결과 검토</span></h6>
								<canvas id="scatterChart" width="300" height="300"></canvas>
								</div>
								<div class="span6" style="margin-left: 0px;margin-top: 50px;">
								<h6><span>평가결과 설명</span></h6>
								<p v-html="resultEvalStirng"></p>
								</div>

							</div>								
						</div>
					</div>	
	
					<div class="divider"></div>
				</div><!--diSale-->

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
	</div>
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
	
	<!-- custom js -->
	<script src="/common/front/js/custom.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
	<script type="text/javascript" src="/common/vue/vue2debug.js"></script>
	<script type="text/javascript" src="/common/vue/axios.min.js"></script>
	<script type="text/javascript" src="/common/vue/Sortable.min.js"></script>
	<script type="text/javascript" src="/common/vue/vuedraggable.umd.min.js"></script>
	
	<script type="text/javascript">
 		
		let bizComboStr =<%=param.getString("bizComboStr")%>;
		let typeBizs = bizComboStr.typeBizs;
		let cateBizs = bizComboStr.cateBizs;
		let sectionLabels =bizComboStr.sectinLabels;
		console.log("typeBizs",typeBizs);
		console.log("cateBizs",cateBizs);
		//console.log("sectionLabels",sectionLabels);
		new Vue({
			el:'#app',
			data:{
				 slideIndex: 1,
				 lMenuClass:'active',
				 typeBizs:typeBizs,
				 cateBizMs:cateBizs,
				 sectionLabels:sectionLabels,
				 cateBizSs:[],
				 selectedCateBizS:null,
				 typeBizVal:'1',
				 cateBizMVal:'3',
				 cateBizSVal:'1',
				 attrb1Obj:null,
				 attrb2Obj:null,
				 sections1:[],
				 sections2:[],
				 sections3:[],
				 sections1selectVal: Array.from({ length: 10 }, () => ''),
				 sections2selectVal: Array.from({ length: 10 }, () => ''),
				 sections3selectVal: Array.from({ length: 10 }, () => ''),
				 chartScoreX:0,
				 chartScoreY:0,
			},
			methods:{
					slideClick: function(pIndex) {
						this.slideIndex = pIndex;
					},
					fileDownClick: function(pStr) {
						alert("개발중입니다.");
					},
					getAttrb1ByCodeNm(codeNm) {
			            // CODE_NM이 가공계획인 항목의 ATTRB_1을 반환
			            const item = this.sectionLabels.find(item => item.CODE_NM === codeNm);
			            return item ? item.ATTRB_1 : '';
			        },					
				  	fetchCateBizSs(mVal) {
				      axios.get('/common/selectSCodeListAjax.do?lcode=R010520&mcode='+ mVal)
				      .then((response) => {
				        console.log(response.data);
				        this.cateBizSs = response.data.resultStats.resultList;
				        console.log(this.cateBizSs);
				        this.cateBizSVal = 1;
					    this.selectedCateBizS = this.getCateBizS(0);
					    if (this.typeBizVal=="1") {
						    this.attrb1Obj = JSON.parse(this.selectedCateBizS.ATTRB_1);
						    this.attrb2Obj = JSON.parse(this.selectedCateBizS.ATTRB_3);
				        } else {
						    this.attrb1Obj = JSON.parse(this.selectedCateBizS.ATTRB_2);
						    this.attrb2Obj = JSON.parse(this.selectedCateBizS.ATTRB_4);
					    }
				        //console.log("this.selectedCateBizS",this.selectedCateBizS);
				      })
				      .catch((error) => {
				        console.log(error);
				      });
			        },					
				  	fetchQuestion() {
				      axios.get('/common/vue/data/question.json')
				      .then((response) => {
				        console.log("fetchQuestion",response.data);
				        this.sections1 = response.data.sections[0];
				        this.sections2 = response.data.sections[1];
				        this.sections3 = response.data.sections[2];
				        console.log("fetchQuestion this.sections1",this.sections1);
				      })
				      .catch((error) => {
				        console.log(error);
				      });
				    },
				    getCateBizS: function(index) {
				        return this.cateBizSs[index];
				    }
			},
			computed:{
				plancd: function() {
					return this.typeBizVal+this.cateBizMVal+this.cateBizSVal;
				},
				typeBizValLabel: function() {
		            var selectedOption = this.typeBizs.find(item => item.CODE == this.typeBizVal);
		            return selectedOption ? selectedOption.CODE_NM : '';
		        },
		        cateBizMsLabel: function() {
		            var selectedOption = this.cateBizMs.find(item => item.CODE == this.cateBizMVal);
		            return selectedOption ? selectedOption.CODE_NM : '';
		        },
		        cateBizSValLabel: function() {
		            let selectedOption = this.cateBizSs.find(item => item.SCLAS_CODE == this.cateBizSVal);
		            return selectedOption ? selectedOption.SCLAS_NM : '';
		        },
		        section11Score: function() {
		            if (!this.sections1selectVal) return 0;
		            // 5번째 배열값까지 합산
		            return this.sections1selectVal.slice(0, 5).map(value => parseFloat(value) || 0).reduce((acc, cur) => acc + cur, 0); // 빈 문자열을 0으로 변환
		            			
		        },
		        section12Score: function() {
		            if (!this.sections1selectVal) return 0;
		            // 5번째 배열값까지 합산
		            return this.sections1selectVal.slice(5).map(value => parseFloat(value) || 0).reduce((acc, cur) => acc + cur, 0); // 빈 문자열을 0으로 변환
		        },
		        section1Score: function() {
		            return this.section11Score+this.section12Score;
		        },
		        section1ScoreValueStr: function() {
			        if (!this.sections1 || this.sections1.length === 0) return "";
			        if (this.section11Score >= 25 && this.section12Score >= 25) {
		              return this.sections1.valuation[0];
		            } else if (this.section11Score < 25 && this.section12Score >= 25) {
		              return this.sections1.valuation[1];
		            } else if (this.section11Score >= 25 && this.section12Score < 25) {
		              return this.sections1.valuation[2];
		            } else {
		              return this.sections1.valuation[3];
		            }
		        },
		        section21Score: function() {
		            if (!this.sections2selectVal) return 0;
		            // 5번째 배열값까지 합산
		            return this.sections2selectVal.slice(0, 5).map(value => parseFloat(value) || 0).reduce((acc, cur) => acc + cur, 0); // 빈 문자열을 0으로 변환
		        },
		        section22Score: function() {
		            if (!this.sections2selectVal) return 0;
		            // 5번째 배열값까지 합산
		            return this.sections2selectVal.slice(5).map(value => parseFloat(value) || 0).reduce((acc, cur) => acc + cur, 0); // 빈 문자열을 0으로 변환
		        },
		        section2Score: function() {
		            return this.section21Score + this.section22Score;
		        },
		        section2ScoreValueStr: function() {
		            if (!this.sections2 || this.sections2.length === 0) return "";
		            if (this.section21Score >= 25 && this.section22Score >= 25) {
		                return this.sections2.valuation[0];
		            } else if (this.section21Score < 25 && this.section22Score >= 25) {
		                return this.sections2.valuation[1];
		            } else if (this.section21Score >= 25 && this.section22Score < 25) {
		                return this.sections2.valuation[2];
		            } else {
		                return this.sections2.valuation[3];
		            }
		        },
		        section31Score: function() {
		            if (!this.sections3selectVal) return 0;
		            // 5번째 배열값까지 합산
		            return this.sections3selectVal.slice(0, 5).map(value => parseFloat(value) || 0).reduce((acc, cur) => acc + cur, 0); // 빈 문자열을 0으로 변환
		        },
		        section32Score: function() {
		            if (!this.sections3selectVal) return 0;
		            // 5번째 배열값까지 합산
		            return this.sections3selectVal.slice(5).map(value => parseFloat(value) || 0).reduce((acc, cur) => acc + cur, 0); // 빈 문자열을 0으로 변환
		        },
		        section3Score: function() {
		            return this.section31Score + this.section32Score;
		        },
		        section3ScoreValueStr: function() {
		            if (!this.sections3 || this.sections3.length === 0) return "";
		            if (this.section31Score >= 25 && this.section32Score >= 25) {
		                return this.sections3.valuation[0];
		            } else if (this.section31Score < 25 && this.section32Score >= 25) {
		                return this.sections3.valuation[1];
		            } else if (this.section31Score >= 25 && this.section32Score < 25) {
		                return this.sections3.valuation[2];
		            } else {
		                return this.sections3.valuation[3];
		            }
		        },
		        averageScore1: function() {
		            var totalScore = this.section11Score + this.section21Score + this.section31Score;
		            var average = totalScore / 3;
		            this.chartScoreY = average.toFixed(1);
		            // 소수점 한자리까지 반올림
		            return average.toFixed(1);
		        },
		        averageScore2: function() {
		            var totalScore = this.section12Score + this.section22Score + this.section32Score;
		            var average = totalScore / 3;
		            // 소수점 한자리까지 반올림
		            this.chartScoreX = average.toFixed(1);
		            return average.toFixed(1);
		        },
		        averageScore: function() {
		            var totalScore = this.section1Score + this.section2Score + this.section3Score;
		            var average = totalScore / 3;
		            // 소수점 한자리까지 반올림
		            return average.toFixed(1);
		        },
		        resultEvalType: function() {
		            if (this.averageScore1 >= 25 && this.averageScore2 >= 25) {
		              return "A";
		            } else if (this.averageScore1 < 25 && this.averageScore2 >= 25) {
		              return "B";
		            } else if (this.averageScore1 >= 25 && this.averageScore2 < 25) {
		              return "C";
		            } else {
		              return "D";
		            }
		        },
		        resultEvalStirng: function() {
		        	if(!this.resultEvalType) return "";
			        console.log("resultEvalType",this.resultEvalType);
		            if (this.resultEvalType =="A") {
		              return "사업계획에 대한 전반적인 사항에 대해 이해와 준비를 갖추고 있는 유형임.<br> 다음 과정으로 사업개시에 대한 준비와 검토가 필요함. ";
		            } else if (this.resultEvalType =="A") {
		              return "사업계획에 대해 조사와 계획은 준비하고 있지만 사업계획의 제반 내용에 대한 이해가 부족한 유형임. <br>사업계획에 대한 교육과 내용의 파악이 우선 요구됨.";
		            } else if (this.resultEvalType =="C") {
		              return "사업계획에 대한 이해와 파악은 높은 편이지만 사업계획에 대한 준비가 부족한 유형임. <br>사업계획에 대한 조사와 계획이 우선 요구됨.";
		            } else {
		              return "사업계획에 대해 전체적으로 이해와 준비가 부족하다고 판단되는 유형임. 사업계획에 대한 학습, <br>조사ㆍ계획을 준비하는 일이 우선적으로 필요함.";
		            }
		         }		        		    
		    },
			watch:{
				cateBizMVal: function(newVal,oldVal) {
					this.fetchCateBizSs(newVal);
				},
				cateBizSVal: function(newValue, oldValue) {
				      // cateBizSVal 값에 따라 적절한 cateBizSs 배열의 요소를 가져옵니다.
				      const index = parseInt(newValue) - 1; // cateBizSVal이 1부터 시작하기 때문에 1을 뺍니다.
				      if (index >= 0 && index < this.cateBizSs.length) {
				        this.selectedCateBizS = this.getCateBizS(index);
				        // 선택된 cateBizS 값에 대한 추가 작업 수행
					    if (this.typeBizVal=="1") {
						    this.attrb1Obj = JSON.parse(this.selectedCateBizS.ATTRB_1);
						    this.attrb2Obj = JSON.parse(this.selectedCateBizS.ATTRB_3);
				        } else {
						    this.attrb1Obj = JSON.parse(this.selectedCateBizS.ATTRB_2);
						    this.attrb2Obj = JSON.parse(this.selectedCateBizS.ATTRB_4);
					    }
				        //console.log("this.selectedCateBizS",this.selectedCateBizS);
				      }
				},
				chartScoreX: function (newValue, oldValue){
					changeData(newValue,this.chartScoreY);
				},
				chartScoreY: function (newValue, oldValue){
					changeData(this.chartScoreX,newValue);
				}
			},
			created() {
			    this.fetchCateBizSs(this.cateBizMVal);
			    this.fetchQuestion();
			},			
		})


	   	var ctx = document.getElementById('scatterChart').getContext('2d');
		var chartData=[{x: 25, y: 25}];
		   // 산점도 차트 생성
	    var scatterChart = new Chart(ctx, {
	      type: 'scatter', // 산점도 유형 지정
	      data: {
	        datasets: [
	          {
	            data: chartData, // 데이터 포인트 1
	            pointStyle: 'circle', // 데이터 포인트 스타일 설정
	            pointRadius: 10, // 데이터 포인트 반지름 설정
	            backgroundColor: 'rgba(255, 99, 132, 0.2)', // 흰색으로 투명한 배경색 설정
	            borderColor: 'rgba(255, 99, 132, 1)' // 흰색으로 투명한 테두리색 설정
	          }
	        ]
	      },
	      options: {
	        scales: {
	          x: {
	            type: 'linear', // x축 유형
	            position: 'bottom', // x축 위치
	            min: 0, // 최소값
	            max: 50, // 최대값
	            stepSize: 25, // 증가 단계 설정
	            title: { // x축 레이블 설정
	              display: true,
	              text: '계획ㆍ준비 수준'
	            },
				ticks: {
	              stepSize: 25, // y축 눈금 사이의 간격 설정
	            }
	          },
	          y: {
	            type: 'linear', // y축 유형
	            position: 'left', // y축 위치
	            min: 0, // 최소값
	            max: 50, // 최대값
	            stepSize: 25, // 증가 단계 설정
	            title: { // y축 레이블 설정
	              display: true,
	              text: '인지ㆍ이해 수준'
	            },
				ticks: {
	              stepSize: 25, // y축 눈금 사이의 간격 설정
	            }
	          }
			},
	        plugins: {
	          legend: {
	            display: false // 레이블 숨기기
	          }
	        }
	      }
	    });  


		// 데이터 변경 함수 정의
		function changeData(px,py) {
			// 새로운 데이터 생성
			var newData = [{x:px , y: py}];
			// 데이터 업데이트
			scatterChart.data.datasets[0].data = newData;
			// 그래프 다시 그리기
			scatterChart.update();
		}
		
	</script>
	
	</body>
	
</html>


