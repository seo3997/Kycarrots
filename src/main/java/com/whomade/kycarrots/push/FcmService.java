package com.whomade.kycarrots.push;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmService {
    private final PushLogRepository pushLogRepository;
    public void sendPush(String targetToken, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(targetToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putData("roomId", "123")         // 원하는 데이터 추가
                    .putData("type", "chat")
                    .putData("msg", body)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 전송 성공: {}", response);
        } catch (Exception e) {
            log.error("FCM 전송 실패", e);
        }
    }

    public void sendPushToAndroid(
            String targetToken,
            Map<String, String> data // ← 추가!
    ) {
        try {

            Message.Builder messageBuilder = Message.builder()
                    .setToken(targetToken)
                    .setAndroidConfig(AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH) // 중요: 백그라운드에서도 수신
                    .build());
            /*
            Message.Builder messageBuilder = Message.builder()
                    .setToken(targetToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build());
            */

            // 데이터 payload 모두 추가
            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }
            log.info("data: {}", data);

            Message message = messageBuilder.build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 전송 성공: {}", response);
        } catch (Exception e) {
            log.error("FCM 전송 실패", e);
        }
    }

    public void sendPushToIos(
            String targetToken,
            String title,
            String body,
            Map<String, String> data
    ) {
        try {
            if (data != null) {
                data.putIfAbsent("id", java.util.UUID.randomUUID().toString());
                data.putIfAbsent("title", title);
                data.putIfAbsent("body", body);
            }

            ApnsConfig apnsConfig = ApnsConfig.builder()
                    .putHeader("apns-push-type", "alert")
                    .putHeader("apns-priority", "10")
                    .setAps(Aps.builder()
                            .setAlert(ApsAlert.builder()
                                    .setTitle(title)
                                    .setBody(body)
                                    .build())
                            .setSound("default")
                            .build())
                    .build();

            Message.Builder builder = Message.builder()
                    .setToken(targetToken)    // ✅ iOS FCM token
                    .setApnsConfig(apnsConfig);

            if (data != null && !data.isEmpty()) {
                builder.putAllData(data);
            }

            String response = FirebaseMessaging.getInstance().send(builder.build());
            log.info("iOS FCM 전송 성공: {}", response);
            log.info("iOS FCM data: {}", data);

        } catch (Exception e) {
            log.error("iOS FCM 전송 실패", e);
        }
    }


    // ✅ 1. Topic 푸시 (ex. /topics/ROLE_PUB)
    public void sendPushToTopic(String topic, String title, String body, Map<String, String> data) {
        String sendStatus = "실패";
        String androidMsgId = null;
        String iosMsgId = null;

        try {
            // 1) Android로 topic 전송
            androidMsgId = sendPushToTopicAndroid(topic, title, body, data);

            // 2) iOS로 topic 전송
            iosMsgId = sendPushToTopicIos(topic, title, body, data);

            sendStatus = "성공";
            log.info("Topic 푸시 성공 topic={}, androidMsgId={}, iosMsgId={}", topic, androidMsgId, iosMsgId);

        } catch (Exception e) {
            log.error("Topic 푸시 실패 topic={}", topic, e);

        } finally {
            // ✅ 로그는 1번만 (중복 방지)
            pushLogRepository.save(PushLog.builder()
                    .TARGET_USER_ID("/topics/" + topic)
                    .PRODUCT_ID(data != null ? data.getOrDefault("productId", null) : null)
                    .MESSAGE_TITLE(title)
                    .MESSAGE_BODY(body)
                    .PUSH_TYPE("상품등록알림")
                    .SEND_STATUS(sendStatus)
                    .READ_YN("N")
                    .build());
        }
    }
    private String sendPushToTopicAndroid(String topic, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        Message.Builder b = Message.builder()
                .setTopic(topic)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build());

        if (data != null && !data.isEmpty()) b.putAllData(data);

        return FirebaseMessaging.getInstance().send(b.build());
    }
    private String sendPushToTopicIos(String topic, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        ApnsConfig apns = ApnsConfig.builder()
                .putHeader("apns-priority", "10")
                .setAps(Aps.builder()
                        .setAlert(ApsAlert.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
                        .setSound("default")
                        .build())
                .build();

        Message.Builder b = Message.builder()
                .setTopic(topic)
                .setApnsConfig(apns);

        // iOS도 data payload 받을 수 있음
        if (data != null && !data.isEmpty()) b.putAllData(data);

        // 참고: iOS도 알림 표시를 위해 notification을 같이 넣어도 되지만,
        // apns.alert에 이미 들어가 있으니 중복 없이 이대로도 충분함.
        return FirebaseMessaging.getInstance().send(b.build());
    }

    // ✅ 2. Token 푸시 + 로그 저장 (ex. 승인요청)
    public void sendPushToUserAndLog(
            String deviceType, // "ANDROID" / "IOS"
            String userId,
            String token,
            String productId,
            String pushType,
            String title,
            String body,
            Map<String, String> data
    ) {
        String sendStatus = "실패";
        String responseId = null;

        try {
            // 1) 플랫폼별 전송만 분기
            if ("ANDROID".equalsIgnoreCase(deviceType)) {
                responseId = sendToAndroidToken(token, title, body, data);
            } else if ("IOS".equalsIgnoreCase(deviceType)) {
                responseId = sendToIosToken(token, title, body, data);
            } else {
                throw new IllegalArgumentException("Unknown deviceType=" + deviceType);
            }

            sendStatus = "성공";
            log.info("Push OK deviceType={}, userId={}, resp={}", deviceType, userId, responseId);

        } catch (Exception e) {
            log.error("Push FAIL deviceType={}, userId={}, err={}", deviceType, userId, e.getMessage(), e);

        } finally {
            // 2) 로그 저장은 공통 (성공/실패 모두 기록)
            savePushLog(userId, productId, pushType, title, body, sendStatus, data);
        }
    }
    private void savePushLog(
            String userId,
            String productId,
            String pushType,
            String title,
            String body,
            String sendStatus,
            Map<String, String> data
    ) {
        try {
            // 예시: deepLink 같은 값을 data에서 뽑아 저장하고 싶으면 여기서 처리
            String deepLink = (data != null) ? data.get("deepLink") : null;

            pushLogRepository.save(PushLog.builder()
                    .TARGET_USER_ID(userId)
                    .PRODUCT_ID(productId)
                    .PUSH_TYPE(pushType)
                    .MESSAGE_TITLE(title)
                    .MESSAGE_BODY(body)
                    .SEND_STATUS(sendStatus)
                    .READ_YN("N")
                    .build());

        } catch (Exception e) {
            // 로그 저장 실패는 서비스 장애로 번지지 않게 별도로만 찍고 끝내는게 보통 안전
            log.error("PushLog SAVE FAIL userId={}, err={}", userId, e.getMessage(), e);
        }
    }
    private String sendToAndroidToken(String token, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        Message.Builder b = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build());

        if (data != null && !data.isEmpty()) b.putAllData(data);
        return FirebaseMessaging.getInstance().send(b.build());
    }

    private String sendToIosToken(String token, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        ApnsConfig apns = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
                        .setSound("default")
                        .build())
                .build();

        Message.Builder b = Message.builder()
                .setToken(token)
                .setApnsConfig(apns);

        if (data != null && !data.isEmpty()) b.putAllData(data);
        return FirebaseMessaging.getInstance().send(b.build());
    }

}
