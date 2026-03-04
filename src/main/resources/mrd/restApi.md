지점 및 지점 판매자 등록 프로세스 추가 rest api 변경
rest api 변경시 안드로이드 소스 파일도 변경해야 함 안드로이드 소스 파일은 /Users/soo/kycarrotsApp/에 존재함

1. 로그인 지점 정보 추가  
   1.1 로그인 api 변경
   POST /api/login
   출력값에 branch*info 추가
   {
   "resultCode": 0,
   "token": "string",
   "login_idx": "string",
   "login_si": "string", // 회원 가입 시 지역(시)
   "login_gu": "string", // 회원 가입 시 지역(구)
   "login_sex": "string", // 성별
   "login_age": "string", // 연령대
   "login_nm": "string", // 회원 이름
   "member_code": "string", // 회원 고유 코드
   "login_id": "string", // 아이디
   "login_cd": "string", // 내부 관리 코드
   "login_social_id": "string", // 소셜 로그인 ID (있는 경우)
   "branch_info": {
   "branch_id": 4, // 지점 PK
   "branch_code": "BR_001", // 지점 고유 코드 (예: 강남본점)
   "branch_name": "강남 본점", // 앱 상단 및 영수증 노출명
   "logo_image_url": "https://cdn.com/logo.png", // 지점별 커스텀 로고
   "branch_status": "RUNNING", // 운영 상태 (RUNNING/STOPPED)
   "toss_client_key": "test_ck*...", // 토스 결제창 띄울 때 필수 키
   "bank_cd": "088", // 입금 은행 코드 (무통장 입금용)
   "account_no": "110-123-456789", // 입금 계좌 번호
   "account_holder": "홍길동(본사)", // 예금주 성명
   "base_shipping_fee": 3000, // 기본 배송비
   "free_shipping_threshold": 50000, // 무료배송 기준 금액
   "extra_shipping_fee": 3000, // 도서산간 추가 배송비
   "is_use_custom_price": 0, // 지점별 판매가 수정 가능 여부 "company_name": "(주)에이아이컴퍼니",
   "representative_name": "이대표",
   "business_number": "123-45-67890",
   "tongsin_number": "2024-서울강남-0123",
   "cs_phone": "02-123-4567", // 고객센터 번호
   "address": "서울특별시 강남구 테헤란로..."
   }
   }

2. 회원가입 BRANCH_ID 추가
   BRANCH_ID를 입력받아 op_user 테이블에 저장
   EndPoint:/api/members/register
   출력값에 branch_info 추가

   2-1input
   {
   "userNo": "string",
   "userId": "string",
   "password": "string",
   "userNm": "string",
   "cttpcSeCode": "string",
   "cttpc": "string",
   "email": "string",
   "areaCode": "string",
   "areaCodeNm": "string",
   "areaSeCodeS": "string",
   "areaSeCodeSNm": "string",
   "areaSeCodeD": "string",
   "userSttusCode": "string",
   "loginDt": "string",
   "userAge": "string",
   "birthDate": "string",
   "uniqueIdentifier": "string",
   "deviceId": "string",
   "duplicateIdentifier": "string",
   "gender": "string",
   "memberCode": "string",
   "citizenshipType": "string",
   "passwordHash": "string",
   "referrerId": "string",
   "registerNo": "string",
   "registDt": "string",
   "updusrNo": "string",
   "updtDt": "string",
   "pushToken": "string",
   "deviceType": "string",
   "wholesalerNo": "string",
   "provider": "string",
   "providerUserId": "string",
   "branchId": "string",
   "joinAppPackage": "string"
   }
   2-2output
   {
   "resultCode": 0,
   "token": "string",
   "login*idx": "string",
   "login_si": "string", // 회원 가입 시 지역(시)
   "login_gu": "string", // 회원 가입 시 지역(구)
   "login_sex": "string", // 성별
   "login_age": "string", // 연령대
   "login_nm": "string", // 회원 이름
   "member_code": "string", // 회원 고유 코드
   "login_id": "string", // 아이디
   "login_cd": "string", // 내부 관리 코드
   "login_social_id": "string", // 소셜 로그인 ID (있는 경우)
   "branch_info": {
   "branch_id": 4, // 지점 PK
   "branch_code": "BR_001", // 지점 고유 코드 (예: 강남본점)
   "branch_name": "강남 본점", // 앱 상단 및 영수증 노출명
   "logo_image_url": "https://cdn.com/logo.png", // 지점별 커스텀 로고
   "branch_status": "RUNNING", // 운영 상태 (RUNNING/STOPPED)
   "toss_client_key": "test_ck*...", // 토스 결제창 띄울 때 필수 키
   "bank_cd": "088", // 입금 은행 코드 (무통장 입금용)
   "account_no": "110-123-456789", // 입금 계좌 번호
   "account_holder": "홍길동(본사)", // 예금주 성명
   "base_shipping_fee": 3000, // 기본 배송비
   "free_shipping_threshold": 50000, // 무료배송 기준 금액
   "extra_shipping_fee": 3000, // 도서산간 추가 배송비
   "is_use_custom_price": 0, // 지점별 판매가 수정 가능 여부 "company_name": "(주)에이아이컴퍼니",
   "representative_name": "이대표",
   "business_number": "123-45-67890",
   "tongsin_number": "2024-서울강남-0123",
   "cs_phone": "02-123-4567", // 고객센터 번호
   "address": "서울특별시 강남구 테헤란로..."
   }
   }
   2-3 회원가입시 지점 선택 추가
   안드로이드 회원가입 화면에서 지점 선택 추가, 저장버튼 클릭시 branchId를 서버로 전송
   안드로이드 화면은 MembershipActivity.kr, activity_membership.xml
   서버 /api/members/register 는 branchId 이미 구현되어 있음
   브랜치리스트 api /api/branch/list 추가 테이블은 tb_branches참조(sql문은 mgt/branch.xml 참조) 브랜치리스트에서 관리자,본사는 빼고 노출 BRANCH_ID=1,2 제외

3. 상품리스트 변경
   3-1 상품리스트 api 변경
   EndPoint:/api/product/buyListAdvertise
   3-1 sql문 변경 (mgt/product도 동일)
   본사에서 물건등록 하기에 tb_product 조회시 USER_NO,WHOLESALER_NO 삭제
   SALE_STATUS 필터링
   <if test="memberCode == 'ROLE_SELL'">
   AND A.USER_NO = #{ss_user_no}
   </if>
   <if test="memberCode == 'ROLE_PROJ'">
   AND A.WHOLESALER_NO = #{ss_user_no}
   </if>
   3-2 앱에서는 판매중인상품만보기 체크시 조호조건 있음
   3-3

4. 토스페이먼트 결제
   앱에서 토스페이먼트 요청시 서버에서 내려온 toss_client_key를 사용 - 앱만수정
   /api/payment/confirm 에서 사용하는 SECRET_KEY는 이미 구현되어 있음

5. 상품주문하기 웹과 동일하게 변경
   안드로이드 상품 상세화면에 구매하기 웹화면과 동일하게 변경
   5-1 안드로이드 상품상세 화면
   AdDetailActivity.kt activity_detail.xml
   /front/shop/detail.jsp 와 동일한 화면 구성
   상세보기는 네이티브로 이미구현되어 있어서 아래 부분만 수정한다.
   배송비 가져오는 부분, 구매가능수량 ,수량 수정시 구매가능 수량 변경은 detail.jsp를 참조해서 변경한다.
   배송비는 로그인시 branch_info의 base_shipping_fee,free_shipping_threshold로 내려오고 있음
   구매가능수량도 detail.jsp에서 사용하는 서버 프로그램 참조 필요하면 /api/product/detail/{productId} RestApi 수정

5-2 안드로이드 주문하기 화면
5-2-1 주문하기 화면 변경
OrderDetailActivity.kt activity_order.xml
/front/shop/chcout.jsp 와 동일한 화면 구성  
/front/shop/chcout.jsp 에서 사용하는 서버 프로그램 참조 필요하면 /api/product/detail/{productId} RestApi 수정 및 배송지 목록,배송지 추가,배송지 수정,배송지 삭제,배송지 기본설정 RestApi 추가
5-2-2
토스페이먼트 결제
앱에서 토스페이먼트 요청시 서버에서 내려온 toss_client_key를 사용 - 앱만수정
/api/payment/confirm 에서 사용하는 SECRET_KEY는 이미 구현되어 있음

6. 지점.본점 안드로이드 대시보드 변경 1.안드로이드 대시보드 화면변경
   6-1 안드로이드 대시보드 화면
   안드로이드 화면 DashboardActivity.kt activity_dashboard.xml
   /views/admin/main.jsp 와 동일한 화면 구성
   rest/dashboard 폴드이용 restApi /api/dashboard 로 개발
   로직은 mgt/main/dashBoard.do 참조 restApi 구조는 rest/branch 를 참조한다.

   6-2 주문관리 화면 추가
   안드로이드 화면에 지점,본점에서 관리 하는 주문관리 화면 추가
   /views/mgt/order/orderList.jsp, /views/mgt/order/orderDetail.jsp 와 동일한 화면 구성
   rest/order 폴드이용 restApi /api/order 로 개발
   로직은 mgt/order/orderList.do 참조 restApi 구조는 rest/branch 를 참조한다.
