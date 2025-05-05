package com.whomade.kycarrots.entity.common;

import lombok.Data;
import java.io.Serializable;

@Data
public class TxtListDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String strIdx = "";
    private String strMsg = "";
}
