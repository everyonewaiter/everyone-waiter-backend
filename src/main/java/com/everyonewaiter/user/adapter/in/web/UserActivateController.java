package com.everyonewaiter.user.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everyonewaiter.security.AuthenticationUser;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.port.in.UserActivateUseCase;
import com.everyonewaiter.user.application.port.in.command.UserActivateCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/activate")
class UserActivateController {

	private final UserActivateUseCase userActivateUseCase;

	@PatchMapping
	ResponseEntity<Void> activate(@AuthenticationUser User user) {
		UserActivateCommand command = new UserActivateCommand(user.getEmail());
		userActivateUseCase.activate(command);
		return ResponseEntity.noContent().build();
	}
}
