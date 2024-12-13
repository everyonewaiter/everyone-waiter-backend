package com.everyonewaiter.user.application.domain.service;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;
import static com.everyonewaiter.common.PreconditionChecker.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everyonewaiter.security.RawPasswordEncoder;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.EncodedPassword;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.port.in.UserSignUpUseCase;
import com.everyonewaiter.user.application.port.in.command.UserSignUpCommand;
import com.everyonewaiter.user.application.port.out.UserCreatePort;
import com.everyonewaiter.user.application.port.out.UserExistsPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UserSignUpService implements UserSignUpUseCase {

	private final RawPasswordEncoder rawPasswordEncoder;
	private final UserCreatePort userCreatePort;
	private final UserExistsPort userExistsPort;

	@Override
	public UserId signUp(UserSignUpCommand command) {
		Email email = command.email();
		PhoneNumber phoneNumber = command.phoneNumber();
		check(!userExistsPort.exists(email), () -> format("already.use.email", List.of(email)));
		check(!userExistsPort.exists(phoneNumber), () -> format("already.use.phone-number", List.of(phoneNumber)));

		EncodedPassword encodedPassword = new EncodedPassword(rawPasswordEncoder.encode(command.password()));
		User user = User.create(email, encodedPassword, phoneNumber);
		user.signUp();
		return userCreatePort.create(user).getId();
	}
}
