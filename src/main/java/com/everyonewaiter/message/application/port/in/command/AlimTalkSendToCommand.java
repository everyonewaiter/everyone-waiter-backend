package com.everyonewaiter.message.application.port.in.command;

import java.util.List;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;
import com.everyonewaiter.user.application.domain.model.Email;

public record AlimTalkSendToCommand(
	Email sender,
	String templateCode,
	AlimTalkMessage message
) implements AlimTalkSendDetail {

	public AlimTalkSendToCommand(String templateCode, AlimTalkMessage message) {
		this(new Email(DEFAULT_SENDER_MAIL), templateCode, message);
	}

	@Override
	public List<AlimTalkMessage> messages() {
		return List.of(message);
	}
}
