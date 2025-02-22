package com.whomade.kycarrots.rest.member;

import com.whomade.kycarrots.dto.login.LoginResponse;
import com.whomade.kycarrots.entity.TbUserSite;
import com.whomade.kycarrots.framework.common.object.DataMap;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/members")
@Slf4j
public class RestMemberController {
    private final OpUserService opUserService;
    private EncodedTokenizer tokenizer = new EncodedTokenizer();

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
}
