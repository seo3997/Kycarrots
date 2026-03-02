package com.whomade.kycarrots.entity.member;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TbAddressBookVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long addressId;
    private String userNo;
    private String addressName;
    private String recipientName;
    private String recipientPhone;
    private String zipCode;
    private String addressMain;
    private String addressDetail;
    private Integer isDefault;
    private String memo;
    private LocalDateTime registDt;
    private LocalDateTime updtDt;
}
