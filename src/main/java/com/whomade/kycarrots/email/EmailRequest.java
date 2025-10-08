package com.whomade.kycarrots.email;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메일 전송 요청")
public record EmailRequest(
        @Schema(example = "user@example.com") String to,
        @Schema(example = "테스트 메일") String subject,
        @Schema(example = "본문 내용입니다.") String content,
        @Schema(description="text 또는 html", example = "text") String type
) {}
