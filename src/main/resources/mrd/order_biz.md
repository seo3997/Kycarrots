1.  주문 목록 관리 기획 정의서 (MRD)
    구현방안은 order_tech.md 를 참조한다.
    1-1 앱에서 사용하는 RestAPI

        1-1-1. 개요
        목적: 사용자가 본인의 과거 구매 내역(결제 완료, 취소 등)을 통합적으로 확인하고 관리함.
        현재 앱에서 구매역역 PurchaseHistoryController의 /api/purchases/buyer/{buyerNo} 의 주문리스트를 보여주고 있는데 이부분을 교체할거야


        참조 모델: 기존 PurchaseHistoryController.java의 페이징(PageRequest) 및 구매자별 리스트 조회 로직.

        1-1-2. 주요 기능 및 요구사항 (Order Management)
        A. 주문 목록 페이징 조회
        테이블은 table은 resources/mrd/payment_sql.md의 tb_orders(마스터)와 tb_order_items를 참조한다.
        기능: buyerNo를 기준으로 최신순 주문 목록을 로드함.

        데이터 결합: 단순 이력(History)이 아닌 tb_orders(마스터)와 tb_order_items(상세)를 Join하여 주문 대표 상품명과 결제 상태를 함께 표시.(tb_orders(마스터)와 tb_order_items는 항상 1:1 관계이다. 즉 주문 하나당 상품이 하나만 존재한다.)

        B. 주문 상태값 관리 (order_status)

        PAID: 결제 완료 상태. (취소 버튼 활성화)

        CANCELLED: 토스 API를 통해 취소가 완료된 상태. (취소 버튼 숨김 및 '취소됨' 표시)

        C. 실시간 결제 취소 (Cancel)

        기능: '결제 완료' 상태인 주문에 대해 즉시 PG 취소 요청을 수행.

        동작: 클릭 시 토스페이먼츠 취소 API 호출 후 성공하면 DB의 상태값을 CANCELLED로 변경.
        1-1-3. API 상세 설계 (Data Mapping)기존 PurchaseHistoryController의 형식을 계승하여 정의합니다.기능엔드포인트 (Endpoint)메서드설명주문 목록 조회/api/orders/buyer/{buyerNo}GET기존 listByBuyer와 동일한 페이징 처리 (page, size).주문 상세 조회/api/orders/{orderNo}GET특정 주문의 상세 상품 리스트(tb_order_items) 포함.결제 취소 실행/api/orders/cancelPOSTorderNo와 취소 사유를 받아 PG 취소 및 DB 업데이트 수행.

    1-2 관리자 주문리스트 관리  
    com.whomade.kycarrots.mgt.product.web.ProdcutController.java 기준으로 주문리스트, 주문상세, 주문취소 기능을 구현한다.

2.  주문관련 코드 관리
    2-1 주문상태코드
    공통코드그룹:R010650
    tb_orders.ORDER_STATUS 필드 사용
    10:READY:결제 대기
    20:FAILED:결제 실패
    30:PAID:결제완료
    40:CANCEL:주문취소
    50:PREPARING:배송준비중
    60:SHIPPING:배송중
    70:DELIVERED:배송완료
    80:RETURN_REQUESTED:반품요청
    90:EXCHANGED:교환완료

2-2.결재 상태 코드
공통코드그룹:R010670
tb_orders.PAYMENT_STATUS 필드 사용
10:READY: 결제대기 중 (주문서는 생성되었으나 아직 결제 승인이 완료되지 않은 초기 상태)
20:FAILED: 결제 실패 (결제 과정 중 오류가 발생하거나 사용자가 결제를 중단한 상태)
30:PAID: 결제 완료 (토스페이먼츠 등 결제 대행사로부터 최종 승인이 완료된 상태)
40:CANCEL: 결제 취소 (환불 처리가 완료되어 결제가 무효화된 상태)

2-3 상품상태코드
공통코드그룹:R010630
tb_product.SALE_STATUS 필드 사용
0:승인요청
1:판매중
10:예약중
20:품절
30:판매중지
98:반려
99:판매완료
