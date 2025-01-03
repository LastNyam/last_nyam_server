package com.lastnyam.lastnyam_server.domain.reservation.controller;

import com.lastnyam.lastnyam_server.domain.reservation.domain.ReservationStatus;
import com.lastnyam.lastnyam_server.domain.reservation.dto.response.ReservationInfoByOwner;
import com.lastnyam.lastnyam_server.domain.reservation.dto.request.ReservationRequest;
import com.lastnyam.lastnyam_server.domain.reservation.dto.response.ReservationInfoByUser;
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

    @GetMapping("/user/reservation")
    public ResponseEntity<ResponseBody<List<ReservationInfoByUser>>> getReservationListByUser(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<ReservationInfoByUser> response = reservationService.getReservationListByUser(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping("/owner/reservation")
    public ResponseEntity<ResponseBody<List<ReservationInfoByOwner>>> getReservationListByOwner(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<ReservationInfoByOwner> response = reservationService.getReservationListByOwner(principal.getUserId());
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

    @PostMapping("/user/reservation/{reservationId}/cancel")
    public ResponseEntity<ResponseBody<Void>> reservationCancelByUser(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        reservationService.reservationCancelByUser(reservationId, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PatchMapping("/owner/reservation/{reservationId}/status")
    public ResponseEntity<ResponseBody<Void>> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestBody @Valid Map<String, ReservationStatus> request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        reservationService.updateReservationStatus(reservationId, request.get("status"), principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }
}
