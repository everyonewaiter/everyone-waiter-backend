package com.everyonewaiter.security;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.port.out.UserFindPort;
import com.everyonewaiter.user.application.port.out.UserStateUpdatePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class NativeUserDetailsService implements UserDetailsService {

	private final UserFindPort userFindPort;
	private final UserStateUpdatePort userStateUpdatePort;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Email email = new Email(username);
		User user = userFindPort.find(email).orElseThrow(() -> new UsernameNotFoundException(username));
		user.signIn(LocalDateTime.now());
		userStateUpdatePort.update(user);
		return new NativeUserDetails(user);
	}
}
