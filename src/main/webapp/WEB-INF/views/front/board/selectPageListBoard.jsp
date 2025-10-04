<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"     type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param"          class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar"  class="java.lang.String" scope="request"/>
<jsp:useBean id="boardComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

<%

    int currentPage = 1, totalPage = 1, currDataNo = resultList.size();
    try {
        currentPage = ((Number)pageNavigationVo.getClass().getMethod("getCurrentPage").invoke(pageNavigationVo)).intValue();
        totalPage   = ((Number)pageNavigationVo.getClass().getMethod("getTotalPage").invoke(pageNavigationVo)).intValue();
        currDataNo  = ((Number)pageNavigationVo.getClass().getMethod("getCurrDataNo").invoke(pageNavigationVo)).intValue();
    } catch(Exception ignore){}

    String ssUserNo =  param.getString("ss_user_no");
    String selMid  = param.getString("sch_bbs_se_code_m");
    String schText = param.getString("sch_text");
    String schType = param.getString("sch_type");
    String selMidNm  = "10".equals(selMid)? "공지사항" :"문의하기";
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover"/>
    <title>게시판</title>

    <!-- 분리된 모바일 CSS 링크 (경로는 프로젝트 구조에 맞춰 조정) -->
    <link rel="stylesheet" href="/common/css/mobile/board-mobile.css?v=<%= System.currentTimeMillis() / 1000 %>" />

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
            submitTo('/front/board/selectPageListBoard.do','get');
        }
        function fnInsertForm(){
            submitTo('/front/board/insertFormBoard.do','post');
        }
        function fnSelect(seq){
            document.getElementsByName('bbs_seq')[0].value=seq;
            submitTo('/front/board/selectBoard.do','get');
        }
        function fnSearch(){
            $('currentPage').value='1';
            submitTo('/front/board/selectPageListBoard.do','get');
        }
        document.addEventListener('DOMContentLoaded',function(){
            var t=document.querySelector('[name=sch_text]');
            if(t){
                t.addEventListener('keydown',e=>{if(e.key==='Enter')e.preventDefault();});
                t.addEventListener('keyup',e=>{if(e.key==='Enter')fnSearch();});
            }

            var more=$('btnMore');
            if(more) more.addEventListener('click',()=>{
                var c=parseInt($('currentPage').value||'1',10);fnGoPage(c+1);
            });
        });
    </script>
</head>
<body>

<div class="header">
    <div class="titlebar">
        <h1><%=selMidNm%></h1>
        <% if ("20".equals(selMid)) { %>
        <button type="button" class="btn btn-write" onclick="fnInsertForm();return false;">+ 등록</button>
        <% } %>
    </div>
    <form id="aform" method="get" action="/front/board/selectPageListBoard.do" style="margin:0;">
        <input type="hidden" name="ss_user_no" id="ss_user_no" value="<%=ssUserNo%>"/>
        <input type="hidden" name="sch_bbs_se_code_l" id="sch_bbs_se_code_l" value="R010170"/>
        <input type="hidden" name="bbs_seq"/>
        <input type="hidden" name="currentPage" id="currentPage" value="<%=currentPage%>"/>
        <input type="hidden" name="sch_type" id="search_list_st" value="<%=schType%>"/>
        <input type="hidden" name="sch_bbs_se_code_m" id="sch_bbs_se_code_m" value="<%=selMid%>"/>
        <div class="search">
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
