package com.everyonewaiter.user.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.RawPassword;
import com.everyonewaiter.user.application.port.in.UserSignInUseCase;
import com.everyonewaiter.user.application.port.in.command.UserSignInCommand;
import com.everyonewaiter.user.application.port.in.response.JwtResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/sign-in")
class UserSignInController {

	private final UserSignInUseCase userSignInUseCase;

	@PostMapping
	ResponseEntity<JwtResponse> signIn(@RequestBody UserSignInRequest request) {
		UserSignInCommand command = new UserSignInCommand(new Email(request.email), new RawPassword(request.password));
		JwtResponse response = userSignInUseCase.signIn(command);
		return ResponseEntity.ok(response);
	}

	record UserSignInRequest(String email, String password) {
	}
}
