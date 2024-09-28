package com.whomade.kycarrots.common.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

public interface EditorService {
	
	void insertEditorFile(DataMap param, List fileList) throws Exception;
}
