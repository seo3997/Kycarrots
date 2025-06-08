package com.whomade.kycarrots.rest.member;

import com.whomade.kycarrots.dto.login.LoginResponse;
import com.whomade.kycarrots.entity.TbUserSite;
import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.service.TbUserSiteService;
import com.whomade.kycarrots.service.member.OpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/members")
@Slf4j
public class RestMemberController {
    private final OpUserService opUserService;
    private EncodedTokenizer tokenizer = new EncodedTokenizer();

    /*
    @PostMapping(value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(
            @RequestParam("id") String id,
            @RequestParam("pass") String pass,
            @RequestParam("ret_id") String retId,
            @RequestParam("appver") String appver) {

        // 여기서는 예시로 단순히 입력값을 확인한 후 고정 토큰을 리턴합니다.
        // 실제 구현 시 id, pass 등으로 인증 로직을 구현하면 됩니다.

        String password = tokenizer.encode(pass);
        password="d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db";
        DataMap param = new DataMap();
        param.put("userId", id);
        param.put("password", password);
        OpUserVO member = opUserService.findByUserIdAndPassword(param);

        String token = "%2FV%2F26xyieYwgQKUf6wFvdeMy3O%2Fw%2Fc6g0sAskcxhDZq1I3kiw2GIHmlt3Mm5SSL0eVM%2BtFASntulXfELYjlr3oQr%2Bu%2FUmTdipdABtBlxDBugFIv9vHqd8bN4TZl7vqGPlL5VRHhKxKzJayL1K6vQ6P1IUZe%2Bz5z1mnnQvRm66b4%3D";

        try {
             token = new EncodedTokenizer().getToken(member);
        } catch (DecoderException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }



        // 인증 성공 시 token 리턴 (여기서는 단순 예시)
        return new LoginResponse(token);
    }
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(
            @RequestParam("id") String id,
            @RequestParam("pass") String pass,
            @RequestParam("reg_id") String regId,  // 클라이언트에서 보내는 이름에 맞춤
            @RequestParam("appver") String appver) {

        String encodedPassword = "";
        try {
            encodedPassword = EgovFileScrty.encryptSHA512(pass);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        DataMap param = new DataMap();
        param.put("userId", id);

        OpUserVO member = opUserService.findByUserIdAndPassword(param);

        if (member == null) {
            // 아이디 없음
            return new LoginResponse(601, null, null, null, null, null, null); // RESULT_NO_USER
        }

        if (!encodedPassword.equals(member.getPassword())) {
            // 비밀번호 불일치
            return new LoginResponse(602, null, null, null, null, null, null); // RESULT_PWD_ERR
        }

        String token = "";
        //token = "%2FV%2F26xyieYwgQKUf6wFvdeMy3O%2Fw%2Fc6g0sAskcxhDZq1I3kiw2GIHmlt3Mm5SSL0eVM%2BtFASntulXfELYjlr3oQr%2Bu%2FUmTdipdABtBlxDBugFIv9vHqd8bN4TZl7vqGPlL5VRHhKxKzJayL1K6vQ6P1IUZe%2Bz5z1mnnQvRm66b4%3D";

        try {
            token = tokenizer.getToken(member);
        } catch (DecoderException e) {
            e.printStackTrace();
            return new LoginResponse(500, null, null, null, null, null, null); // 서버 에러
        }

        return new LoginResponse(
                200,                    // RESULT_CODE_200
                token,
                String.valueOf(member.getUserNo()),
                "",
                "",
                "",
                ""
        );
    }


    @PostMapping(value = "/email-check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> checkEmailDuplicate(@RequestParam("email") String email) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = opUserService.existsByEmail(email);

        if (exists) {
            response.put("result", false);
            response.put("message", "이미 사용 중인 이메일입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            response.put("result", true);
            response.put("message", "사용 가능한 이메일입니다.");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody OpUserVO user) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 비밀번호 암호화
            String encryptedPassword = EgovFileScrty.encryptSHA512(user.getPassword());
            user.setPassword(encryptedPassword);

            int result = opUserService.insertUser(user);
            OpUserAuthorVO opUserAuthorVO = new OpUserAuthorVO();
            opUserAuthorVO.setUserNo(Long.parseLong(user.getUserNo()));
            opUserAuthorVO.setAuthorId(user.getMemberCode());
            opUserAuthorVO.setRegisterNo(1);
            opUserService.insertAuthUser(opUserAuthorVO);

            if (result > 0) {
                response.put("result", true);
                response.put("message", "회원가입이 완료되었습니다.");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);

            } else {
                response.put("result", true);
                response.put("message", "회원가입 실패.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생", e);
            response.put("result", true);
            response.put("message", "서버 오류.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
