package com.lastnyam.lastnyam_server.domain.user.controller;

import com.lastnyam.lastnyam_server.domain.user.dto.response.LikeStoreInfo;
import com.lastnyam.lastnyam_server.domain.user.service.UserService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api/user/store")
@RequiredArgsConstructor
public class LikeStoreController {
    private final UserService userService;

    @PostMapping("/like")
    public ResponseEntity<ResponseBody<Void>> likeStore(
            @RequestBody Map<String, Long> request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.likeStore(request.get("storeId"), principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @DeleteMapping("/{storeId}/like")
    public ResponseEntity<ResponseBody<Void>> unlikeStore(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.unlikeStore(storeId, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/like")
    public ResponseEntity<ResponseBody<List<LikeStoreInfo>>> getLikeStores(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<LikeStoreInfo> response = userService.getLikeStores(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
