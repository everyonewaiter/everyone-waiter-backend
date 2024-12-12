package com.everyonewaiter.user.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everyonewaiter.user.application.port.in.UserSignInUseCase;
import com.everyonewaiter.user.application.port.in.command.UserSignInCommand;
import com.everyonewaiter.user.application.port.in.response.JwtResponse;
import com.everyonewaiter.user.application.port.out.UserSignInPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UserSignInService implements UserSignInUseCase {

	private final UserSignInPort userSignInPort;

	@Override
	public JwtResponse signIn(UserSignInCommand command) {
		String accessToken = userSignInPort.signIn(command.email(), command.password());
		return new JwtResponse(accessToken);
	}
}
