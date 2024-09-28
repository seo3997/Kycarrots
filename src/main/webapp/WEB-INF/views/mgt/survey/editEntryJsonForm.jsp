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
		<script type="text/javascript" src="/common/vue/axios.min.js"></script>
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
<div id="app">

	<form method="post" action="http://smartmot.cafe24.com/isurvey/editEntryForm.jsp" name="form">
	<input type="hidden" name="surveyId" value="<%=surveyId%>">
	<div id="page-content" class="clearfix">
	 	<div>
			<h2><img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;입력양식 편집</h2>	
		</div>
		<div class="row-fluid">
			<div align="right">
			<input type="button" name="saveSurvey" value="Done" class="btn btn-small btn-warning" onclick="fn_done();">&nbsp;&nbsp;
			<input type="button" name="preview" 	value="Preview" class="btn btn-small  btn-success" onclick="JavaScript:new_window('/mgt/survey/entry.do?surveyId=<%=surveyId%>&surveyMode=2')">&nbsp;&nbsp;
			<input type="button" name="btnSelect" 	value="Survey Select" class="btn btn-small  btn-primary" onclick="JavaScript:new_serveyList('/mgt/survey/surveylist.do?pid=<%=pid%>&OrgSurveyId=<%=surveyId%>')">
			</div>
		</div>
		<div class="row-fluid">
			<br>
			<div class="tab-content">							
				<div align="left">
				  <draggable v-model="SECTIONS" @start="onSectionDragStart"  @end="onSectionDragEnd">
				    <div v-for="(section, index) in SECTIONS" :key="section.SECTION_ID">
							<br>
							<h4>{{section.SECTION_TITLE }}</h4>
				  		<draggable v-model="section.QUESTIONS"  @start="onQuestionDragStart($event,section.QUESTIONS)" @end="onQuestionDragEnd($event,section.QUESTIONS)" v-bind:data-list-id="section.SECTION_ID">
				    		<div v-for="(question, jinndex) in section.QUESTIONS" :key="question.QUESTION_ID">
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
													<td style="color : #ffffff">{{question.QUESTION_TYPETEXT_HTML}}</td>
													<td nowrap="" align="right"> <a href="#"  @click.prevent="updateQuestion(section,question)" style="font-size:80%;color:#ffffff">edit</a>&nbsp;&nbsp;&nbsp;
																		<a href="" style="font-size:80%;color:#ffffff">copy</a>&nbsp;&nbsp;&nbsp;
																		<a href="" style="font-size:80%;color:#ffffff">delete</a>&nbsp;&nbsp;&nbsp; 
													</td>
												</tr>
												</tbody>
												</table>
										  </td>
									  </tr>
									  <tr>
										<td class="globalsettings" v-html="question.HTML_NEW"></td>
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
							 <b-button v-b-modal.modal-question-select class="btn-mini" @click="openAddQuestion(section)">add question here</b-button>
							 <b-button v-b-modal.modal-input-comment   class="btn-mini" @click="openAddComment(section)">add text here</b-button>
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
	 
	  	
	   	<b-modal v-model="selectModalFlag" id="modal-question-select" title="질문 유형 선택">
	 	   <template #modal-footer>
	    		<b-button variant="info" @click="$bvModal.hide('modal-question-select')">Cancel</b-button>
	  		</template>
	       <survey-type-selector :survey-id="'survey-id'" :above="'above'" :section-nr="1" :question-nr="1" @question-selected="selectedQuestionType"/>
	    </b-modal>
	   	
	   	<b-modal v-model="inputTextlineMoalFlag" id="modal-input-textline" title="입력양식 편집  Edit" @ok="inputTextlineOk">
			<input-textline ref="inputTextlineRef" :form-data="inputTextlineData" @form-submitted="handleFormSubmitted" />
	  	</b-modal>
	
	   	<b-modal v-model="inputRadioModalFlag" id="modal-input-radio" title="입력양식 편집  Multiple choice" @ok="inputRadioOk">
			<input-radio ref="inputRadioRef" :form-data="inputRadioData" @form-radiosubmitted="handleFormSubmitted" />
	  	</b-modal>
	
	   	<b-modal v-model="inputCheckboxModalFlag" id="modal-input-check" title="입력양식 편집  Check all that apply" @ok="inputCheckOk">
			<input-checkbox ref="inputcheckRef" :form-data="inputCheckboxData" @form-radiosubmitted="handleFormSubmitted" />
	  	</b-modal>
	
	   	<b-modal v-model="inputTextareaModalFlag" id="modal-input-textarea" title="입력양식 편집  Comment/Essay question" @ok="inputTextareaOk">
			<input-textarea ref="inputTextareaRef" :form-data="inputTextareaData" @form-radiosubmitted="handleFormSubmitted" />
	  	</b-modal>
	
	   	<b-modal v-model="inputImageModalFlag" id="modal-input-image" title="입력양식 편집  Input Image" @ok="inputImageOk">
			<input-image ref="inputImageRef" :form-data="inputImageData" @form-radiosubmitted="handleFormSubmitted" />
	  	</b-modal>
	
	   	<b-modal v-model="inputLocationModalFlag" id="modal-input-location" title="입력양식 편집 Input Location" @ok="inputLocationOk">
			<input-location ref="inputLocationRef" :form-data="inputLocationData" @form-radiosubmitted="handleFormSubmitted" />
	  	</b-modal>
	
	 
	</div><!--id="page-content"  -->	
	</form>
  

</div>

<script type="module">
	import SurveyTypeSelectorModal from "/common/vue/js/SurveyTypeSelectorModal.js"
	import SurveyTypeSelector from "/common/vue/js/SurveyTypeSelector.js"
	import InputTextline from "/common/vue/js/InputTextline.js"
	import InputRadio from "/common/vue/js/InputRadio.js"
	import InputCheckbox from "/common/vue/js/InputCheckbox.js"
	import InputTextarea from "/common/vue/js/InputTextarea.js"
	import InputImage from "/common/vue/js/InputImage.js"
	import InputLocation from "/common/vue/js/InputLocation.js"
	import InputComment from "/common/vue/js/InputComment.js"
	let surveyId="<%=surveyId%>";
	let surveyJson=<%=entryForm.getSurveyJaon()%>;
    let sections = surveyJson.SECTIONS;
    console.log(sections);
	// Vue 인스턴스 생성 전에 VueDraggable을 등록해야 합니다.
	Vue.component('draggable', window.vuedraggable.default);

	 
	new Vue({
	  el: '#app',
	  data: {
		  SURVEY_ID:surveyId,
		  SECTIONS:sections,
          QUESTIONS:[],
		  SECTION:{},
		  SECTION_INDEX:0,
		  QUESTION_INDEX:0,
          mode:'',
          inputType:'',
          questionSelectModalYn:true,
		  selectModalFlag:false,
		  inputTextlineMoalFlag:false,
		  inputRadioModalFlag:false,
		  inputCheckboxModalFlag:false,
		  inputTextareaModalFlag:false,
		  inputImageModalFlag:false,
		  inputLocationModalFlag:false,
		  inputCommentModalFlag:false,
 		  inputTextlineData: {
        	prompt: '',
        	showDivider: '1',
        	label: '',
        	visibleWidth: '30',
        	maxLength: '300'
      	  },
    	  inputRadioData: {
        	surveyId: '1566023377421',
        	prompt: '',
        	showDivider: true,
        	options: '',
        	otherShortAnswer: false,
        	otherShortAnswerLabel: 'other:',
        	layout: 'vertical'
      	  },
 		  inputCheckboxData: {
        	// 초기 데이터를 설정합니다.
        	surveyId: '1566023377421',
        	prompt: '',
        	showDivider: true,
        	options: 'option 1\noption 2\noption 3',
        	otherShortAnswer: false,
        	otherShortAnswerLabel: 'other:',
        	layout: 'vertical'
      	  },
 		  inputTextareaData: {
        	// 초기 데이터를 설정합니다.
		     prompt: '', // 질문 프롬프트
        	showDivider: '1', // 이전 항목과 시각적으로 구분할지 여부
        	label: '', // 라벨
        	visibleWidth: '30', // 가시 너비
        	maxLength: '300' // 최대 길이
      	  },
   		  inputImageData: {
        	surveyId: '1566023377421',
        	prompt: '',
        	showDivider: '1',
        	label: '',
        	visibleWidth: '30',
        	maxLength: '300'
      	  },
   		  inputLocationData: {
    		surveyId: '1566023377421',
        	prompt: 'Your prompt here',
        	showDivider: true,
        	label: '',
        	size: '30',
        	maxLength: '300'
      	  },
   		  inputCommentData: {
    		surveyId: '1566023377421',
        	comment: '',
        	showDivider: true
      	 },
		 QUESTION:{},
		 initialQuestions: [],
	  },
      components:{
      	SurveyTypeSelectorModal,
      	SurveyTypeSelector,
     	InputTextline,
		InputRadio,
		InputCheckbox,
		InputTextarea,
		InputImage,
		InputLocation,
		InputComment
	  },
	  methods: {
	    // 드래그 앤 드롭 종료 시 호출되는 메서드
	    onSectionDragStart(event) {
	      	console.log("onSectionDragStart");

		},
	    onSectionDragEnd(event) {
	      	console.log("onSectionDragEnd");
	      	console.log('이동된 아이템 인덱스 event.newIndex:', event.newIndex);
   			const positionChanges = {
				surveyId: this.SURVEY_ID,
				sectionListPositions: []
			}
		
			this.SECTIONS.forEach((section, index) => {
				positionChanges.sectionListPositions.push({
					sectionId	: section.SECTION_ID,
					position	: index
				})
			});
			console.log('section positionChanges:', positionChanges);		
			//this.dragSection(positionChanges);

		},
     	onQuestionDragStart(event,pQuestions) {
	      	console.log("onQuestionDragStart");
			this.initialQuestions = [];
			pQuestions.forEach((question, index) => {
				this.initialQuestions.push({
					questionId	: question.QUESTION_ID,
					sectionId	: question.SECTION_ID,
					position	: index
				})
			});

 
	    },
    	onQuestionDragEnd(event,pQuestions) {
	      	console.log("onQuestionDragEnd");
	      	console.log("event",event);
	        console.log('이동된 아이템 인덱스 this.section.QUESTIONS:', pQuestions);
			const fromListId = event.from.dataset.listId
      		const toListId = event.to.dataset.listId
      		console.log('fromListId', fromListId)
      		console.log('toListId', toListId)

 
			/*
      		const changedListIds = [fromListId]
      		if (fromListId !== toListId) {
        		changedListIds.push(toListId)
      		}
      		console.log('changedListIds', changedListIds)
			*/
			
            this.QUESTIONS = pQuestions;
			const positionChanges = {
				surveyId: this.SURVEY_ID,
				questionListPositions: []
			}
		
			this.QUESTIONS.forEach((question, index) => {
				positionChanges.questionListPositions.push({
					questionId	: question.QUESTION_ID,
					sectionId	: question.SECTION_ID,
					position	: index
				})
			});
			console.log('positionChanges:', positionChanges);		
			
			if(this.isPositionChanged(positionChanges)) {
				console.log('****변경됨********');		
				this.dragQuestion(positionChanges);
			}

	    },
	    modifiedHtml(pOriginalHtml) {
	        // 수정된 HTML 문자열을 반환합니다.
	        let modifiedHtml = pOriginalHtml.replace('disabled = true', 'disabled = false');
	        console.log(modifiedHtml);
	        return modifiedHtml;
	     },
		 openAddQuestion(section) {
			console.log("openAddQuestion",section);
			this.SECTION    = section;
			console.log("this.SECTION",this.SECTION);

      		//this.selectModalFlag = !this.selectModalFlag;
 	     },
		 openAddComment(section) {
			console.log(section);
      		//this.selectModalFlag = !this.selectModalFlag;
    	 },
		 handleFormSubmitted(formData) {
      		// formData를 사용하여 필요한 작업을 수행합니다.
      		console.log('Submitted formData:', formData);
		 },
 		 selectedQuestionType(type) {
      		// formData를 사용하여 필요한 작업을 수행합니다.
      		console.log('type:', type);
  			this.selectModalFlag = !this.selectModalFlag;
			//this.$bvModal.hide('modal-question-select');
			this.selectedQuestionModal(type,'insert');
 		 },
 		 selectedQuestionModal(type,pmode) {
     		console.log('selectedQuestionModal type:', type);
     		console.log('selectedQuestionModal pmode:', pmode);
  
        	this.mode = pmode;
			if(type == "radio" || type == "inputRadio") {
				this.inputType="inputRadio";
				this.inputRadioModalFlag = !this.inputRadioModalFlag;
			} else if(type == "checkbox" || type == "inputCheckbox") {
				this.inputType="inputCheckbox";
				this.inputCheckboxModalFlag = !this.inputCheckboxModalFlag;
			} else if(type == "textline" || type == "inputTextline") {
				this.inputType="inputTextline";
				this.inputTextlineMoalFlag = !this.inputTextlineMoalFlag;
			} else if(type == "textarea" || type == "inputTextarea") {
				this.inputType="inputTextarea";
				this.inputTextareaModalFlag = !this.inputTextareaModalFlag;
			} else if(type == "textimage" || type == "inputImage") {
				this.inputType="inputImage";
				this.inputImageModalFlag = !this.inputImageModalFlag;
			} else if(type == "textlocation" || type == "inputLocation") {
				this.inputType="inputLocation";
				this.inputLocationModalFlag = !this.inputLocationModalFlag;
			}
    	 },
 		 inputTextlineOk() {
      		// formData를 사용하여 필요한 작업을 수행합니다.
			const inputTextline = this.$refs.inputTextlineRef;
      		const formData = inputTextline.formData;
      		console.log('inputTextlineOk:', formData);

			let aData={};
			aData.mode          = this.mode;
			aData.type          = this.inputType;
			aData.surveyId 		= this.SURVEY_ID;
            aData.section 		= this.SECTION.SECTION_ID;
            
			if(this.mode=="insert")
		    	aData.question 	= 0;
			else 
		    	aData.question 	= this.QUESTION.QUESTION_ID;
            aData.prompt 		= formData.prompt;
            aData.showDivider 	= formData.showDivider;
            aData.label 		= formData.label;
            aData.size 			= formData.visibleWidth;
            aData.maxLength		= formData.maxLength;
     		console.log('aData:', aData);
 
            
			this.addQuestion(aData);

 		 },
 		 inputRadioOk() {
      		console.log('inputRadioOk');
		 },
 		 inputCheckOk() {
      		console.log('inputCheckOk');
		 },
 		 inputTextareaOk() {
      		console.log('inputTextareaOk');
		 },
 		 inputImageOk() {
      		console.log('inputImageOk');
		 },
 		 inputLocationOk() {
      		console.log('inputLocationOk');
		 },
 		 inputCommentOk() {
      		console.log('inputCommentOk');
		 },
 		 updateQuestion(pSection,pQuestion) {
      		console.log('updateQuestion',pQuestion);

            this.SECTION   = pSection;
			this.QUESTION  = pQuestion;

            this.inputType = this.QUESTION.QUESTION_TYPE;

 		    this.inputTextlineData.prompt		= this.QUESTION.QUESTIONTEXT;
 		    this.inputTextlineData.showDivider	= this.QUESTION.SHOWDIVIDER;
 		    this.inputTextlineData.label		= this.QUESTION.QUESTION_LABEL;
 		    this.inputTextlineData.visibleWidth	= this.QUESTION.TEXTSIZE;
 		    this.inputTextlineData.maxLength	= this.QUESTION.MAXLENGTH;

            this.selectedQuestionModal(this.inputType,'update');

		 },
		 async addQuestion(pData) {
				try {
					// POST 요청을 보냅니다. URL은 실제 엔드포인트로 바꿔주세요.
					const response = await axios.post(
						'/mgt/survey/editQuestionAjax.do',
						 pData,
						 {
							headers: {
								'Content-Type': 'application/x-www-form-urlencoded'
							}
						 }
					);

					// 요청이 성공했을 때의 처리를 작성합니다.
					console.log('POST 요청이 성공했습니다.');
					console.log('response.data:', response.data);
					console.log('response.data.QUESTION:', response.data.QUESTION);
					this.QUESTION = response.data.QUESTION;

					if(pData.mode=="insert")
    					this.SECTIONS[this.SECTION.SECTION_ID].QUESTIONS.push(this.QUESTION);
                    else 
    					this.SECTIONS[this.SECTION.SECTION_ID].QUESTIONS.splice(this.QUESTION.QUESTION_ID,1,this.QUESTION);

					// 여기서 필요한 추가 작업을 수행할 수 있습니다.
				} catch (error) {
					// 요청이 실패했을 때의 처리를 작성합니다.
					console.error('POST 요청이 실패했습니다:', error);
				}
 
		 },
		 async dragQuestion(pData) {
				try {
					// POST 요청을 보냅니다. URL은 실제 엔드포인트로 바꿔주세요.
					const response = await axios.post(
						'/mgt/survey/dragQuestion',
						 pData,
						 {
							headers: {
								'Content-Type': 'application/json'
							}
						 }
					);

					// 요청이 성공했을 때의 처리를 작성합니다.
					console.log('POST 요청이 성공했습니다.');
					console.log('response.data:', response.data);


					// 여기서 필요한 추가 작업을 수행할 수 있습니다.
				} catch (error) {
					// 요청이 실패했을 때의 처리를 작성합니다.
					console.error('POST 요청이 실패했습니다:', error);
				}
 
			},
		 async dragSection(pData) {
				try {
					// POST 요청을 보냅니다. URL은 실제 엔드포인트로 바꿔주세요.
					const response = await axios.post(
						'/mgt/survey/dragSection',
						 pData,
						 {
							headers: {
								'Content-Type': 'application/json'
							}
						 }
					);

					// 요청이 성공했을 때의 처리를 작성합니다.
					console.log('POST 요청이 성공했습니다.');
					console.log('response.data:', response.data);

					this.SECTIONS.forEach((obj, index) => {
	        			obj.SECTION_ID = index; // 배열의 인덱스로 교체
						obj.QUESTIONS.forEach((item, jindex) => {
	        			    item.SECTION_ID = index; // 배열의 인덱스로 교체
      					});

      				});

					console.log('this.SECTIONS',this.SECTIONS);

					// 여기서 필요한 추가 작업을 수행할 수 있습니다.
				} catch (error) {
					// 요청이 실패했을 때의 처리를 작성합니다.
					console.error('POST 요청이 실패했습니다:', error);
				}
 
    	},
		isPositionChanged(pSurveyData) {
      		// 초기 상태와 현재 상태를 비교하여 위치가 변경되었는지 확인
            console.log("this.initialQuestions",this.initialQuestions);
            console.log("this.pSurveyData",pSurveyData.questionListPositions);

      		for (let i = 0; i < pSurveyData.questionListPositions.length; i++) {
        		if (pSurveyData.questionListPositions[i].questionId !==this.initialQuestions[i].questionId) {
          			return true;
        		}
      		}
      		return false;
    	},


	  }

	});
</script>


 
</body>

</html>
