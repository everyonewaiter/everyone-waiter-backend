package com.everyonewaiter.message.application.port.in.command;

import java.util.List;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;
import com.everyonewaiter.user.application.domain.model.Email;

public interface AlimTalkSendDetail {

	String DEFAULT_SENDER_MAIL = "noreply@everyonewaiter.com";

	Email sender();

	String templateCode();

	List<AlimTalkMessage> messages();
}
