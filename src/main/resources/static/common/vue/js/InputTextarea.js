export default {
  name:'InputTextarea',	
  props: {
	   formData: {
	      type: Object,
	      required: true,
	      default: () => ({
	    	    surveyId: '1566023377421',
	            prompt: '1aaaaaaa',
	            showDivider: true,
	            cols: 60,
	            rows: 3
	       })
		}
  },
  methods: {
	   submitForm() {
		      // Emitting an event to notify the parent component
		      this.$emit('form-textareasubmitted');
		},
	    resetForm() {
			   this.formData.prompt = '';
			      this.formData.showDivider = true;
			      this.formData.cols = 50;
			      this.formData.rows = 3;
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
                <textarea v-model="formData.prompt" wrap="physical" cols="50" :rows="3" class="span12"></textarea><br><br>
                <b>Separate this item visually from the previous one?</b><br><font color="#999999">(choosing no allows you to visually merge questions or text items)</font><br>
                <div class="control-group">
                  <div class="controls">
                    <label>
                      <input type="radio" name="showDivider" value="1" v-model="formData.showDivider">
                      <span class="lbl">Yes</span>
                    </label>
                    <label>
                      <input type="radio" name="showDivider" value="0" v-model="formData.showDivider">
                      <span class="lbl">No</span>
                    </label>
                  </div>
                </div><br>
                <b>Width in characters:</b>
                <input type="text" name="cols" v-model="formData.cols" size="3" class="span1" maxlength="3"> <font color="#999999">(10 to 100)</font><br><br>
                <b>Number of lines:</b>
                <input type="text" name="rows" v-model="formData.rows" size="2" class="span1" maxlength="2"> <font color="#999999">(2 to 50)</font><br><br>
                <!--
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