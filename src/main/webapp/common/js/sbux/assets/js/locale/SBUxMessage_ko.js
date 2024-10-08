(function() {
    SBUxG.DEF.MSG = {
        // LICENSE
        NOT_FOUND_LICENSE: '[SBUx] 라이선스가 등록되지 않았습니다.',
        INVALID_LICENSE: '[SBUx] 라이선스가 유효하지 않습니다.',
        EXPIRED_LICENSE: '[SBUx] 사용기간이 만료되었습니다.',
        UNMATCH_VER_LICENSE: '[SBUx] 버전이 올바르지 않습니다.',

        // COMMON
        NOT_DEV_API: '[SBUx] SBUxMethod.${attr} API 는 미개발된 기능입니다.',
        NOT_DEV_FUNC: '[SBUx] ${attr} 컴포넌트의 ${attr1} 는 미개발된 기능입니다.',
        NOT_FOUND_TAG: '[SBUx] ${attr} 명인 태그가 존재하지 않습니다.',
        NOT_ENTERED_INVALID_TAG: '[SBUx] ${attr} 태그명이 잘못되었습니다.',
        NOT_ENTERED_ATTR: '[SBUx] ${attr} 속성이 입력되지 않았습니다.',
        NOT_PROVIDE_ATTR: '[SBUx] ${attr} 속성의 기능은 제공하지 않습니다.',
        NOT_PROVIDE_ATTR_ATTR: '[SBUx] ${attr} 에서 <br> ${attr1} 는 <br>제공하지 않습니다.',
        NOT_PROVIDE_FUNC: '[SBUx] ${attr} 을 바로 사용할 수 없습니다.',
        NOT_ENTERED_ID: '[SBUx] id 가 입력되지 않았습니다.',
        NOT_ENTERED_NAME: '[SBUx] name 이 입력되지 않았습니다.',
        NOT_ENTERED_UITYPE: '[SBUx] uitype 이 입력되지 않았습니다.',
        NOT_ENTERED_MODE: '[SBUx] mode 가 입력되지 않았습니다.',
        NOT_ENTERED_REQ_ATTRI: '[SBUx] 필수 속성이 입력되지 않았습니다.',
        NOT_EXIST_FORMNAME: '[SBUx] Form Node 에 대한 name 이 있어야 합니다.',
        NOT_EXIST_FORMID: '[SBUx] Form Node 에 대한 id 가 있어야 합니다.',
        OVER_PARAMS: '[SBUx] 파라메터 개수는 최대 10개까지 지원합니다.',
        INVALID_UITYPEVALUE: '[SBUx] 잘못된 uitype 속성 값이 입력되었습니다. 철자를 확인해 주세요.',
        INVALID_ATTRIVALUE: '[SBUx] 잘못된 속성 값이 입력되었습니다. 철자를 확인해 주세요.',
        INVALID_ATTRIVALUE_COUNT: '[SBUx] 부족한 속성값이 입력되었습니다.',
        INVALID_ATTRIVALUE_SPACE: '[SBUx] 공백이거나 스페이스는 허용하지 않습니다.',
        INVALID_ATTRIVALUE_UNDEFINED: '[SBUx] Undefined 는 허용하지 않습니다.',
        MUST_ATTRIVALUE : '[SBUx] ${attr} 속성값으로 설정되어 있어야 합니다.',

        NOT_FOUND_TEXT: '검색된 결과가 없습니다.',

        INFORMATION_TEXT : '알려드립니다',

        // MESSAGE
        MESSAGE_NOT_FOUND_NAME: '[SBUx] 참조할 수 있는 name 이 없습니다.',
        MESSAGE_NOT_FOUND_ID: '[SBUx] 참조할 수 있는 id 가 없습니다.',

        // VALIDATE
        MESSAGE_ONLY_NUMBER: '[SBUx] 숫자가 입력되어야 합니다.',
        MESSAGE_ONLY_STRING: '[SBUx] 문자가 입력되어야 합니다.',
        MESSAGE_ONLY_BOOLEAN: '[SBUx] true 또는 false 가 입력되어야 합니다.',
        MESSAGE_ONLY_PHONE_NUMBER: '[SBUx] 전화번호는 숫자만 입력하셔야 합니다.',
        MESSAGE_NOT_ALLOWED_RANGE: '[SBUx] 허용된 범위를 벗어났습니다.',
        MESSAGE_NOT_ALLOWED_DIGIT: '[SBUx] 소수점은 허용치 않습니다.',
        MESSAGE_NOT_ALLOWED_MINUS: '[SBUx] 마이너스값은 허용치 않습니다.',
        MESSAGE_NOT_ALLOWED_STRING: '[SBUx] 문자는 허용치 않습니다.',
        MESSAGE_NOT_ALLOWED_SPACE: '[SBUx] 공백은 허용치 않습니다.',

        // DATEPICKER
        DATEPICKER_INPUT_FROM_TEXT : '시작일',
        DATEPICKER_INPUT_TO_TEXT : '종료일',

        // RADIO
        RADIO_NO_DATA: '미지정',

        // CHECKBOX
        CHECKBOX_NO_DATA: '미지정',

        // SELECT BOX
        SELECT_NO_DATA: '-없음-',
        SELECT_UNSELECTED: '-선택하세요-',
        IS_SELECT_ALL_TEXT: '전체 선택',
        TITLE_SELECT_MAX_TEXT: '개 선택됨',
        TITLE_SELECT_ALL_TEXT: '전체 선택됨',
        SELECT_MAX_COUNT_TEXT : '${attr} 개 이상 선택할 수 없습니다.',

        // LISTBOX
        LISTBOX_NO_DATA: '데이터가 존재하지 않습니다.',

        // TREE
        SELECT_NODE_NEED_TITLE: 'Tree Info',
        SELECT_NODE_NEED: '노드가 선택되지 않았습니다',
        INVALID_NODE_INFO :'올바르지 않은 노드 정보입니다.',
        AVAILABLE_JSONDATA_TYPE: 'JsonData 형태만 가능합니다',
        NEW_TREE_NODE_TEXT: '신규 노드',
        NEW_TREE_NODE_CREATION_FAILED : '노드 생성에 실패하였습니다.',
        NEW_TREE_NODE_VALUE: '',
        INFO_DELETE_CHILD_NODE: '하위노드도 함께 삭제됩니다.',
        INFO_DELETE_NODE: '노드를 삭제합니다.',
        NODE_DUPLICATION_KEY: '노드의 중복키가 발생하였습니다.제품사에 문의바랍니다.',
        TREE_NO_DATA : '데이터가 존재하지 않습니다.',

        // ALERT
        CONFIRM_OK: '확인',
        CONFIRM_CANCEL: '취소',
        CLOSE_ON_FOOTER : '확인',

        // DROPDOWN TEXT
        DROPDOWN_TEXT: '선택하세요',

        // COLSE TEXT
        CLOSE_TEXT: '닫기',

        // TAB TEXT
        TAB_MENU_LIST_DEL_CONFIRM_TITLE : 'Tab Info',
        TAB_MENU_LIST_DEL_ALL_DESC : '전체 탭을 닫으시겠습니까?',
        TAB_MENU_LIST_DEL_FOCUS_DESC : '현재 탭을 닫으시겠습니까?',
        TAB_MENU_LIST_DEL_EXCEPT_FOCUS_DESC : '현재 탭을 제외한 탭을 닫으시겠습니까?',
        TAB_MENU_LIST_DEL_EXCEPT_FIXED_DESC : '고정탭을 제외한 탭을 닫으시겠습니까?',

        TAB_DONT_CLOSE : '다른 탭이 모두 비활성 상태입니다.',
        TAB_OPEN_NOTFOUND_LINK : '연결된 화면이 존재하지 않습니다.',
        TAB_DONT_REMOVE : '삭제할 탭이 존재하지 않습니다.',
        TAB_MENU_LIST_DEL_ALL : '전체 탭 닫음',
        TAB_MENU_LIST_DEL_FOCUS : '현재 탭 닫음',
        TAB_MENU_LIST_DEL_EXCEPT_FOCUS : '현재 탭을 제외한 탭 닫음',
        TAB_MENU_LIST_DEL_EXCEPT : '를 제외한 탭 닫음',
        TAB_MENU_DONT_REMOVE : '닫을 수 없는 탭 입니다.',
        TAB_MENU_CLOSE_CONFIRM_TEXT : '',

        // PROGRESS BAR
        PROGRESS_NOT_ALLOWED_TWOBAR : '"indicator-type" 속성이 "normal" 값인 경우는 sbux-bar tag 를 하나만 설정하셔야 합니다.',
        PROGRESS_LOADING_TEXT : 'LOADING',

        // CONTEXT MENU
        CTXT_MENU_DATA_COLLECT : '[SBUx] Child nodes 의 데이터 취합에서 오류가 발생하였습니다.',
        CTXT_MENU_INVALID_SELECTOR : '[SBUx] ${attr} 의 Selector 지정을 확인하시기 바랍니다.',

        // SBGRID
        NOT_ENTERED_COLUMS: '[SBUx] 참조할 수 있는 컬럼정보가 없습니다.',
        NOT_ENTERED_STYLE : '[SBUx] 그리드의 너비와 폭을 정하는 스타일 정보가 없습니다.',
        NO_ROWS_SELECTED : '[SBUx] 입력될 데이터의 행을 선택하셔야 합니다.',

        // Required
        IE9_NOT_ENTERED_REQ_COMMON: '필수사항입니다.',
        IE9_NOT_ENTERED_REQ_INPUT: '이 입력란을 작성하세요.',
        IE9_NOT_ENTERED_REQ_SELECT: '목록에서 항목을 선택하세요.',
        IE9_NOT_ENTERED_REQ_CHECK: '이 확인란을 선택해야 합니다.',
        IE9_NOT_ENTERED_REQ_RADIO: '옵션을 선택해야 합니다.',
        IE9_NOT_ENTERED_REQ_PICKER: '일자를 선택하세요.',
        IE9_NOT_ENTERED_REQ_DROPDOWN: '목록에서 항목을 선택하세요.',

        // Minlength
        IE_MINLENGTH_REQ_INPUT : '이 텍스트를 *자 이상으로 늘리세요(현재 # 자 사용중).',

        // integer maxlength
        INTEGER_MAXLENGTH_REQ_INPUT : '정수값을 *자 이하로 제한합니다(현재 # 자 사용중)',
        INTEGER_MINLENGTH_REQ_INPUT : '정수값을 *자 이상으로 늘리세요(현재 # 자 사용중).',

        // DATA STORE
        DATA_RECEIVE_LOADING_TITLE: '데이터 로딩중',
        DATA_RECEIVE_LOADING_TEXT: '잠시만 기다려 주세요',
        DATA_RECEIVE_ERROR_TITLE: '데이터 로딩 에러',
        DATA_RECEIVE_ERROR_TEXT: '세션이 종료되었거나 네트워크 문제가 발생하였습니다.',
        DATA_SEND_JSONDATA_ERROR: '[SBUx] 문법오류가 있는 Send Data 입니다.',
        DATA_NETWORK_ERROR_TITLE: '네크워크 연결 에러',
        DATA_NETWORK_ERROR_TEXT: '네크워크 연결에 문제가 발생하였습니다.',

        // COMMON ERROR_MSG
        JSONDATA_MUST_HAVE: 'jsondata-ref 속성은 필수입니다.',
        JSONDATA_NOT_FOUND: 'jsondata-ref 에 매핑된 객체를 찾을 수 없거나 값이 없는 상태입니다.',
        JSONDATA_ALREADY_CHANGED: 'json 형태로 이미 변경되어 있습니다. datastore-data-type="json" 속성을 제거하여 주십시오.',

        // SBGrid
        SBGRID21_NOT_IMPORT: '[SBUx] SBGrid 2.1 파일을 Load 하지 못하였습니다. SBUxConfig 설정에서 SBGrid : { Version2_1 : true } 를 추가하십시오.',
        SBGRID25_NOT_IMPORT: '[SBUx] SBGrid 2.5 파일을 Load 하지 못하였습니다. SBUxConfig 설정에서 SBGrid : { Version2_5 : true } 를 추가하십시오.',

        // Carousel
        CAROUSEL_NOT_FOUND : '표시할 슬라이드가 없습니다.',

        // Side menu
        SIDEMENU_NO_DATA : '데이터가 존재하지 않습니다.',
        SIDEMENU_FILTER_PLACEHOLDER : '메뉴명 검색',

        // floating
        FLOATING_NO_DATA : '데이터가 존재하지 않습니다.',
        FLOATING_FILTER_PLACEHOLDER : '메뉴명 검색',

        // Fileupload
        HEADER_TITLE : '첨부파일목록',
        HEADER_FILENAME : '파일명',
        ADD_FILE : '추가',
        CANCEL_FILE : '취소',
        UPLOAD_ALL_FILE : '전체업로드',
        DELETE_FILE : '삭제',

        // Web Editor Button
        EDITOR_BUTTON_PARAGRAPH : '문단구분',
        EDITOR_BUTTON_BOLD   : '굵게',
        EDITOR_BUTTON_UNDERLINE  : '밑줄',
        EDITOR_BUTTON_ITALIC : '기울임',
        EDITOR_BUTTON_STROKE : '취소선',
        EDITOR_BUTTON_BULLET_LIST : '불릿리스트',
        EDITOR_BUTTON_NUM_LIST : '순서리스트',
        EDITOR_BUTTON_PICTURE : '이미지 링크',
        EDITOR_BUTTON_LINK : '주소 링크',
        EDITOR_BUTTON_CLEAN : '태그 제거',
        EDITOR_BUTTON_PREVIEW : '미리보기',

        EDITOR_BUTTON_PICTURE_MSG : '이미지 주소',
        EDITOR_BUTTON_PICTURE_ALT_MSG : '도움말',
        EDITOR_BUTTON_LINK_MSG : '링크 주소',

        // image viewer
        IMAGEVIEWER_UNTITLED : '무제',

        // web accessibility
        WEB_ACCESS_UNTITLED : '미정',

        // localstorage
        TO_USE_LOCALSTORAGE : '[SBUx] SBUxConfig 설정에서 SystemLogType : "storage" 또는 DeveloperTipType : "storage" 를 사용하려면, 주소 표시 줄에서 http 또는 https로 시작해야 합니다. 시스템이 동작하기 위해 "콘솔" 유형으로 변경합니다.'
    };

    SBUxG.DEF.MSG_KOR = {
        RESIDENT_WRONG_NUMERIC: '주민등록번호를 숫자로 입력하세요',
        RESIDENT_WRONG_FRONT_NUMERIC: '주민등록번호 앞자리를 확인하세요',
        RESIDENT_WRONG_NUMERIC_DIGIT: '주민등록번호 자릿수를 확인하세요',
        RESIDENT_WRONG: '주민등록번호를 확인하고 다시 입력하세요'
    };

    SBUxG.DEF.MSG_VALIDATE = {
        INPUT : '입력 조건을 확인해 주세요',
        DATEPICKER : '날짜 조건을 확인해 주세요',
        RADIO : '선택 조건을 확인해 주세요',
        CHECKBOX : '선택 조건을 확인해 주세요',
        SELECT : '선택 조건을 확인해 주세요',
        TEXTAREA : '입력 조건을 확인해 주세요',
        LISTBOX : '선택 조건을 확인해 주세요',
        DROPDOWN : '선택 조건을 확인해 주세요'
    };
}());