1.  지점 및 지점 판매자 등록 프로세스 추가 기술 스택 및 아키텍처
    지점등록은 branch폴더를 생성하여 관리한다.
    각소스는 product를 참조한다.
    1-1 상품대시보드 관리자  
     1-1-1 com.whomade.kycarrots.mgt.branch
    web - com.whomade.kycarrots.mgt.branch.controller
    service - com.whomade.kycarrots.mgt.branch.service
    dto - com.whomade.kycarrots.mgt.branch.dto
    1-1-2 mapper xml
    resources/mapper/mysql/mgt/branxh/BranchMapper.xml
    1-1-3 jsp
    /WEB-INF/views/mgt/branch

    브래치관리프로그램에서 지점리스트는 /mgt/branch/selectPageListBranch.do로 시작한다.

    1-2 Rest API 앱에서 사용 (branch)
    1-2-1 rest,service, repository 폴더사용 다음과 같은 패턴을 이용한다.
    각폴더별 product를 참조한다.
    Controller -> service-> Repository -> Mapper -> Mapper.xml -> DB

            1-2-1-1 rest
            com.whomade.kycarrots.rest.branch.BranchController
            1-2-1-2 service
            com.whomade.kycarrots.service.branch.BranchService

            1-2-1-3 repository
            com.whomade.kycarrots.repository.mybatis.branch.BranchRepository
            com.whomade.kycarrots.repository.mybatis.branch.BranchMapper

            1-2-1-4 mapper xml
            resources/mapper/mysql/rest/BranchMapper.xml

    1-3 front
    1-3-1 front/shop
    front/shop/list: 상품 목록 (전 지점 공통 상품 로드)
    front/shop/detail: 상품 상세 및 주문하기 버튼
    front/shop/checkout: 결제 페이지 (지점별 TOSS_CLIENT_KEY 로드 및 결제창 호출)
    front/shop/order-list: 내 주문 내역 (로그인한 사용자의 BRANCH_ID 기준 조회)
    1-3-2 로그인/회원가입 수정
    회원가입 (front/member)
    로그인 (front/login)

2.  table은 resources/mrd/branch_sql.md 를 참조한다.
    DB연동이 필요한 소스는 product를 참조한다.
