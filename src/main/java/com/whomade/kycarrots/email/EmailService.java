package com.whomade.kycarrots.email;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // 🔥 테스트용 (실서버에서는 절대 이렇게 하면 안 됨)
    @Value("${sendgrid.api-key}")
    private String apiKey;

    @Value("${sendgrid.from-address}")
    private String fromAddress;

    public String send(String to, String subject, String content, String type) throws Exception {
        Content body = new Content("html".equalsIgnoreCase(type) ? "text/html" : "text/plain", content);
        Mail mail = new Mail(new Email(fromAddress), subject, new Email(to), body);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        SendGrid sg = new SendGrid(apiKey.trim());
        Response res = sg.api(request);

        int code = res.getStatusCode();        // ← 정수 코드만 사용
        System.out.println("SendGrid status = " + code);

        if (code < 200 || code >= 300) {
            // 실패는 예외로 던져 상위에서 EMAIL_SEND_FAILED 처리
            throw new IllegalStateException("SendGrid failed: " + code + " body=" + res.getBody());
        }
        return String.valueOf(code);           // ← "202" 등 숫자만 반환
    }
}