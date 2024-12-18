package com.everyonewaiter.user.application.port.in;

import com.everyonewaiter.user.application.port.in.command.UserActivateCommand;

public interface UserActivateUseCase {

	void activate(UserActivateCommand command);
}
