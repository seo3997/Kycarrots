<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO" %>

<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param"     class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="fileList"  class="java.util.ArrayList" type="java.util.List" scope="request"/>

<%
    String ssAuthorId = (String)request.getAttribute("ssAuthorId");
    if (ssAuthorId == null) ssAuthorId = "";

    String bbsSeq = resultMap.getString("BBS_SEQ");
    String bbsNm  = resultMap.getString("BBS_SE_NM");
    String title  = resultMap.getString("SJ");
    String regNm  = resultMap.getString("REGISTER_NM");
    String regId  = resultMap.getString("REGISTER_ID");
    String regDt  = resultMap.getString("REGIST_DT");
    String atchYn = resultMap.getString("ATCH_YN"); // "N"이면 이미지/동영상은 본문 표시

    // 목록 복귀용 검색 파라미터
    String schType = param.getString("sch_type");
    String schText = param.getString("sch_text");
    String schMid  = param.getString("sch_bbs_se_code_m");
    String currPg  = param.getString("currentPage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover"/>
    <title>게시판</title>

    <!-- 모바일 공통 CSS (리스트/상세 공용). 상세 전용 보강은 .board-view 접두사로 넣어주세요 -->
    <link rel="stylesheet" href="/common/css/mobile/board-mobile.css?v=1.2"/>
    <script>
        function $(id){ 
            return document.getElementById(id); 
        }
        function submitTo(action, method){
            var f = $('aform'); 
            f.action = action; 
            f.method = method || 'get'; 
            f.submit();
        }
        function fnGoList(){ 
            submitTo('/front/board/selectPageListBoard.do', 'get'); 
        }
        function fnGoUpdateForm(){ 
            submitTo('/front/board/updateFormBoard.do', 'get'); 
        }
        function fnGoDelete(){
            if(confirm('삭제하시겠습니까?')) 
              submitTo('/front/board/deleteBoard.do', 'post');
        }
        function fnDownload(file_id){
            $('file_id').value = file_id;
            submitTo('/common/file/FileDown.do', 'post');
        }
    </script>
</head>
<body class="board-view"><!-- 상세 전용 스코프 클래스 -->

<!-- 상단 고정 헤더 -->
<header class="header">
    <div class="titlebar">
        <h1>공지사항</h1>
        <% if ("ROLE_ADMIN".equals(ssAuthorId)) { %>
        <button type="button" class="btn btn-write" onclick="fnGoUpdateForm();return false;">수정</button>
        <% } %>
    </div>
</header>

<!-- 상세 본문 -->
<main class="view-wrap">
    <form id="aform" method="post" action="/front/board/selectBoard.do" style="margin:0;">
        <input type="hidden" name="bbs_seq" value="<%=bbsSeq%>"/>
        <!-- 목록 복귀 파라미터 유지 -->
        <input type="hidden" name="sch_type" value="<%=schType%>"/>
        <input type="hidden" name="sch_text" value="<%=schText%>"/>
        <input type="hidden" name="sch_bbs_se_code_m" value="<%=schMid%>"/>
        <input type="hidden" name="currentPage" value="<%=currPg%>"/>
        <input type="hidden" name="file_id" id="file_id"/>

        <section class="vcard">
            <div class="vrow">
                <div class="lab">게시판구분</div>
                <div class="val"><%= bbsNm %></div>
            </div>

            <div class="vtitle"><%= title %></div>

            <div class="vrow">
                <div class="lab">등록자명</div>
                <div class="val"><%= regNm %></div>
            </div>
            <div class="vrow">
                <div class="lab">등록자ID</div>
                <div class="val"><%= regId %></div>
            </div>
            <div class="vrow">
                <div class="lab">등록일시</div>
                <div class="val"><%= regDt %></div>
            </div>

            <div class="vcontent"><%= resultMap.getHtml("CN") %></div>

            <%-- 첨부 처리 --%>
            <%
                if (fileList != null && fileList.size() > 0){
                    boolean inlineMode = "N".equalsIgnoreCase(atchYn); // N이면 이미지/동영상은 본문 표시
                    if (inlineMode) {
            %>
            <div class="files">
                <%
                    for (int i=0; i<fileList.size(); i++){
                        AtFileVO fvo = (AtFileVO) fileList.get(i);
                        String ext = fvo.getFile_ext_nm();
                        String src = fvo.getFile_rltv_path() + fvo.getFile_id() + "." + ext;

                        if ("jpg".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext)
                                || "png".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext) || "bmp".equalsIgnoreCase(ext)) {
                %>
                <div style="margin-top:10px;">
                    <img src="<%=src%>" alt="<%=fvo.getFile_nm()%>"
                         style="max-width:100%; border:1px solid #eee; border-radius:8px;">
                </div>
                <%
                } else if ("mp4".equalsIgnoreCase(ext) || "webm".equalsIgnoreCase(ext) || "ogg".equalsIgnoreCase(ext)) {
                %>
                <div style="margin-top:10px;">
                    <video controls style="width:100%; height:auto; border:1px solid #eee; border-radius:8px;">
                        <source src="<%=src%>">
                    </video>
                </div>
                <%
                } else {
                %>
                <div class="file-item">
                    <div class="name"><%=fvo.getFile_nm()%> (<%= (int)Math.ceil(fvo.getFile_size()/1000.0) %> KB)</div>
                    <button type="button" class="btn btn-outline"
                            onclick="fnDownload('<%=fvo.getFile_id()%>');return false;">다운로드</button>
                </div>
                <%
                        }
                    }
                %>
            </div>
            <%
            } else {
            %>
            <div class="files">
                <div class="vrow" style="margin-top:6px;">
                    <div class="lab">첨부파일</div>
                    <div class="val">
                        <%
                            for (int i=0; i<fileList.size(); i++){
                                AtFileVO fvo = (AtFileVO) fileList.get(i);
                        %>
                        <div class="file-item">
                            <div class="name"><%=fvo.getFile_nm()%> (<%= (int)Math.ceil(fvo.getFile_size()/1000.0) %> KB)</div>
                            <button type="button" class="btn btn-outline"
                                    onclick="fnDownload('<%=fvo.getFile_id()%>');return false;">다운로드</button>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </section>

        <!-- 하단 액션 -->
        <div class="footer-actions">
            <button type="button" class="btn btn-outline" onclick="fnGoList();return false;">목록</button>
            <% if ("ROLE_ADMIN".equals(ssAuthorId)) { %>
            <button type="button" class="btn btn-primary" onclick="fnGoUpdateForm();return false;">수정</button>
            <button type="button" class="btn btn-outline" onclick="fnGoDelete();return false;">삭제</button>
            <% } %>
        </div>
    </form>
</main>

</body>
</html>
