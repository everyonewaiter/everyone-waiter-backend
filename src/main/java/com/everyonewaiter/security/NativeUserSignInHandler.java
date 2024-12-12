package com.everyonewaiter.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Component;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.RawPassword;
import com.everyonewaiter.user.application.port.out.UserSignInPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class NativeUserSignInHandler implements UserSignInPort {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtProvider jwtProvider;

	@Override
	public String signIn(Email email, RawPassword password) {
		AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email.value(), password.value()));
		return jwtProvider.generate(email);
	}
}
