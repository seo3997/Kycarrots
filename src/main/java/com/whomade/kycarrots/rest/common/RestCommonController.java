package com.whomade.kycarrots.rest.common;

import com.whomade.kycarrots.dto.login.LoginResponse;
import com.whomade.kycarrots.entity.common.OpCodeVo;
import com.whomade.kycarrots.entity.common.OpSclasCodeVO;
import com.whomade.kycarrots.entity.common.TxtListDataInfo;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import com.whomade.kycarrots.service.common.OpCodeService;
import com.whomade.kycarrots.service.member.OpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/common")
@Slf4j
public class RestCommonController {
    private final OpCodeService opCodeService;

    @GetMapping(value = "/codelist", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TxtListDataInfo> codelist(@RequestParam("groupId") String groupId) {
        DataMap param = new DataMap();
        param.put("groupId", groupId);
        List<OpCodeVo> opCodeList = opCodeService.selectListCode(param);

        // OpCodeVo → TxtListDataInfo 변환
        List<TxtListDataInfo> result = opCodeList.stream()
                .map(vo -> {
                    TxtListDataInfo info = new TxtListDataInfo();
                    info.setStrIdx(vo.getCode()); // 고유 값
                    info.setStrMsg(vo.getCodeNm()); // 출력 메시지
                    return info;
                })
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping(value = "/sCodeList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TxtListDataInfo> sCodeList(@RequestParam("groupId") String groupId,@RequestParam("mcode") String mcode) {
        DataMap param = new DataMap();
        param.put("groupId", groupId);
        param.put("mcode", mcode);
        List<OpSclasCodeVO> opCodeList = opCodeService.selectSCodeList(param);

        // OpCodeVo → TxtListDataInfo 변환
        List<TxtListDataInfo> result = opCodeList.stream()
                .map(vo -> {
                    TxtListDataInfo info = new TxtListDataInfo();
                    info.setStrIdx(vo.getSclasCode()); // 고유 값
                    info.setStrMsg(vo.getSclasNm()); // 출력 메시지
                    return info;
                })
                .collect(Collectors.toList());

        return result;
    }
}
