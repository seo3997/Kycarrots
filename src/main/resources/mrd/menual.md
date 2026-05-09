1.권한별 온라인 메뉴얼 생성
1.1 op\*author 의 ROLE_CODE 권한코드 정의 아래 3개의 권한 메뉴얼을 html로 작성 1.본사: ROLE_SELL: 입금 확인, 배송 처리, 물류를 담당하는 본사 운영 관리자 2.판매지점: ROLE_PROJ: 독립 결제창을 운영하며 본사에 원가를 송금하는 지점 판매자 3.구매자: ROLE_PUB: 지점 앱을 통해 물건을 구매하는 일반 사용자

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
/mgt/main/dashBoard.do - 본사 대시보드
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
