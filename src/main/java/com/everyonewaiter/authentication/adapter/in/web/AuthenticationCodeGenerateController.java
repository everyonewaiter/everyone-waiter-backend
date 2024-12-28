package com.everyonewaiter.authentication.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.authentication.application.port.in.AuthenticationAttemptCountIncrementUseCase;
import com.everyonewaiter.authentication.application.port.in.AuthenticationCodeGenerateUseCase;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationAttemptCountIncrementCommand;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeGenerateCommand;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication/code")
class AuthenticationCodeGenerateController {

	private final AuthenticationAttemptCountIncrementUseCase authenticationAttemptCountIncrementUseCase;
	private final AuthenticationCodeGenerateUseCase authenticationCodeGenerateUseCase;

	@PostMapping
	ResponseEntity<Void> generate(@RequestBody AuthenticationCodeGenerateRequest request) {
		AuthenticationPurpose purpose = AuthenticationPurpose.valueOf(request.purpose().toUpperCase());
		PhoneNumber phoneNumber = new PhoneNumber(request.phoneNumber());

		AuthenticationAttemptCountIncrementCommand authenticationAttemptCountIncrementCommand =
			new AuthenticationAttemptCountIncrementCommand(phoneNumber, purpose);
		authenticationAttemptCountIncrementUseCase.increment(authenticationAttemptCountIncrementCommand);

		AuthenticationCodeGenerateCommand authenticationCodeGenerateCommand =
			new AuthenticationCodeGenerateCommand(phoneNumber, purpose);
		authenticationCodeGenerateUseCase.generate(authenticationCodeGenerateCommand);

		return ResponseEntity.noContent().build();
	}

	record AuthenticationCodeGenerateRequest(String phoneNumber, String purpose) {
	}
}
