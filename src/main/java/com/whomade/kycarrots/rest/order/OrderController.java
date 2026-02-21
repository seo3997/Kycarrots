package com.whomade.kycarrots.rest.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/buyer/{buyerNo}")
    public Page<DataMap> listByBuyer(@PathVariable Long buyerNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        DataMap param = new DataMap();
        param.put("userNo", buyerNo);
        return orderService.selectPageListOrder(param, PageRequest.of(page, size));
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<Map<String, Object>> getOrderDetail(@PathVariable String orderNo) {
        Map<String, Object> res = new HashMap<>();
        OrderVo orderVo = orderService.selectOrderByNo(orderNo);
        if (orderVo == null) {
            return ResponseEntity.notFound().build();
        }

        List<OrderItemVo> items = orderService.selectOrderItemsByOrderId(orderVo.getOrderId());
        res.put("order", orderVo);
        res.put("items", items);
        return ResponseEntity.ok(res);
    }

    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> cancelOrder(@RequestBody Map<String, Object> param) {
        String orderNo = (String) param.get("orderNo");
        String cancelReason = (String) param.get("cancelReason");
        Integer userNo = (Integer) param.get("userNo");

        DataMap result = orderService.cancelOrder(orderNo, cancelReason, userNo);

        if (result.getBoolean("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}
