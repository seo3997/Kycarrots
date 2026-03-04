package com.whomade.kycarrots.rest.dashboard;

import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import com.whomade.kycarrots.mgt.branch.service.MgtBranchService;
import com.whomade.kycarrots.mgt.order.service.MgtOrderService;
import com.whomade.kycarrots.service.product.TnProductService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Dashboard", description = "지점/본점 관리자 대시보드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
@Slf4j
public class DashboardController {

    private final MgtOrderService mgtOrderService;
    private final TnProductService tnProductService;
    private final MgtBranchService mgtBranchService;
    private final CommonCodeService commonCodeService;
    private final EncodedTokenizer tokenizer;

    @Operation(summary = "대시보드 통계 데이터 조회", description = "금일 매출, 신규 주문, 미처리 상담 등 대시보드 현황 데이터를 조회합니다.")
    @GetMapping("")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Map<String, Object>> getDashboardData(@RequestParam("token") String token) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            DataMap param = new DataMap();
            param.put("ss_user_no", user.getUserNo());
            param.put("userNo", user.getUserNo());
            param.put("branchId", user.getBranchId());
            param.put("memberCode", user.getMemberCode());

            Map<String, Object> result = new HashMap<>();

            // 상품 현황 카운트
            DataMap prductCntMap = tnProductService.getProductStatusCounts(param);
            // 대시보드 통계 (매출 등)
            DataMap dashboardStats = mgtOrderService.selectDashboardStats(param);
            // 최근 주문 목록
            List<DataMap> dashboardOrderList = mgtOrderService.selectDashboardOrderList(param);

            // 본사 정보 조회 (BR_0002) - 필요한 경우 앱에서 활용
            DataMap headQuarterParam = new DataMap();
            headQuarterParam.put("branchCode", "BR_0002");
            DataMap headQuarterBranch = mgtBranchService.selectBranchByCode(headQuarterParam);

            // 택배사 코드 조회 (R010660)
            DataMap codeParam = new DataMap();
            codeParam.put("group_id", "R010660");
            List<DataMap> deliveryCompanyList = commonCodeService.selectCodeList(codeParam);

            result.put("prductCntMap", prductCntMap);
            result.put("dashboardStats", dashboardStats);
            result.put("dashboardOrderList", dashboardOrderList);
            result.put("memberCode", user.getMemberCode());
            result.put("headQuarterBranch", headQuarterBranch);
            result.put("deliveryCompanyList", deliveryCompanyList);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("대시보드 데이터 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
