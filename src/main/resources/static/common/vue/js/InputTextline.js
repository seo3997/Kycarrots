export default {
  name:'InputTextline',	
  props: {
    formData: {
      type: Object,
      required: true,
      default: () => ({
        prompt: '',
        showDivider: '1',
        label: '',
        visibleWidth: '30',
        maxLength: '300'
      })
    }
  },
  methods: {
    QuestionOk() {
      this.$emit('form-submitted', this.formData);
    },
    resetForm() {
      this.formData.prompt = '';
      this.formData.showDivider = '1';
      this.formData.label = '';
      this.formData.visibleWidth = '30';
      this.formData.maxLength = '300';
    }
  },
  template: `
    <div id="page-content" class="clearfix">
	  <!--
      <div>
        <h3>
          <small>&nbsp;입력양식 편집 &nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;
          <img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;Edit
        </h3>
      </div>
      <br>
      -->
      <div class="row-fluid">
        <div class="tab-content">
          <table cellspacing="1" cellpadding="2" width="100%" border="0">
            <tr>
              <td>
                <h2>Short Answer - one line</h2>
                Hint: You can leave Question prompt empty to make a short answer field; seem to belong to the previous question, e.g. first name, last name.
                <b>Question prompt:</b> <font color="#999999">(empty or any text including HTML)</font><br>
                <textarea v-model="formData.prompt" wrap="physical" cols="50" class="span12" rows="3"></textarea><br><br>
                <b>Separate this item visually from the previous one?</b><br><font color="#999999">(choosing no allows you to visually merge questions or text items)</font><br>
                <div class="control-group">
                  <div class="controls">
                    <label>
                      <input type="radio" name="showDivider" value="1" checked v-model="formData.showDivider">
                      <span class="lbl">Yes</span>
                    </label>
                    <label>
                      <input type="radio" name="showDivider" value="0" v-model="formData.showDivider">
                      <span class="lbl">No</span>
                    </label>
                  </div>
                </div><br>
                <b>Label:</b> <input type="text" name="label" size="30" maxLength="100" v-model="formData.label"> <font color="#999999">(empty or any text including HTML)</font><br><br>
                <b>Visible width in characters:</b>
                <input type="text" name="size" class="span1" size="3" maxLength="3" v-model="formData.visibleWidth"> <font color="#999999">(1 to 100)</font><br><br>
                <b>Maximum number of characters:</b>
                <input type="text" name="maxLength" size="3" class="span1" maxLength="3" v-model="formData.maxLength"> <font color="#999999">(1 to 300)</font><br><br>
                <!--
                <button class="btn btn-primary" @click="QuestionOk">OK</button>&nbsp;&nbsp;
                <button class="btn" @click="resetForm">Cancel</button>
                -->
              </td>
            </tr>
          </table>
        </div>
        <!--/tab-content-->
        <div class="vspace-6"></div>
      </div>
      <!--/row-fluid-->
    </div>
    <!--/#page-content-->
  `
}