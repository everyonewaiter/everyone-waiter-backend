package com.everyonewaiter.message.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;
import com.everyonewaiter.message.application.port.in.AlimTalkSendExecutor;
import com.everyonewaiter.message.application.port.in.AuthenticationCodeAlimTalkUseCase;
import com.everyonewaiter.message.application.port.in.command.AlimTalkSendToCommand;
import com.everyonewaiter.message.application.port.in.command.AuthenticationCodeAlimTalkCommand;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class AuthenticationCodeAlimTalkService implements AuthenticationCodeAlimTalkUseCase {

	private final AlimTalkSendExecutor alimTalkSendExecutor;

	@Override
	public void sendAuthenticationCodeAlimTalk(AuthenticationCodeAlimTalkCommand command) {
		PhoneNumber recipient = command.phoneNumber();
		String content = """
			[모두의 웨이터]
			\s
			인증번호는 [%s]입니다.
			""".formatted(command.authenticationCode()).trim();

		AlimTalkMessage message = new AlimTalkMessage(recipient.value(), content);
		alimTalkSendExecutor.sendTo(new AlimTalkSendToCommand("authenticationCode", message));
	}
}
