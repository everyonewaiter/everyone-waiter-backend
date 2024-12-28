package com.everyonewaiter.message.application.port.out;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;

public interface AlimTalkSender {

	void sendTo(String templateCode, AlimTalkMessage message);
}
