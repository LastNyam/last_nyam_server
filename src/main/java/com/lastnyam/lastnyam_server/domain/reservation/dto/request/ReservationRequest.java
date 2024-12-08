package com.lastnyam.lastnyam_server.domain.reservation.dto.request;

import lombok.Getter;

@Getter
public class ReservationRequest {
    // TODO. 검증..
    private Long foodId;
    private int amount;
}
