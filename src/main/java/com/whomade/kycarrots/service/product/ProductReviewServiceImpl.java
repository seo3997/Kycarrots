package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

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
            DataMap product = commonMybatisDao.selectOne("mgt.product.selectProduct", param);

            String productName = "상품";
            if (product != null) {
                productName = product.getString("TITLE");
            }
            // [Fix] 수신 대상 지점은 상품의 지점이어야 함 (현재 세션 지점이 아님)
            String branchId = product != null ? product.getString("BRANCH_ID") : param.getString("ss_branch_id");

            List<String> roles = java.util.Arrays.asList("ROLE_ADMIN", "ROLE_SELL", "ROLE_PROJ");
            Long actorUserNo = param.getLong("ss_user_no");

            Map<String, String> data = new java.util.HashMap<>();
            data.put("type", "product");
            data.put("targetId", productId);
            data.put("title", "새로운 상품 리뷰 등록");
            data.put("body", "[" + productName + "] 상품에 새로운 리뷰가 등록되었습니다.");

            pushService.sendTargetPush(
                    actorUserNo,
                    roles,
                    branchId,
                    null,
                    null,
                    "새로운 상품 리뷰 등록",
                    "[" + productName + "] 상품에 새로운 리뷰가 등록되었습니다.",
                    "PRODUCT_REVIEW",
                    data);
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

    @Override
    public void restoreReview(DataMap param) throws Exception {
        commonMybatisDao.update("product.review.restoreReview", param);
    }
}
