package com.lastnyam.lastnyam_server.domain.reservation.controller;

import com.lastnyam.lastnyam_server.domain.reservation.dto.response.ReservationInfo;
import com.lastnyam.lastnyam_server.domain.reservation.dto.request.ReservationRequest;
import com.lastnyam.lastnyam_server.domain.reservation.service.ReservationService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/owner/reservation/{reservationId}/ok")
    public ResponseEntity<ResponseBody<Void>> reservationAccept(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        reservationService.reservationAccept(reservationId, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PostMapping("/owner/reservation/{reservationId}/cancel")
    public ResponseEntity<ResponseBody<Void>> reservationCancel(
            @PathVariable Long reservationId,
            @RequestBody @Valid Map<String, String> request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        reservationService.reservationCancel(reservationId, request.get("cancelMessage"), principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }
}
