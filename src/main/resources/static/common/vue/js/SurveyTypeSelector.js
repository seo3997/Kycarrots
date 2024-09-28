export default {
  name:'SurveyTypeSelector',	
  template: `
    <div>
      <!--
      <h3>
        <small>&nbsp;입력양식 편집 &nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;
        <img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;질문 유형 선택
      </h3>
      <br>
      -->
      <div class="row-fluid">
        <div class="tab-content">
          <table cellspacing="1" cellpadding="2" width="100%" border="0">
            <tbody>
              <tr>
                <td bgcolor="#5D5D5D">
                  <table cellspacing="0" cellpadding="0" width="100%" border="0">
                    <tbody>
                      <tr>
                        <td style="color:#ffffff" align="center">아래에서 질문 유형 하나를 선택하세요.</td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
              <tr>
                <td bgcolor="#ffffff" valign="top" colspan="3">
                  <table cellspacing="6" cellpadding="3" width="302" border="0">
                    <tbody>
                      <tr v-for="(question, index) in questionTypes" :key="index">
                        <td valign="top" nowrap="" align="left">
                          <a href="#" @click="handleClick(question.type)">
                            {{ question.label }}<br>
                            <img :src="question.image" :alt="question.label" :width="question.width" :height="question.height">
                          </a>
                          <br>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
            </tbody>
          </table>
        </div><!--/tab-content-->
      </div><!--/row-fluid-->
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
      questionTypes: [
        { type: 'radio', label: 'Multiple choice - only one response allowed', image: '/common/img/questiontype_radio.gif', width: '39', height: '60' },
        { type: 'checkbox', label: 'Check all that apply', image: '/common/img/questiontype_checkbox.gif', width: '39', height: '60' },
        { type: 'textline', label: 'Short answer - one line', image: '/common/img/questiontype_textline.gif', width: '120', height: '32' },
        { type: 'textarea', label: 'Comment/Essay question', image: '/common/img/questiontype_textarea.gif', width: '145', height: '48' },
        { type: 'textimage', label: 'Input Image', image: '/common/img/questiontype_textline.gif', width: '120', height: '32' },
        { type: 'textlocation', label: 'Input Location', image: '/common/img/questiontype_textline.gif', width: '120', height: '32' }
      ]
    };
  },
  methods: {
    handleClick(type) {
        this.$emit('question-selected', type);
    }
  }
}