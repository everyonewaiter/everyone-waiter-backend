package com.everyonewaiter.authentication.application.domain.service;

import org.springframework.stereotype.Service;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationCodeGenerator;
import com.everyonewaiter.authentication.application.port.in.AuthenticationCodeGenerateUseCase;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeGenerateCommand;
import com.everyonewaiter.authentication.application.port.out.AuthenticationCodeCreatePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthenticationCodeGenerateService implements AuthenticationCodeGenerateUseCase {

	private static final Long FIVE_MINUTE_MILLISECONDS = 5 * 60 * 1000L;

	private final AuthenticationCodeGenerator authenticationCodeGenerator;
	private final AuthenticationCodeCreatePort authenticationCodeCreatePort;

	@Override
	public void generate(AuthenticationCodeGenerateCommand command) {
		Integer code = authenticationCodeGenerator.generate();
		AuthenticationCode authenticationCode =
			AuthenticationCode.create(command.phoneNumber(), command.purpose(), code, FIVE_MINUTE_MILLISECONDS);
		authenticationCodeCreatePort.create(authenticationCode);
	}
}
