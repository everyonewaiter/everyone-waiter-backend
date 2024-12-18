package com.everyonewaiter.mail.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everyonewaiter.mail.application.port.in.EmailAuthenticationMailUseCase;
import com.everyonewaiter.mail.application.port.in.command.EmailAuthenticationMailCommand;
import com.everyonewaiter.user.application.domain.model.Email;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail/authentication")
class EmailAuthenticationMailController {

	private final EmailAuthenticationMailUseCase emailAuthenticationMailUseCase;

	@PostMapping
	ResponseEntity<Void> sendEmailAuthenticationMail(@RequestBody EmailAuthenticationMailRequest request) {
		EmailAuthenticationMailCommand command = new EmailAuthenticationMailCommand(new Email(request.email()));
		emailAuthenticationMailUseCase.sendEmailAuthenticationMail(command);
		return ResponseEntity.noContent().build();
	}

	record EmailAuthenticationMailRequest(String email) {
	}
}
