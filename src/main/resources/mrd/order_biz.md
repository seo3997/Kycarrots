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
0:상품등록중
1:판매중
20:품절
30:판매중지
99:판매완료

2-4 지점의 본사 입금상태코드
공통코드그룹:R010680
tb_orders.BRANCH_DEPOSIT_STATUS 필드 사용
10:WAITING:입금대기
20:BRREQUESR:입금확인요청
30:DEPOSITED:입금완료
40:CANCEL:입금취소

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

4. push 로직 점검
   4-1 사용자 권한 및 진입 경로 정의
   권한 코드,구분,주요 진입 경로 (Web / App),서버 소스 경로
   ROLE*PUB,구매자,"front/shop, App(사용자용)","/front/shop, /rest/*"
   ROLE*PROJ,지점판매자,"admin/main, mgt/order, App(관리자용)","/mgt/*, /rest/_"
   ROLE_SELL,본사,"admin/main, mgt/order, App(관리자용)","/mgt/_, /rest/\*"

4-2 상태 변경에 따른 Push 알림 로직
상태가 변경될 때, 누가 변경했는지와 누구에게 알림이 가는지가 핵심입니다.

4-2-1. 상품 상태 변경 (본사 권한)
판매중(1)으로 변경 시: 본사(ROLE_SELL)가 변경 → **모두(PUB, PROJ, SELL)**에게 Push 전송.
4-2-2. 주문 및 입금 상태 변경 (프로세스 순서)
프로세스,상태 변경 (코드),실행 주체,Push 수신 대상,비고
주문/결제 완료,배송준비중(50) / 입금대기(10),구매자,"SELL, PROJ",결제 시 입금상태 10으로 초기화
입금확인 요청,입금확인요청(20),지점,SELL,본사에게 입금 확인 부탁
입금완료 처리,입금완료(30) / 배송중(60),본사,"PROJ, PUB",입금 확인 시 배송 시작 알림
배송 완료,배송완료(70),본사,PROJ,지점에 배송 완료 알림
주문 확정,주문확정(99),지점,SELL,정산 대상 확정 알림
취소/반품,주문취소(40) / 반품요청(80),구매자,"SELL, PROJ",운영측에 즉시 알림
교환 완료,교환완료(90),본사,PROJ,지점에 교환 처리 결과 알림

4-3. 권한별 상세 진입 화면 (Web/App 통합)
지점과 본사는 동일한 관리자 인프라(mgt)를 공유하되, 권한에 따라 기능이 분기됩니다.

4-3-1. 구매자 (ROLE_PUB)
Web: views/front/shop/ (detail.jsp, order_list.jsp)
App: AdDetailActivity, OrderActivity, OrderDetailActivity
주요 기능: 주문 생성, 결제, 취소 신청, 반품 신청

4-3-2. 지점판매자 (ROLE_PROJ)
Web: views/mgt/order/ (selectOrder.jsp, selectPageListOrder.jsp)
App: DashboardActivity, OrderMgtActivity
주요 기능: 주문 확인, 입금확인요청(20), 주문확정(99)

4-3-3. 본사 (ROLE_SELL)
Web: views/admin/main.jsp, views/mgt/order/
App: DashboardActivity, OrderMgtActivity

4-4. 로직 점검 요약 (핵심 포인트)
입금 상태의 연동: 구매자가 결제하면 주문상태는 50(배송준비중), 지점-본사 간 입금상태는 10(입금대기)이 됩니다.
본사의 역할: 본사는 실질적인 물류(배송)와 돈의 흐름(입금확인)을 최종 승인하며, 이때 지점과 구매자에게 알림을 줍니다.
지점의 역할: 지점은 자기 채널의 주문을 관리하고, 최종적으로 99(주문확정)를 통해 본사에 정산을 요구하는 흐름입니다.주요 기능: 상품 상태 관리, 입금완료(30) 처리, 배송중(60)/배송완료(70) 처리, 교환완료(90) 처리

5. 상품리뷰 및 상품문의 업무 추가
   5-1. 화면 구성 및 진입로 (Front-End)
   사용자 경험 최적화를 위해 detail.jsp 내에 탭 구조를 적용하며, 앱에서도 동일하게 사용할 수 있도록 구성합니다.

웹 UI 구성: views/front/shop/detail.jsp 하단에 탭 메뉴 구현

<ul class="detail-tabs">
    <li class="active" onclick="moveTab('desc')">상품상세</li>
    <li onclick="moveTab('review')">리뷰 (12)</li>
    <li onclick="moveTab('qna')">상품문의 (5)</li>
</ul>
하이브리드 대응: Front 화면은 App WebView에서도 공통 사용하며, 데이터 통신은 rest/ 폴더 내 API를 참조합니다
5-2. 데이터베이스 및 소스 참조 구조
기존 시스템의 파일 관리 및 게시판 로직을 그대로 계승합니다.
참조 테이블: /mrd/payment_sql.md 내 정의된 tb_product_reviews, tb_product_qna 사용
관리자 로직 (Mgt): mgt/mboard 소스의 게시판 관리 기능을 참조하여 답변 및 노출 제어 구현
사용자 로직 (Front): front/shop 소스를 참조하여 리스트 및 상세 화면 구현
App/API 로직: 모바일 앱 연동을 위해 rest/review, rest/qna 폴더 내 Rest API 신규 추가
5-3. 파일 업로드 및 이미지 처리 (Common Logic)
이미지 첨부 로직의 통일성을 위해 공통 모듈을 사용합니다.

리뷰 이미지 업로드: mboard의 첨부파일 처리 로직(tb_file 연동)을 그대로 참조

핵심 컬럼: ATCH_DOC_ID를 통해 tb_file 테이블과 조인하여 이미지 로드
본사 사입/직배송형 분양몰
사용자설정 Gem
본사 사입/직배송형 분양몰님이 보낸 내용
제시해주신 내용을 바탕으로 5. 상품리뷰 및 상품문의 업무 추가에 대한 MRD(기획 요구사항 정의서)를 최종 정리해 드립니다.

기존 소스(mgt/mboard, front/shop)의 로직을 최대한 재활용하면서, 앱(App) 대응을 위한 Rest API 표준화에 초점을 맞추었습니다.

5. 상품리뷰 및 상품문의 업무 추가
   5-1. 화면 구성 및 진입로 (Front-End)
   사용자 경험 최적화를 위해 detail.jsp 내에 탭 구조를 적용하며, 앱에서도 동일하게 사용할 수 있도록 구성합니다.

웹 UI 구성: views/front/shop/detail.jsp 하단에 탭 메뉴 구현

HTML

<ul class="detail-tabs">
    <li class="active" onclick="moveTab('desc')">상품상세</li>
    <li onclick="moveTab('review')">리뷰 (12)</li>
    <li onclick="moveTab('qna')">상품문의 (5)</li>
</ul>
하이브리드 대응: Front 화면은 App WebView에서도 공통 사용하며, 데이터 통신은 rest/ 폴더 내 API를 참조합니다.

5-2. 데이터베이스 및 소스 참조 구조
기존 시스템의 파일 관리 및 게시판 로직을 그대로 계승합니다.

참조 테이블: /mrd/payment_sql.md 내 정의된 tb_product_reviews, tb_product_qna 사용

관리자 로직 (Mgt): mgt/mboard 소스의 게시판 관리 기능을 참조하여 답변 및 노출 제어 구현

사용자 로직 (Front): front/shop 소스를 참조하여 리스트 및 상세 화면 구현

App/API 로직: 모바일 앱 연동을 위해 rest/review, rest/qna 폴더 내 Rest API 신규 추가

5-3. 파일 업로드 및 이미지 처리 (Common Logic)
이미지 첨부 로직의 통일성을 위해 공통 모듈을 사용합니다.
리뷰 이미지 업로드: mboard의 첨부파일 처리 로직(tb_file 연동)을 그대로 참조
핵심 컬럼: ATCH_DOC_ID를 통해 tb_file 테이블과 조인하여 이미지 로드

5-4. Push 알림 발송 로직
등록 및 답변 시점에 따른 수신 대상 정의입니다.
분류,발생 이벤트,주체 (Actor),수신자 (Receiver),알림 목적
상품문의,문의 등록,구매자(PUB),"지점(PROJ), 본사(SELL)",신규 문의 확인 및 답변 요청
,답변 완료,본사/지점,구매자(PUB),문의 답변 완료 안내
상품리뷰,리뷰 작성,구매자(PUB),"지점(PROJ), 본사(SELL)",평점 모니터링 및 상품 관리

본사 사입/직배송형 분양몰
사용자설정 Gem
본사 사입/직배송형 분양몰님이 보낸 내용
제시해주신 내용을 바탕으로 5. 상품리뷰 및 상품문의 업무 추가에 대한 MRD(기획 요구사항 정의서)를 최종 정리해 드립니다.

기존 소스(mgt/mboard, front/shop)의 로직을 최대한 재활용하면서, 앱(App) 대응을 위한 Rest API 표준화에 초점을 맞추었습니다.

5. 상품리뷰 및 상품문의 업무 추가
   5-1. 화면 구성 및 진입로 (Front-End)
   사용자 경험 최적화를 위해 detail.jsp 내에 탭 구조를 적용하며, 앱에서도 동일하게 사용할 수 있도록 구성합니다.

웹 UI 구성: views/front/shop/detail.jsp 하단에 탭 메뉴 구현

HTML

<ul class="detail-tabs">
    <li class="active" onclick="moveTab('desc')">상품상세</li>
    <li onclick="moveTab('review')">리뷰 (12)</li>
    <li onclick="moveTab('qna')">상품문의 (5)</li>
</ul>
하이브리드 대응: Front 화면은 App WebView에서도 공통 사용하며, 데이터 통신은 rest/ 폴더 내 API를 참조합니다.

5-2. 데이터베이스 및 소스 참조 구조
기존 시스템의 파일 관리 및 게시판 로직을 그대로 계승합니다.

참조 테이블: /mrd/payment_sql.md 내 정의된 tb_product_reviews, tb_product_qna 사용

관리자 로직 (Mgt): mgt/mboard 소스의 게시판 관리 기능을 참조하여 답변 및 노출 제어 구현

사용자 로직 (Front): front/shop 소스를 참조하여 리스트 및 상세 화면 구현

App/API 로직: 모바일 앱 연동을 위해 rest/review, rest/qna 폴더 내 Rest API 신규 추가

5-3. 파일 업로드 및 이미지 처리 (Common Logic)
이미지 첨부 로직의 통일성을 위해 공통 모듈을 사용합니다.

리뷰 이미지 업로드: product의 첨부파일 처리 로직(tb_file 연동)을 그대로 참조

핵심 컬럼: ATCH_DOC_ID를 통해 tb_file 테이블과 조인하여 이미지 로드
업로드폴드는 product의 하윅 review 폴더를 사용한다.
product:
upload-dir: /Users/soo/uploads/product/review
public-url: http://127.0.0.1:9000/common/img/product/review
resource-path: file:///Users/soo/uploads/product/review/

5-4. Push 알림 발송 로직
등록 및 답변 시점에 따른 수신 대상 정의입니다.

분류 발생 이벤트 주체 (Actor) 수신자 (Receiver) 알림 목적
상품문의 문의 등록 구매자(PUB) 지점(PROJ), 본사(SELL) 신규 문의 확인 및 답변 요청
답변 완료 본사/지점 구매자(PUB) 문의 답변 완료 안내
상품리뷰 리뷰 작성 구매자(PUB) 지점(PROJ), 본사(SELL) 평점 모니터링 및 상품 관리
5-5. 개발 핵심 점검 사항 (Key Points)
소스 재사용: 신규 개발보다는 mboard의 파일 업로드와 front/shop의 리스트 로직을 API화(@RestController) 하는 데 집중할 것.

App 연동성: 웹뷰(WebView) 내에서 리뷰 작성 시, 앱의 로그인 세션(USER_NO)과 권한이 Rest API와 정상적으로 연동되는지 점검.
Push 공통화: 웹(JSP)에서 답변을 달거나 앱(API)에서 답변을 달거나 동일한 Push 서비스 클래스를 호출하여 누락이 없도록 할 것.

6. 안드로이드 앱 상품리뷰 및 상품문의 업무 추가
   5번 상품리뷰 및 상품문의 업무 추가의 front/shop/detail.jsp의 상품리뷰 상품문의 부분을 안드로이드 앱에서 네이티브로 구현한다.
   안드로이드 앱경로는 /Users/soo/kycarrotsApp 있음
   안드로이드 상품상세는 AdDetailActivity.kr 임
   상품리뷰,상품문의 추가 화면은 com.whomade.kycarrots.ui.ad.adreview, com.whomade.kycarrots.ui.ad.adqna 임

상품리뷰리 이미지 사진촬영및 갤러리이미지 업로드 기능은 안드로이드 앱에서 네이티브로 구현하는데 이미 상품들록이 이미지 업로드기능이 있어 이걸(com.whomade.kycarrots.ui.ad.admake.KtMakeADImgRegiView) 참조 한다. 이미지 선택,촬영후 미리보기, 삭제 기능 포함한다.
이미지관련 파일은 res/xml/provider_paths.xml 이다.

7.안드로이드 앱 상품리뷰 및 상품문의 등록시 push 전송 로직점검
1.상품구매자가 상푸리뷰나 상품문의를 하는데 자기한테 push를 보내면 안됨
2.본사,지점은 push를 받아야함
3.push 전송시 
ProductQnaServiceImpl.java
 insertQna에서 
 insert into tb_push_log 에서 Data too long for column 'TARGET_VALUE' 오류나고 있음 