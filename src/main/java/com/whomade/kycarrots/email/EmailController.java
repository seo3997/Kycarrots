package com.whomade.kycarrots.email;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mail")
@RestController
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService emailService;
    public EmailController(EmailService emailService) { this.emailService = emailService; }

    @Operation(summary = "메일 전송", description = "SendGrid를 이용해 메일을 전송합니다.")
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody EmailRequest req) throws Exception {
        String result = emailService.send(req.to(), req.subject(), req.content(), req.type());
        return ResponseEntity.ok(result);
    }
}
