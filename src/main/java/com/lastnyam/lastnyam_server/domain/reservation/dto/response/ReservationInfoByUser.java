package com.lastnyam.lastnyam_server.domain.reservation.dto.response;

import com.lastnyam.lastnyam_server.domain.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReservationInfoByUser {
    private Long reservationId;
    private Long storeId;
    private String storeName;
    private Long foodId;
    private byte[] foodImage;
    private ReservationStatus status;
    private String foodName;
    private int number;
    private int price;
    private LocalDateTime reservationDate;
}
