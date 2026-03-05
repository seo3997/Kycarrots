1. 챗팅 및 상품 시스템 구조 변경 상세 설계 (MRD)

1.1 데이터베이스 및 필드 변경
tb_product 테이블: WHOLESALER_NO 필드 삭제 (도매처 개념 제거).
tb_chat_room 테이블: SELLER_ID 컬럼을 BRANCH_ID로 변경 (지점 중심의 관리).
시스템 환경: SYSTEM_TYPE = 1 관련 로직 전체 삭제 (오직 SYSTEM_TYPE = 2인 본사-지점-구매자 구조만 유지).

1.2 room*id 생성 로직 정의
room_id는 고유 식별을 위해 [상품ID]*[참여 주체 1]\_[참여 주체 2] 구조로 생성합니다.

용자 권한 대상 room_id 구성 방식
구매자 (ROLE_PUB) 소속 지점 productId + userId + branchId
지점 (ROLE_PROJ) 본사 productId + branchId + 본사branchId(BR_0002)
본사 (ROLE_SELL) 지점 선택 productId + targetBranchId + 본사branchId(BR_0002)

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
