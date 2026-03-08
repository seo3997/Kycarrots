package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("productReviewService")
public class ProductReviewServiceImpl implements ProductReviewService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Resource
    private com.whomade.kycarrots.push.PushService pushService;

    @Override
    public List<DataMap> selectPageListReview(DataMap param) throws Exception {
        return commonMybatisDao.selectList("product.review.selectPageListReview", param);
    }

    @Override
    public int selectTotCntReview(DataMap param) throws Exception {
        return commonMybatisDao.selectOne("product.review.selectTotCntReview", param);
    }

    @Override
    public DataMap selectReview(DataMap param) throws Exception {
        return commonMybatisDao.selectOne("product.review.selectReview", param);
    }

    @Override
    public void insertReview(DataMap param) throws Exception {
        commonMybatisDao.insert("product.review.insertReview", param);

        // Push Notification to HQ and Branch
        try {
            String productId = param.getString("productId");
            String productName = param.getString("productName");
            if (productName == null || productName.isEmpty()) {
                DataMap product = commonMybatisDao.selectOne("mgt.product.selectProduct", param);
                if (product != null)
                    productName = product.getString("TITLE");
            }
            if (productName == null)
                productName = "상품";

            List<String> roles = java.util.Arrays.asList("ROLE_ADMIN", "ROLE_SELL", "ROLE_PROJ");

            pushService.sendTargetPush(
                    roles,
                    null, // sending to all admins and the specific branch?
                    null,
                    null,
                    "새로운 상품 리뷰 등록",
                    "[" + productName + "] 상품에 새로운 리뷰가 등록되었습니다.",
                    "PRODUCT_REVIEW",
                    java.util.Collections.singletonMap("productId", productId));
        } catch (Exception e) {
            // Ignore push error to not break the main transaction
        }
    }

    @Override
    public void updateReview(DataMap param) throws Exception {
        commonMybatisDao.update("product.review.updateReview", param);
    }

    @Override
    public void deleteReview(DataMap param) throws Exception {
        commonMybatisDao.update("product.review.deleteReview", param);
    }
}
