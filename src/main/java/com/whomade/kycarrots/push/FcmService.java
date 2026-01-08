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

    /*
    public void sendSilentPushToIos(String targetToken, Map<String, String> data) {
        try {
            ApnsConfig apnsConfig = ApnsConfig.builder()
                    .putHeader("apns-push-type", "background")
                    .putHeader("apns-priority", "5")
                    .setAps(Aps.builder()
                            .setContentAvailable(true) // ✅ silent 핵심
                            .build())
                    .build();

            Message.Builder builder = Message.builder()
                    .setToken(targetToken)
                    .setApnsConfig(apnsConfig);

            if (data != null && !data.isEmpty()) builder.putAllData(data);

            String response = FirebaseMessaging.getInstance().send(builder.build());
            log.info("iOS Silent FCM 전송 성공: {}", response);
        } catch (Exception e) {
            log.error("iOS Silent FCM 전송 실패", e);
        }
    }

     */

    // ✅ 1. Topic 푸시 (ex. /topics/ROLE_PUB)
    public void sendPushToTopic(String topic, String title, String body, Map<String, String> data) {
        try {
            Message.Builder messageBuilder = Message.builder()
                    .setTopic(topic)
                    .setNotification(Notification.builder()
                    .build());

            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            Message message = messageBuilder.build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Topic 푸시 성공 ({}): {}", topic, response);

            // 로그 저장
            pushLogRepository.save(PushLog.builder()
                    .TARGET_USER_ID("/topics/" + topic)
                    .PRODUCT_ID(data != null ? data.getOrDefault("productId", null) : null)
                    .MESSAGE_TITLE(title)
                    .MESSAGE_BODY(body)
                    .PUSH_TYPE("상품등록알림")
                    .SEND_STATUS("성공")
                    .READ_YN("N")
                    .build()
            );
            log.warn("로그저장");
        } catch (Exception e) {
            log.error("Topic 푸시 실패", e);
        }
    }

    // ✅ 2. Token 푸시 + 로그 저장 (ex. 승인요청)
    public void sendPushToUserAndLog(String userId, String targetToken, String title, String body,
                                     String productId, String pushType, Map<String, String> data) {
        try {
            Message.Builder messageBuilder = Message.builder()
                    .setToken(targetToken)
                    .setNotification(Notification.builder()
                    .build());

            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            Message message = messageBuilder.build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Token 푸시 성공 ({}): {}", userId, response);

            // 로그 저장
            pushLogRepository.save(PushLog.builder()
                    .TARGET_USER_ID(userId)
                    .PRODUCT_ID(productId)
                    .MESSAGE_TITLE(title)
                    .MESSAGE_BODY(body)
                    .PUSH_TYPE(pushType)
                    .SEND_STATUS("성공")
                    .READ_YN("N")
                    .build()
            );

        } catch (Exception e) {
            log.error("Token 푸시 실패", e);

            // 실패 로그 저장
            pushLogRepository.save(PushLog.builder()
                    .TARGET_USER_ID(userId)
                    .PRODUCT_ID(productId)
                    .MESSAGE_TITLE(title)
                    .MESSAGE_BODY(body)
                    .PUSH_TYPE(pushType)
                    .SEND_STATUS("실패")
                    .READ_YN("N")
                    .build()
            );
        }
    }

}
