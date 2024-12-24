package com.everyonewaiter.authentication.application.port.in;

import com.everyonewaiter.authentication.application.port.in.command.AuthenticationAttemptCountIncrementCommand;

public interface AuthenticationAttemptCountIncrementUseCase {

	void increment(AuthenticationAttemptCountIncrementCommand command);
}
