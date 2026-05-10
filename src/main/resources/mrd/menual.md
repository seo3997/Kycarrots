1.권한별 온라인 메뉴얼 생성
1.1 ROLE_CODE 권한코드 정의 아래 3개의 권한 메뉴얼을 html로 작성 1.본사: ROLE_SELL: 입금 확인, 배송 처리, 물류를 담당하는 본사 운영 관리자 2.판매지점: ROLE_PROJ: 독립 결제창을 운영하며 본사에 원가를 송금하는 지점 판매자 3.구매자: ROLE_PUB: 지점 앱을 통해 물건을 구매하는 일반 사용자

2.생성폴드  
 static/manual/menual_index.html 인덱스 페이지
static/manual/menual_process.html 전체 프로세스
static/manual/menual_sell.html 본사 메뉴얼
static/manual/menual_proj.html 지점 메뉴얼
static/manual/menual_pub.html 구매자 메뉴얼  
 static/manual/image/web/권한별폴드 이미지  
 static/manual/image/app/권한별폴드 이미지

front 배포시 같이 배포됨

3.메뉴얼에 포함되어야하는 내용
3-1 menual_process.html은 1번내용을 요약
3-2 본사,판매지점은 관리자화면 ,모바일웹, 모바일앱별도 메뉴얼 작성, 구매자는 모바일웹, 모바일앱 화면 기준으로 작성
3-3 관리자 화면은 아래 5.관리자 화면 접속 에서 아이디, 패스워드 로 로그인해서
7-1의 업무설명 url의 화면을 캡쳐해서 메뉴얼화
3-4 구매자 웹화면연 아래 6.구매자 웹화면 접속 에서 아이디, 패스워드로 로그인해서 7-1의 업무설명 url의 화면을 캡쳐해서 메뉴얼화
3-5 모바일앱은 특정한 폴드 이미지 참조 - 이건 이미지 작업후 진행

4.전체 프로세스
/mrd/아래의 \*\_biz\*.md 파일의 내용을 요약

5.관리자 화면 접속
http://asagong.com/admin/login.do
5-1 ROLE_SELL 접속
아이디:sel1@gmail.com
패스워드:1234
5-2 ROLE_PROJ 접속
아이디:amsa@gmail.com
패스워드:1234

6.구매자 웹화면 접속
6-1 http://amsa.asagong.com/ 로접속
아이디:amsa@gmail.com
패스워드:1234

7.권한별 업무
7-1 업무설명
7-1-1 ROLE_SELL
7-1-1-1 메인화면
http://amsagong.com/mgt/main/dashBoard.do - 본사 대시보드

1. 상세업무:
   미처리주문 - 판매지점에서 발주한 물건중에 아직 입금확인이 안된 주문
   지점미금액 - 판매지점별로 주문건이 있으나 입금확인이 안된 금액 합계
   오늘의 수금액 - 금일 입금 확인이 완료된 주문금액의 합계
   배송중인주문 - 판매지점에서 배송처리를 했으나 배송완료 처리가 안된 주문
   지점주문list - 전체 지점의 주문리스트를 보여줌
   입금확인 - 택배사, 운송장번호를 입력후 배송시작 버튼을 클릭하면 주문상태가 배송중으로 변경됨
   배송처리 - 주문수신에서 배송완료 처리

2.주문관련 코드 관리
2-1 주문상태코드
10:결제대기
20:결제실패
30:결제완료
40:주문취소
50:배송준비중
60:배송중
70:배송완료
80:반품요청
89:반품완료
90:교환완료
99:주문확정

3.결재 상태 코드
10:결제대기
20:결제실패
30:결제완료
40:결제취소

7-1-1-2 기본정보
/mgt/branch/selectPageListBranch.do - 지점관리 (지점리스트,지점상세 지점등록,지점수정,)
/admin/user/selectPageListUserMgt.do - 사용자관리
7-1-1-3 상품관리
/mgt/product/selectPageListProduct.do - 상품관리
/mgt/product/review/selectPageListReview.do - 상품리뷰관리
/mgt/product/qna/selectPageListQna.do - 상품문의관리
7-1-1-4 주문관리
/mgt/order/selectPageListOrder.do - 주문관리
7-1-1-5 게시판
/mgt/mboard/selectPageListBoard.do

7-1-2 ROLE_PROJ
7-1-2-1 메인화면
/mgt/main/dashBoard.do - 본사 대시보드
7-1-2-3 상품관리
/mgt/product/selectPageListProduct.do - 상품관리
/mgt/product/review/selectPageListReview.do - 상품리뷰관리
/mgt/product/qna/selectPageListQna.do - 상품문의관리
7-1-2-4 주문관리
/mgt/order/selectPageListOrder.do - 주문관리

7-1-3 ROLE_PUB
http://amsa.asagong.com/shop/list.do - 상품리스트
http://amsa.asagong.com/shop/detail.do?productId=110 -상품상세

8.추가내용
8-1 menual_sell.html
8-1-1- 지점등록화면
2-2. [신규 등록] 지점 등록 에서
토스 MID, 토스 시크릿키,토스 클라이언트 키는 토스에서 발급받은 키를 입력합니다.
배송비관련은 본사만 입력합니다 각지점은 본사 배송비를 같이 사용합니다.

8-1-2 사용자등록화면
3-2. [신규 등록] 계정 생성
권한이 지점판매인경우 해당 지점코드를 선택
권한이 본사인경우 본사만 선택가능

8-1-3 상품등록화면
4-2. [신규 등록] 상품 등록
상품등록시 에디터 모드는 Summernote 에디터, Raw HTML 직접입력,Plain Text (단순 텍스트) 3가지가 있으며 Raw HTML 직접입력 모드에서 html에서 입력후 Summernote 에디터로 전환후 사용합니다.
Summernote 에디터 에서 이미지 업로드후 수정 가능함니다.
미리보기 클릭시 상세보기화면 노출됩니다.

이미지는 3장까지 업로드 가능합니다.

8-1-4 게시판관리 6. 시스템 설정 => 게시판관리로 문구변경한다

8-2 menual_proj.html
8-2-1 8-1 menual_sell.html 기준으로 작성한댜.
8-2-1 대시보드
8-1 menual_sell.html 기준으로 작성한댜
시스템 상태 코드 안내 추가한다

8-3 menual_pub.html 수정내용
8-3-1 http://www.asagong.com 상단에 지점선택 버튼클릭시 지점 선택 팝업에서 지점을 쇼핑몰로 진입또는 직접 각지점 주소로 쇼핑몰로 진입한다

9.모바일앱용 메뉴얼
9-1.생성폴드  
static/manual/app/menual_app_android.html (설치 전용)
static/manual/app/menual_app_ios.html (설치 전용)
static/manual/app/menual_app_sell.html (본사 가이드)
static/manual/app/menual_app_proj.html (지점 가이드)
static/manual/app/menual_app_pub.html (구매자 가이드)
static/manual/image/app/권한별폴드 이미지

9-2.안드로이드 이미지 캡쳐
/Users/soo/kycarrotsApp 에서 에뮬레이트를 실행해서 화면을 캡쳐한다.
9-2-1 ROLE_SELL 접속
아이디:sel1@gmail.com
패스워드:1234
9-2-1-1 가이드 내용
햄버거메뉴에서 대시보드,주문관리 각리스트에서 상세 화면에서의 버튼 위주로 기술한다.

9-2-2 ROLE_PROJ 접속
아이디:amsa@gmail.com
패스워드:1234
9-2-1-2 가이드 내용
햄버거메뉴에서 대시보드,주문관리 각리스트에서 상세 화면에서의 버튼 위주로 기술한다.

9-2-3.ROLE_PUB 접속
아이디:amsa@gmail.com
패스워드:1234
