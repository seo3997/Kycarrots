1. 상품 등록 항목조정
   mrd/payment_sql.md tb_product 참조
   상품들록 폴더는 mgt/product 참조
   1-1 항목변경
   1. 긴급사유 -> 상품상세
   2. 상품출하일 화면에서 삭제

1-2 EDITOR_MODE 컬럼 추가

1.  컬럼추가
    ALTER TABLE tb_product
    ADD COLUMN EDITOR_MODE TINYINT(1) NOT NULL DEFAULT 1
    COMMENT '상세설명 타입 (1: Summernote 에디터, 2: 텍스트 전용)';
2.  화면에서 EDITOR_MODE 선택시 Summernote 에디터, 텍스트 전용
3.  Summernote 에디터 사용시
    1. 이미지 업로드시 tb_product_images 테이블에 저장
    2. 이미지 삭제시 tb_product_images 테이블에서 삭제
    3. Summernote 에디터 사용시 이미지 업로드시 서버에 저장되는 경로는 지금 이미지 첨부와 동일한 경로룰 사용한다.
    4. 상품이미지 업로드 mgt/product를 참조한다.
       ProducterviceImpl.java의 insertProduct 메소드 참조
