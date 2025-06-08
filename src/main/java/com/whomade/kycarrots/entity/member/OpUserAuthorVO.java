package com.whomade.kycarrots.entity.member;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OpUserAuthorVO {
    private Long userNo;
    private String authorId;
    private Integer registerNo;
    private LocalDateTime registDt;
    private Integer updusrNo;
    private LocalDateTime updtDt;
}
