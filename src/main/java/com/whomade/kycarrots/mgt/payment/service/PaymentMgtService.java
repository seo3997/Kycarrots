package com.whomade.kycarrots.mgt.payment.service;

import com.whomade.kycarrots.framework.common.object.DataMap;
import java.util.List;

public interface PaymentMgtService {
    DataMap getDashboardStats(DataMap param);

    List<DataMap> getPaymentList(DataMap param);

    DataMap getPaymentDetail(DataMap param);

    DataMap cancelPayment(DataMap param);
}
