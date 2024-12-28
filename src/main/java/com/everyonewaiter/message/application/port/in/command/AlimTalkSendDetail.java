package com.everyonewaiter.message.application.port.in.command;

import java.util.List;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;

public interface AlimTalkSendDetail {

	String templateCode();

	List<AlimTalkMessage> messages();
}
