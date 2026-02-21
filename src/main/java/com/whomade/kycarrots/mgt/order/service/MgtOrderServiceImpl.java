package com.whomade.kycarrots.mgt.order.service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service("mgtOrderService")
public class MgtOrderServiceImpl implements MgtOrderService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Override
    public List<DataMap> selectPageListOrder(ModelMap model, DataMap param) throws Exception {
        int currentPage = Integer.parseInt(param.getString("curPage", "1"));
        int rowCount = Integer.parseInt(param.getString("rowCount", "10"));

        param.put("offset", (currentPage - 1) * rowCount);
        param.put("limit", rowCount);

        List<DataMap> resultList = commonMybatisDao.selectList("mgt.order.selectPageListOrder", param);
        int totalCount = commonMybatisDao.selectOne("mgt.order.selectOrderCount", param);

        model.addAttribute("totalCount", totalCount);

        return resultList;
    }
}
