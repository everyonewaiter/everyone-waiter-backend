package com.everyonewaiter.authentication.application.domain.service;

import static com.everyonewaiter.common.PreconditionChecker.*;

import org.springframework.stereotype.Service;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.authentication.application.port.in.AuthenticationCodeVerifyUseCase;
import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeVerifyCommand;
import com.everyonewaiter.authentication.application.port.out.AuthenticationCodeFindPort;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthenticationCodeVerifyService implements AuthenticationCodeVerifyUseCase {

	private final AuthenticationCodeFindPort authenticationCodeFindPort;

	@Override
	public void verify(AuthenticationCodeVerifyCommand command) {
		PhoneNumber phoneNumber = command.phoneNumber();
		AuthenticationPurpose purpose = command.purpose();
		Integer code = command.authenticationCode();

		AuthenticationCode target = new AuthenticationCode(phoneNumber, purpose, code);
		AuthenticationCode source = authenticationCodeFindPort.findOrElseThrowAndDelete(target.getKey());
		require(source.equals(target), () -> "require.match.authentication.code");
	}
}
