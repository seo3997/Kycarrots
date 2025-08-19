package com.whomade.kycarrots.dto.advertise;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InterestRequest {
    private Long userNo;
    private Long productId;
    private Integer actorNo; // REGISTER_NO/UPDUSR_NO로 활용(선택)
}
