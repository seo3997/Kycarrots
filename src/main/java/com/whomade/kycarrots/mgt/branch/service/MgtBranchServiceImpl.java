package com.whomade.kycarrots.mgt.branch.service;

import com.whomade.kycarrots.entity.branch.TbBranchVo;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;

@Slf4j
@Service("mgtBranchService")
public class MgtBranchServiceImpl implements MgtBranchService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Override
    public List<DataMap> selectPageListBranch(ModelMap model, DataMap param) throws Exception {
        int totalCount = commonMybatisDao.selectOne("mgt.branch.selectBranchCount", param);
        param.put("totalCount", totalCount);

        com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil.createNavigationInfo(model, param);

        List<DataMap> resultList = commonMybatisDao.selectList("mgt.branch.selectPageListBranch", param);

        return resultList;
    }

    @Override
    public DataMap selectBranch(DataMap param) throws Exception {
        return commonMybatisDao.selectOne("mgt.branch.selectBranch", param);
    }

    @Override
    @Transactional
    public DataMap registerBranchAndSeller(DataMap param) throws Exception {
        DataMap result = new DataMap();

        try {
            // 1. Register Branch
            TbBranchVo branchVo = new TbBranchVo();
            branchVo.setBranchCode(param.getString("branchCode"));
            branchVo.setBranchName(param.getString("branchName"));
            branchVo.setDomainUrl(param.getString("domainUrl"));
            branchVo.setLogoImageUrl(param.getString("logoImageUrl"));
            branchVo.setCompanyName(param.getString("companyName"));
            branchVo.setRepresentativeName(param.getString("representativeName"));
            branchVo.setBusinessNumber(param.getString("businessNumber"));
            branchVo.setTongsinNumber(param.getString("tongsinNumber"));
            branchVo.setCsPhone(param.getString("csPhone"));
            branchVo.setAddress(param.getString("address"));
            branchVo.setTossClientKey(param.getString("tossClientKey"));
            branchVo.setTossSecretKey(param.getString("tossSecretKey"));
            branchVo.setTossMid(param.getString("tossMid"));
            branchVo.setBillingCycle(param.getString("billingCycle"));
            branchVo.setIsUseCustomPrice("Y".equals(param.getString("isUseCustomPrice")));
            branchVo.setIsActive(true);
            branchVo.setRegisterNo(param.getInt("ss_user_no"));
            branchVo.setUpdusrNo(param.getInt("ss_user_no"));

            commonMybatisDao.insert("mgt.branch.insertBranch", branchVo);
            Long branchId = branchVo.getBranchId();

            // 2. Register Seller Account (ROLE_PROJ)
            OpUserVO userVo = new OpUserVO();
            userVo.setUserId(param.getString("sellerId"));

            // Password Encryption
            String rawPassword = param.getString("sellerPassword");
            String encryptedPassword = EgovFileScrty.encryptSHA512(rawPassword);
            userVo.setPassword(encryptedPassword);

            userVo.setUserNm(param.getString("sellerName"));
            userVo.setEmail(param.getString("sellerEmail"));
            userVo.setMemberCode("ROLE_PROJ");
            userVo.setBranchId(String.valueOf(branchId));
            userVo.setUserSttusCode("10"); // Active
            userVo.setRegisterNo(String.valueOf(param.getInt("ss_user_no")));
            userVo.setUpdusrNo(String.valueOf(param.getInt("ss_user_no")));

            commonMybatisDao.insert("mgt.branch.insertBranchSeller", userVo);

            result.put("success", true);
            result.put("branchId", branchId);
            result.put("message", "지점 및 판매자 등록이 완료되었습니다.");

        } catch (Exception e) {
            log.error("Branch registration failed", e);
            result.put("success", false);
            result.put("message", "등록 중 오류가 발생했습니다: " + e.getMessage());
            throw e; // Transaction rollback
        }

        return result;
    }

    @Override
    public void updateBranch(DataMap param) throws Exception {
        TbBranchVo branchVo = new TbBranchVo();
        branchVo.setBranchId(param.getLong("branchId"));
        branchVo.setBranchCode(param.getString("branchCode"));
        branchVo.setBranchName(param.getString("branchName"));
        branchVo.setDomainUrl(param.getString("domainUrl"));
        branchVo.setLogoImageUrl(param.getString("logoImageUrl"));
        branchVo.setCompanyName(param.getString("companyName"));
        branchVo.setRepresentativeName(param.getString("representativeName"));
        branchVo.setBusinessNumber(param.getString("businessNumber"));
        branchVo.setTongsinNumber(param.getString("tongsinNumber"));
        branchVo.setCsPhone(param.getString("csPhone"));
        branchVo.setAddress(param.getString("address"));
        branchVo.setTossClientKey(param.getString("tossClientKey"));
        branchVo.setTossSecretKey(param.getString("tossSecretKey"));
        branchVo.setTossMid(param.getString("tossMid"));
        branchVo.setBillingCycle(param.getString("billingCycle"));
        branchVo.setIsUseCustomPrice("Y".equals(param.getString("isUseCustomPrice")));
        branchVo.setUpdusrNo(param.getInt("ss_user_no"));

        commonMybatisDao.update("mgt.branch.updateBranch", branchVo);
    }

    @Override
    public void deleteBranch(DataMap param) throws Exception {
        commonMybatisDao.delete("mgt.branch.deleteBranch", param);
    }
}
