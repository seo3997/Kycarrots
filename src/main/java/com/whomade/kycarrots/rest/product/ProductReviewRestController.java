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
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product/review")
public class ProductReviewRestController {

    @Resource(name = "productReviewService")
    private ProductReviewService productReviewService;

    @Resource(name = "AtFileMngUtil")
    private AtFileMngUtil atFileMngUtil;

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Autowired
    private EncodedTokenizer tokenizer;

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

        UserInfoVo userInfoVo = getUserInfo(request);
        if (userInfoVo == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }
        param.put("ss_user_no", userInfoVo.getUserNo());
        param.put("userNo", userInfoVo.getUserNo());
        param.put("ss_user_id", userInfoVo.getId());
        param.put("ssAuthorId", userInfoVo.getAuthorId());
        param.put("ss_branch_id", userInfoVo.getBranchId());

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

    @PostMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        Map<String, Object> result = new HashMap<>();

        UserInfoVo userInfoVo = getUserInfo(request);
        if (userInfoVo == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }
        param.put("ss_user_no", userInfoVo.getUserNo());
        param.put("userNo", userInfoVo.getUserNo());
        param.put("ss_user_id", userInfoVo.getId());
        param.put("ssAuthorId", userInfoVo.getAuthorId());
        param.put("ss_branch_id", userInfoVo.getBranchId());

        // Handle file upload
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multiRequest.getFiles("reviewFile");
            if (!fileList.isEmpty()) {
                DataMap existingReview = productReviewService.selectReview(param);
                String docId = (existingReview != null) ? existingReview.getString("ATCH_DOC_ID") : null;

                if (docId == null || docId.isEmpty()) {
                    docId = SysUtil.getDocId();
                }

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

        productReviewService.updateReview(param);
        result.put("success", true);
        return result;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(HttpServletRequest request) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        Map<String, Object> result = new HashMap<>();

        UserInfoVo userInfoVo = getUserInfo(request);
        if (userInfoVo != null) {
            param.put("ss_user_no", userInfoVo.getUserNo());
            param.put("userNo", userInfoVo.getUserNo());
            param.put("ssAuthorId", userInfoVo.getAuthorId());
        }

        productReviewService.deleteReview(param);
        result.put("success", true);
        return result;
    }

    @PostMapping("/restore")
    public Map<String, Object> restore(HttpServletRequest request) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        Map<String, Object> result = new HashMap<>();

        UserInfoVo userInfoVo = getUserInfo(request);
        if (userInfoVo != null) {
            param.put("ss_user_no", userInfoVo.getUserNo());
            param.put("userNo", userInfoVo.getUserNo());
            param.put("ssAuthorId", userInfoVo.getAuthorId());
        }

        productReviewService.restoreReview(param);
        result.put("success", true);
        return result;
    }

    private UserInfoVo getUserInfo(HttpServletRequest request) {
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        if (userInfoVo == null) {
            String token = request.getParameter("token");
            if (token != null && !token.isEmpty()) {
                OpUserVO opUserVO = tokenizer.getMember(token);
                if (opUserVO != null) {
                    userInfoVo = new UserInfoVo();
                    userInfoVo.setUserNo(opUserVO.getUserNo());
                    userInfoVo.setId(opUserVO.getUserId());
                    userInfoVo.setUserNm(opUserVO.getUserNm());
                    userInfoVo.setAuthorId(opUserVO.getMemberCode());
                    userInfoVo.setBranchId(opUserVO.getBranchId());
                }
            }
        }
        return userInfoVo;
    }
}
