<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>배송지 목록</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Outfit', sans-serif; padding: 20px; background-color: #f8fafc; color: #1e293b; }
        .title { font-size: 1.25rem; font-weight: 700; margin-bottom: 20px; color: #2563eb; }
        .address-item { 
            background: white; padding: 15px; border-radius: 12px; margin-bottom: 12px; 
            box-shadow: 0 1px 3px rgba(0,0,0,0.1); border: 1px solid #e2e8f0; cursor: pointer;
            transition: transform 0.2s, border-color 0.2s;
        }
        .address-item:hover { transform: translateY(-2px); border-color: #2563eb; }
        .item-head { display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px; }
        .tag { font-size: 0.75rem; padding: 2px 8px; border-radius: 4px; font-weight: 600; }
        .tag-default { background: #eff6ff; color: #2563eb; }
        .tag-normal { background: #f1f5f9; color: #64748b; }
        .name { font-weight: 700; font-size: 1rem; }
        .phone { font-size: 0.85rem; color: #64748b; }
        .addr { font-size: 0.9rem; margin-top: 5px; color: #334155; }
        .empty-msg { text-align: center; padding: 50px 0; color: #94a3b8; }
        .btn-select { 
            width: 100%; border: none; background: #2563eb; color: white; 
            padding: 10px; border-radius: 8px; font-weight: 600; cursor: pointer; 
            margin-top: 10px; font-size: 0.9rem; 
        }
    </style>
</head>
<body>
    <div class="title"><i class="fas fa-truck"></i> 저장된 배송지</div>

    <c:choose>
        <c:when test="${not empty addressList}">
            <c:forEach var="addr" items="${addressList}">
                <div class="address-item" onclick="selectItem('${addr.RECIPIENT_NAME}', '${addr.RECIPIENT_PHONE}', '${addr.ZIP_CODE}', '${addr.ADDRESS_MAIN}', '${addr.ADDRESS_DETAIL}', '${addr.MEMO}')">
                    <div class="item-head">
                        <span class="name">${addr.ADDRESS_NAME}</span>
                        <c:if test="${addr.IS_DEFAULT == 1}">
                            <span class="tag tag-default">기본</span>
                        </c:if>
                    </div>
                    <div style="margin-bottom: 5px;">
                        <span class="name">${addr.RECIPIENT_NAME}</span>
                        <span class="phone">${addr.RECIPIENT_PHONE}</span>
                    </div>
                    <div class="addr">
                        [${addr.ZIP_CODE}] ${addr.ADDRESS_MAIN}<br>
                        ${addr.ADDRESS_DETAIL}
                    </div>
                    <c:if test="${not empty addr.MEMO}">
                        <div style="font-size: 0.8rem; color: #94a3b8; margin-top: 5px;">메모: ${addr.MEMO}</div>
                    </c:if>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="empty-msg">저장된 배송지가 없습니다.</div>
        </c:otherwise>
    </c:choose>

    <script>
        function selectItem(name, phone, zip, addr1, addr2, memo) {
            window.opener.selectAddress({
                recipientName: name,
                recipientPhone: phone,
                zipCode: zip,
                addressMain: addr1,
                addressDetail: addr2,
                memo: memo
            });
            window.close();
        }
    </script>
</body>
</html>
