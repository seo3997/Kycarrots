<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover"/>
    <title>이용약관 · 개인정보처리방침</title>
    <link rel="stylesheet" href="/common/css/mobile/terms.css?v=<%=System.currentTimeMillis()/1000%>">

    <!-- 탭 전용 최소 스타일 (원하면 terms.css로 옮겨도 됨) -->
    <style>
        .tabs { max-width: 960px; margin: 0 auto; }
        .tab-nav { display:flex; gap:8px; padding:8px 0; border-bottom:1px solid var(--divider, #e5e5ea); position:sticky; top:0; background:var(--bg,#fff); z-index:5; }
        .tab-btn {
            appearance:none; border:1px solid var(--divider,#e5e5ea); background:var(--chip-bg,#f2f2f7);
            padding:10px 14px; border-radius:10px; font-size:15px; cursor:pointer;
        }
        .tab-btn[aria-selected="true"] { background:#5F567E; color:#fff; border-color:#5F567E; }
        .tab-panels { padding-top:10px; }
        .tab-panel { display:none; }
        .tab-panel.active { display:block; }
    </style>
</head>
<body>
<div id="wrapper">
    <div class="join_terms">
        <div class="tabs" role="tablist" aria-label="약관/정책 탭">

            <div class="tab-nav">
                <button class="tab-btn" role="tab" id="tab-terms1" aria-controls="panel-terms1" aria-selected="true" data-target="terms1">
                    이용약관
                </button>
                <button class="tab-btn" role="tab" id="tab-terms2" aria-controls="panel-terms2" aria-selected="false" data-target="terms2">
                    개인정보처리방침
                </button>
            </div>

            <div class="tab-panels">
                <!-- 탭 패널 1 -->
                <section id="panel-terms1" class="tab-panel active" role="tabpanel" aria-labelledby="tab-terms1">
                    <h2 class="section-title" style="margin:14px 0 10px;font-size:clamp(18px,3.8vw,22px);font-weight:800;">이용약관</h2>
                    <jsp:include page="/link/join_terms1.do">
                        <jsp:param name="fragment" value="Y"/>
                    </jsp:include>
                </section>

                <!-- 탭 패널 2 -->
                <section id="panel-terms2" class="tab-panel" role="tabpanel" aria-labelledby="tab-terms2">
                    <h2 class="section-title" style="margin:14px 0 10px;font-size:clamp(18px,3.8vw,22px);font-weight:800;">개인정보처리방침</h2>
                    <jsp:include page="/link/join_terms2.do">
                        <jsp:param name="fragment" value="Y"/>
                    </jsp:include>
                </section>
            </div>

            <div style="padding: 30px 0; text-align: center; border-top: 1px solid #eee; margin-top: 20px;">
                <form action="/front/registForm.do" method="get">
                    <input type="hidden" name="agree" value="Y">
                    <c:forEach var="p" items="${param}">
                        <c:if test="${p.key != 'agree'}">
                            <input type="hidden" name="${p.key}" value="<c:out value="${p.value}"/>">
                        </c:if>
                    </c:forEach>
                    <button type="submit" style="background:#5F567E; color:#fff; border:none; padding:15px 40px; border-radius:12px; font-size:18px; font-weight:700; cursor:pointer; width:100%; max-width:400px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">이용약관 및 개인정보 동의하고 계속하기</button>
                </form>
            </div>

        </div>
    </div>
</div>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        const branchIdLoad = parseInt("${branchInfo.BRANCH_ID}");
        if(isNaN(branchIdLoad) || branchIdLoad <= 2){
            alert("판매지점이 선택되지 않았습니다. 도메인을 확인하세요.");
            location.href = "/";
            return;
        }
    });

    (function () {
        const navBtns = Array.from(document.querySelectorAll('.tab-btn'));
        const panels = {
            terms1: document.getElementById('panel-terms1'),
            terms2: document.getElementById('panel-terms2')
        };

        function activate(key, pushHash) {
            // 버튼 상태
            navBtns.forEach(btn => {
                const on = btn.dataset.target === key;
                btn.setAttribute('aria-selected', on ? 'true' : 'false');
            });
            // 패널 상태
            Object.keys(panels).forEach(k => {
                panels[k].classList.toggle('active', k === key);
            });
            if (pushHash) {
                history.replaceState(null, '', '#' + key);
            }
            // 화면 맨 위로 튀는 것 방지
            panels[key].scrollIntoView({ block: 'start', behavior: 'instant' });
            window.scrollBy(0, -8);
        }

        // 클릭 이벤트
        navBtns.forEach(btn => {
            btn.addEventListener('click', () => activate(btn.dataset.target, true));
        });

        // 해시로 초기 탭 결정 (#terms1 | #terms2)
        const hash = (location.hash || '#terms1').replace('#', '');
        activate(hash === 'terms2' ? 'terms2' : 'terms1', false);

        // 뒤로가기로 해시 바뀔 때도 반영
        window.addEventListener('hashchange', () => {
            const h = (location.hash || '#terms1').replace('#', '');
            activate(h === 'terms2' ? 'terms2' : 'terms1', false);
        });
    })();
</script>
</body>
</html>
