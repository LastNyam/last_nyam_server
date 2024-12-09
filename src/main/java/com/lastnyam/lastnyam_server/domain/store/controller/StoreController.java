package com.lastnyam.lastnyam_server.domain.store.controller;

import com.lastnyam.lastnyam_server.domain.store.dto.request.RegisterStoreRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.request.UpdateStoreAddressRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.request.UploadReviewRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.response.ReviewInfo;
import com.lastnyam.lastnyam_server.domain.store.dto.response.MyPageStoreInfo;
import com.lastnyam.lastnyam_server.domain.store.dto.response.StoreInfo;
import com.lastnyam.lastnyam_server.domain.store.dto.response.StorePositionInfo;
import com.lastnyam.lastnyam_server.domain.store.service.StoreService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @PatchMapping("/owner/store/image")
    public ResponseEntity<ResponseBody<Void>> updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        storeService.updateProfileImage(principal.getUserId(), file);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/owner/store")
    public ResponseEntity<ResponseBody<MyPageStoreInfo>> getMyStoreInfo(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        MyPageStoreInfo response = storeService.getMyStoreInfo(principal.getUserId());
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

    @GetMapping("/owner/review")
    public ResponseEntity<ResponseBody<List<ReviewInfo>>> getReviewList(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<ReviewInfo> response = storeService.getReviewList(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping("/user/store")
    public ResponseEntity<ResponseBody<List<StorePositionInfo>>> getStoreList() {
        List<StorePositionInfo> response = storeService.getStoreList();
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping("/user/store/{storeId}")
    public ResponseEntity<ResponseBody<StoreInfo>> getStoreInfo(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        StoreInfo response = storeService.getStoreInfo(storeId, principal);
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
