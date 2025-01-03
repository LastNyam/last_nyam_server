package com.lastnyam.lastnyam_server.domain.sms.service;

import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.sms.dto.request.CheckCodeRequest;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoolsmsService {
	private final UserRepository userRepository;
	private final OwnerRepository ownerRepository;

	@Value("${coolsms.api.key}")
	private String apiKey;

	@Value("${coolsms.api.secret}")
	private String apiSecret;

	@Value("${coolsms.api.number}")
	private String fromPhoneNumber;

	private Map<String, String> codeStorage = new ConcurrentHashMap<>();

	public void sendsmsByUser(String to) {
		log.info("send sms request(user) phoneNumber: {}", to);
		userRepository.findByPhoneNumber(to)
				.ifPresent(it -> {
					throw new ServiceException(ExceptionCode.DUPLICATED_PHONE_NUMBER);
				});

		this.sandsmd(parsePhoneNumber(to));
	}

	public void sendsmsByOwner(String to) {
		log.info("send sms request(owner) phoneNumber: {}", to);
		ownerRepository.findByPhoneNumber(to)
				.ifPresent(it -> {
					throw new ServiceException(ExceptionCode.DUPLICATED_PHONE_NUMBER);
				});

		this.sandsmd(parsePhoneNumber(to));
	}

	private void sandsmd(String toPhoneNumber) {
		String numStr = generateRandomNumber();

		codeStorage.put(toPhoneNumber, numStr);
		// 사용자가 한번 하고 또 보냈다면? 동시성 문제 이슈로 일단 제거
//		Executors.newSingleThreadScheduledExecutor().schedule(() -> codeStorage.remove(toPhoneNumber), 5, TimeUnit.MINUTES);

		try {
			Message coolsms = new Message(apiKey, apiSecret);

			HashMap<String, String> params = new HashMap<>();
			params.put("to", toPhoneNumber);
			params.put("from", fromPhoneNumber);
			params.put("type", "sms");
			params.put("text", "[lastnyam] 본인확인 인증번호는 [" + numStr + "] 입니다.");

			coolsms.send(params);
		} catch (Exception e) {
			log.error("send fail: {}", e.getMessage());
			throw new ServiceException(ExceptionCode.SEND_MESSAGE_FAILED);
		}
	}

	private String parsePhoneNumber(String to) {
		return to.replace("-", "");
	}

	private String generateRandomNumber() {
		Random rand = new Random();
		StringBuilder numStr = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			numStr.append(rand.nextInt(10));
		}
		return numStr.toString();
	}

	public Boolean checkCode(CheckCodeRequest request) {
		String code = codeStorage.get(parsePhoneNumber(request.getPhoneNumber()));
		if (code == null) {
			throw new ServiceException(ExceptionCode.CODE_EXPIRY);
		}
		return request.getVerification().equals(code);
	}
}