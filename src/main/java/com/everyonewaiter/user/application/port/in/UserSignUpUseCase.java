package com.everyonewaiter.user.application.port.in;

import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.port.in.command.UserSignUpCommand;

public interface UserSignUpUseCase {

	UserId signUp(UserSignUpCommand command);
}
