package com.everyonewaiter.user.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.RawPassword;
import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.port.in.UserSignUpUseCase;
import com.everyonewaiter.user.application.port.in.command.UserSignUpCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserSignUpController {

	private final UserSignUpUseCase userSignUpUseCase;

	@PostMapping
	ResponseEntity<Void> signUp(@RequestBody UserSignUpRequest request) {
		UserSignUpCommand command = new UserSignUpCommand(
			new Email(request.email),
			new RawPassword(request.password),
			new PhoneNumber(request.phoneNumber)
		);
		UserId userId = userSignUpUseCase.signUp(command);
		return ResponseEntity.created(URI.create(userId.toString())).build();
	}

	record UserSignUpRequest(String email, String password, String phoneNumber) {
	}
}
