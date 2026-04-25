package com.whomade.kycarrots.rest.branch;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.mgt.branch.service.MgtBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/branch")
@Slf4j
public class RestBranchController {

    private final MgtBranchService mgtBranchService;
    private final com.whomade.kycarrots.repository.mybatis.member.OpUserMapper opUserMapper;

    @GetMapping("/list")
    public ResponseEntity<List<DataMap>> getBranchList() {
        try {
            DataMap param = new DataMap();
            param.put("excludeAdmin", "Y");
            List<DataMap> list = mgtBranchService.selectListBranch(param);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("지점 목록 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<DataMap> getBranchInfo(@RequestParam("branchId") String branchId) {
        try {
            DataMap info = opUserMapper.selectBranchSimple(Long.parseLong(branchId));
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            log.error("지점 정보 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
