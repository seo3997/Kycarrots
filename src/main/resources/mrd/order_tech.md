1.  결제 시스템 기술 스택 및 아키텍처
    결제시스템은 각 폴드에 payment폴더를 생성하여 관리한다.
    각소스는 product를 참조한다.
    1-1 상품대시보드 관리자  
     1-1-1 com.whomade.kycarrots.mgt.payment
    web - com.whomade.kycarrots.mgt.payment.controller
    service - com.whomade.kycarrots.mgt.payment.service
    dto - com.whomade.kycarrots.mgt.payment.dto
    1-1-2 mapper xml
    resources/mapper/mysql/mgt/payment/PaymentMapper.xml
    1-1-3 jsp
    /WEB-INF/views/mgt/payment

    1-2 Rest API 앱에서 사용
    1-2-1 rest,service, repository 폴더사용 다음과 같은 패턴을 이용한다.
    각폴더별 product를 참조한다.
    Controller -> service-> Repository -> Mapper -> Mapper.xml -> DB

            1-2-1-1 rest
            com.whomade.kycarrots.rest.payment.PaymentController
            1-2-1-2 service
            com.whomade.kycarrots.service.payment.PaymentService

            1-2-1-3 repository
            com.whomade.kycarrots.repository.mybatis.payment.PaymentRepository
            com.whomade.kycarrots.repository.mybatis.payment.PaymentMapper

            1-2-1-4 mapper xml
            resources/mapper/mysql/rest/PaymentMapper.xml

2.  table은 resources/mrd/payment_sql.md 를 참조한다.
3.  결제 시스템은 토스페이먼츠(Toss Payments)를 사용한다.
4.  DB연동이 필요한 소스는 product를 참조한다.
