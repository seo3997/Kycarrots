package com.whomade.kycarrots.rest.member;

import com.whomade.kycarrots.dto.LinkSocialRequest;
import com.whomade.kycarrots.dto.SimpleResultResponse;
import com.whomade.kycarrots.dto.SocialAuthRequest;
import com.whomade.kycarrots.dto.login.LoginResponse;
import com.whomade.kycarrots.dto.login.ResetChangeRequest;
import com.whomade.kycarrots.dto.user.StringResponse;
import com.whomade.kycarrots.dto.user.PushTokenVo;
import com.whomade.kycarrots.dto.user.UnlinkSocialRequest;

import com.whomade.kycarrots.dto.login.PasswordChangeRequest;
import com.whomade.kycarrots.email.PasswordResetResult;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.service.member.OpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import com.whomade.kycarrots.email.PasswordResetService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members")
@Slf4j
public class RestMemberController {
    private final OpUserService opUserService;
    private final PasswordResetService passwordResetService;
    private final com.whomade.kycarrots.front.member.service.AddressBookService addressBookService;

    @Autowired
    private EncodedTokenizer tokenizer = new EncodedTokenizer();

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /*
     * @PostMapping(value = "/login",
     * produces = MediaType.APPLICATION_JSON_VALUE)
     * public LoginResponse login(
     * 
     * @RequestParam("id") String id,
     * 
     * @RequestParam("pass") String pass,
     * 
     * @RequestParam("ret_id") String retId,
     * 
     * @RequestParam("appver") String appver) {
     * 
     * // 여기서는 예시로 단순히 입력값을 확인한 후 고정 토큰을 리턴합니다.
     * // 실제 구현 시 id, pass 등으로 인증 로직을 구현하면 됩니다.
     * 
     * String password = tokenizer.encode(pass);
     * password=
     * "d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db";
     * DataMap param = new DataMap();
     * param.put("userId", id);
     * param.put("password", password);
     * OpUserVO member = opUserService.findByUserIdAndPassword(param);
     * 
     * String token =
     * "%2FV%2F26xyieYwgQKUf6wFvdeMy3O%2Fw%2Fc6g0sAskcxhDZq1I3kiw2GIHmlt3Mm5SSL0eVM%2BtFASntulXfELYjlr3oQr%2Bu%2FUmTdipdABtBlxDBugFIv9vHqd8bN4TZl7vqGPlL5VRHhKxKzJayL1K6vQ6P1IUZe%2Bz5z1mnnQvRm66b4%3D";
     * 
     * try {
     * token = new EncodedTokenizer().getToken(member);
     * } catch (DecoderException e1) {
     * // TODO Auto-generated catch block
     * e1.printStackTrace();
     * }
     * 
     * 
     * 
     * // 인증 성공 시 token 리턴 (여기서는 단순 예시)
     * return new LoginResponse(token);
     * }
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(
            @RequestParam("id") String id,
            @RequestParam("pass") String pass,
            @RequestParam("login_cd") String loginCd,
            @RequestParam("reg_id") String regId, // 클라이언트에서 보내는 이름에 맞춤
            @RequestParam("appver") String appver,
            @RequestParam("providerUserId") String providerUserId) {

        String encodedPassword = "";
        try {
            encodedPassword = EgovFileScrty.encryptSHA512(pass);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        DataMap param = new DataMap();
        param.put("userId", id);

        OpUserVO member = opUserService.seelectUser(param);

        if (member == null) {
            // 아이디 없음
            return new LoginResponse(601, null, null, null, null, null, null, null, null, null, null, null, null); // RESULT_NO_USER
        }

        if (!encodedPassword.equals(member.getPassword()) && "PWD".equals(loginCd)) {
            // 비밀번호 불일치
            return new LoginResponse(602, null, null, null, null, null, null, null, null, null, null, null, null); // RESULT_PWD_ERR
        }

        if (!"PWD".equals(loginCd)) {
            // 소셜회원 아님
            DataMap socialParam = new DataMap();
            socialParam.put("provider", loginCd);
            socialParam.put("providerUserId", providerUserId);

            if (!opUserService.existsSocialAccount(socialParam)) {
                return new LoginResponse(605, null, null, null, null, null, null, null, null, null, null, null, null); // RESULT_PWD_ERR
            }
        }

        /*
         * if (!memberCode.equals(member.getMemberCode())) {
         * // 회원타입 불일치
         * return new LoginResponse(603, null, null, null, null, null, null,
         * null,null,null,null,null); // RESULT_PWD_ERR
         * }
         */

        String token = "";
        // token =
        // "%2FV%2F26xyieYwgQKUf6wFvdeMy3O%2Fw%2Fc6g0sAskcxhDZq1I3kiw2GIHmlt3Mm5SSL0eVM%2BtFASntulXfELYjlr3oQr%2Bu%2FUmTdipdABtBlxDBugFIv9vHqd8bN4TZl7vqGPlL5VRHhKxKzJayL1K6vQ6P1IUZe%2Bz5z1mnnQvRm66b4%3D";

        try {
            token = tokenizer.getToken(member);
        } catch (DecoderException e) {
            e.printStackTrace();
            return new LoginResponse(500, null, null, null, null, null, null, null, null, null, null, null, null); // 서버
                                                                                                                   // 에러
        }

        if (StringUtils.hasText(regId)) {
            member.setPushToken(regId);
            opUserService.updatePushToken(member);
        }

        com.whomade.kycarrots.dto.BranchInfoVo branchInfo = null;
        if (member.getBranchId() != null && !member.getBranchId().isEmpty()) {
            branchInfo = opUserService.selectBranchInfo(Long.valueOf(member.getBranchId()));
        }

        return new LoginResponse(
                200, // RESULT_CODE_200
                token,
                String.valueOf(member.getUserNo()),
                "",
                "",
                "",
                "",
                member.getUserNm(),
                member.getMemberCode(),
                member.getUserId(),
                loginCd,
                String.valueOf(member.getUserNo()),
                branchInfo);
    }

    @PostMapping(value = "/email-check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> checkEmailDuplicate(@RequestParam("email") String email) {
        Map<String, Object> response = new HashMap<>();

        DataMap param = new DataMap();
        param.put("userId", email);
        // 4) 유저 조회 후 토큰 발급
        OpUserVO member = opUserService.seelectUser(param);
        if (member != null) {
            response.put("result", false);
            response.put("message", member.getUserNo());
        } else {
            response.put("result", true);
            response.put("message", "사용가능한 이메일입니다.");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse registerUser(@RequestBody OpUserVO user) {
        try {
            // 비밀번호 암호화
            String encryptedPassword = EgovFileScrty.encryptSHA512(user.getPassword());
            user.setPassword(encryptedPassword);

            int result = opUserService.insertUser(user);

            if (result > 0) {
                String token = "";
                try {
                    token = tokenizer.getToken(user);
                } catch (DecoderException e) {
                    e.printStackTrace();
                }

                com.whomade.kycarrots.dto.BranchInfoVo branchInfo = null;
                if (user.getBranchId() != null && !user.getBranchId().isEmpty()) {
                    branchInfo = opUserService.selectBranchInfo(Long.valueOf(user.getBranchId()));
                }

                return new LoginResponse(
                        200, // RESULT_CODE_200
                        token,
                        String.valueOf(user.getUserNo()),
                        "",
                        "",
                        "",
                        "",
                        user.getUserNm(),
                        user.getMemberCode(),
                        user.getUserId(),
                        user.getProvider(),
                        user.getProviderUserId(),
                        branchInfo);
            } else {
                return new LoginResponse(0, null, null, null, null, null, null, null, null, null, null, null, null); // 서버
                                                                                                                     // 에러
            }
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생", e);
            return new LoginResponse(0, null, null, null, null, null, null, null, null, null, null, null, null); // 서버
                                                                                                                 // 에러
        }
    }

    @PostMapping(value = "/userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMemberByToken(@RequestParam("token") String token) {
        OpUserVO opUserVO = null;
        try {
            opUserVO = tokenizer.getMember(token);
        } catch (Exception e) {
            log.error("토큰 디코딩 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 처리 오류");
        }

        if (opUserVO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰");
        }

        return ResponseEntity.ok(opUserVO);
    }

    @PostMapping(value = "/push/savetoken", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleResultResponse> registerPushToken(
            @RequestBody PushTokenVo request) {
        if (request.getUserId() == null || request.getPushToken() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(SimpleResultResponse.fail("userId와 pushToken은 필수입니다."));
        }

        OpUserVO user = new OpUserVO();
        user.setUserNo(request.getUserNo());
        user.setUserId(request.getUserId());
        user.setPushToken(request.getPushToken());
        user.setDeviceType(request.getDeviceType());

        try {
            int updated = opUserService.updatePushToken(user);

            if (updated > 0) {
                return ResponseEntity.ok(
                        SimpleResultResponse.ok("푸시 토큰 저장 성공"));
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(SimpleResultResponse.fail("해당 사용자를 찾을 수 없습니다."));
            }

        } catch (Exception e) {
            log.error("푸시 토큰 저장 오류", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail("서버 오류"));
        }
    }

    // GET /api/members/find-email?nm=이름&hp=010-1234-5678
    @GetMapping("/find-email")
    public ResponseEntity<StringResponse> findEmail(
            @RequestParam("nm") String name,
            @RequestParam("hp") String phone) {

        // 하이픈 등 제거해서 비교
        OpUserVO opUserVO = new OpUserVO();
        opUserVO.setUserNm(name);
        opUserVO.setCttpc(phone);

        OpUserVO found = opUserService.findEmailByNameAndPhone(opUserVO);

        String email = (found != null) ? found.getEmail() : null;
        if (email == null || email.isBlank()) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(new StringResponse(email));

    }

    // GET /api/members/find-password?mail=abc@xyz.com&hp=010-1234-5678
    @GetMapping("/find-password")
    public ResponseEntity<StringResponse> findPassword(
            @RequestParam("mail") String mail,
            @RequestParam("hp") String phone) {
        // 1) 파라미터 체크
        String email = mail == null ? "" : mail.trim().toLowerCase();
        if (email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            return ResponseEntity.ok(new StringResponse(Const.RESULT_CODE_ERR));
        }

        // 2) 사용자 존재 및 정보 일치 확인 (이메일 & 전화번호)
        DataMap param = new DataMap();
        param.put("userId", email);
        param.put("cttpc", phone);
        
        OpUserVO user = opUserService.seelectUser(param);
        if (user == null) {
            return ResponseEntity.ok(new StringResponse(Const.RESULT_NO_USER)); // 601
        }

        // 3) 리셋 메일 발송
        try {
            PasswordResetResult result = passwordResetService.sendResetMail(email);
            return switch (result) {
                case OK -> ResponseEntity.ok(new StringResponse(Const.RESULT_CODE_200)); // 200
                case EMAIL_SEND_FAILED -> ResponseEntity.ok(new StringResponse(Const.RESULT_PWD_ERR)); // 602
                case USER_NOT_FOUND -> ResponseEntity.ok(new StringResponse(Const.RESULT_NO_USER)); 
            };
        } catch (Exception e) {
            return ResponseEntity.ok(new StringResponse(String.valueOf(Const.RESULT_CODE_ERR)));
        }
    }

    @PostMapping(value = "/reset/change.do", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponse> resetChange(
            @RequestParam("uid") String userId,
            @RequestParam("sel") String selector,
            @RequestParam("ver") String verifier,
            @RequestBody ResetChangeRequest body) {

        // 입력검증 (간단)
        String np = (body != null && body.getNewPassword() != null) ? body.getNewPassword().trim() : "";
        String cp = (body != null && body.getConfirmPassword() != null) ? body.getConfirmPassword().trim() : "";

        if (userId == null || userId.isBlank()
                || selector == null || selector.isBlank()
                || verifier == null || verifier.isBlank()
                || np.isEmpty() || !np.equals(cp) || np.length() > 20) {
            return ResponseEntity.ok(new StringResponse(Const.RESULT_CODE_ERR)); // "0"
        }

        String code = opUserService.verifyAndChange(userId, selector, verifier, np);
        return ResponseEntity.ok(new StringResponse(code)); // "200"/"604"/"601"/"0"
    }

    /** 소셜 공용 로그인 */
    @PostMapping(value = "/social", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> socialLogin(@RequestBody SocialAuthRequest req) {

        // 0) 파라미터 검증
        if (!StringUtils.hasText(req.getProvider())) {
            return ResponseEntity.ok(new LoginResponse(400, null, null, null, null, null, null, "provider required", "",
                    null, null, null, null));
        }

        // 1) 토큰 검증 → provider_user_id(+email) 추출 (실구현으로 교체)
        /*
         * TokenInfo tokenInfo = introspect(req);
         * if (!tokenInfo.valid) {
         * return ResponseEntity.ok(new LoginResponse(604, null, null, null, null, null,
         * null, "invalid token")); // RESULT_NO_DATA(온보딩 유도)
         * }
         */

        final String provider = req.getProvider().toUpperCase();
        final String providerUserId = req.getProviderUserId(); // 핵심
        // final String email = tokenInfo.email; // 필요 시 활용

        // 2) 소셜로 사용자 조회 (JOIN 한방 쿼리)
        DataMap dm = new DataMap();
        dm.put("provider", provider);
        dm.put("providerUserId", providerUserId);

        OpUserVO member = opUserService.selectUserBySocial(dm);
        if (member == null) {
            // 매핑 없음 → 온보딩 필요
            return ResponseEntity.ok(new LoginResponse(604, null, null, null, null, null, null, "onboarding required",
                    "", null, null, null, null));
        }

        // 3) 마지막 로그인 갱신
        opUserService.updatetouchLastLogin(dm);

        // 4) 토큰 발급 (기존 login과 동일)
        String token;
        try {
            token = tokenizer.getToken(member);
        } catch (DecoderException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse(500, null, null, null, null, null, null, "server error", null,
                    null, null, null, null));
        }

        com.whomade.kycarrots.dto.BranchInfoVo branchInfo = null;
        if (member.getBranchId() != null && !member.getBranchId().isEmpty()) {
            branchInfo = opUserService.selectBranchInfo(Long.valueOf(member.getBranchId()));
        }

        return ResponseEntity.ok(new LoginResponse(
                200, // RESULT_CODE_200
                token,
                String.valueOf(member.getUserNo()), // userNo
                "", "", "", "", // 예전 필드 자리 유지
                member.getUserNm(), // userName
                member.getMemberCode(),
                member.getUserId(),
                provider,
                providerUserId,
                branchInfo));
    }

    @PostMapping(value = "/link", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> linkSocial(@RequestBody LinkSocialRequest req) {

        // 0) 파라미터 검증
        if (req.getUserNo() == null || req.getUserNo().isEmpty()) {
            return ResponseEntity.ok(new LoginResponse(400, null, null, null, null, null, null, "userNo required", null,
                    null, null, null, null));
        }
        if (!StringUtils.hasText(req.getProvider())) {
            return ResponseEntity.ok(new LoginResponse(400, null, null, null, null, null, null, "provider required",
                    null, null, null, null, null));
        }
        if (!StringUtils.hasText(req.getProviderUserId())) {
            return ResponseEntity.ok(new LoginResponse(400, null, null, null, null, null, null,
                    "providerUserId required", null, null, null, null, null));
        }

        final String provider = req.getProvider().toUpperCase();

        // 1) 이미 해당 소셜 UID가 다른 사용자에 매핑되어 있는지 확인 (권장)
        DataMap chk = new DataMap();
        chk.put("provider", provider);
        chk.put("providerUserId", req.getProviderUserId());
        OpUserVO mapped = opUserService.selectUserBySocial(chk);
        if (mapped != null && !String.valueOf(req.getUserNo()).equals(mapped.getUserNo())) {
            // 이미 다른 유저에 묶여 있으면 충돌
            return ResponseEntity.ok(new LoginResponse(409, null, null, null, null, null, null,
                    "providerUserId already linked to another user", null, null, null, null, null));
        }

        // 2) INSERT (또는 이미 동일 유저에 매핑되어 있으면 스킵)
        if (mapped == null) {
            OpUserVO vo = new OpUserVO();
            vo.setUserNo(req.getUserNo());
            vo.setProvider(provider);
            vo.setProviderUserId(req.getProviderUserId());

            int ins = opUserService.insertTbSocialAccount(vo); // <-- 네가 준비한 서비스 메서드
            if (ins < 1) {
                return ResponseEntity.ok(new LoginResponse(500, null, null, null, null, null, null,
                        "insert social account failed", null, null, null, null, null));
            }
        }

        // 3) 마지막 로그인 갱신(선택)
        opUserService.updatetouchLastLogin(chk);

        DataMap param = new DataMap();
        param.put("userId", req.getUserId());
        // 4) 유저 조회 후 토큰 발급
        OpUserVO member = opUserService.seelectUser(param);
        if (member == null) {
            return ResponseEntity.ok(new LoginResponse(601, null, null, null, null, null, null, "user not found", null,
                    null, null, null, null));
        }

        String token;
        try {
            token = tokenizer.getToken(member);
        } catch (DecoderException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse(500, null, null, null, null, null, null, "server error", null,
                    null, null, null, null));
        }

        com.whomade.kycarrots.dto.BranchInfoVo branchInfo = null;
        if (member.getBranchId() != null && !member.getBranchId().isEmpty()) {
            branchInfo = opUserService.selectBranchInfo(Long.valueOf(member.getBranchId()));
        }

        return ResponseEntity.ok(new LoginResponse(
                200, // RESULT_CODE_200
                token,
                String.valueOf(member.getUserNo()), // userNo
                "", "", "", "", // 예전 필드 유지
                member.getUserNm(), // userName
                member.getMemberCode(), // memberCode (있다면)
                member.getUserId(),
                provider,
                req.getProviderUserId(),
                branchInfo));
    }

    @PostMapping(value = "/unlink", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleResultResponse> unlinkSocial(@RequestBody UnlinkSocialRequest req) {

        if (!StringUtils.hasText(req.getProvider())) {
            return ResponseEntity.ok(SimpleResultResponse.fail("provider required"));
        }
        if (!StringUtils.hasText(req.getProviderUserId())) {
            return ResponseEntity.ok(SimpleResultResponse.fail("providerUserId required"));
        }

        String provider = req.getProvider().toUpperCase();

        DataMap dm = new DataMap();
        dm.put("provider", provider);
        dm.put("providerUserId", req.getProviderUserId());

        // (권장) dm.put("userNo", req.getUserNo());

        int deleted = opUserService.deleteTbSocialAccount(dm);

        if (deleted > 0) {
            return ResponseEntity.ok(SimpleResultResponse.ok("unlink success"));
        } else {
            return ResponseEntity.ok(SimpleResultResponse.fail("no mapping found"));
        }
    }

    @GetMapping("/address")
    public ResponseEntity<List<DataMap>> getAddressList(@RequestParam("token") String token) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            DataMap param = new DataMap();
            param.put("userNo", user.getUserNo());
            List<DataMap> list = addressBookService.selectAddressList(param);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("배송지 목록 조회 실패", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/address")
    public ResponseEntity<SimpleResultResponse> addAddress(@RequestParam("token") String token,
            @RequestBody com.whomade.kycarrots.entity.member.TbAddressBookVo addressVo) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            addressVo.setUserNo(user.getUserNo());
            addressBookService.insertAddress(addressVo);
            return ResponseEntity.ok(SimpleResultResponse.ok("배송지 추가 성공"));
        } catch (Exception e) {
            log.error("배송지 추가 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail(e.getMessage()));
        }
    }

    @PostMapping("/address/update/{id}")
    public ResponseEntity<SimpleResultResponse> updateAddress(@PathVariable("id") Long id,
            @RequestParam("token") String token,
            @RequestBody com.whomade.kycarrots.entity.member.TbAddressBookVo addressVo) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            addressVo.setUserNo(user.getUserNo());
            addressVo.setAddressId(id);
            addressBookService.updateAddress(addressVo);
            return ResponseEntity.ok(SimpleResultResponse.ok("배송지 수정 성공"));
        } catch (Exception e) {
            log.error("배송지 수정 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail(e.getMessage()));
        }
    }

    @PostMapping("/address/delete/{id}")
    public ResponseEntity<SimpleResultResponse> deleteAddress(@PathVariable("id") Long id,
            @RequestParam("token") String token) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            DataMap param = new DataMap();
            param.put("userNo", user.getUserNo());
            param.put("addressId", id);
            addressBookService.deleteAddress(param);
            return ResponseEntity.ok(SimpleResultResponse.ok("배송지 삭제 성공"));
        } catch (Exception e) {
            log.error("배송지 삭제 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail(e.getMessage()));
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleResultResponse> updateUser(@RequestParam("token") String token,
            @RequestBody OpUserVO user) {
        try {
            OpUserVO current = tokenizer.getMember(token);
            if (current == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            user.setUserNo(current.getUserNo());
            int result = opUserService.updateUser(user);
            if (result > 0) {
                return ResponseEntity.ok(SimpleResultResponse.ok("사용자 정보 수정 성공"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(SimpleResultResponse.fail("수정 실패"));
            }
        } catch (Exception e) {
            log.error("사용자 정보 수정 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail(e.getMessage()));
        }
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<SimpleResultResponse> changePassword(@RequestParam("token") String token,
            @RequestBody PasswordChangeRequest request) {
        try {
            OpUserVO current = tokenizer.getMember(token);
            if (current == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                return ResponseEntity.badRequest().body(SimpleResultResponse.fail("새 비밀번호가 일치하지 않습니다."));
            }

            String resultCode = opUserService.changePassword(current.getUserId(), request.getCurrentPassword(),
                    request.getNewPassword());

            if ("200".equals(resultCode)) {
                return ResponseEntity.ok(SimpleResultResponse.ok("비밀번호가 성공적으로 변경되었습니다."));
            } else if ("602".equals(resultCode)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SimpleResultResponse.fail("현재 비밀번호가 일치하지 않습니다."));
            } else if ("601".equals(resultCode)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SimpleResultResponse.fail("사용자를 찾을 수 없습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(SimpleResultResponse.fail("비밀번호 변경 중 오류가 발생했습니다. 코드: " + resultCode));
            }

        } catch (Exception e) {
            log.error("비밀번호 변경 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail(e.getMessage()));
        }
    }

}
