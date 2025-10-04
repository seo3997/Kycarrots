<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO" %>

<jsp:useBean id="resultMap"     class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param"         class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="fileList"      class="java.util.ArrayList" type="java.util.List" scope="request"/>
<jsp:useBean id="boardComboStr" class="java.util.ArrayList" type="java.util.List" scope="request"/>

<%
    // 값 준비
    String bbsSeq     = resultMap.getString("BBS_SEQ");
    String atchDocId  = resultMap.getString("ATCH_DOC_ID");
    String sj         = param.getString("sj", resultMap.getString("SJ"));
    String cn         = param.getString("cn", resultMap.getString("CN"));
    String selMid     = param.getString("bbs_se_code_m", resultMap.getString("BBS_SE_CODE_M"));
    String atchYnRaw  = resultMap.getString("ATCH_YN");
    boolean atchY     = (atchYnRaw == null || atchYnRaw.isEmpty() || "Y".equalsIgnoreCase(atchYnRaw));
    boolean atchN     = "N".equalsIgnoreCase(atchYnRaw);

    // 목록 복귀용
    String schType = param.getString("sch_type");
    String schText = param.getString("sch_text");
    String schMid  = param.getString("sch_bbs_se_code_m");
    String currPg  = param.getString("currentPage");
    String schMidNm  = "10".equals(schMid)? "공지사항" :"문의하기";
    String ssUserNo =  param.getString("ss_user_no");

%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover"/>
    <title>게시물 수정</title>

    <!-- 모바일 공통 CSS (초 단위 캐시버스터) -->
    <link rel="stylesheet" href="/common/css/mobile/board-mobile.css?v=<%= System.currentTimeMillis() / 1000 %>"/>
    <script>
        function $(id){ return document.getElementById(id); }
        function submitTo(action, method){
            const f = $('aform');
            f.action = action; f.method = method || 'post';
            f.submit();
        }
        function fnDetail(){
            submitTo('/front/board/selectBoard.do', 'posty');
        }

        function fnDownload(file_id){
            $('file_id').value = file_id;
            submitTo('/common/file/FileDown.do', 'post');
        }

        function fnFileDel(file_id){
            if(!confirm('삭제하시겠습니까?')) return;
            const xhr = new XMLHttpRequest();
            xhr.open('POST','/common/file/deleteFileInfAjax.do');
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded; charset=UTF-8');
            xhr.onload = function(){
                try {
                    const res = JSON.parse(xhr.responseText||'{}');
                    alert((res.resultStats && res.resultStats.resultMsg) ? res.resultStats.resultMsg : '처리되었습니다.');
                    location.reload();
                } catch(e){ alert('삭제 처리 중 오류가 발생했습니다.'); }
            };
            xhr.onerror = function(){ alert('통신 오류가 발생했습니다.'); };
            xhr.send('file_id=' + encodeURIComponent(String(file_id||'')));
        }

        // 화면표시(N)일 때는 업로드 확장자 제한 (이미지/MP4만)
        function extAllowedForInline(files){
            const ok = new Set(['jpg','jpeg','png','gif','bmp','mp4']); // 소문자 기준
            for (let i=0; i<files.length; i++){
                const name = (files[i].name||'').trim();
                const dot  = name.lastIndexOf('.');
                const ext  = dot >= 0 ? name.substring(dot+1).toLowerCase() : '';
                if (!ok.has(ext)) return {ok:false, bad:name};
            }
            return {ok:true};
        }

        function fnGoUpdate(){
            const bbs = $('bbs_se_code_m');
            const title = $('sj');
            const body  = $('cn');
            const files = $('upload').files;
            const inline = $('atch_yn_n').checked;

            if(!bbs.value){ alert('게시판구분을 선택하세요.'); bbs.focus(); return; }
            if(!title.value.trim()){ alert('제목을 입력해 주세요.'); title.focus(); return; }
            if(!body.value.trim()){ alert('내용을 입력해 주세요.'); body.focus(); return; }

            if (inline && files && files.length > 0){
                const r = extAllowedForInline(files);
                if(!r.ok){ alert('화면표시 모드에서는 이미지(jpg/jpeg/png/gif/bmp) 또는 mp4만 업로드 가능합니다.\n문제 파일: ' + r.bad); return; }
            }
            if(confirm('수정하시겠습니까?')){
                submitTo('/front/board/updateBoard.do', 'post');
            }
        }

        document.addEventListener('DOMContentLoaded', function(){
            const hint = $('hint-inline'), atN = $('atch_yn_n'), atY = $('atch_yn_y');
            function toggleHint(){ if(hint) hint.style.display = (atN && atN.checked) ? 'block' : 'none'; }
            if (atN && atY){ atN.addEventListener('change', toggleHint); atY.addEventListener('change', toggleHint); toggleHint(); }
        });
    </script>
</head>
<body class="board-form"><!-- 폼 전용 스코프 -->

<header class="header">
    <div class="titlebar">
        <h1><%=schMidNm%> 수정</h1>
        <button type="button" class="btn btn-outline" onclick="fnDetail();return false;">취소</button>
    </div>
</header>

<main class="form-wrap">
    <form id="aform" method="post" action="/front/board/updateBoard.do" enctype="multipart/form-data" style="margin:0;">
        <!-- 식별/복귀 파라미터 -->
        <input type="hidden" name="ss_user_no"      id="ss_user_no" value="<%=ssUserNo%>"/>
        <input type="hidden" name="bbs_seq"        value="<%= bbsSeq %>"/>
        <input type="hidden" name="atch_doc_id"    value="<%= atchDocId %>"/>
        <input type="hidden" name="sch_type"       value="<%= schType %>"/>
        <input type="hidden" name="sch_text"       value="<%= schText %>"/>
        <input type="hidden" name="sch_bbs_se_code_m" value="<%= schMid %>"/>
        <input type="hidden" name="currentPage"    value="<%= currPg %>"/>
        <input type="hidden" name="file_id"        id="file_id"/>
        <input type="hidden" name="bbs_se_code_l"  id="bbs_se_code_l" value="R010170"/>
        <input type="hidden" name="bbs_se_code_m"  id="bbs_se_code_m" value="<%=selMid%>"/>

        <section class="card">
            <!-- 제목 -->
            <div class="field">
                <label for="sj">제목</label>
                <input type="text" class="input" name="sj" id="sj" maxlength="100" placeholder="제목을 입력하세요" value="<%= sj %>"/>
            </div>

            <!-- 내용 -->
            <div class="field">
                <label for="cn">내용</label>
                <textarea class="textarea" name="cn" id="cn" placeholder="내용을 입력하세요"><%= cn %></textarea>
            </div>

            <!-- 새 첨부파일 업로드 -->
            <div class="field">
                <label for="upload">첨부파일</label>
                <input type="file" id="upload" name="upload" multiple />
                <div class="hint">여러 파일을 한 번에 선택할 수 있습니다.</div>
            </div>

            <!-- 첨부 표시 방식 -->
            <div class="field">
                <label>첨부파일 표시여부</label>
                <div class="radio-row">
                    <label><input type="radio" name="atch_yn" id="atch_yn_y" value="Y" <%= atchY ? "checked" : "" %> > 다운로드</label>
                    <label><input type="radio" name="atch_yn" id="atch_yn_n" value="N" <%= atchN ? "checked" : "" %> > 화면표시</label>
                </div>
                <div id="hint-inline" class="hint" style="display:none;">
                    화면표시 선택 시, 이미지(jpg/jpeg/png/gif/bmp) 또는 mp4만 업로드하세요. (확장자는 소문자 권장)
                </div>
            </div>

            <!-- 기존 첨부 목록 -->
            <%
                if (fileList != null && fileList.size() > 0) {
            %>
            <div class="field">
                <label>첨부파일 목록</label>
                <div class="files">
                    <%
                        for (int i=0; i<fileList.size(); i++) {
                            AtFileVO fvo = (AtFileVO) fileList.get(i);
                            int kb = (int)Math.ceil(fvo.getFile_size()/1000.0);
                    %>
                    <div class="file-item">
                        <div class="name"><%= fvo.getFile_nm() %> (<%= kb %> KB)</div>
                        <div style="display:flex; gap:8px;">
                            <button type="button" class="btn btn-outline" onclick="fnDownload('<%= fvo.getFile_id() %>');return false;">다운로드</button>
                            <button type="button" class="btn btn-outline" onclick="fnFileDel('<%= fvo.getFile_id() %>');return false;">삭제</button>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
            <% } %>
        </section>

        <!-- 하단 액션 -->
        <div class="footer-actions">
            <button type="button" class="btn btn-outline" onclick="fnDetail();return false;">취소</button>
            <button type="button" class="btn btn-primary" onclick="fnGoUpdate();return false;">확인</button>
        </div>
    </form>
</main>

</body>
</html>
