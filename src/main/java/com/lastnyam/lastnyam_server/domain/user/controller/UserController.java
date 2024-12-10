package com.lastnyam.lastnyam_server.domain.user.controller;

import com.lastnyam.lastnyam_server.domain.user.dto.response.UserInfo;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.domain.user.dto.request.UpdateNicknameRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.request.LoginRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.request.SignupRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.response.CheckNicknameResponse;
import com.lastnyam.lastnyam_server.domain.user.dto.response.LoginResponse;
import com.lastnyam.lastnyam_server.domain.user.service.UserService;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseBody<Void>> signup(@RequestBody @Valid SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<ResponseBody<CheckNicknameResponse>> checkNickname(@RequestParam String nickname) {
        CheckNicknameResponse response = userService.checkNickname(nickname);
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<ResponseBody<Void>> updateNickname(
            @RequestBody UpdateNicknameRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.updateNickname(principal.getUserId(), request);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<ResponseBody<Void>> updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.updateProfileImage(principal.getUserId(), file);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/my-info")
    public ResponseEntity<ResponseBody<UserInfo>> getMyInformation(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        UserInfo response = userService.getMyInformation(principal.getUserId());
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @PostMapping("/fcm/register")
    public ResponseEntity<ResponseBody<Void>> uploadFCM(
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.uploadFCM(principal.getUserId(), request.get("token"));
        return ResponseEntity.ok(createSuccessResponse());
    }
}
