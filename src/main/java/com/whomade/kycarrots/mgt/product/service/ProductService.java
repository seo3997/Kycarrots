package com.whomade.kycarrots.mgt.product.service;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

	List<DataMap> selectPageListProcuct(ModelMap model, DataMap param) throws Exception;

	List<DataMap> selectListProduct(DataMap param) throws Exception;

	DataMap selectProduct(DataMap param) throws Exception;

	void insertProduct(DataMap param, List<MultipartFile> fileList, List<TnProductImageVo> metas) throws Exception;

	void updateProduct(DataMap param, List<MultipartFile> files, List<TnProductImageVo> metas) throws Exception;

	void deleteProduct(DataMap param) throws Exception;

	void updateProductStatus(DataMap param) throws Exception;

	DataMap uploadSummernoteImage(DataMap param, MultipartFile file) throws Exception;

	void deleteSummernoteImage(DataMap param) throws Exception;

}
