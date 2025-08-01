package com.whomade.kycarrots.push;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PushLogService {

    private final PushLogRepository pushLogRepository;

    public void savePushLog(String targetUserId, String productId, String title, String body, String type, String status) {
        PushLog log = PushLog.builder()
                .TARGET_USER_ID(targetUserId)
                .PRODUCT_ID(productId)
                .MESSAGE_TITLE(title)
                .MESSAGE_BODY(body)
                .PUSH_TYPE(type)
                .SEND_STATUS(status)
                .SENT_AT(LocalDateTime.now())
                .READ_YN("N")
                .build();
        pushLogRepository.save(log);
    }
}
