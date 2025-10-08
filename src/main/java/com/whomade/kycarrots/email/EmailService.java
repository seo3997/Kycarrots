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
        Content body = new Content(
                "html".equalsIgnoreCase(type) ? "text/html" : "text/plain",
                content
        );
        Mail mail = new Mail(new Email(fromAddress), subject, new Email(to), body);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        SendGrid sg = new SendGrid(apiKey.trim());
        Response response = sg.api(request);

        System.out.println("SendGrid status = " + response.getStatusCode());
        return "Status: " + response.getStatusCode();
    }
}