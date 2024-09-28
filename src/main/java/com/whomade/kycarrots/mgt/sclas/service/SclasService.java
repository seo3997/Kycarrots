package com.whomade.kycarrots.mgt.sclas.service;

import com.whomade.kycarrots.framework.common.object.DataMap;

import java.util.List;


public interface SclasService {
	
	List<DataMap> selectListSclas(DataMap param) throws Exception;

	List<DataMap> selectListItem(DataMap param) throws Exception;
	
	DataMap selectOpCodeNm(DataMap param) throws Exception;
	DataMap selectSclas(DataMap param) throws Exception;
	
	void updateSclas(DataMap param) throws Exception;
	void deleteSclas(DataMap param) throws Exception;
	void insertSclas(DataMap param) throws Exception;
}
