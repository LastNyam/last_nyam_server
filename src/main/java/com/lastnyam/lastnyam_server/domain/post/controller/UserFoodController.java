package com.lastnyam.lastnyam_server.domain.post.controller;

import com.lastnyam.lastnyam_server.domain.post.dto.response.PostDetailInfo;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostInfo;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostInfoWithPosition;
import com.lastnyam.lastnyam_server.domain.post.service.FoodPostService;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api/user/food")
@RequiredArgsConstructor
public class UserFoodController {
    private final FoodPostService foodPostService;

    @GetMapping
    public ResponseEntity<ResponseBody<List<PostInfoWithPosition>>> getPostList() {
        List<PostInfoWithPosition> response = foodPostService.getFoodPostList();
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<ResponseBody<PostDetailInfo>> getPostDetailInfo(@PathVariable Long foodId) {
        PostDetailInfo response = foodPostService.getPostDetailInfo(foodId);
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<ResponseBody<List<PostInfo>>> getPostInfoByStore(@PathVariable Long storeId) {
        List<PostInfo> response = foodPostService.getPostInfoByStore(storeId);
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
