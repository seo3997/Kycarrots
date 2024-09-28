package com.whomade.kycarrots.controller;

import com.whomade.kycarrots.service.EmailService;
import com.whomade.kycarrots.service.TbUserSiteService;
import com.whomade.kycarrots.entity.TbUserSite;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HelloController {
    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String maiFrom;

    @GetMapping("admin/test")
    public String test() {
        return "admin/test"; // test.html 파일을 렌더링합니다.
    }

    @RequestMapping(value = "/admin/test.do", method = RequestMethod.GET)
    public String login() {
        return "admin/test"; // test.html 파일을 렌더링합니다.
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello"; // test.html 파일을 렌더링합니다.
    }

    @GetMapping("/mailSend")
    public String mailSend() {
        String to = "admin@planf.shop";
        String subject = "Subject of the email";
        String htmlContent = "<h1>This is the HTML content of the email</h1>";

        File imageFile = new File("c://Temp//aa.png");
        String attachmentName = "image1.PNG";
        String mimeType = "image/png";

        try {
            //byte[] imageData = emailService.readFileToByteArray(imageFile);
            InputStreamSource imageData = chartBitmapMultipartFile();
            emailService.sendEmailWithAttachment(maiFrom,to, subject, htmlContent, imageData, attachmentName, mimeType);
            System.out.println("Email sent successfully.");
        } catch (MessagingException | IOException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
        return "hello"; // test.html 파일을 렌더링합니다.
    }

    /*
    public InputStreamSource chartBitmapMultipartFile() {
        XYChart chart = new XYChart(350, 150);
        chart.getStyler().setChartBackgroundColor(Color.white);
        chart.getStyler().setChartTitleVisible(false);

        List<Double> yData = new ArrayList<>();
        yData.add(10.);
        yData.add(20.);
        yData.add(30.);
        yData.add(40.);
        yData.add(50.);
        yData.add(60.);

        XYSeries series = chart.addSeries("y(result)", null, yData);
        series.setLineColor(Color.BLUE);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        try {
            byte[] imgByte = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);
            return new ByteArrayResource(imgByte);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    */
    public InputStreamSource chartBitmapMultipartFile() {
        List<Double> yData = new ArrayList<>();
        yData.add(10.);
        yData.add(20.);
        yData.add(30.);
        yData.add(40.);
        yData.add(50.);
        yData.add(60.);

        return new InputStreamSource() {
            @Override
            public InputStream getInputStream() throws IOException {
                XYChart chart = new XYChart(350, 150);
                chart.getStyler().setChartBackgroundColor(Color.white);
                chart.getStyler().setChartTitleVisible(false);
                XYSeries series = chart.addSeries("y(result)", null, yData);
                series.setLineColor(Color.BLUE);
                series.setMarker(SeriesMarkers.NONE);
                series.setLineStyle(SeriesLines.SOLID);
                try {
                    byte[] imgByte = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);
                    ByteArrayInputStream bos = new ByteArrayInputStream(imgByte);
                    return bos;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
