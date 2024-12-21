package com.everyonewaiter.message.application.port.out;

public interface AlimTalkSender {

	void sendTo(String templateCode, String recipient, String content);
}
