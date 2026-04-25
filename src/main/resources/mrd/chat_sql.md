"branchId": "string",

1. tb_chat_room 변경
   SELLER_ID 에서 BRANCH_ID로 변경
   CREATE TABLE `tb_chat_room` (
   `ID` bigint NOT NULL AUTO_INCREMENT,
   `PRODUCT_ID` bigint NOT NULL,
   `BUYER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
   `BRANCH_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
   `ROOM_ID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
   PRIMARY KEY (`ID`),
   UNIQUE KEY `UK_ROOM_ID` (`ROOM_ID`)
   ) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

2.tb_chat_message
CREATE TABLE `tb_chat_message` (
`ID` bigint NOT NULL AUTO_INCREMENT,
`ROOM_ID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
`SENDER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
`SENDER_GROUP` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`RECEIVE_GROUP` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`MESSAGE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
`CREATED_AT` datetime DEFAULT CURRENT_TIMESTAMP,
`IS_READ` tinyint(1) DEFAULT '0',
PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

3.tb_push_log
CREATE TABLE `tb_push_log` (
`ID` bigint NOT NULL AUTO_INCREMENT COMMENT 'PK',
`ACTOR_USER_NO` bigint DEFAULT NULL COMMENT '행동자 userNo (승인/반려/상태변경 수행자)',
`TARGET_TYPE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '수신 대상 타입: USER | TOPIC',
`TARGET_VALUE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '수신 대상 값: USER=userNo, TOPIC=topic명',
`PRODUCT_ID` bigint DEFAULT NULL COMMENT '관련 상품 ID',
`EVENT_TYPE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '이벤트 유형: 승인완료 | 반려 | 재승인요청 | 판매완료 등',
`MESSAGE_TITLE` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '푸시 알림 제목',
`MESSAGE_BODY` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '푸시 알림 내용',
`PUSH_TYPE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '푸시 전송 타입: ANDROID | IOS | ALL',
`SEND_STATUS` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'SUCCESS' COMMENT '전송 결과: SUCCESS | FAIL',
`READ_YN` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'N' COMMENT '수신자 읽음 여부',
`SENT_AT` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '푸시 전송 시각',
PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
