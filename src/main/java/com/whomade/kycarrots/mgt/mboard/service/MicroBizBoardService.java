package com.whomade.kycarrots.mgt.mboard.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;


public interface MicroBizBoardService {
	
	List<DataMap> selectPageListBoard(ModelMap model, DataMap param)throws Exception;
	
	DataMap selectBoard(DataMap param)throws Exception;
	
	void insertBoard(DataMap param , List fileList)throws Exception;
	
	void updateBoard(DataMap param, List fileList)throws Exception;
	
	void deleteBoard(DataMap param)throws Exception;

	List<DataMap> selectListBoard(DataMap param)throws Exception;
	
}
