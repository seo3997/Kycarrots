package com.whomade.kycarrots.mgt.payment.service;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.service.payment.PaymentService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("paymentMgtService")
@RequiredArgsConstructor
public class PaymentMgtServiceImpl implements PaymentMgtService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    private final PaymentService paymentService;

    @Override
    public DataMap getDashboardStats(DataMap param) {
        return commonMybatisDao.selectOne("mgt.payment.selectPaymentStats", param);
    }

    @Override
    public List<DataMap> getPaymentList(DataMap param) {
        return commonMybatisDao.selectList("mgt.payment.selectRecentPaymentList", param);
    }

    @Override
    public DataMap getPaymentDetail(DataMap param) {
        DataMap detail = commonMybatisDao.selectOne("mgt.payment.selectPaymentDetail", param);
        if (detail != null) {
            Long orderId = detail.getLong("ORDER_ID");
            List<OrderItemVo> items = commonMybatisDao.selectList("mgt.payment.selectOrderItemsByOrderId", orderId);
            detail.put("orderItems", items);
        }
        return detail;
    }

    @Override
    @Transactional
    public DataMap cancelPayment(DataMap param) {
        String orderNo = param.getString("merchantUid");
        String cancelReason = param.getString("cancelReason", "관리자 취소");

        // 관리자 번호가 없을 수 있으므로 시스템 관리자 번호(보통 1)를 기본값으로 사용
        return paymentService.cancelPayment(orderNo, cancelReason, 1);
    }
}
