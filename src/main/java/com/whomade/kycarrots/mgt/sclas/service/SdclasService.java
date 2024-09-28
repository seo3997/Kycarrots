package com.whomade.kycarrots.mgt.sclas.service;

import com.whomade.kycarrots.framework.common.object.DataMap;

import java.util.List;


public interface SdclasService {
	
	List selectListSdclas(DataMap param) throws Exception;
	
	DataMap selectItemName(DataMap param) throws Exception;

	DataMap selectSdclas(DataMap param) throws Exception;
	
	void deleteSdclas(DataMap param) throws Exception;
	
	void insertSdclas(DataMap param) throws Exception;

	void updateSdclas(DataMap param) throws Exception;
}
