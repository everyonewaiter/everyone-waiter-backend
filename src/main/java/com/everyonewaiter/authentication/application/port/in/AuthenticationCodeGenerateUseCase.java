package com.everyonewaiter.authentication.application.port.in;

import com.everyonewaiter.authentication.application.port.in.command.AuthenticationCodeGenerateCommand;

public interface AuthenticationCodeGenerateUseCase {

	void generate(AuthenticationCodeGenerateCommand command);
}
