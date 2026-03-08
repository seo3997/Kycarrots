package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.framework.common.object.DataMap;
import java.util.List;

public interface ProductReviewService {
    List<DataMap> selectPageListReview(DataMap param) throws Exception;

    int selectTotCntReview(DataMap param) throws Exception;

    DataMap selectReview(DataMap param) throws Exception;

    void insertReview(DataMap param) throws Exception;

    void updateReview(DataMap param) throws Exception;

    void deleteReview(DataMap param) throws Exception;

    void restoreReview(DataMap param) throws Exception;
}
