package com.whomade.kycarrots.rest.order;

import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import com.whomade.kycarrots.mgt.order.service.MgtOrderService;
import com.whomade.kycarrots.service.order.OrderService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Order Management", description = "지점/본점 주문 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Slf4j
public class OrderMgtController {

    private final MgtOrderService mgtOrderService;
    private final OrderService orderService;
    private final CommonCodeService commonCodeService;
    private final EncodedTokenizer tokenizer;
    private final com.whomade.kycarrots.service.member.OpUserService opUserService;

    @Operation(summary = "주문 목록 조회", description = "지점/본점 권한별 주문 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getOrderList(@RequestParam("token") String token,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "orderStDt", required = false) String orderStDt,
            @RequestParam(value = "orderEdDt", required = false) String orderEdDt,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
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
            param.put("orderStatus", orderStatus);
            param.put("orderStDt", orderStDt);
            param.put("orderEdDt", orderEdDt);
            param.put("searchKeyword", searchKeyword);

            ModelMap model = new ExtendedModelMap();
            List<DataMap> resultList = mgtOrderService.selectPageListOrder(model, param);

            Map<String, Object> result = new HashMap<>();
            result.put("resultList", resultList);
            result.put("param", param);
            result.put("totalCount", model.get("totalCount")); // MgtOrderService에서 model에 담는 정보를 추출 가능 시 사용

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("주문 목록 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "주문 상세 조회", description = "주문 상세 정보(마스터 및 상세 아이템)를 조회합니다.")
    @GetMapping("/{orderId}")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Map<String, Object>> getOrderDetail(@RequestParam("token") String token,
            @PathVariable("orderId") String orderId) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            OrderVo resultVo = orderService.selectOrderById(Long.parseLong(orderId));
            if (resultVo == null) {
                return ResponseEntity.notFound().build();
            }

            List<OrderItemVo> itemList = orderService.selectOrderItemsByOrderId(resultVo.getOrderId());

            // 택배사 코드 조회 (R010660)
            DataMap codeParam = new DataMap();
            codeParam.put("group_id", "R010660");
            List<DataMap> deliveryCompanyList = commonCodeService.selectCodeList(codeParam);

            Map<String, Object> res = new HashMap<>();
            res.put("resultVo", resultVo);
            res.put("orderItemList", itemList);
            res.put("deliveryCompanyList", deliveryCompanyList);

            return ResponseEntity.ok(res);
        } catch (Exception e) {
            log.error("주문 상세 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "입금 확인 처리 (본사/관리자용)", description = "본사 관리자가 지점 무통장 입금 확인 처리를 수행합니다.")
    @PostMapping("/confirmDeposit")
    public ResponseEntity<?> confirmDeposit(@RequestParam("token") String token,
            @RequestParam("orderId") String orderId,
            @RequestParam(value = "carrier", required = false) String carrier,
            @RequestParam(value = "trackingNo", required = false) String trackingNo) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            DataMap param = new DataMap();
            param.put("orderId", orderId);
            param.put("deliveryCompanyCode", carrier);
            param.put("trackingNo", trackingNo);
            param.put("updusrNo", user.getUserNo());

            mgtOrderService.confirmBranchDeposit(param);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("입금 확인 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "지점 입금 확인 요청 (지점용)", description = "지점 관리자가 본사에 입금 확인 요청을 보냅니다.")
    @PostMapping("/requestBranchDeposit")
    public ResponseEntity<?> requestBranchDeposit(@RequestParam("token") String token,
            @RequestParam("orderId") String orderId) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            DataMap param = new DataMap();
            param.put("orderId", orderId);
            param.put("updusrNo", user.getUserNo());

            // Get order info to get orderNo
            OrderVo orderVo = orderService.selectOrderById(Long.parseLong(orderId));
            if (orderVo != null) {
                param.put("orderNo", orderVo.getOrderNo());
            }

            // Get branch info
            String branchName = user.getBranchId();
            if (user.getBranchId() != null && !user.getBranchId().isEmpty()) {
                com.whomade.kycarrots.dto.BranchInfoVo branchInfo = opUserService
                        .selectBranchInfo(Long.parseLong(user.getBranchId()));
                if (branchInfo != null && branchInfo.getBranchName() != null) {
                    branchName = branchInfo.getBranchName();
                }
            }
            param.put("branchName", branchName);

            mgtOrderService.requestBranchDeposit(param);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("지점 입금 확인 요청 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "배송 정보(운송장 등) 업데이트", description = "운송장 번호 입력 및 배송 상태를 변경합니다.")
    @PostMapping("/updateShipping")
    public ResponseEntity<?> updateShipping(@RequestParam("token") String token,
            @RequestParam("orderId") String orderId,
            @RequestParam("carrier") String carrier,
            @RequestParam("trackingNo") String trackingNo) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            DataMap param = new DataMap();
            param.put("orderId", orderId);
            param.put("deliveryCompanyCode", carrier);
            param.put("trackingNo", trackingNo);
            param.put("updusrNo", user.getUserNo());

            mgtOrderService.updateOrderShippingInfo(param);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("배송 정보 업데이트 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "주문 상태 변경", description = "주문의 상태 코드를 직접 변경합니다.")
    @PostMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestParam("token") String token,
            @RequestParam("orderId") String orderId,
            @RequestParam("status") String status) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            DataMap param = new DataMap();
            param.put("orderId", orderId);
            param.put("orderStatus", status);
            param.put("updusrNo", user.getUserNo());

            mgtOrderService.updateOrderShippingInfo(param);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("주문 상태 변경 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
