1.[Task] 앱 버전 체크 관리자 및 REST API 개발
1.1 데이터베이스 설계
tb_app_version 테이블을 생성하고 초기 데이터를 구축한다. 테이블과 데이타는 이미 있음
DROP TABLE IF EXISTS `tb_app_version`;
CREATE TABLE `tb_app_version` (
    `APP_VER_NO`     int          NOT NULL AUTO_INCREMENT COMMENT '버전 일련번호(PK)',
    `OS_TYPE`        varchar(10)  NOT NULL COMMENT '플랫폼 (ANDROID / IOS)',
    `LATEST_VERSION` varchar(20)  NOT NULL COMMENT '최신 버전 (ex: 1.1.0)',
    `MIN_VERSION`    varchar(20)  NOT NULL COMMENT '최소 지원 버전 (이하 버전은 강제 업데이트)',
    `UPDATE_MSG`     text                  COMMENT '업데이트 공지 메시지',
    `STORE_URL`      varchar(255)          COMMENT '스토어 연결 URL',
    `USE_YN`         char(1)      DEFAULT 'Y' COMMENT '사용 여부 (Y/N)',
    `REGUSR_NO`      varchar(50)  DEFAULT NULL COMMENT '등록자 번호',
    `REGIST_DT`      timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    `UPDFUSR_NO`     varchar(50)  DEFAULT NULL COMMENT '수정자 번호',
    `UPDT_DT`        timestamp    NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (`APP_VER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='앱 버전 관리 테이블';
1.2 샘플 데이터
1.2.1 안드로이드 초기 데이터
INSERT INTO tb_app_version (OS_TYPE, LATEST_VERSION, MIN_VERSION, UPDATE_MSG, STORE_URL, USE_YN)
VALUES (
'ANDROID',
'1.0.0',
'1.0.0',
'새로운 버전이 출시되었습니다. 최신 버전으로 업데이트 해주세요.',
'https://play.google.com/store/apps/details?id=com.whomade.kycarrots',
'Y'
);

1.2.2 iOS 초기 데이터
INSERT INTO tb_app_version (OS_TYPE, LATEST_VERSION, MIN_VERSION, UPDATE_MSG, STORE_URL, USE_YN)
VALUES (
'IOS',
'1.0.0',
'1.0.0',
'새로운 버전이 출시되었습니다. 최신 버전으로 업데이트 해주세요.',
'https://apps.apple.com/kr/app/kycarrots/id123456789',
'Y'
);

1.3 백엔드 개발 요구사항
관리자 페이지 (Back-office):
참조: com.whomade.kycarrots.mgt.branch.controller
대상: com.whomade.kycarrots.mgt.appversion 패키지 생성 후 CRUD 구현
REST API (Mobile Interface):
참조: com.whomade.kycarrots.rest.branch.RestBranchController
대상: com.whomade.kycarrots.rest.appversion 패키지 생성
기능: 앱에서 현재 버전과 OS를 보내면 최신 버전 및 업데이트 필요 여부를 반환

1.4 클라이언트 수정 사항
Android: /Users/soo/kycarrotsApp/.../FrFindEmail.kt의 API 호출 로직을 참조하여 앱 실행 시 버전 체크 API를 호출하도록 구현

iOS: /Users/soo/kyarrotsIos/.../FindIdViewController.swift의 API 호출 로직을 참조하여 동일 기능 구현

1.5 클라이언트 호출 시점
Android:IntroActivity.kt
iOs:IntroViewController.swift
기존 로그인 로직은 이미 안정적으로 운영되고 있는 핵심 기능이므로, 인트로(스플래시) 단계에서 추가되는 버전 체크 기능이 로그인 프로세스에 침범하거나 방해하지 않아야 한다.
