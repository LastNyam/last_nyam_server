package com.lastnyam.lastnyam_server.domain.owner.controller;

import com.lastnyam.lastnyam_server.domain.owner.service.OwnerService;
import com.lastnyam.lastnyam_server.domain.owner.dto.request.LoginRequest;
import com.lastnyam.lastnyam_server.domain.owner.dto.request.SignupRequest;
import com.lastnyam.lastnyam_server.domain.owner.dto.response.LoginResponse;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;


@Controller
@RequestMapping("/api/owner/auth")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseBody<Void>> signup(@RequestBody @Valid SignupRequest request) {
        ownerService.signup(request);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = ownerService.login(request);
        return ResponseEntity.ok(createSuccessResponse(response));
    }

}
