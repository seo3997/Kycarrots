<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param"          class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="boardComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

<%
    String cnDocId  = param.getString("cn_doc_id");
    String selMid   = param.getString("sch_bbs_se_code_m");
    String sj       = param.getString("sj");
    String cn       = param.getString("cn");
    String selMidNm  = "10".equals(selMid)? "공지사항" :"문의하기";
    String ssUserNo =  param.getString("ss_user_no");

%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover"/>
    <title>게시물 등록</title>

    <!-- 모바일 공통 CSS (초단위 캐시 버스터) -->
    <link rel="stylesheet" href="/common/css/mobile/board-mobile.css?v=<%= System.currentTimeMillis() / 1000 %>"/>

    <script>
        function $(id){ return document.getElementById(id); }
        function submitTo(action, method){
            const f = $('aform');
            f.action = action;
            f.method = method || 'post';
            f.submit();
        }
        function fnGoList(){
            submitTo('/front/board/selectPageListBoard.do', 'post');
        }

        function extAllowedForInline(files){
            const ok = new Set(['jpg','jpeg','png','gif','bmp','mp4']); // 소문자만
            for (let i=0; i<files.length; i++){
                const name = (files[i].name || '').trim();
                const dot  = name.lastIndexOf('.');
                const ext  = dot >= 0 ? name.substring(dot+1).toLowerCase() : '';
                if (!ok.has(ext)) return {ok:false, bad:name};
            }
            return {ok:true};
        }

        function fnGoInsert(){
            const bbs = $('bbs_se_code_m');
            const sj  = $('sj');
            const cn  = $('cn');
            const atY = $('atch_yn_y');
            const atN = $('atch_yn_n');
            const files = $('upload').files;

            if(!bbs.value){ alert('게시판구분을 선택하세요.'); bbs.focus(); return; }
            if(!sj.value.trim()){ alert('제목을 입력해 주세요.'); sj.focus(); return; }
            if(!cn.value.trim()){ alert('내용을 입력해 주세요.'); cn.focus(); return; }

            if (atN.checked && files && files.length > 0){
                const r = extAllowedForInline(files);
                if(!r.ok){
                    alert('화면표시 모드에서는 이미지(jpg/jpeg/png/gif/bmp) 또는 mp4만 업로드 가능합니다.\n문제 파일: ' + r.bad);
                    return;
                }
            }
            if(confirm('등록하시겠습니까?')) submitTo('/front/board/insertBoard.do', 'post');
        }

        document.addEventListener('DOMContentLoaded', function(){
            const hint = $('hint-inline'), atN = $('atch_yn_n'), atY = $('atch_yn_y');
            function toggleHint(){ if(hint) hint.style.display = atN && atN.checked ? 'block' : 'none'; }
            if (atN && atY){ atN.addEventListener('change', toggleHint); atY.addEventListener('change', toggleHint); toggleHint(); }
        });
    </script>
</head>
<body class="board-form"><!-- ← 스코프 클래스 추가 -->

<header class="header">
    <div class="titlebar">
        <h1><%=selMidNm%> 등록</h1>
        <button type="button" class="btn btn-outline" onclick="fnGoList();return false;">목록</button>
    </div>
</header>

<main class="form-wrap">
    <form id="aform" method="post" action="/front/board/insertBoard.do" enctype="multipart/form-data" style="margin:0;">
        <input type="hidden" name="ss_user_no"          id="ss_user_no"         value="<%=ssUserNo%>"/>
        <input type="hidden" name="cn_doc_id"           id="cn_doc_id"          value="<%= cnDocId %>"/>
        <input type="hidden" name="bbs_se_code_l"       id="bbs_se_code_l"      value="R010170"/>
        <input type="hidden" name="bbs_se_code_m"       id="bbs_se_code_m"      value="<%=selMid%>"/>
        <input type="hidden" name="sch_bbs_se_code_m"   id="sch_bbs_se_code_m"  value="<%=selMid%>"/>

        <section class="card">
            <div class="field">
                <label for="sj">제목</label>
                <input type="text" class="input" name="sj" id="sj" maxlength="100" placeholder="제목을 입력하세요" value="<%= sj %>"/>
            </div>

            <div class="field">
                <label for="cn">내용</label>
                <textarea class="textarea" name="cn" id="cn" placeholder="내용을 입력하세요"><%= cn %></textarea>
            </div>

            <div class="field">
                <label for="upload">첨부파일</label>
                <input type="file" id="upload" name="upload" multiple />
                <div class="hint">여러 파일을 한 번에 선택할 수 있습니다.</div>
            </div>

            <div class="field">
                <label>첨부파일 표시여부</label>
                <div class="radio-row">
                    <label><input type="radio" name="atch_yn" id="atch_yn_y" value="Y" checked> 다운로드</label>
                    <label><input type="radio" name="atch_yn" id="atch_yn_n" value="N"> 화면표시</label>
                </div>
                <div id="hint-inline" class="hint" style="display:none;">
                    화면표시 선택 시, 이미지(jpg/jpeg/png/gif/bmp) 또는 mp4만 업로드하세요. (확장자는 소문자 권장)
                </div>
            </div>
        </section>

        <div class="footer-actions">
            <button type="button" class="btn btn-outline" onclick="fnGoList();return false;">목록</button>
            <button type="button" class="btn btn-primary" onclick="fnGoInsert();return false;">등록</button>
        </div>
    </form>
</main>

</body>
</html>
