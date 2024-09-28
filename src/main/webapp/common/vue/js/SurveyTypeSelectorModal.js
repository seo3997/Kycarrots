// SurveyTypeSelectorModal.js
import SurveyTypeSelector from '/common/vue/js/SurveyTypeSelector.js';

// SurveyTypeSelectorModal 컴포넌트를 정의합니다.
export default{
  name:'SurveyTypeSelectorModal',	
  components: {
	  SurveyTypeSelector
  },  
  template: `
  <div>
    <div class="modal-mask">
      <div class="modal-container">
        <div class="modal-header">
          <slot name="header">default header</slot>
        </div>

        <div class="modal-body">
         <survey-type-selector :surveyId="surveyId" :above="above" :sectionNr="sectionNr" :questionNr="questionNr" />
          <slot name="body"></slot>
        </div>

        <div class="modal-footer">
          <slot name="footer">
            default footer
            <button
              class="modal-default-button"
              @click="$emit('close')"
            >OK</button>
          </slot>
        </div>
      </div>
    </div>
   </div> 
    `,
  props: {
    surveyId: String,
    above: String,
    sectionNr: Number,
    questionNr: Number
  },
  data() {
    return {
      modalIsActive: false
    };
  },
  methods: {
    openModal() {
      this.modalIsActive = true;
    },
    closeModal() {
      this.modalIsActive = false;
    }
  }
}