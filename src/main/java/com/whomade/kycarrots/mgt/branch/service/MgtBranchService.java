package com.whomade.kycarrots.mgt.branch.service;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;
import java.util.List;

public interface MgtBranchService {
    List<DataMap> selectPageListBranch(ModelMap model, DataMap param) throws Exception;

    DataMap selectBranch(DataMap param) throws Exception;

    DataMap insertBranch(DataMap param) throws Exception;

    void updateBranch(DataMap param) throws Exception;

    void deleteBranch(DataMap param) throws Exception;

    List<DataMap> selectListBranch(DataMap param) throws Exception;

    String selectNextBranchCode() throws Exception;
}
