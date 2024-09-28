export default {
  name:'InputRadio',	
  props: {
	   formData: {
	      type: Object,
	      required: true,
	      default: () => ({
	        surveyId: '',
	        prompt: 'AAAAA',
	        showDivider: true,
	        options: 'option 1\noption 2\noption 3',
	        otherShortAnswer: false,
	        otherShortAnswerLabel: 'other:',
	        layout: 'vertical'
	      })
		}
  },
  methods: {
	   submitForm() {
		      // Emitting an event to notify the parent component
		      this.$emit('form-radiosubmitted');
		},
	    resetForm() {
			  this.formData.surveyId = '';
			  this.formData.prompt = 'AAAAA';
			  this.formData.showDivider = true;
			  this.formData.options = 'option 1\noption 2\noption 3';
			  this.formData.otherShortAnswer = false;
			  this.formData.otherShortAnswerLabel = 'other:';
			  this.formData.layout = 'vertical';
	    }
  },
  template: `
  <div id="page-content" class="clearfix">
    <div class="row-fluid">
      <div class="tab-content">
        <table cellspacing="1" cellpadding="2" width="100%" border="0">
          <tr>
            <td>
              <form @submit.prevent="submitForm">
                <input type="hidden" name="surveyId" :value="formData.surveyId">
                <b>Question prompt:</b> <font color="#999999">(empty or any text including HTML)</font><br>
                <textarea v-model="formData.prompt" wrap="physical" cols="50" class='span12' rows="3"></textarea><br><br>
                <b>Separate this item visually from the previous one?</b><br><font color="#999999">(choosing no allows you to visually merge questions or text items)</font><br>
                <div class='control-group'>
                  <div class='controls'>
                    <label>
                      <input type="radio" name="showDivider" :value="true" v-model="formData.showDivider">
                      <span class='lbl'>Yes</span>
                    </label>
                    <label>
                      <input type="radio" name="showDivider" :value="false" v-model="formData.showDivider">
                      <span class='lbl'>No</span>
                    </label>
                  </div>
                </div><br>
                <b>Multiple choice options:</b> <font color="#999999">(list one per line, minimum is 2)</font><br>
                <textarea v-model="formData.options" wrap="virtual" cols="50" class='span12' rows="3"></textarea><br>
                <br><b>Do you want an &amp;quot;other:&amp;quot; short answer field?</b><br>
                <div class='control-group'>
                  <div class='controls'>
                    <label>
                      <input type="radio" name="otherShortAnswer" :value="true" v-model="formData.otherShortAnswer">
                      <span class='lbl'>Yes, and use this label text:</span><input type="text" size="30" maxLength="100" name="otherShortAnswerLabel" v-model="formData.otherShortAnswerLabel">
                    </label>
                    <label>
                      <input type="radio" name="otherShortAnswer" :value="false" v-model="formData.otherShortAnswer">
                      <span class='lbl'>No</span>
                    </label>
                  </div>
                </div><br>
                <b>Layout:</b><br>
                <div class='control-group'>
                  <div class='controls'>
                    <label>
                      <input type="radio" name="layout" value="vertical" v-model="formData.layout">
                      <span class='lbl'>each option on a separate line</span>
                    </label>
                    <label>
                      <input type="radio" name="layout" value="horizontalTextRight" v-model="formData.layout">
                      <span class='lbl'>all options on one line</span>
                    </label>
                  </div>
                </div>
                <!--
                <br><br>
                <input type="submit" class="btn btn-primary" name="save" value="OK">&nbsp;&nbsp;<input type="submit" class="btn" name="cancel" value="Cancel">
              	-->
              </form>
            </td>
          </tr>
        </table>
      </div><!--/tab-content-->
      <div class="vspace-6"></div>
    </div><!--/row-fluid-->
  </div><!--/#page-content-->
  `
}