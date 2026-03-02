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
    89:반품완료
    90:EXCHANGED:교환완료
    99:주문확정

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

2-4 지점의 본사 입금상태코드
공통코드그룹:R010680
tb_orders.BRANCH_DEPOSIT_STATUS 필드 사용
10:WAITING:입금대기
20:DEPOSITED:입금완료
30:CANCEL:입금취소

3.회원 배송지 관리 기능 구현
3-1 테이블 생성
tb_address_book 테이블 생성되어 있음
mrd/payment_sql.md 참조 tb_address_book 참조

DB 구축: tb_address_book 테이블 신설 (회원번호 기준 멀티 주소 지원).
마이페이지 기능: 배송지 추가/수정/삭제 및 '기본 배송지 설정' 기능 구현.
주문서 연동: 주문 페이지 진입 시 '기본 배송지' 정보를 자동 로드할 것.
'배송지 목록' 버튼 클릭 시 팝업창을 통해 저장된 다른 주소를 선택할 수 있게 할 것.
신규 주소 입력 후 결제 시 '배송지 목록에 저장' 체크박스를 제공할 것.

3-2배송지 관리의 핵심 기능 (자동화 포인트)
테이블만 만든다고 끝나는 게 아니라, 주문서에서 어떻게 돌아갈지 로직을 잡아야 합니다.
① 기본 배송지 자동 설정 (IS_DEFAULT)
고객이 처음 주소를 등록하면 자동으로 IS_DEFAULT = 1로 저장합니다.
새로운 주소를 '기본 배송지'로 설정하면, 기존에 1이었던 다른 주소들은 자동으로 0으로 업데이트되어야 합니다.

② 최근 배송지 불러오기
주문서 페이지가 열릴 때, 해당 회원의 IS_DEFAULT = 1인 데이터를 먼저 조회해서 자동으로 입력 필드를 채워줍니다.

③ 배송 메모 자동화
MEMO 필드를 활용해 "경비실에 맡겨주세요", "배송 전 연락주세요" 같은 문구를 저장해두면 고객이 매번 타이핑할 필요가 없습니다.
