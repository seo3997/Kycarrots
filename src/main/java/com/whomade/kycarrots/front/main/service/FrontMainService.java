package com.whomade.kycarrots.front.main.service;

import com.whomade.kycarrots.framework.common.object.DataMap;

import java.util.List;


public interface FrontMainService {
	List<DataMap> selectPlanFList(DataMap param)throws Exception;
	DataMap selectPlanF(DataMap param)throws Exception;
	void insertPlanF(DataMap param)throws Exception;
	void updatePlanF(DataMap param)throws Exception;
	void deletePlanF(DataMap param)throws Exception;
}
