<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty addressList}">
        <c:forEach var="addr" items="${addressList}">
            <div style="background: white; padding: 1rem; border-radius: 12px; margin-bottom: 0.8rem; border: 1px solid #e2e8f0; cursor: pointer; transition: all 0.2s;" 
                 onclick="selectAddress({
                     recipientName: '${addr.RECIPIENT_NAME}',
                     recipientPhone: '${addr.RECIPIENT_PHONE}',
                     zipCode: '${addr.ZIP_CODE}',
                     addressMain: '${addr.ADDRESS_MAIN}',
                     addressDetail: '${addr.ADDRESS_DETAIL}',
                     memo: '${addr.MEMO}'
                 })">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.3rem;">
                    <c:set var="isDefaultStr">${addr.IS_DEFAULT}</c:set>
                    <span style="font-weight: 700; font-size: 1rem;">${addr.ADDRESS_NAME}
                        <c:if test="${isDefaultStr eq 'true' or isDefaultStr eq '1'}">
                            <span style="background: #eff6ff; color: #3b82f6; padding: 0.1rem 0.4rem; border-radius: 4px; font-size: 0.75rem; margin-left: 0.3rem; vertical-align: middle;">기본</span>
                        </c:if>
                    </span>
                </div>
                <div style="margin-bottom: 0.3rem;">
                    <span style="font-weight: 600; color: #334155;">${addr.RECIPIENT_NAME}</span>
                    <span style="font-size: 0.9rem; color: #64748b; margin-left: 0.5rem;">${addr.RECIPIENT_PHONE}</span>
                </div>
                <div style="font-size: 0.9rem; color: #475569; line-height: 1.4;">
                    [${addr.ZIP_CODE}] ${addr.ADDRESS_MAIN}<br>
                    ${addr.ADDRESS_DETAIL}
                </div>
                <c:if test="${not empty addr.MEMO}">
                    <div style="font-size: 0.85rem; color: #94a3b8; margin-top: 0.4rem;">메모: ${addr.MEMO}</div>
                </c:if>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 3rem 0; color: #94a3b8;">
            저장된 배송지가 없습니다.
        </div>
    </c:otherwise>
</c:choose>
