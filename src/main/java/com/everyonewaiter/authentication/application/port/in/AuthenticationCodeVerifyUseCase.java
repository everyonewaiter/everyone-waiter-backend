package com.everyonewaiter.authentication.application.port.in;

import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeVerifyCommand;

public interface AuthenticationCodeVerifyUseCase {

	void verify(AuthenticationCodeVerifyCommand command);
}
