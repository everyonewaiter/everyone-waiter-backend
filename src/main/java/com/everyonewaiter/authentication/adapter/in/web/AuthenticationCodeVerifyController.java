package com.everyonewaiter.authentication.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.authentication.application.port.in.AuthenticationCodeVerifyUseCase;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeVerifyCommand;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication/code/verify")
class AuthenticationCodeVerifyController {

	private final AuthenticationCodeVerifyUseCase authenticationCodeVerifyUseCase;

	@PostMapping
	ResponseEntity<Void> verify(@RequestBody AuthenticationCodeVerifyRequest request) {
		AuthenticationPurpose purpose = AuthenticationPurpose.valueOf(request.purpose().toUpperCase());
		PhoneNumber phoneNumber = new PhoneNumber(request.phoneNumber());

		AuthenticationCodeVerifyCommand command =
			new AuthenticationCodeVerifyCommand(phoneNumber, purpose, request.code());
		authenticationCodeVerifyUseCase.verify(command);

		return ResponseEntity.noContent().build();
	}

	record AuthenticationCodeVerifyRequest(String phoneNumber, String purpose, Integer code) {
	}
}
