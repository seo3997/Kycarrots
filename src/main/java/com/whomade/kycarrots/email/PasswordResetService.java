package com.whomade.kycarrots.email;

import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.repository.mybatis.member.OpUserMapper;
import com.whomade.kycarrots.service.EmailCafe24Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordResetService {

    private final OpUserMapper opUserMapper;
    private final EmailService emailService;
    private final EmailCafe24Service emailCafe24Service;


    @Value("${mailreset.base-url}")
    private String resetBaseUrl; // 예: https://your.app/reset

    @Value("${mailreset.ttl-minutes:120}")
    private int ttlMinutes;

    private final SecureRandom random = new SecureRandom();

    public PasswordResetResult sendResetMail(String email) {
        if (email == null || email.isBlank()) return PasswordResetResult.USER_NOT_FOUND;
        final String normalized = email.trim().toLowerCase();

        // 1) 사용자 확인
        OpUserVO user = opUserMapper.selectByEmail(normalized);
        if (user == null) return PasswordResetResult.USER_NOT_FOUND;

        // 2) 최신 토큰으로 덮어쓰기(UPSERT)
        LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);
        String selector = randomHex(16);
        String verifier = randomBase64(32);
        String verifierHash = sha256Base64(verifier);
        LocalDateTime expiresAt = nowUtc.plusMinutes(ttlMinutes);

        PasswordResetToken token = new PasswordResetToken(
                user.getUserId(), selector, verifierHash, expiresAt, "N"
        );
        opUserMapper.upsertToken(token);

        // 3) 메일 발송 (uid + sel + ver 포함)
        String link = String.format("%s?uid=%s&sel=%s&ver=%s",
                resetBaseUrl, url(user.getUserId()), url(selector), url(verifier));

        log.debug("link: {}", link);

        String subject = "[kyCarrots] Password reset";
        String html = """
            <p>안녕핫요 %s,</p>
            <p>아래 링크를 클릭하여 비밀번호를 재설정하세요( %d분 동안 유효):</p>
            <p><a href="%s">%s</a></p>
            <p>요청하지 않으셨다면 이 이메일을 무시하셔도 됩니다.</p>
            """.formatted(safe(user.getUserNm()), ttlMinutes, link, link);

        try {
            // SendGrid 또는 Cafe24 중 하나 선택
            String status = emailService.send(normalized, subject, html, "html");      // SendGrid
            //String status = emailCafe24Service.send(normalized, subject, html, "html");   // Cafe24
           int code;
            try { code = Integer.parseInt(status.trim()); }
            catch (NumberFormatException e) { code = 0; }

            if (code == 200 || code == 202) {
                return PasswordResetResult.OK;
            } else {
                return PasswordResetResult.EMAIL_SEND_FAILED;
            }
        } catch (Exception e) {
            // SendGrid에서 4xx/5xx는 여기로 떨어짐
            return PasswordResetResult.EMAIL_SEND_FAILED;
        }
    }

    // ===== helpers =====
    private static String randomHex(int bytes) {
        byte[] buf = new byte[bytes];
        new SecureRandom().nextBytes(buf);
        StringBuilder sb = new StringBuilder(bytes * 2);
        for (byte b : buf) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private static String randomBase64(int bytes) {
        byte[] buf = new byte[bytes];
        new SecureRandom().nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }

    private static String sha256Base64(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(md.digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private static String url(String v) {
        return URLEncoder.encode(v, StandardCharsets.UTF_8);
    }

    private static String safe(String v) { return v == null ? "" : v; }
}