package com.lastnyam.lastnyam_server.domain.post.controller;

import com.lastnyam.lastnyam_server.domain.post.dto.request.UploadFoodRequest;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostInfo;
import com.lastnyam.lastnyam_server.domain.post.service.OwnerFoodService;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api/owner/food")
@RequiredArgsConstructor
public class OwnerFoodController {
    private final OwnerFoodService ownerFoodService;

    @PostMapping
    public ResponseEntity<ResponseBody<Void>> uploadFoodPost(
            @ModelAttribute UploadFoodRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        ownerFoodService.uploadFoodPost(request, principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<PostInfo>>> getMyFoodPost(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<PostInfo> response = ownerFoodService.getMyFoodPost(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }

}
