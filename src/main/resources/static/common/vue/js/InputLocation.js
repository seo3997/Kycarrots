export default {
  name:'InputComment',	
  props: {
	   formData: {
	      type: Object,
	      required: true,
	      default: () => ({
	    	    surveyId: '1566023377421',
	            prompt: '',
	            showDivider: true,
	            label: '',
	            visibleWidth: '30',
	            maxLength: '300'
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
		      this.formData.label = '';
		      this.formData.visibleWidth = '30';
		      this.formData.maxLength = '300';
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
                <b>Label:</b> <input type="text" name="label" size="30" maxlength="100" v-model="formData.label"> <font color="#999999">(empty or any text including HTML)</font><br><br>
                <b>Visible width in characters:</b>
                <input type="text" name="size" size="3" class='span1' maxlength="3" v-model="formData.visibleWidth"> <font color="#999999">(1 to 100)</font><br><br>
                <b>Maximum number of characters:</b>
                <input type="text" name="maxLength" size="3" class='span1' maxlength="3" v-model="formData.maxLength"> <font color="#999999">(1 to 300)</font><br><br>
                <!--
                <input type="submit" class="btn btn-primary" name="save" value="OK">&nbsp;&nbsp;<input type="submit" class="btn" name="cancel" value="Cancel">
              	-->
              </form>
            </td>
          </tr>
        </table>
      </div>
      <!--/tab-content-->
      <div class="vspace-6"></div>
    </div>
    <!--/row-fluid-->
  </div>
  `
}