package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service("productQnaService")
public class ProductQnaServiceImpl implements ProductQnaService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Resource
    private com.whomade.kycarrots.push.PushService pushService;

    @Override
    public List<DataMap> selectPageListQna(DataMap param) throws Exception {
        return commonMybatisDao.selectList("product.qna.selectPageListQna", param);
    }

    @Override
    public int selectTotCntQna(DataMap param) throws Exception {
        return commonMybatisDao.selectOne("product.qna.selectTotCntQna", param);
    }

    @Override
    public DataMap selectQna(DataMap param) throws Exception {
        return commonMybatisDao.selectOne("product.qna.selectQna", param);
    }

    @Override
    public void insertQna(DataMap param) throws Exception {
        commonMybatisDao.insert("product.qna.insertQna", param);

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
            data.put("title", "새로운 상품 문의 등록");
            data.put("body", "[" + productName + "] 상품에 새로운 문의가 등록되었습니다.");

            pushService.sendTargetPush(
                    actorUserNo,
                    roles,
                    branchId,
                    null,
                    null,
                    "새로운 상품 문의 등록",
                    "[" + productName + "] 상품에 새로운 문의가 등록되었습니다.",
                    "PRODUCT_QNA",
                    data);
        } catch (Exception e) {
        }
    }

    @Override
    public void updateQna(DataMap param) throws Exception {
        commonMybatisDao.update("product.qna.updateQna", param);
    }

    @Override
    public void deleteQna(DataMap param) throws Exception {
        commonMybatisDao.update("product.qna.deleteQna", param);
    }

    @Override
    public void updateQnaAnswer(DataMap param) throws Exception {
        commonMybatisDao.update("product.qna.updateQnaAnswer", param);

        // Push Notification to Buyer
        try {
            DataMap qna = commonMybatisDao.selectOne("product.qna.selectQna", param);
            if (qna != null) {
                Long userNo = qna.getLong("USER_NO");
                String productId = qna.getString("PRODUCT_ID");

                Map<String, String> data = new java.util.HashMap<>();
                data.put("type", "product");
                data.put("targetId", productId);
                data.put("title", "상품 문의 답변 완료");
                data.put("body", "문의하신 내용에 대한 답변이 등록되었습니다.");

                pushService.sendTargetPush(
                        param.getLong("ss_user_no"),
                        null,
                        null,
                        null,
                        userNo,
                        "상품 문의 답변 완료",
                        "문의하신 내용에 대한 답변이 등록되었습니다.",
                        "PRODUCT_QNA_ANSWER",
                        data);
            }
        } catch (Exception e) {
        }
    }

}
