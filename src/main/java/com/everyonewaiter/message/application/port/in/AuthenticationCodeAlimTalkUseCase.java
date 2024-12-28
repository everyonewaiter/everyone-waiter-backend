package com.everyonewaiter.message.application.port.in;

import com.everyonewaiter.message.application.port.in.command.AuthenticationCodeAlimTalkCommand;

public interface AuthenticationCodeAlimTalkUseCase {

	void sendAuthenticationCodeAlimTalk(AuthenticationCodeAlimTalkCommand command);
}
