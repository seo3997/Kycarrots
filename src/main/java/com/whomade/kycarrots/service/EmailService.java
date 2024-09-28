package com.whomade.kycarrots.service;

import jakarta.activation.DataSource;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-07-11
 */
@Service
public class EmailService {
    //@Autowired
    //private JavaMailSender mailSender;


    public void sendEmailWithAttachment(String from, String to, String subject, String htmlContent, InputStreamSource attachmentData, String attachmentName, String mimeType) throws MessagingException, IOException {
        //MimeMessage mimeMessage = mailSender.createMimeMessage();
        JavaMailSender mailSender = getSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // Set HTML content



        // Create DataSource from byte array
        //DataSource dataSource = new ByteArrayDataSource(attachmentData, mimeType);
        helper.addAttachment(attachmentName, attachmentData,"image/png");

        mailSender.send(mimeMessage);
    }

    public byte[] readFileToByteArray(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

    private JavaMailSenderImpl getSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("mw-002.cafe24.com");
        //sender.setPort(587);
        sender.setPort(25);
        sender.setUsername("admin@planf.shop");
        sender.setPassword("amway1122!@");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable",false);
        javaMailProperties.put("mail.smtp.auth",true);
        javaMailProperties.put("mail.transport.protocol","smtp");
        javaMailProperties.put("mail.debug",false);
        sender.setJavaMailProperties(javaMailProperties);
        return sender;



    }
}
