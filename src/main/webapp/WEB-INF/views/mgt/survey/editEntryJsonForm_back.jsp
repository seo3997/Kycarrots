<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="SectionList"   		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionList"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionLableList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>



<%
	String surveyId 	= param.getString("surveyId");
	String pid 			= param.getString("userNo");
	System.out.println("editEntryJsonForm surveyId["+surveyId+"]*********");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);

	System.out.println("editEntryJsonForm surveyId["+entryForm.getSurveyJaon()+"]*********");

	
%>


<!doctype html>
<html lang="en"><head>
		<meta charset="utf-8">
		<title>Survey Tool</title>
		<meta name="description" content="Common Buttons &amp; Icons">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!--basic styles-->
		<link type="text/css" rel="stylesheet" href="/common/vue/bootstrapvue/bootstrap.css" />
		<link type="text/css" rel="stylesheet" href="/common/vue/bootstrapvue/bootstrap-vue.min.css" />
		
		
		<!--page specific plugin styles-->
		<!--fonts-->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300">
		<!--ace styles-->
		<link rel="stylesheet" href="/common/assets/css/ace.min.css">
		<link rel="stylesheet" href="/common/assets/css/ace-responsive.min.css">
		<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css">
<style>
		.btn-outline-primary{
			color:#007BFF !important;;
		}
		.modal-mask {
		  position: fixed;
		  z-index: 9998;
		  top: 0;
		  left: 0;
		  width: 100%;
		  height: 100%;
		  background-color: rgba(0, 0, 0, 0.5);
		  display: flex;
		  transition: opacity 0.3s ease;
		}
		
		.modal-container {
		  width: 600px;
		  height:730px;
		  margin: auto;
		  padding: 20px 30px;
		  background-color: #fff;
		  border-radius: 2px;
		  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
		  transition: all 0.3s ease;
		}
		
		.modal-header h3 {
		  margin-top: 0;
		  color: #42b983;
		}
		
		.modal-body {
		  margin: 20px 0;
		  max-height: 550px 
		}
		
		.modal-default-button {
		  float: right;
		}
		
		/*
		 * The following styles are auto-applied to elements with
		 * transition="modal" when their visibility is toggled
		 * by Vue.js.
		 *
		 * You can easily play with the modal transition by editing
		 * these styles.
		 */
		
		.modal-enter-from {
		  opacity: 0;
		}
		
		.modal-leave-to {
		  opacity: 0;
		}
		
		.modal-enter-from .modal-container,
		.modal-leave-to .modal-container {
		  -webkit-transform: scale(1.1);
		  transform: scale(1.1);
		}
		</style>		
		<!-- Load polyfills to support older browsers -->
		<script src="/common/vue/bootstrapvue/polyfill.js" crossorigin="anonymous"></script>
		
		<script type="text/javascript" src="/common/vue/vue2debug.js"></script>
		<script src="/common/vue/bootstrapvue/bootstrap-vue.min.js"></script>
		<!-- Load the following for BootstrapVueIcons support -->
		<script src="/common/vue/bootstrapvue/bootstrap-vue-icons.min.js"></script>
		
		<script type="text/javascript" src="/common/vue/Sortable.min.js"></script>
		<script type="text/javascript" src="/common/vue/vuedraggable.umd.min.js"></script>
		
		
		<script language="JavaScript" type="text/javascript"><!--
			function isIE4()
			{ return( navigator.appName.indexOf("Microsoft") != -1 && (navigator.appVersion.charAt(0)=='4') ); }
			
			function new_window(freshurl) {
				  SmallWin = window.open(freshurl, 'Survey','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
				  if (!isIE4())	{
				    if (window.focus) { SmallWin.focus(); }
				  }
				  if (SmallWin.opener == null) SmallWin.opener = window;
				  SmallWin.opener.name = "Main";
			}
			
			function new_serveyList(freshurl) {
				  serveyListWin = window.open(freshurl, 'SurveyRegist','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
				  serveyListWin.opener.name = "SurveyRegist";
			}
			
			function fn_done() {
				opener.location.href="/mgt/survey/manageSurveyMenu.do?surveyId=<%=surveyId%>";
				self.close();
			}
			
		//-->
		</script>
		

		
	</head>
<body>
<form method="post" action="http://smartmot.cafe24.com/isurvey/editEntryForm.jsp" name="form">
<input type="hidden" name="surveyId" value="<%=surveyId%>">
<div id="app">
 
	<div class="row-fluid">
		<br>
		<div class="tab-content">							
			<div align="left">
			  <draggable v-model="sections"  @end="onDragEnd">
			    <div v-for="(section, index) in sections" :key="section.SECTION_ID">
						{{section.SECTION_ID }}/{{ index}}
						<br>
						<h4>{{section.SECTION_TITLE }}</h4>
			  		<draggable v-model="section.tbQuestions"  @end="onDragEnd">
			    		<div v-for="(tbQuestion, jinndex) in section.tbQuestions" :key="tbQuestion.QUESTION_ID">
							<table cellspacing="0" cellpadding="0" border="0"  bordercolor = "#5D5D5D" width="100%">
							<tbody>
							<tr>
								<td bgcolor="#ffffff">
									<table cellspacing="1" cellpadding="2" border="0" width="100%">
									<tbody>
									<tr>
										<td bgcolor="#5D5D5D">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tbody>
											<tr>
												<td style="color : #ffffff">{{tbQuestion.QUESTION_TYPETEXT_HTML}}</td>
											</tr>
											</tbody>
											</table>
									  </td>
								  </tr>
								  <tr>
									<td class="globalsettings" v-html="tbQuestion.HTML_NEW"></td>
								  </tr>
								  </tbody>
								  </table>
							  </td>
						    </tr>
						    </tbody>
						    </table>
				 			</div>
		  				</draggable>
				
						 <br>
						 <div align = "right">
						 <a href="/mgt/survey/addQuestion.do?surveyId=<%=surveyId%>&section=<%=0%>&question=<%=1%>&above=N" style="font-size:80%"><input type="button" name="Add question here" value="add question here" span class="btn btn-mini btn-light"></a>&nbsp; 
						 <a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&type=comment&section=<%=0%>&question=<%=0%>&above=N" style="font-size:80%"><input type="button" name="Add text here" value="add text here" span class="btn btn-mini btn-light"></a>&nbsp; 
						 <a href="/mgt/survey/addSection.do?surveyId=<%=surveyId%>&section=<%=0+1%>" style="font-size:80%"><input type="button" name="Add section" value="Add section" span class="btn btn-mini btn-light"></a>&nbsp; 
						 <a href="/mgt/survey/deleteSection.do?surveyId=<%=surveyId%>&section=<%=0%>" style="font-size:80%"><input type="button" name="Delete Section" value="Delete Section" span class="btn btn-mini btn-light" ></a>&nbsp; 
						 <a href="/mgt/survey/editSection.do?surveyId=<%=surveyId%>&section=<%=0%>" style="font-size:80%"><input type="button" name="Edit Section" value="Edit Section" span class="btn btn-mini btn-light" ></a>&nbsp; 
						 </div>

		 			</div>
  				</draggable>
			</div><!-- left -->
		</div><!-- tab-content -->
	</div><!-- row-fluid -->
 
	<br>
	<div align="right">
	<input type="button" name="saveSurvey" 	value="Done" 			class="btn btn-small btn-warning" onclick="fn_done();">&nbsp;&nbsp;
	<input type="button" name="preview" 	value="Preview" 		class="btn btn-small  btn-success" onclick="JavaScript:new_window('/mgt/survey/entry.do?surveyId=<%=surveyId%>&surveyMode=2')">&nbsp;&nbsp;
	<input type="button" name="btnSelect" 	value="Survey Select" 	class="btn btn-small  btn-primary" onclick="JavaScript:new_serveyList('/mgt/survey/surveylist.do?pid=<%=pid%>&OrgSurveyId=<%=surveyId%>')">
	</div>
  	
  	<b-table striped hover :items="items"></b-table>
  	
  	<b-form-datepicker id="example-datepicker" v-model="value" class="mb-3"></b-form-datepicker>
    <p>Value: '{{ value }}'</p>
  	
  	<b-button v-b-modal.modal-1>Launch demo modal</b-button> 
   	<b-modal id="modal-1" title="질문 유형 선택">
         <survey-type-selector :surveyId="surveyId" :above="above" :sectionNr="sectionNr" :questionNr="questionNr" />
  	</b-modal>

</div><!--id="page-content"  -->	

</form>
  

</div>

<script type="module">
	import SurveyTypeSelectorModal from "/common/vue/js/SurveyTypeSelectorModal.js"
	import SurveyTypeSelector from "/common/vue/js/SurveyTypeSelector.js"

	let surveyJson=<%=entryForm.getSurveyJaon()%>;
    let sections = surveyJson.sections;
    console.log(sections);
	// Vue 인스턴스 생성 전에 VueDraggable을 등록해야 합니다.
	Vue.component('draggable', window.vuedraggable.default);

	 
	new Vue({
	  el: '#app',
	  data: {
		  sections:sections,
          questionSelectModalYn:true,
 		  items: [
				  { age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
				  { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
				  { age: 89, first_name: 'Geneva', last_name: 'Wilson' },
				  { age: 38, first_name: 'Jami', last_name: 'Carney' }
				],
		  value: ''
	  },
      components:{
      	SurveyTypeSelectorModal,
      	SurveyTypeSelector
	  },
	  methods: {
	    // 드래그 앤 드롭 종료 시 호출되는 메서드
	    onDragEnd(event) {
	        console.log('이동된 아이템 인덱스:', this.questions);
	      	console.log('이동된 아이템 인덱스:', event.newIndex);
	      // 여기에서 필요한 작업을 수행할 수 있습니다.
	    },
	    modifiedHtml(pOriginalHtml) {
	        // 수정된 HTML 문자열을 반환합니다.
	        let modifiedHtml = pOriginalHtml.replace('disabled = true', 'disabled = false');
	        console.log(modifiedHtml);
	        return modifiedHtml;
	     }
	  }
	});
</script>


 
</body>

</html>
