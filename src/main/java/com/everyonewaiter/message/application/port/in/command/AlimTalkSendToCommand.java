package com.everyonewaiter.message.application.port.in.command;

import java.util.List;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;

public record AlimTalkSendToCommand(String templateCode, AlimTalkMessage message) implements AlimTalkSendDetail {

	@Override
	public List<AlimTalkMessage> messages() {
		return List.of(message);
	}
}
