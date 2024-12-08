package com.lastnyam.lastnyam_server.domain.reservation.dto.response;

import com.lastnyam.lastnyam_server.domain.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReservationInfo {
    private Long reservationId;
    private String userNickname;
    private byte[] userImage;
    private ReservationStatus status;
    private String foodName;
    private int number;
    private int price;
    private LocalDateTime reservationDate;
}
