package com.everyonewaiter.user.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.port.in.UserActivateUseCase;
import com.everyonewaiter.user.application.port.in.command.UserActivateCommand;
import com.everyonewaiter.user.application.port.out.UserFindPort;
import com.everyonewaiter.user.application.port.out.UserStateUpdatePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UserActivateService implements UserActivateUseCase {

	private final UserFindPort userFindPort;
	private final UserStateUpdatePort userStateUpdatePort;

	@Override
	public void activate(UserActivateCommand command) {
		User user = userFindPort.findOrElseThrow(command.email());
		user.activate();
		userStateUpdatePort.update(user);
	}
}
