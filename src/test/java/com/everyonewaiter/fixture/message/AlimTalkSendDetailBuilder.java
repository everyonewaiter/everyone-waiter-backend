package com.everyonewaiter.fixture.message;

import java.util.List;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;
import com.everyonewaiter.message.application.port.in.command.AlimTalkSendDetail;

public class AlimTalkSendDetailBuilder {

	private final String content = "CONTENT";
	private final String recipient = "01012345678";
	private final String templateCode = "TEMPLATE_CODE";

	public AlimTalkSendDetail build() {
		return new AlimTalkSendDetail() {

			@Override
			public String templateCode() {
				return templateCode;
			}

			@Override
			public List<AlimTalkMessage> messages() {
				return List.of(new AlimTalkMessage(recipient, content));
			}
		};
	}
}
