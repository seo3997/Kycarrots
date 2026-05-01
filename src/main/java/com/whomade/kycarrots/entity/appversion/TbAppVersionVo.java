package com.whomade.kycarrots.entity.appversion;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TbAppVersionVo {
    private Integer versionId;
    private String osType;
    private String latestVersion;
    private String minVersion;
    private String updateMsg;
    private String storeUrl;
    private String useYn;
    private String regusrNo;
    private Timestamp registDt;
    private String updfusrNo;
    private Timestamp updtDt;
}
