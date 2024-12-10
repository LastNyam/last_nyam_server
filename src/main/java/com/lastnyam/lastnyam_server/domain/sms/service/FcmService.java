package com.lastnyam.lastnyam_server.domain.sms.service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FcmService {

    @Value("${fcm.api-url}")
    private String fcmApiUrl;

    @Value("${fcm.server-key}")
    private String serverKey;

    public void sendNotification(String targetToken, String title, String body) {
        // HTTP 요청 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + serverKey);
        headers.set("Content-Type", "application/json");

        // FCM 메시지 생성
        JsonObject notification = new JsonObject();
        notification.addProperty("title", title);
        notification.addProperty("body", body);

        JsonObject message = new JsonObject();
        message.add("notification", notification);
        message.addProperty("to", targetToken);

        // 요청 객체 생성
        HttpEntity<String> request = new HttpEntity<>(message.toString(), headers);

        // FCM 서버로 요청 전송
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(fcmApiUrl, request, String.class);
            System.out.println("FCM Response: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error sending FCM notification: " + e.getMessage());
        }
    }
}
