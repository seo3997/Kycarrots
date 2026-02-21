1.  주문 시스템 기술 스택 및 아키텍처
    주문시스템은 각 폴드에 order폴더를 생성하여 관리한다.
    각소스는 product를 참조한다.
    1-1 관리자용 주문관리  
     1-1-1 com.whomade.kycarrots.mgt.order
    web - com.whomade.kycarrots.mgt.order.controller
    service - com.whomade.kycarrots.mgt.order.service
    dto - com.whomade.kycarrots.mgt.order.dto
    1-1-2 mapper xml
    resources/mapper/mysql/mgt/order/OrderMapper.xml
    1-1-3 jsp
    /WEB-INF/views/mgt/order

    1-2 Rest API 앱에서 사용
    1-2-1 rest,service, repository 폴더사용 다음과 같은 패턴을 이용한다.
    각폴더별 product를 참조한다.
    Controller -> service-> Repository -> Mapper -> Mapper.xml -> DB

            1-2-1-1 rest
            com.whomade.kycarrots.rest.order.OrderController
            1-2-1-2 service
            com.whomade.kycarrots.service.order.OrderService

            1-2-1-3 repository
            com.whomade.kycarrots.repository.mybatis.order.OrderRepository
            com.whomade.kycarrots.repository.mybatis.order.OrderMapper

            1-2-1-4 mapper xml
            resources/mapper/mysql/rest/OrderMapper.xml

2.  table은 resources/mrd/payment_sql.md 를 참조한다.
3.  DB연동이 필요한 소스는 product를 참조한다.
