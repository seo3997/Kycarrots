상품 등록 페이지 및 HTML 에디터 적용 요구사항

1. 상품 등록 항목 및 화면 조정
   참조 파일: \* DB: mrd/payment_sql.md 내 tb_product 테이블
   화면/로직: mgt/product 폴더 내 상품 등록 소스

항목 변경 사항:
긴급사유 필드 명칭을 **[상품상세]**로 변경 및 용도 전환.
상품출하일 필드는 사용자 화면 및 등록 프로세스에서 삭제.

2. DB 스키마 변경
   대상 테이블: tb_product
   추가 컬럼: 테이블에 이미 반영되어 있음
   ALTER TABLE tb_product
   ADD COLUMN EDITOR_MODE TINYINT(1) NOT NULL DEFAULT 1
   COMMENT '상세설명 타입 (1: Summernote 에디터 2: Raw HTML 직접 입력 3: 텍스트 전용)';

3. 기능 요구사항 (Editor Logic)
   모드 선택: 화면에서 EDITOR_MODE 선택에 따라 입력창 전환 (Summernote ↔ 일반 Textarea).
   이미지 관리 (Summernote 사용 시):
   저장: 에디터에 이미지 삽입 시 tb_product_images 테이블에 레코드 생성.
   삭제: 에디터 내에서 이미지 삭제 시 tb_product_images에서 해당 레코드 삭제.
   경로: 현재 상품 이미지 첨부와 동일한 서버 경로 사용.
   로직 참조: \* ProductServiceImpl.java의 insertProduct 메소드 내 이미지 처리 로직을 그대로 계승하여 구현.
