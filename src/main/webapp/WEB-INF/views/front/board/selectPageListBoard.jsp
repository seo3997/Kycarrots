<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"     type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param"          class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar"  class="java.lang.String" scope="request"/>
<jsp:useBean id="boardComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

<%
    String ssAuthorId = (String)request.getAttribute("ssAuthorId");
    if (ssAuthorId == null) ssAuthorId = "";

    int currentPage = 1, totalPage = 1, currDataNo = resultList.size();
    try {
        currentPage = ((Number)pageNavigationVo.getClass().getMethod("getCurrentPage").invoke(pageNavigationVo)).intValue();
        totalPage   = ((Number)pageNavigationVo.getClass().getMethod("getTotalPage").invoke(pageNavigationVo)).intValue();
        currDataNo  = ((Number)pageNavigationVo.getClass().getMethod("getCurrDataNo").invoke(pageNavigationVo)).intValue();
    } catch(Exception ignore){}

    String selMid  = param.getString("sch_bbs_se_code_m");
    String schText = param.getString("sch_text");
    String schType = param.getString("sch_type");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover"/>
    <title>게시판</title>

    <!-- 분리된 모바일 CSS 링크 (경로는 프로젝트 구조에 맞춰 조정) -->
    <link rel="stylesheet" href="/common/css/mobile/board-mobile.css?v=1.02"/>

    <script>
        function $(id){
            return document.getElementById(id);
        }
        function submitTo(action, method){
            var f=$('aform');
            f.action=action;
            f.method=method||'get';
            f.submit();
        }
        function fnGoPage(p){
            $('currentPage').value=String(p);
            submitTo('/mgt/mboard/selectPageListBoard.do','get');
        }
        function fnInsertForm(){
            submitTo('/mgt/mboard/insertFormBoard.do','post');
        }
        function fnSelect(seq){
            document.getElementsByName('bbs_seq')[0].value=seq;
            submitTo('/mgt/mboard/selectBoard.do','get');
        }
        function fnSearch(){
            $('currentPage').value='1';
            submitTo('/mgt/mboard/selectPageListBoard.do','get');
        }
        document.addEventListener('DOMContentLoaded',function(){
            var t=document.querySelector('[name=sch_text]');
            if(t){t.addEventListener('keydown',e=>{if(e.key==='Enter')e.preventDefault();});
                t.addEventListener('keyup',e=>{if(e.key==='Enter')fnSearch();});}
            var cb=$('sch_bbs_se_code_m'); if(cb) cb.addEventListener('change',fnSearch);
            var more=$('btnMore'); if(more) more.addEventListener('click',()=>{var c=parseInt($('currentPage').value||'1',10);fnGoPage(c+1);});
        });
    </script>
</head>
<body>

<div class="header">
    <div class="titlebar">
        <h1>공지사항</h1>
        <% if ("ROLE_ADMIN".equals(ssAuthorId)) { %>
        <button type="button" class="btn btn-write" onclick="fnInsertForm();return false;">+ 등록</button>
        <% } %>
    </div>
    <form id="aform" method="get" action="/mgt/mboard/selectPageListBoard.do" style="margin:0;">
        <input type="hidden" name="sch_bbs_se_code_l" id="sch_bbs_se_code_l" value="R010170"/>
        <input type="hidden" name="bbs_seq"/>
        <input type="hidden" name="currentPage" id="currentPage" value="<%=currentPage%>"/>
        <input type="hidden" name="sch_type" id="search_list_st" value="<%=schType%>"/>
        <div class="search">
            <select id="sch_bbs_se_code_m" name="sch_bbs_se_code_m">
                <option value="">전체</option>
                <%
                    for (Object o : boardComboStr){
                        DataMap r = (DataMap)o;
                        String code=r.getString("CODE");
                        String nm=r.getString("CODE_NM");
                        String sel=(code!=null && code.equals(selMid))?"selected":"";
                %>
                <option value="<%=code%>" <%=sel%>><%=nm%></option>
                <% } %>
            </select>
            <input type="text" name="sch_text" placeholder="검색어를 입력하세요" value="<%=schText%>"/>
            <button type="button" class="btn btn-primary" onclick="fnSearch();return false;">검색</button>
        </div>
    </form>
</div>

<div class="container">
    <%
        if (resultList.size()>0){
            for (int i=0;i<resultList.size();i++){
                DataMap row=(DataMap)resultList.get(i);
                String seq = row.getString("BBS_SEQ");
                String sj  = row.getString("SJ");
                String reg = row.getString("REGISTER_NM");
                String dt  = row.getString("REGIST_DT");
                int no = currDataNo - i;
    %>
    <a class="tap" href="javascript:void(0);" onclick="fnSelect('<%=seq%>');">
        <div class="card">
            <div class="sj"><%= (sj==null?"":sj) %></div>
            <div class="meta">
                <span>작성자: <%= (reg==null?"":reg) %></span>
                <span>작성일: <%= (dt==null?"":dt) %></span>
                <span class="no">No.<%=no%></span>
            </div>
        </div>
    </a>
    <% } %>

    <% if (totalPage>currentPage) { %>
    <button id="btnMore" type="button" class="more">더보기 (<%=currentPage%> / <%=totalPage%>)</button>
    <% } else { %>
    <div class="end">마지막 페이지입니다</div>
    <% } %>

    <% } else { %>
    <div class="empty">등록된 데이터가 없습니다.</div>
    <% } %>
</div>

</body>
</html>
