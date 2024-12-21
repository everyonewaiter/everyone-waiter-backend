package com.everyonewaiter.fixture.message;

import java.util.ArrayList;
import java.util.List;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;
import com.everyonewaiter.message.application.port.in.command.AlimTalkSendDetail;

public class AlimTalkSendDetailBuilder {

	private final List<AlimTalkMessage> messages = new ArrayList<>();
	private final String templateCode = "TEMPLATE_CODE";

	public AlimTalkSendDetailBuilder() {
		messages.add(new AlimTalkMessage("01012345678", "CONTENT"));
	}

	public AlimTalkSendDetailBuilder addMessage(String recipient, String content) {
		messages.add(new AlimTalkMessage(recipient, content));
		return this;
	}

	public AlimTalkSendDetail build() {
		return new AlimTalkSendDetail() {

			@Override
			public String templateCode() {
				return templateCode;
			}

			@Override
			public List<AlimTalkMessage> messages() {
				return messages;
			}
		};
	}
}
