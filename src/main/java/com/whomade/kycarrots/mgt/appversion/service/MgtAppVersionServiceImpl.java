package com.whomade.kycarrots.mgt.appversion.service;

import com.whomade.kycarrots.entity.appversion.TbAppVersionVo;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Slf4j
@Service("mgtAppVersionService")
public class MgtAppVersionServiceImpl implements MgtAppVersionService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Override
    public List<DataMap> selectPageListAppVersion(ModelMap model, DataMap param) throws Exception {
        int totalCount = commonMybatisDao.selectOne("mgt.appversion.selectAppVersionCount", param);
        param.put("totalCount", totalCount);

        param.put("pageInputName", "curPage");
        com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil.createNavigationInfo(model, param);

        return commonMybatisDao.selectList("mgt.appversion.selectPageListAppVersion", param);
    }

    @Override
    public DataMap selectAppVersion(DataMap param) throws Exception {
        return commonMybatisDao.selectOne("mgt.appversion.selectAppVersion", param);
    }

    @Override
    public void insertAppVersion(DataMap param) throws Exception {
        TbAppVersionVo vo = new TbAppVersionVo();
        vo.setOsType(param.getString("osType"));
        vo.setLatestVersion(param.getString("latestVersion"));
        vo.setMinVersion(param.getString("minVersion"));
        vo.setUpdateMsg(param.getString("updateMsg"));
        vo.setStoreUrl(param.getString("storeUrl"));
        vo.setUseYn(param.getString("useYn", "Y"));
        vo.setRegusrNo(param.getString("ss_user_no"));
        vo.setUpdfusrNo(param.getString("ss_user_no"));

        commonMybatisDao.insert("mgt.appversion.insertAppVersion", vo);
    }

    @Override
    public void updateAppVersion(DataMap param) throws Exception {
        TbAppVersionVo vo = new TbAppVersionVo();
        vo.setVersionId(param.getInt("versionId"));
        vo.setOsType(param.getString("osType"));
        vo.setLatestVersion(param.getString("latestVersion"));
        vo.setMinVersion(param.getString("minVersion"));
        vo.setUpdateMsg(param.getString("updateMsg"));
        vo.setStoreUrl(param.getString("storeUrl"));
        vo.setUseYn(param.getString("useYn"));
        vo.setUpdfusrNo(param.getString("ss_user_no"));

        commonMybatisDao.update("mgt.appversion.updateAppVersion", vo);
    }

    @Override
    public void deleteAppVersion(DataMap param) throws Exception {
        commonMybatisDao.delete("mgt.appversion.deleteAppVersion", param);
    }

    @Override
    public DataMap checkVersion(DataMap param) throws Exception {
        // Find latest active version for the platform
        return commonMybatisDao.selectOne("mgt.appversion.selectLatestAppVersion", param);
    }
}
