package com.whomade.kycarrots.mgt.appversion.service;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;
import java.util.List;

public interface MgtAppVersionService {
    List<DataMap> selectPageListAppVersion(ModelMap model, DataMap param) throws Exception;
    DataMap selectAppVersion(DataMap param) throws Exception;
    void insertAppVersion(DataMap param) throws Exception;
    void updateAppVersion(DataMap param) throws Exception;
    void deleteAppVersion(DataMap param) throws Exception;
    DataMap checkVersion(DataMap param) throws Exception;
}
