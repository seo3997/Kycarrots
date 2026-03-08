package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.framework.common.object.DataMap;
import java.util.List;

public interface ProductQnaService {
    List<DataMap> selectPageListQna(DataMap param) throws Exception;

    int selectTotCntQna(DataMap param) throws Exception;

    DataMap selectQna(DataMap param) throws Exception;

    void insertQna(DataMap param) throws Exception;

    void updateQna(DataMap param) throws Exception;

    void deleteQna(DataMap param) throws Exception;

    void updateQnaAnswer(DataMap param) throws Exception;
}
