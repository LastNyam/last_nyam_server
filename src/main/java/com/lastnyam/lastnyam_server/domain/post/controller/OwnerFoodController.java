package com.lastnyam.lastnyam_server.domain.post.controller;

import com.lastnyam.lastnyam_server.domain.post.dto.request.UpdatePostStatusRequest;
import com.lastnyam.lastnyam_server.domain.post.dto.request.UploadFoodRequest;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostDetailInfo;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostInfo;
import com.lastnyam.lastnyam_server.domain.post.service.FoodPostService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api/owner/food")
@RequiredArgsConstructor
public class OwnerFoodController {
    private final FoodPostService foodPostService;

    @PostMapping
    public ResponseEntity<ResponseBody<Void>> uploadFoodPost(
            @ModelAttribute UploadFoodRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        foodPostService.uploadFoodPost(request, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<PostInfo>>> getMyFoodPost(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<PostInfo> response = foodPostService.getMyFoodPost(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<ResponseBody<PostDetailInfo>> getPostDetailInfo(@PathVariable Long foodId) {
        PostDetailInfo response = foodPostService.getPostDetailInfo(foodId);
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @PatchMapping("/status")
    public ResponseEntity<ResponseBody<Void>> updatePostStatus(
            @RequestBody UpdatePostStatusRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        foodPostService.updatePostStatus(request, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }
}
