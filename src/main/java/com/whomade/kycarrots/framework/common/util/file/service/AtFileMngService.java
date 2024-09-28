package com.whomade.kycarrots.framework.common.util.file.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;

public interface AtFileMngService {

	/**
     * 파일에 대한 목록을 조회한다.
     * 
     * @param fvo : AtFileVO
     * @return
     * @throws Exception
     */
    public List<AtFileVO> selectFileInfs(AtFileVO fvo) throws Exception;

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fvo
     * @throws Exception
     */
    public String insertFileInf(AtFileVO fvo) throws Exception;

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fvoList
     * @throws Exception
     */
    public String insertFileInfs(List fvoList) throws Exception;

    /**
     * 하나의 파일을 삭제한다.
     * 
     * @param fvo
     * @throws Exception
     */
    public void deleteFileInf(DataMap fvo) throws Exception;
    
    /**
     * 여러 개의 파일을 삭제한다.
     * 
     * @param fvoList
     * @throws Exception
     */
    public void deleteFileInfs(DataMap param) throws Exception;

    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public AtFileVO selectFileInf(AtFileVO fvo) throws Exception;
}
