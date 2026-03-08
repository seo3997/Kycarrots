package com.whomade.kycarrots.rest.product;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SysUtil;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;
import com.whomade.kycarrots.service.product.ProductReviewService;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import jakarta.annotation.Resource;
import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/review")
public class ProductReviewRestController {

    @Resource(name = "productReviewService")
    private ProductReviewService productReviewService;

    @Resource(name = "AtFileMngUtil")
    private AtFileMngUtil atFileMngUtil;

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @GetMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        Map<String, Object> result = new HashMap<>();

        int totalCount = productReviewService.selectTotCntReview(param);
        List<DataMap> list = productReviewService.selectPageListReview(param);

        result.put("totalCount", totalCount);
        result.put("list", list);
        result.put("success", true);
        return result;
    }

    @PostMapping("/insert")
    public Map<String, Object> insert(HttpServletRequest request) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        Map<String, Object> result = new HashMap<>();

        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        if (userInfoVo == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }
        param.put("ss_user_no", userInfoVo.getUserNo());
        param.put("userNo", userInfoVo.getUserNo());
        param.put("ss_user_id", userInfoVo.getId());
        param.put("ssAuthorId", userInfoVo.getAuthorId());

        // Handle file upload
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multiRequest.getFiles("reviewFile");
            if (!fileList.isEmpty()) {
                String docId = SysUtil.getDocId();
                param.put("atchDocId", docId);
                for (MultipartFile mfile : fileList) {
                    if (!mfile.isEmpty()) {
                        AtFileVO reAtFile = atFileMngUtil.parseFileInf(mfile, docId, "product/review",
                                param.getString("ss_user_no"));
                        commonMybatisDao.insert("common.file.insertAttchFile", reAtFile);
                    }
                }
            }
        }

        productReviewService.insertReview(param);
        result.put("success", true);
        return result;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(HttpServletRequest request) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        Map<String, Object> result = new HashMap<>();

        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        if (userInfoVo != null) {
            param.put("ss_user_no", userInfoVo.getUserNo());
            param.put("userNo", userInfoVo.getUserNo());
            param.put("ssAuthorId", userInfoVo.getAuthorId());
        }

        productReviewService.deleteReview(param);
        result.put("success", true);
        return result;
    }
}
