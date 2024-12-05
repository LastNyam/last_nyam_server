package com.lastnyam.lastnyam_server.domain.store.controller;

import com.lastnyam.lastnyam_server.domain.store.dto.request.RegisterStoreRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.response.StoreInfo;
import com.lastnyam.lastnyam_server.domain.store.service.StoreService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    // TODO. 가게 정보 수정

    @GetMapping("/owner/store")
    public ResponseEntity<ResponseBody<StoreInfo>> getMyStoreInfo(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        StoreInfo response = storeService.getMyStoreInfo(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
