1. 결제 시스템 프로세스 정의 (Flow)
   결제는 크게 주문 생성 -> 결제 요청 -> 결제 승인/검증 -> 결과 처리의 4단계로 나뉩니다.

Step 1: 주문 생성 (Internal)
Trigger: 상품 상세 페이지에서 '결제하기' 클릭.
Action: \* tb_orders에 ORDER_STATUS='READY', PAYMENT_STATUS='READY' 상태로 레코드 생성.

tb_order_items에 선택한 상품/옵션 정보 스냅샷 저장 (추후 상품 가격이 변해도 주문 당시 가격 유지).

Output: MERCHANT_UID (가맹점 주문번호) 생성.

Step 2: 결제 요청 (Client to PG)
Action: 클라이언트(App/Web)에서 PG사 SDK(포트원, 토스 등) 호출.

Data: 주문번호, 금액, 상품명, 구매자 정보를 전달.

Step 3: 결제 승인 및 검증 (Server to PG)
Action: PG사로부터 전달받은 imp_uid 또는 tid를 서버로 전달.

Critical Check: 서버 대 서버로 PG사에 해당 결제 건의 실제 결제 금액을 조회하여, tb_orders의 TOTAL_PAY_AMOUNT와 일치하는지 반드시 검증 (위변조 방지).

Step 4: 결제 정보 업데이트 (Database)
Action: \* tb_payments에 PG사 응답 데이터(카드사, TID, 영수증 URL 등) 기록.

tb_orders의 ORDER_STATUS 및 PAYMENT_STATUS를 PAID로 업데이트.

Step 5: 지점 판매에따른 결제프로세스 수정
토스 페이먼츠 연동시 지점별 토스 클라이언트 키, 시크릿 키, MID를 사용해야함.
mrd/branch_sql.md 참조
tb_branches 의 TOSS_CLIENT_KEY, TOSS_SECRET_KEY, TOSS_MID 를 사용해야함.
