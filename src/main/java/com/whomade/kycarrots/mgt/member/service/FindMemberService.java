package com.whomade.kycarrots.mgt.member.service;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;


public interface FindMemberService {
	DataMap selectId(ModelMap model, DataMap param)throws Exception;
	DataMap selectPw(ModelMap model,DataMap param)throws Exception;
	void updatePw(ModelMap model,DataMap param)throws Exception;
	public void searchUserPassword(DataMap param)throws Exception;
}
