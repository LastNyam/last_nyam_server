package com.lastnyam.lastnyam_server.domain.reservation.controller;

import com.lastnyam.lastnyam_server.domain.reservation.dto.response.ReservationInfo;
import com.lastnyam.lastnyam_server.domain.reservation.dto.request.ReservationRequest;
import com.lastnyam.lastnyam_server.domain.reservation.service.ReservationService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/user/reservation")
    public ResponseEntity<ResponseBody<Void>> reservationFood(
            @RequestBody ReservationRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        reservationService.reservationFood(request, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/owner/reservation")
    public ResponseEntity<ResponseBody<List<ReservationInfo>>> getReservationList(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<ReservationInfo> response = reservationService.getReservationList(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
