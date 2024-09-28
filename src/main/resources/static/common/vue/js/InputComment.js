export default {
  name:'InputComment',	
  props: {
	   formData: {
	      type: Object,
	      required: true,
	      default: () => ({
	    	  surveyId: '1566023377421',
	          comment: '',
	          showDivider: true
	       })
		}
  },
  methods: {
	   submitForm() {
		      // Emitting an event to notify the parent component
		      this.$emit('form-textareasubmitted');
		},
	    resetForm() {
			this.formData.comment = '';
		    this.formData.showDivider = true;
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
                <b>Text:</b> 
                <input type="radio" name="texttype" value="plain" checked> Plain Text <br>
                <textarea v-model="formData.comment" wrap="virtual" cols="50" class='span12' rows="15"></textarea>
                <br><br>
                <b>Separate this item visually from the previous one?</b><br>
                <font color="#999999">(choosing no allows you to visually merge questions or text items)</font><br>
                <div class='control-group'>
                  <div class='controls'>
                    <label>
                      <input type="radio" name="showDivider" value="1" v-model="formData.showDivider">
                      <span class='lbl'>Yes</span>
                    </label>
                    <label>
                      <input type="radio" name="showDivider" value="0" v-model="formData.showDivider">
                      <span class='lbl'>No</span>
                    </label>
                  </div>
                </div><br>
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