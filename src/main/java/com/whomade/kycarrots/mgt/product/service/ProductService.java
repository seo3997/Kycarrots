package com.whomade.kycarrots.mgt.product.service;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;

import java.util.List;


public interface ProductService {
	
	List<DataMap> selectPageListProcuct(ModelMap model, DataMap param)throws Exception;
	
	DataMap selectProduct(DataMap param)throws Exception;
	
	void insertProduct(DataMap param , List fileList)throws Exception;
	
	void updateProduct(DataMap param, List fileList)throws Exception;
	
	void deleteProduct(DataMap param)throws Exception;


}
