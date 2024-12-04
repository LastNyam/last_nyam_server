package com.lastnyam.lastnyam_server.domain.post.controller;

import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api/owner/food")
@RequiredArgsConstructor
public class OwnerFoodController {


    @PostMapping
    public ResponseEntity<ResponseBody<Void>> uploadFoodPost(@ModelAttribute ) {

        return ResponseEntity.ok(createSuccessResponse());
    }
}
