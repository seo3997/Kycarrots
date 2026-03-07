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

    // =========================================================
    // ✅ Topic 푸시 (기존 시그니처 유지: actorUserNo 없이 호출 가능)
    // =========================================================
    public void sendPushToTopic(String topic, String title, String body, Map<String, String> data) {
        // actorUserNo 모르니 null로 기록 (기존 호출부 깨지지 않게)
        sendPushToTopicAndLog(null, topic, title, body, data, "TOPIC_PUSH");
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
            // Android topic
            sendPushToTopicAndroid(topic, title, body, data);
            // iOS topic
            sendPushToTopicIos(topic, title, body, data);

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

    private String sendPushToTopicAndroid(String topic, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        Message.Builder b = Message.builder()
                .setTopic(topic)
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build());

        if (data != null && !data.isEmpty())
            b.putAllData(data);
        return FirebaseMessaging.getInstance().send(b.build());
    }

    private String sendPushToTopicIos(String topic, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        ApnsConfig apns = ApnsConfig.builder()
                .putHeader("apns-priority", "10")
                .setAps(Aps.builder()
                        .setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
                        .setSound("default")
                        .build())
                .build();

        Message.Builder b = Message.builder()
                .setTopic(topic)
                .setApnsConfig(apns);

        if (data != null && !data.isEmpty())
            b.putAllData(data);
        return FirebaseMessaging.getInstance().send(b.build());
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

        try {
            if ("ANDROID".equalsIgnoreCase(deviceType)) {
                sendToAndroidToken(token, title, body, data);
            } else if ("IOS".equalsIgnoreCase(deviceType)) {
                sendToIosToken(token, title, body, data);
            } else {
                throw new IllegalArgumentException("Unknown deviceType=" + deviceType);
            }

            sendStatus = "SUCCESS";

        } catch (Exception e) {
            log.error("User push FAIL deviceType={}, targetUserNo={}, eventType={}", deviceType, targetUserNo,
                    eventType, e);

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
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build());

        if (data != null && !data.isEmpty())
            b.putAllData(data);
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
