지점 및 지점 판매자 등록 프로세스 추가 rest api 변경

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

   2.회원가입시 BRANCH_ID를 입력받아 op_user 테이블에 저장
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
