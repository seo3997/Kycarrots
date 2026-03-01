1. 사용자 권한 및 회원 구분 (MEMBER_CODE)
   이미 코드가 구현되어 있다.
   1.1 op_author 의 ROLE_CODE 권한코드 정의
   ROLE_ADMIN: 시스템 전체 인프라 및 마스터 데이터를 관리하는 시스템 어드민
   ROLE_SELL: 입금 확인, 배송 처리, 물류를 담당하는 본사 운영 관리자
   ROLE_PROJ: 독립 결제창을 운영하며 본사에 원가를 송금하는 지점 판매자
   ROLE_PUB: 지점 앱을 통해 물건을 구매하는 일반 사용자
   1.2 op_user의 MEMBER_CODE에 회원구분코드를 사용자 권한을 부여한다.

2. 지점 및 지점 판매자 등록 프로세스 추가
   테이블은 mrd/branch_sql.md 참조
   기술스펙은 mrd/branch_tech.md 참조
   BRANCH_ID 추가됨 로그인, 회원가입 ,결제시 BRANCH_ID를 파악할수 있도록 해야한다.  
   2.1 테이블 변경
   2.1.1 추가되는 테이블
   tb_branches:지점 테이
   2.1.2 기존 테이블 컬럼 추가
   op_user
   tb_orders
   tb_order_items
   tb_payments  
   2.2 업무프로세스 추가
   2.2.1 지점 생성 프로세스
   1. 지점 및 판매자 등록 흐름 (Admin Logic)
      본사 관리자(ROLE_SELL) 로그인: 지점 관리 메뉴 접속.
      지점 정보 입력: 지점명, 토스 키, 앱 패키지명 등 tb_branches 정보 입력.
      지점 판매자 정보 입력: 해당 지점을 운영할 계정 정보(ID, 비밀번호 등) 입력.
      트랜잭션 처리:
      tb_branches에 지점 데이터 생성 및 BRANCH_ID 발급.
      op_user에 발급된 BRANCH_ID를 할당하고 MEMBER_CODE = 'ROLE_PROJ'로 계정 생성.
3. rest api 변경
   BRANCH_ID가 추가됨에 따라 모든 API는 아래 규칙을 따릅니다.
   Header/Param 전략: 모든 클라이언트(App/Web) 요청 시 X-Branch-Id를 Header에 담거나, URL 파라미터로 명시해야 합니다.
   주요 변경 API:
   회원가입 (POST /api/member/join): 전달받은 BRANCH_ID를 op_user 테이블에 강제 삽입.
   로그인 (POST /api/login): 회원의 MEMBER_CODE와 BRANCH_ID를 토큰(JWT)에 담아 반환.
   주문생성 (POST /api/shop/order): tb_orders와 tb_order_items 생성 시 BRANCH_ID를 상속받아 저장.
   결제승인 (POST /api/shop/pay/confirm): 주문의 BRANCH_ID를 조회하여 해당 지점의 TOSS_SECRET_KEY로 토스 서버와 통신.

4. 각지점벌 상품주문을 할수 있는 프런터 페이지 개발
   기존 front/board 구조를 활용하여 분양몰 전용 상점을 구축합니다.

4.1 폴더 구조 및 기능 매핑
front/shop/list: 상품 목록 (전 지점 공통 상품 로드)
front/shop/detail: 상품 상세 및 주문하기 버튼
front/shop/checkout: 결제 페이지 (지점별 TOSS_CLIENT_KEY 로드 및 결제창 호출)
front/shop/order-list: 내 주문 내역 (로그인한 사용자의 BRANCH_ID 기준 조회)
4.2 로그인/회원가입 수정 포인트
회원가입 (front/member): 앱 빌드 시 설정된 BuildConfig.BRANCH_ID를 가입 API에 자동으로 포함하도록 수정.
로그인 (front/login): 로그인 성공 후 지점별 테마 색상이나 로고를 세팅하는 로직 추가.

5. 대시보드 추가
   지점별 정산 대시보드: ROLE_PROJ가 로그인했을 때 "이번 달 본사에 입금해야 할 총 원가"를 한눈에 보여주는 요약 페이지가 필요합니다.

입금 승인 자동화 알림: ROLE_SELL이 본사 관리자에서 '입금 확인'을 누르면, 해당 BRANCH_ID를 사용하는 ROLE_PUB에게 "결제 완료! 곧 배송이 시작됩니다"라는 FCM 푸시가 가도록 로직을 추가하세요.

지점별 도메인/패키지명 검증: API 호출 시 BRANCH_ID와 실제 요청한 앱의 패키지명이 일치하는지 검증하는 인터셉터(Interceptor)를 두면 보안이 강화됩니다.
