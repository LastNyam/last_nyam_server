package com.lastnyam.lastnyam_server.domain.sms.controller;

import com.lastnyam.lastnyam_server.domain.sms.dto.FcmNotificationRequest;
import com.lastnyam.lastnyam_server.domain.sms.service.FcmService;
import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import com.lastnyam.lastnyam_server.global.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    @Autowired
    private FcmService fcmService;

    @PostMapping("/send")
    public ResponseEntity<ResponseBody<Void>> sendNotification(@RequestBody FcmNotificationRequest request) {
        fcmService.sendNotification(request.getTargetToken(), request.getTitle(), request.getBody());
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}
