package com.everyonewaiter.user.application.port.in;

import com.everyonewaiter.user.application.port.in.command.UserSignInCommand;
import com.everyonewaiter.user.application.port.in.response.JwtResponse;

public interface UserSignInUseCase {

	JwtResponse signIn(UserSignInCommand command);
}
