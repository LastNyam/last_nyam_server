package com.lastnyam.lastnyam_server.domain.sms.controller;

import com.lastnyam.lastnyam_server.domain.sms.dto.request.CheckCodeRequest;
import com.lastnyam.lastnyam_server.domain.sms.dto.request.SendCoolsmsRequest;
import com.lastnyam.lastnyam_server.domain.sms.dto.response.CheckCodeResponse;
import com.lastnyam.lastnyam_server.domain.sms.service.CoolsmsService;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createSuccessResponse;

@Controller
@RequiredArgsConstructor
public class CoolsmsController {
    private final CoolsmsService coolsmsService;

    @PostMapping("/api/user/auth/send-code/phone")
    public ResponseEntity<ResponseBody<Void>> sendsms(@RequestBody SendCoolsmsRequest request) {
        coolsmsService.sendsms(request.getPhoneNumber());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PostMapping("/api/user/auth/check/phone")
    public ResponseEntity<ResponseBody<CheckCodeResponse>> checkCode(@RequestBody CheckCodeRequest request) {
        Boolean isValidCode = coolsmsService.checkCode(request);
        return ResponseEntity.ok(createSuccessResponse(new CheckCodeResponse(isValidCode)));
    }
}
