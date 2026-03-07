1. 챗팅 및 상품 시스템 구조 변경 상세 설계 (MRD)

1.1 데이터베이스 및 필드 변경
테이블은 mrd/chat_sql.md 참조
tb_product 테이블: WHOLESALER_NO 필드 삭제 (도매처 개념 제거).
tb_chat_room 테이블: SELLER_ID 컬럼을 BRANCH_ID로 변경 (지점 중심의 관리).
시스템 환경: SYSTEM_TYPE = 1 관련 로직 전체 삭제 (오직 SYSTEM_TYPE = 2인 본사-지점-구매자 구조만 유지).

1.2 room*id 생성 로직 정의
room_id는 고유 식별을 위해 [상품ID]*[참여 주체 1]\_[참여 주체 2] 구조로 생성합니다.

용자 권한 대상 room_id 구성 방식
구매자 (ROLE_PUB) 소속 지점 productId + userId + branchId
지점 (ROLE_PROJ) 본사 productId + branchId + 본사branchId(2)
본사 (ROLE_SELL) 지점 선택 productId + targetBranchId + 본사branchId(2)

1.3 챗팅 흐름 (Communication Flow)
구매자: 오직 본인이 소속된 지점(ROLE_PROJ) 관리자와만 대화 가능.
지점: 본인 지점의 구매자(ROLE_PUB) 및 본사(ROLE_SELL) 관리자와 대화 가능.
본사: 시스템 내 모든 지점(ROLE_PROJ) 관리자와 대화 가능.

1.4 Push 알림 로직 개선
대상 특정: 특정 userId가 아닌 ROLE과 BRANCH_ID 조합으로 대상 특정.
구매자 문의 시 → 해당 지점(BRANCH_ID)의 모든 ROLE_PROJ 사용자에게 발송.
본사가 지점에 메시지 시 → 해당 지점(BRANCH_ID)의 모든 ROLE_PROJ 사용자에게 발송.

1.5 안드로이드 소스 변경
1-5-1 tb_product 테이블에 WHOLESALER_NO 필드 삭제
안드로이드, 서버소스 모두 수정 필요
WHOLESALER_NO 관련되 모든 소스 삭제
room_id를 생성하기위한 WHOLESALER_NO 삭제
1-5-2 안드로이드 소스에서 WHOLESALER_NO 가져외기 위한 모든 소스 삭제

1-5-3 상품리스트에서 상품상세 넘어갈때 userId 를 넘기지 않음
상품상세에서 상품올린 userId 를 알필요가 없
아래 로직 삭제
putExtra(AdDetailActivity.EXTRA_USER_ID, item.userId)

1-5-4 안드로이드 SYSTEM_TYPE=2인 경우만 사용
Constants.SYSTEM_TYPE 항목 삭제
SYSTEM_TYPE=1 인경우 모든 소스 삭제

1-6 서비스 단계별 Push 알림 발송 정의
1-6-1. 발생 이벤트 및 발송 조건
번호,발생 이벤트,발송 조건 (Trigger),수신 대상 (Receiver),알림 메시지 예시
1,신규 상품 등록,본사(ROLE_SELL) 상품 등록 완료 시,"전체 사용자 (ROLE_PUB, ROLE_PROJ)",[신상품] 새로운 상품이 등록되었습니다. 지금 확인해보세요!
2,주문/결제 완료,구매자(ROLE_PUB) 결제 완료 시,해당 지점(ROLE_PROJ) & 본사(ROLE_SELL),[주문완료] 새로운 주문 접수 (주문번호: {order_id})
3,입금 확인 요청,지점(ROLE_PROJ)이 본사에 요청 시,본사(ROLE_SELL) 전체,[입금확인요청] [{branch_nm}]에서 입금을 요청했습니다.
4,배송 시작,상태가 **'배송중'**으로 변경 시,해당 주문 구매자 (ROLE_PUB),[배송시작] 상품을 택배사에 전달하였습니다. (송장번호 확인)

1-6-2. 기술적 구현 가이드 (개발 참고용)
Logic: 관리자/프론트에서 주문 상태 변경(DELIVERY_READY → DELIVERING) 및 송장 번호 저장 API 호출 시 트리거.

Target 추출: tb_order 테이블의 user_id를 조회하여 단일 발송.

Android Deep-Link: 푸시 클릭 시 OrderDetailActivity로 이동하도록 order_id를 데이터 페이로드(Data Payload)에 포함.

Landing Activity: com.package.OrderDetailActivity (예시)

Params: {"order_id": "12345"}

1-6-3. 소스 구조 개선 (Refactoring)
기존 참조: TnProductService.insertProductWithImages 내의 sendPushToTopic 로직.
개선 방향: \* PushService(또는 공통 Component)로 푸시 발송 로직 분리.
호출 위치: 1. 관리자 상품 등록 (mgt/product/) 2. 프론트/앱 결제 완료 시 3. 주문 상태 변경(입금확인, 배송시작 등) 시
