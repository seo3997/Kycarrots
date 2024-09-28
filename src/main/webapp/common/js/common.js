/**
 * Copyright (c) 2000 by LG-EDS Systems Inc
 * All rights reserved.
 *
 * 자바스크립트 공통함수
 *
 * 주의: 아래의 모든 메소드는 입력폼의 필드이름(myform.myfield)을
 *       파라미터로 받는다. 필드의 값(myform.myfield.value)이 아님을
 *       유념할 것.
 *
 * @version 1.1, 2000/10/06
 * @author 박종진(JongJin Park), ecogeo@dreamwiz.com
 */

//로딩바 이벤트 (로딩 on)
window.onbeforeunload = function(e){
    if(e != null && e != undefined){
    	//파일 다운로드 예외처리
    	if(document.getElementById('aform') != null && document.getElementById('aform').action.indexOf('file/FileDown.do') == -1){
    		wrapLoadingMask('show');
    	}
    }
};

$(function(){
	//로딩바 이벤트 (로딩 off)
	wrapLoadingMask('hide');
	//숫자만 입력 받도록 함.
	$('.numeric').css('imeMode','disabled').keypress(function(event) {
			if(event.which && (event.which < 48 || event.which > 57) ) {
			event.preventDefault();
		}
	}).keyup(function(){
		if( $(this).val() != null && $(this).val() != '' ) {
		 $(this).val( $(this).val().replace(/[^0-9]/g, '') );
		}
	});
	
	//숫자와 '-' 만 입력 받도록 함.
	$('.numericdash').css('imeMode','disabled').keypress(function(event) {
			if(event.which && event.which != 45 && (event.which < 48 || event.which > 57) ) {
			event.preventDefault();
		}
	}).keyup(function(){
		if( $(this).val() != null && $(this).val() != '' ) {
		 $(this).val( $(this).val().replace(/[^0-9\-]/g, '') );
		}
	});
	
	
	
	/**
	 * ajax 기본 호출시 셋팅 
	 */
	$.ajaxSetup({
		// 호출전에 request 해더에 'AJAX'를 추가하여 보낸다.
		beforeSend : function(xhr){
			//ajax 로딩바 이벤트 (로딩 on)
			//wrapLoadingMask('show');
			xhr.setRequestHeader('AJAX', true);
		},
		complete : function(xhr,status){
			//ajax 로딩바 이벤트 (로딩 off)
			//wrapLoadingMask('hide');
		}
	});
	
	// iCheck 적용
	/*
	$('.icheck input').iCheck({
		checkboxClass: 'icheckbox_flat-green',
		radioClass: 'iradio_flat-green'
	});
	*/


	// 콘텐츠 탭
	var triggerTabList = [].slice.call(document.querySelectorAll('#myTab a'))
	triggerTabList.forEach(function (triggerEl) {
		var tabTrigger = new bootstrap.Tab(triggerEl);
		triggerEl.addEventListener('click', function (event) {
			event.preventDefault();
			tabTrigger.show();
		});
	});



});

function nvl(obj, val) {
		
	if (obj == null || obj == "null") {
		// 두번째 매개변수가 없을경우 빈값으로 설정
		return val === undefined ? "" : val;
	}
	else{
		return obj;
	}
}

/**
 * 3자리 마다 콤마를 찍어준다
 */
function setComma(value) {
  value=value+"";
  var reg = /(^[+-]?\d+)(\d{3})/;
  var n = value;
  while(reg.test(n)) {
   n = n.replace(reg, '$1' + ',' + '$2');
  }
  return n;
}

function removeComma(str)
{
	str = str+"";
	n = str.replace(/,/g,"");
	return n;
}



//파일 업로드시 제외되어야할 확장자들
function f_CheckExceptFileExt(objId){	//input id
	
	var exceptExtNames = "EXE,BAT,COM,JSP,ASP,HTML,PHP,SH";	//제외될 파일 확장자
	var fileName = document.getElementsByName(objId);	//파일정보
	var checkMsg = "";
	
	for(i = 0; i < fileName.length; i++){
		
		if(fileName[i].value.length != 0){
			
			if(exceptExtNames.indexOf((fileName[i].value.substring(fileName[i].value.lastIndexOf(".")+1, fileName[i].value.length)).toUpperCase()) >= 0){
				
				checkMsg = fileName[i].value.substring(fileName[i].value.lastIndexOf(".")+1, fileName[i].value.length) + " 확장자 파일은 첨부할 수 없습니다.";
				break;
			}
		}
	}

	return checkMsg;
}

//파일 업로드시 허용되는 확장자들
function f_CheckAcceptFileExt(objId, extList){	//input id

	var exceptExtNames = extList.toUpperCase();	//제외될 파일 확장자
	var fileName = document.getElementsByName(objId);	//파일정보
	var checkMsg = "";
	
	for(i = 0; i < fileName.length; i++){
		
		if(fileName[i].value.length != 0){
			
			if(exceptExtNames.indexOf((fileName[i].value.substring(fileName[i].value.lastIndexOf(".")+1, fileName[i].value.length)).toUpperCase()) == -1){
				
				checkMsg = fileName[i].value.substring(fileName[i].value.lastIndexOf(".")+1, fileName[i].value.length) + " 확장자 파일은 첨부할 수 없습니다.";
				break;
			}
		}
	}
	return checkMsg;
}

function goHistoryBack(num){
	if(num == '' || num ==  null){ num = -1; }
	history.go(num);
}

function fnGoUrl(url){
	location.href = url;
}

function commify(n) {
	var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
	n += '';                          // 숫자를 문자열로 변환
	while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
	return n;
}

/**
 * 여러개의 첨부파일 양식을 그릴경우 같은 obj 안에서는 다시 그릴경우는 폼양식이 갱신이 되며
 * 새로운 곳은 sel_file_box[숫자], input_file_wrap[숫자] 이런식으로 증가하게 되며 file name인 upload만 초기는 숫자가 없고 그 이후로 upload[숫자] 형식으로 naming 이 된다.
 * 
 * @param obj : 파일양식 그리영역
 * @param num : 현재 첨부파일 개수
 * @param max_count : 최대 첨부파일 개수
 */
function fnComboStrFile(obj, num, max_count){
	// 기본 값
	var ii = '0';
	// select박스 id, 파일박스 id
	var sel_id = 'sel_file_box';
	var file_box_id = 'input_file_wrap';

	var tag_n = 'upload';
	
	// 해당 obj영역에 첨부파일 양식이 있는지 확인
	// 양식이 있는경우
	if($(obj).find('.sel_file_box').length > 0){
		sel_id = $(obj).find('.sel_file_box').attr('id');
		file_box_id = $(obj).find('.input_file_wrap').attr('id');
		tag_n = $(obj).find('.input_file_wrap input[type=file]').attr('name');
	}
	// 없는경우
	else {
		// 총 첨부파일 양식의 숫자를 구한다.
		ii = $('.sel_file_box').length;
		
		// id 값에 숫자를 추가한다.
		sel_id += ii;
		file_box_id += ii;
		// 폼양식이 하나 이상일경우에만 upload에 숫자를 붙여준다.
		if(ii > 0){
			tag_n += ii;
		}
	}
	
	// 기존에 있는 이벤트를 제거한다.
	$('#' + sel_id).off('change');
	
	var mc = max_count ? max_count : MAX_FILE_COUNT;
	var n = mc - num;
	var html = '';
	
	// 첨부파일 최대개수일때
	if(mc == num){
		html += '<span class="no_file_text">더이상 등록하실수 없습니다.</span>';
	}
	else {
//		html += '<select id="' + sel_id + '" class="selectA sel_file_box">';
		html += '<select id="' + sel_id + '" class="form-control custom">';
		
		for(var i = 1; i <= n ; i++){
			html += '<option value="' + i + '">' + i + '</option>';
		}
		
		html += '</select>';
		html += '<div id="' + file_box_id + '" class="input_file_wrap">';
		html += '	<input type="file" name="' + tag_n + '" />';
		html += '</div>';
	}
	
	$(obj).html(html);
	
	// 첨부파일 개수 변경 이벤트
	$('#' + sel_id).on({
		'change' : function(e){
			var n = $(this).val();
			var txt = '';
			for(var i = 0; i < n; i++){
				txt += '<input type="file" name="' + tag_n + '" /><br/>';
			}
			
			$('#' + file_box_id).html(txt);
		}
	});
}

// 정상적으로 json 호출이 되었지만 code error로 떨어질경우 해당 함수를 호출한다.
function ajaxErrorMsg(response){
	alert(response.resultStats.resultMsg);
}

function f_getList(objId){

	var objList = document.getElementsByName(objId);
	return objList;
}

/**
 * @type   : function
 * @access : public
 * @desc   : input 필드나 textarea 의 사이즈를 제한하여 지정된 길이 이상
 *           입력할 경우 추가입력된 메세지를 출력하고 추가 입력된 문자는 삭제함
 *
 * <xmp>
 *  <INPUT TYPE="text" NAME="txtDesc" OnKeyUp="cfLengthCheck(this, 100);">
 *  <TEXTAREA name="txtDesc" rows="10" cols="60" OnKeyUp="cfLengthCheck(this, 4000);"></TEXTAREA>
 * </xmp>
 * @param  : targetObj - Textarea Object
 * @param  : maxLength - Max Length(영문기준)
 * @return :
 */
function cfLengthCheck(targetContext, targetObj, maxLength) {
    var len = 0;
    var newtext = "";
    for(var i=0 ; i < targetObj.value.length; i++) {
        var c = escape(targetObj.value.charAt(i));

        if ( c.length == 1 ) len ++;
        else if ( c.indexOf("%u") != -1 ) len += 2;
        else if ( c.indexOf("%") != -1 ) len += c.length/3;

        if( len <= maxLength ) newtext += unescape(c);
    }

    if ( len > maxLength ) {

        alert(targetContext+" "+maxLength+" 자를 초과할 수 없습니다.");
        //cfAlertMsg(JMSG_COMF_ERR_007, [maxLength]);
        targetObj.value = newtext;
        targetObj.focus();
        return false;
    }
}

function fnSetNumeric(){
	//숫자만 입력 받도록 함.
	$('.numeric').css('imeMode','disabled').keypress(function(event) {
			if(event.which && (event.which < 48 || event.which > 57) ) {
			event.preventDefault();
		}
	 }).keyup(function(){
		if( $(this).val() != null && $(this).val() != '' ) {
		 $(this).val( $(this).val().replace(/[^0-9]/g, '') );
		}
	 });
}

/*
 * code 관련 함수
 */

function getComboStr(resourceCodeList, valueColName, nameColName, selectedDtlCd, addOptionType){
	var rstlStr = '';
	var selStr = '';
	
	if (addOptionType == 'C') {	//Choice
		rstlStr = '<option value="">선택하세요</option>';
	}
	else if (addOptionType == 'A') {	//All
		rstlStr = '<option value="">전체</option>';
	}
	
	for(var i = 0; i < resourceCodeList.length; i++){
		selStr = "";
		var tmpMap = resourceCodeList[i];
		
		if(eval('tmpMap.' + valueColName) == selectedDtlCd)
			selStr = "selected='selected'";
		
		rstlStr += "<option value='"+eval('tmpMap.' + valueColName)+"' "+selStr+">"+eval('tmpMap.' + nameColName)+"</option>";
	}
	
	return rstlStr;
}

// 리스트에서 해당 코드 이름 조회
function getComboName(resourceCodeList, valueColName, nameColName, selectedDtlCd){
	var rstlStr = "";
	var selStr = "";
	
	for(var i = 0; i < resourceCodeList.length; i++){
		selStr = "";
		var tmpMap = resourceCodeList[i];
		if(eval('tmpMap.' + valueColName) == selectedDtlCd){
			rstlStr = eval('tmpMap.' + nameColName);
		}
	}
	
	return rstlStr;
}

//1024 * 1024 = 1M
var FILE_MAX_SIZE = 524288000;
var IMG_MAX_SIZE = 10485760;

// 첨부파일 사이즈 계산
function f_CheckFileSize(objId, type){
	var fileName = document.getElementsByName(objId);	//파일정보
	var checkMsg = "";
	
	for(i = 0; i < fileName.length; i++){
		if(fileName[i].value.length != 0){
			
			// file size를 구해올수 있는경우 최신 브라우저는 구해올수 있음
			if(fileName[i].files){
				// 이미지 형식인경우
				if(type == 'image'){
					if(fileName[i].files[0].size > IMG_MAX_SIZE){
						checkMsg = '이미지는 최대 10M 를 넘을수 없습니다.';
						break;
					}
				}
				// 그외인경우
				else {
					if(fileName[i].files[0].size > FILE_MAX_SIZE){
						checkMsg = '파일은 최대 500M 를 넘을수 없습니다.';
						break;
					}
				}
			}
		}
	}
	return checkMsg;
}

//검수자 조회
function fnGetProjectCatList(pLcodeStr,pMcodeStr,pScode,pSelectedDtlCd,pAddOptionType){
	var param = { 
			'lcode' : pLcodeStr, 
			'mcode' : pMcodeStr 
	};
	
	$.ajax({
		url : '/common/selectProjectCatListAjax.do',
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				/*
				$.each(results,function(index, value){
				    console.log('My array has at position ' + index + ', this value: ' + results[index].SCLAS_CODE);
				});
				*/
				pScode
			    .find('option')
			    .remove();
				
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].SCLAS_CODE,
				        text : results[i].SCLAS_NM 
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetSCodeList***********************");
		}
	});
}

//해당 회사의 프로젝트만 조회 AJAX 
function fnGetProjectList(company_id,pScode,pSelectedDtlCd,pAddOptionType){	
	var param = { 
			'company_id' : company_id
	};
	
	$.ajax({
		url : '/common/selectProjectListAjax.do',
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				/*
				$.each(results,function(index, value){
				    console.log('My array has at position ' + index + ', this value: ' + results[index].SCLAS_CODE);
				});
				*/
				pScode
			    .find('option')
			    .remove();
		
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].CODE,
				        text : results[i].CODE_NM 
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetSCodeList***********************");
		}
	});
}

//업체별 사용자 리스트 AJAX
function fnGetUserList(company_id,pScode,pSelectedDtlCd,pAddOptionType){	
	var param = { 
			'company_id' : company_id
	};
	
	$.ajax({
		url : '/common/selectUserListAjax.do',
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				
				$.each(results,function(index, value){
				    //console.log('My array has at position ' + index + ', this value: ' + results[index].SCLAS_CODE);
				});
				
				pScode
			    .find('option')
			    .remove();
		
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].CODE,
				        text: results[i].CODE+" ("+results[i].CODE_NM+")"
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetUserList***********************");
		}
	});
}


//업체별 카테고리 리스트 AJAX
function fnGetCompanyCodeList(param,url,pScode,pSelectedDtlCd,pAddOptionType){	
	
	$.ajax({
		url : url,
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				
				pScode
			    .find('option')
			    .remove();
		
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].CODE,
				        text: results[i].CODE_NM
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetCompanyCodeList***********************");
		}
	});
}


//사용자의 권한 가져오는 AJAX
function fnGetUserAuthorList(user_id,pScode,pSelectedDtlCd,pAddOptionType){	
	var param = { 
			'user_id' : user_id
	};
	
	$.ajax({
		url : '/common/selectUserAuthorListAjax.do',
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				/*
				$.each(results,function(index, value){
				    console.log('My array has at position ' + index + ', this value: ' + results[index].SCLAS_CODE);
				});
				*/
				pScode
			    .find('option')
			    .remove();
		
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].CODE,
				        text : results[i].CODE_NM 
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetSCodeList***********************");
		}
	});
}

//사용자 권한 리스트 가져오는 AJAX
function fnGetAllUserAuthorList(pScode,pSelectedDtlCd,pAddOptionType){	
	$.ajax({
		url : '/common/selectAllAuthorList.do',
		type : 'post',
		dataType : 'json',
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				
				$.each(results,function(index, value){
				    //console.log('My array has at position ' + index + ', this value: ' + results[index].SCLAS_CODE);
				});
				
				pScode
			    .find('option')
			    .remove();
		
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].CODE,
				        text: results[i].CODE_NM
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetSCodeList***********************");
		}
	});
}

//소분류코드 가져오기
function fnGetSCodeList(pLcodeStr,pMcodeStr,pScode,pSelectedDtlCd,pAddOptionType){
	var param = { 
			'lcode' : pLcodeStr, 
			'mcode' : pMcodeStr 
	};
	$.ajax({
		url : '/common/selectSCodeListAjax.do',
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				/*
				$.each(results,function(index, value){
				    console.log('My array has at position ' + index + ', this value: ' + results[index].SCLAS_CODE);
				});
				*/
				pScode
			    .find('option')
			    .remove();
				
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].SCLAS_CODE,
				        text : results[i].SCLAS_NM 
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetSCodeList***********************");
		}
	});
}


//세분류코드 가져오기
function fnGetSDCodeList(pLcodeStr,pMcodeStr,pCcodeStr,pScode,pSelectedDtlCd,pAddOptionType){
	var param = { 
			'lcode' : pLcodeStr, 
			'mcode' : pMcodeStr, 
			'ccode' : pCcodeStr 
	};
	$.ajax({
		url : '/common/selectSDCodeListAjax.do',
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			//console.log(JSON.stringify(response));
			if(response.resultStats.resultCode == 'ok'){
				var results=response.resultStats.resultList;
				/*
				$.each(results,function(index, value){
				    console.log('My array has at position ' + index + ', this value: ' + results[index].SCLAS_CODE);
				});
				*/
				pScode
			    .find('option')
			    .remove();
				
				if (pAddOptionType=="C") {										//Choice
					pScode.append($('<option>', { 
				        value: '',
				        text : '선택하세요' 
				    }));
				}
				else if (pAddOptionType=="A") {	//All
					pScode.append($('<option>', { 
				        value: '',
				        text : '전체' 
				    }));
				}
				
				$.each(results, function (i, item) {
					pScode.append($('<option>', { 
				        value: results[i].SDCLAS_CODE,
				        text : results[i].SDCLAS_NM 
				    }));
				});
				pScode.val(pSelectedDtlCd);
			} else {

			}
		},
		error : function(){
			console.log("***************************fnGetSDCodeList***********************");
		}
	});
}




function getAddr(){
	// AJAX 주소 검색 요청
	$.ajax({
		url:"http://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"	// 주소검색 OPEN API URL
		,type:"post"
		,data:$("#form").serialize() 								// 요청 변수 설정
		,dataType:"jsonp"											// 크로스도메인으로 인한 jsonp 이용, 검색결과형식 JSON 
		,crossDomain:true
		,success:function(jsonStr){									// jsonStr : 주소 검색 결과 JSON 데이터			
			$("#list").html("");									// 결과 출력 영역 초기화
			$("#pageApi").html("");
			var errCode = jsonStr.results.common.errorCode;
			var errDesc = jsonStr.results.common.errorMessage;
			if(errCode != "0"){ 
				alert(errCode+"="+errDesc);
			}else{
				if(jsonStr!= null){
					makeListJson(jsonStr);							// 결과 JSON 데이터 파싱 및 출력
					pageMake(jsonStr);
				}
			}
		}
		,error: function(xhr,status, error){
			alert("에러발생");										// AJAX 호출 에러
		}
	});
}
function makeListJson(jsonStr){
	var htmlStr = "";
	htmlStr += "<table class='table table-hover table-bordered'>";
	htmlStr += "<caption>우편번호 검색 결과 목록</caption>";
	htmlStr += "<colgroup>";
	htmlStr += "<col style='width:75%'/>";
	htmlStr += "<col style='width:25%'/>";
	htmlStr += "</colgroup>";
	htmlStr += "<thead>";
	htmlStr += "<tr>";
	htmlStr += "<th scope='col' class='text-center'>도로명 주소</th>";
	htmlStr += "<th scope='col' class='text-center'>우편번호</th>";
	htmlStr += "</tr>";
	htmlStr += "</thead>";
	htmlStr += "<tbody>";

	
	// jquery를 이용한 JSON 결과 데이터 파싱
	$(jsonStr.results.juso).each(function(){
		htmlStr += "<tr style='cursor:pointer;cursor:hand;' onclick=\"fnZipSelect('"+this.zipNo+"', '"+this.roadAddr+"')\">";
		htmlStr += "<td>";
		htmlStr += "<p class='mgb-5'>";
		htmlStr += "<span class='color-dngrn'>[도로명]</span>"+this.roadAddr;
		htmlStr += "</p> ";										
		htmlStr += "<p> ";
		htmlStr += "<span class='color-dngrn'>[지번]</span>"+this.jibunAddr;
		htmlStr += "</p>";
		htmlStr += "</td>";
		htmlStr += "<td class='text-center'>"+this.zipNo+"</td>";
		htmlStr += "</tr>";
	});
	htmlStr += "	</tbody>";
	htmlStr += "</table>";
	// 결과 HTML을 FORM의 결과 출력 DIV에 삽입
	$("#list").html(htmlStr);
}	

function pageMake(jsonStr){
	var total = jsonStr.results.common.totalCount; // 총건수
	var pageNum = document.form.currentPage.value;

	var paggingStr = "<ul class='pagination pagination-sm no-margin'>";
	if(total < 1){
	}else{
		var PAGEBLOCK=0;
		var pageSize=0;
		var totalPages=0;
		var firstPage=0;
		var lastPage=0;
		var nextPage = 0 ;
		var prePage = 0 ;	

		PAGEBLOCK=eval(document.form.countPerPage.value);
		pageSize=eval(document.form.countPerPage.value);
		totalPages = Math.floor((total-1)/pageSize) + 1;
		
		firstPage = Math.floor((pageNum-1)/PAGEBLOCK) * PAGEBLOCK + 1;		
		if( firstPage <= 0 ) firstPage = 1;
		lastPage = firstPage-1 + PAGEBLOCK;
		if( lastPage> totalPages ) lastPage = totalPages;		


		nextPage = lastPage+1 ;
		prePage = firstPage-5 ;	
		
		//console.log("firstPage["+firstPage+"]prePage["+prePage+"]nextPage["+nextPage+"]lastPage["+lastPage+"]pageNum["+pageNum+"]");
		
		if( firstPage > PAGEBLOCK ){
			paggingStr +=  "<li><a href=\"#\" title=\"앞으로 가기\" onclick=\"goPage('"+prePage+"');\">&lt;</a></li>";
		}
		
		for( i=firstPage; i<=lastPage; i++ ){
			if( pageNum == i )
				paggingStr += "<li class=\"active\"><a href=\"#\" onclick=\"goPage('"+i+"');\">"+i+"</a></li>";
			else	
				paggingStr += "<li><a href=\"#\" onclick=\"goPage('"+i+"');\">"+i+"</a></li>";
		}
		if( lastPage < totalPages ){
			paggingStr +=  "<li><a href=\"#\" title=\"뒤로가기\" onclick=\"goPage('"+eval(nextPage)+"'); return false;\">&gt;</a></li>";
		}

		paggingStr += "</ul>";

		$("#pageApi").html(paggingStr);
	}
		
}


function checkNumScreen(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

//AJAX호출
function fnCallbackAjax(pUrl,pParma,pType,pFncallback){
 	var dataString = pParma;
    $.ajax({
        type		: pType,
        url			: pUrl,
        data		: dataString,
        cache		: false,
		dataType 	: 'json',
        success: function (response) {
            //console.log(response);
            if (typeof pFncallback === "function") {
            	pFncallback(response);
            }
        },
        error: function(err) {
            console.log(err);
        }
   });
}

//AJAX POST Form 호출
function fnCallbackFormAjax(pUrl,pForm,pFncallback){
    $.ajax({
        type: "post",
        url: pUrl,
		data : pForm.serialize(), 
		dataType : 'json',
        cache: false,
        success: function (response) {
        	//console.log(response);
            if (typeof pFncallback === "function") {
            	pFncallback(response);
            }
			
        },
        error: function(err) {
            console.log(err);
        }
   });
}


//AJAX POST Form 호출
function fnCallbackFileFormAjax(pUrl,pForm,pFncallback){
    $.ajax({
        type: "post",
        url: pUrl,
		data : new FormData(pForm[0]), 
		processData: false,
	    contentType: false,
		dataType : 'json',
        cache: false,
        success: function (response) {
        	//console.log(response);
            if (typeof pFncallback === "function") {
            	pFncallback(response);
            }
			
        },
        error: function(err) {
            console.log(err);
        }
   });

}


//undefined Chk
function fnUndefined(pParam){
	if(pParam) return pParam;
	else return "";
}

function fnGoProject(pCd){
	if(pCd=="1"){
			location.href="/mgt/project/selectPageListProjectIssue.do?project_seq="+$("#project_seq").val();
	}else if(pCd=="2"){
		location.href="/mgt/project/selectPageListProjectTodo.do?project_seq="+$("#project_seq").val();
	}else{
		location.href="/mgt/project/selectPageListProjectBoard.do?project_seq="+$("#project_seq").val();
	}
}

// Dimmed Loading Progress
/* call function
	wrapLoadingMask('show');
	wrapLoadingMask('hide')  OR  wrapLoadingMask();
*/

function wrapLoadingMask(stat){
	$('.dimmed').detach();
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	var loadingWrap = '<div class="dimmed" style="width:'+ maskWidth +'px;height:'+ maskHeight +'px"><div class="loading-bar">';
			loadingWrap += '<div class="lds-default"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>';
			loadingWrap += '</div></div>';
	
	if(stat == 'show'){
		$('body').css({'overflow':'hidden','height':'100%'});
		$(loadingWrap).appendTo(document.body).show();
		$('#dimmed').fadeTo("slow",0.6);
	}else if(stat == 'hide'){
		$('.dimmed').detach(); $('body').removeAttr('style');
	}else{
		$('.dimmed').detach(); $('body').removeAttr('style');
	}
}

//비율구하기			
function getCalRate(pNumber1,pNumber2,pPoint) {
	let iReturn = 0;
	
	if(pNumber2==0) return iReturn;

	iReturn = (pNumber1/pNumber2)*100;
	iReturn  = iReturn.toFixed(pPoint);
	return iReturn;
}

//비율금액구하기	(재료비 금액)		
function getCalRateCostAmt(pAmt,pRate) {
	let iReturn = 0;
	if(pRate==0) return pAmt;

	iReturn = ((pRate/100) * pAmt)/10;
	iReturn = Math.floor(iReturn)*10;
	return iReturn;
}

//비율금액구하기			
function getCalRateAmt(pAmt,pRate,pPoint) {
	let iReturn = 0;
	if(pRate==0) return 0;

	iReturn = (pAmt * pRate/100);
	
	iReturn = iReturn.toFixed(pPoint);
	return iReturn;
}



//숫자구하기			
function getNumber(pNumberStr) {
	let iReturn = 0;

	if(typeof pNumberStr == "undefined" || pNumberStr == null || pNumberStr == "") return iReturn;
	
	iReturn = eval(pNumberStr);
	
	return iReturn;
}

//숫자구하기 			
function getNumberPoint(pNumberStr,pPoint) {
	let iReturn = 0;
	
	if(typeof pNumberStr == "undefined" || pNumberStr == null || pNumberStr == "") return iReturn;

	iReturn = eval(pNumberStr).toFixed(pPoint);
	return iReturn;
}

//0이면 공백으로 리턴			
function setZeroToSpace(pNumber) {
	let sReturn = "";

	if(typeof pNumber == "undefined" || pNumber == null || pNumber == "") return sReturn;

	sReturn = pNumber;

	if(pNumber==0)
		sReturn = "";
	
	return sReturn;
}

function isEmpty(str){
	if(typeof str == "undefined" || str == null || str == "")
		return true;
	else
		return false ;
}

//소수점 짜르기			
function numFixed(num,point) {
    var m = num.toFixed(point);
    return m;
}

//천원으로 나누기 소수점 한자리에서 반올림
function roundToOneDecimal(param1, divisor) {
    var result = (param1) / divisor; 		// 두 파라미터를 곱한 후 divisor로 나누기
    result = Math.round(result); 			// 소수점 이하를 반올림하여 정수로 만듦
    return result;
}


//엑셀 INT소수점 짜르기			
function excelInt(number) {
    return Math.floor(number);
}

//판매경비,제조경비 소수점 계산
function excelIntAmt(number, pRate) {
    return Math.floor((number * pRate) / 10) * 10;
};

function fnLogin(){
	if($('#popUserId').val()==""){
		alert("아디디를 입력하세요.");
   		return;
	}

	if($('#popUserPw').val()==""){
		alert("패스워드를 입력하세요.");
   		return;
	}
	
	 var sUrl 	= "/front/loginAjax.do";
	 var sParam ={'user_id' : $('#popUserId').val()
			     ,'user_pw' : $('#popUserPw').val()
			 	 };
	 var sType 	= "post";
	 var sReturn="N";
	 wrapLoadingMask("show");
	 console.log("***************show*************");
	 
	 fnCallbackAjax(sUrl,sParam,sType,function fncallback(pResponse){
			//	console.log(pResponse);
		 	var results=pResponse.resultStats.resultList;
			//alert(pResponse.resultStats.resultMsg);
			 //wrapLoadingMask("hide");
		 	if(pResponse.resultStats.resultCode=="ok"){
				$("#myPopSuccessAlertS").show("slow").delay(2000).hide("3000");
		 		location.href="/";
		 		$('#myLoginModal').modal('toggle');
		 	}else{
				alert(pResponse.resultStats.resultMsg);
				wrapLoadingMask("hide");
			}
	 });
}


