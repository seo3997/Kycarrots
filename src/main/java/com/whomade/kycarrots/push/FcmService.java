package com.whomade.kycarrots.push;

import com.google.firebase.messaging.*;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.service.member.OpUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class FcmService {

    private final PushLogRepository pushLogRepository;
    private final OpUserService opUserService;

    public FcmService(PushLogRepository pushLogRepository, @Lazy OpUserService opUserService) {
        this.pushLogRepository = pushLogRepository;
        this.opUserService = opUserService;
    }

    // =========================================================
    // ✅ Topic 푸시 (기존 시그니처 유지: actorUserNo 없이 호출 가능)
    // =========================================================
    public void sendPushToTopic(String topic, String title, String body, Map<String, String> data) {
        // actorUserNo 모르니 null로 기록 (기존 호출부 깨지지 않게)
        sendPushToTopicAndLog(null, topic, title, body, data, "TOPIC_PUSH");
    }

    public void sendPushToConditionAndLog(Long actorUserNo,
            String condition,
            String title,
            String body,
            Map<String, String> data,
            String eventType) {

        String sendStatus = "FAIL";
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setNotification(AndroidNotification.builder()
                            .setSound("default")
                            .build())
                    .build();

            ApnsConfig apnsConfig = ApnsConfig.builder()
                    .putHeader("apns-priority", "10")
                    .setAps(Aps.builder()
                            .setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
                            .setSound("default")
                            .build())
                    .build();

            Message.Builder builder = Message.builder()
                    .setCondition(condition)
                    .setAndroidConfig(androidConfig)
                    .setApnsConfig(apnsConfig);

            if (data != null && !data.isEmpty()) {
                builder.putAllData(data);
            }
            
            // ✅ 알림 페이로드 대신 데이터 페이로드만 사용하여 백그라운드에서도 onMessageReceived가 실행되도록 함
            if (title != null) builder.putData("title", title);
            if (body != null) builder.putData("body", body);

            FirebaseMessaging.getInstance().send(builder.build());

            sendStatus = "SUCCESS";
            log.info("Condition push OK condition={}, eventType={}", condition, eventType);

        } catch (Exception e) {
            log.error("Condition push FAIL condition={}, eventType={}", condition, eventType, e);

        } finally {
            String targetId = data != null ? data.get("targetId") : (data != null ? data.get("productId") : null);

            savePushLog(
                    actorUserNo,
                    "CONDITION",
                    condition,
                    targetId,
                    eventType,
                    "ALL",
                    title,
                    body,
                    sendStatus);
        }
    }

    // ✅ 새 버전: actorUserNo + eventType까지 기록
    public void sendPushToTopicAndLog(Long actorUserNo,
            String topic,
            String title,
            String body,
            Map<String, String> data,
            String eventType) {

        String sendStatus = "FAIL";
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setNotification(AndroidNotification.builder()
                            .setSound("default")
                            .build())
                    .build();

            ApnsConfig apnsConfig = ApnsConfig.builder()
                    .putHeader("apns-priority", "10")
                    .setAps(Aps.builder()
                            .setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
                            .setSound("default")
                            .build())
                    .build();

            Message.Builder builder = Message.builder()
                    .setTopic(topic)
                    .setAndroidConfig(androidConfig)
                    .setApnsConfig(apnsConfig);

            if (data != null && !data.isEmpty()) {
                builder.putAllData(data);
            }
            
            // ✅ 알림 페이로드 대신 데이터 페이로드만 사용하여 백그라운드에서도 onMessageReceived가 실행되도록 함
            if (title != null) builder.putData("title", title);
            if (body != null) builder.putData("body", body);

            FirebaseMessaging.getInstance().send(builder.build());

            sendStatus = "SUCCESS";
            log.info("Topic push OK topic={}, eventType={}", topic, eventType);

        } catch (Exception e) {
            log.error("Topic push FAIL topic={}, eventType={}", topic, eventType, e);

        } finally {
            String targetId = data != null ? data.get("targetId") : (data != null ? data.get("productId") : null);

            // ✅ 새 테이블 기준 저장
            savePushLog(
                    actorUserNo,
                    "TOPIC",
                    topic, // ❗ /topics/ 붙이지 않음
                    targetId,
                    eventType,
                    "ALL", // PUSH_TYPE (topic은 플랫폼 2번 전송이라 ALL로)
                    title,
                    body,
                    sendStatus);
        }
    }

    // ✅ 새 버전: actorUserNo 기록 가능
    public void sendPushToUserAndLog(
            Long actorUserNo,
            String deviceType, // ANDROID / IOS
            String targetUserNo, // ✅ 수신자 userNo (문자열)
            String token,
            String title,
            String body,
            String targetId,
            String eventType, // ✅ 반려 / 재승인요청 / 판매완료 ...
            Map<String, String> data) {
        String sendStatus = "FAIL";

        log.info("Attempting single push: targetUserNo={}, device={}, token={}", targetUserNo, deviceType, token);

        try {
            if ("ANDROID".equalsIgnoreCase(deviceType)) {
                sendToAndroidToken(token, title, body, data);
            } else if ("IOS".equalsIgnoreCase(deviceType)) {
                sendToIosToken(token, title, body, data);
            } else {
                log.warn("Unknown device type for push: {}", deviceType);
                throw new IllegalArgumentException("Unknown deviceType=" + deviceType);
            }

            sendStatus = "SUCCESS";
            log.info("Push send SUCCESS: targetUserNo={}, eventType={}", targetUserNo, eventType);

        } catch (FirebaseMessagingException e) {
            log.error("FCM FirebaseMessagingException: [Code: {}] [Message: {}] [User: {}] [Event: {}]",
                    e.getMessagingErrorCode(), e.getMessage(), targetUserNo, eventType);

            // "Requested entity was not found" or "UNREGISTERED" error means the token is
            // no longer valid.
            if (MessagingErrorCode.UNREGISTERED.equals(e.getMessagingErrorCode()) ||
                    "Requested entity was not found.".equals(e.getMessage())) {
                try {
                    OpUserVO u = new OpUserVO();
                    u.setUserNo(targetUserNo);
                    u.setPushToken(null);
                    opUserService.updatePushToken(u);
                    log.info("Cleared invalid FCM token in DB for userNo={}", targetUserNo);
                } catch (Exception ex) {
                    log.error("Failed to clear invalid token in DB for userNo={}", targetUserNo, ex);
                }
            }
        } catch (Exception e) {
            log.error("Generic push Exception for targetUserNo={}, eventType={}", targetUserNo, eventType, e);
        } finally {
            String tid = targetId;
            savePushLog(
                    actorUserNo,
                    "USER",
                    targetUserNo,
                    tid,
                    eventType,
                    deviceType.toUpperCase(), // PUSH_TYPE에 플랫폼 저장(ANDROID/IOS)
                    title,
                    body,
                    sendStatus);
        }
    }

    public void sendPushToUser(
            String deviceType, // ANDROID / IOS
            String token,
            String title,
            String body,
            Map<String, String> data) {
        String sendStatus = "FAIL";
        try {
            if ("ANDROID".equalsIgnoreCase(deviceType)) {
                sendToAndroidToken(token, title, body, data);
            } else if ("IOS".equalsIgnoreCase(deviceType)) {
                sendToIosToken(token, title, body, data);
            } else {
                throw new IllegalArgumentException("Unknown deviceType=" + deviceType);
            }
            sendStatus = "SUCCESS";
            log.info("User push OK deviceType={}, targetUserNo={}, eventType={}", deviceType);

        } catch (Exception e) {
            log.error("User push FAIL deviceType={}, targetUserNo={}, eventType={}", deviceType, e);

        }
    }

    private String sendToAndroidToken(String token, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        Message.Builder b = Message.builder()
                .setToken(token)
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build());

        if (data != null && !data.isEmpty())
            b.putAllData(data);

        // ✅ 알림 페이로드 대신 데이터 페이로드만 사용하여 백그라운드에서도 onMessageReceived가 실행되도록 함
        if (title != null) b.putData("title", title);
        if (body != null) b.putData("body", body);

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

        if (data != null && !data.isEmpty())
            b.putAllData(data);
        return FirebaseMessaging.getInstance().send(b.build());
    }

    // =========================================================
    // ✅ PushLog 저장 (새 테이블 스키마)
    // =========================================================
    private void savePushLog(
            Long actorUserNo,
            String targetType,
            String targetValue,
            String targetId,
            String eventType,
            String pushType,
            String title,
            String body,
            String sendStatus) {
        try {
            pushLogRepository.save(PushLog.builder()
                    .actorUserNo(actorUserNo)
                    .targetType(targetType)
                    .targetValue(targetValue)
                    .targetId(targetId)
                    .eventType(eventType)
                    .messageTitle(title)
                    .messageBody(body)
                    .pushType(pushType)
                    .sendStatus(sendStatus)
                    .readYn("N")
                    .build());
        } catch (Exception e) {
            log.error("PushLog SAVE FAIL targetType={}, targetValue={}, err={}", targetType, targetValue,
                    e.getMessage(), e);
        }
    }

}
