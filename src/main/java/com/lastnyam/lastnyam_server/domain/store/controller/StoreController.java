package com.lastnyam.lastnyam_server.domain.store.controller;

import com.lastnyam.lastnyam_server.domain.store.dto.request.RegisterStoreRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.request.UpdateStoreAddressRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.request.UploadReviewRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.response.StoreInfo;
import com.lastnyam.lastnyam_server.domain.store.service.StoreService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/owner/store")
    public ResponseEntity<ResponseBody<Void>> registerStore(
            @ModelAttribute RegisterStoreRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        storeService.registerStore(request, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PatchMapping("/owner/store/name")
    public ResponseEntity<ResponseBody<Void>> updateStoreName(
            @RequestBody @Valid Map<String, String> request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        storeService.updateStoreName(request.get("storeName"), principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PatchMapping("/owner/store/address")
    public ResponseEntity<ResponseBody<Void>> updateStoreAddress(
            @RequestBody @Valid UpdateStoreAddressRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        storeService.updateStoreAddress(request, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PatchMapping("/owner/store/call-number")
    public ResponseEntity<ResponseBody<Void>> updateStoreContactNumber(
            @RequestBody @Valid Map<String, String> request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        storeService.updateStoreContactNumber(request.get("callNumber"), principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/owner/store")
    public ResponseEntity<ResponseBody<StoreInfo>> getMyStoreInfo(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        StoreInfo response = storeService.getMyStoreInfo(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @PostMapping("/user/review")
    public ResponseEntity<ResponseBody<Void>> uploadReview(
            @RequestBody @Valid UploadReviewRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        storeService.uploadReview(request, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }
}
