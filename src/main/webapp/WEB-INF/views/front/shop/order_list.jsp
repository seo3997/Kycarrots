<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문 내역 - ${branchInfo.BRANCH_NAME}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #2563eb;
            --bg: #f8fafc;
            --text: #1e293b;
            --text-muted: #64748b;
            --radius: 12px;
            --shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1);
        }

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Outfit', sans-serif; background-color: var(--bg); color: var(--text); }

        .header { background: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05); }
        .logo { display: flex; align-items: center; gap: 0.75rem; text-decoration: none; color: var(--text); font-weight: 700; font-size: 1.25rem; }

        .container { max-width: 900px; margin: 2rem auto; padding: 2rem; }
        h1 { font-size: 1.75rem; margin-bottom: 2rem; }

        .order-card { background: white; border-radius: 20px; box-shadow: var(--shadow); padding: 1.5rem; margin-bottom: 2rem; border: 1px solid #e2e8f0; }
        .order-header { display: flex; justify-content: space-between; border-bottom: 1px solid #f1f5f9; padding-bottom: 1rem; margin-bottom: 1.5rem; }
        .order-id { font-weight: 700; color: var(--text-muted); }
        .order-date { font-size: 0.875rem; color: var(--text-muted); }

        .order-item { display: flex; gap: 1.5rem; align-items: center; }
        .item-img { width: 80px; height: 80px; background: #f1f5f9; border-radius: 12px; background-size: cover; background-position: center; }
        .item-info { flex: 1; }
        .item-name { font-weight: 600; font-size: 1.125rem; }
        .item-meta { color: var(--text-muted); font-size: 0.875rem; }

        .order-status { display: flex; align-items: center; gap: 0.5rem; }
        .badge { padding: 0.25rem 0.75rem; border-radius: 20px; font-size: 0.75rem; font-weight: 600; }
        .badge-success { background: #dcfce7; color: #166534; }

        .empty-state { text-align: center; padding: 4rem 2rem; background: white; border-radius: 20px; box-shadow: var(--shadow); }
        .empty-state i { font-size: 3rem; color: #cbd5e1; margin-bottom: 1.5rem; }
        .btn-home { display: inline-block; margin-top: 1.5rem; padding: 0.75rem 1.5rem; background: var(--primary); color: white; text-decoration: none; border-radius: 10px; }
    </style>
</head>
<body>

<header class="header">
    <a href="/shop/list.do" class="logo">
        <div style="width: 40px; height: 40px; background: var(--primary); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white;">
            <i class="fas fa-shopping-bag"></i>
        </div>
        <span>${branchInfo.BRANCH_NAME}</span>
    </a>
</header>

<main class="container">
    <h1>주문 내역</h1>

    <c:choose>
        <c:when test="${not empty resultList}">
            <c:forEach var="item" items="${resultList}">
                <div class="order-card">
                    <div class="order-header">
                        <span class="order-id">주문번호: ${item.ORDER_ID}</span>
                        <span class="order-date">${item.REGIST_DT}</span>
                    </div>
                    <div class="order-item">
                        <div class="item-img" style="background-image: url('${item.PRODUCT_IMG_URL}')"></div>
                        <div class="item-info">
                            <div class="item-name">${item.PRODUCT_NAME}</div>
                            <div class="item-meta">수량: ${item.QTY}개 | 결제금액: ${item.PAY_AMOUNT}원</div>
                        </div>
                        <div class="order-status">
                            <span class="badge badge-success">결제완료</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <i class="fas fa-receipt"></i>
                <p>주문 내역이 없습니다.</p>
                <a href="/shop/list.do" class="btn-home">쇼핑하러 가기</a>
            </div>
        </c:otherwise>
    </c:choose>
</main>

</body>
</html>
