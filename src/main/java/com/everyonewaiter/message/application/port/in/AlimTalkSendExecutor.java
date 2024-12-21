package com.everyonewaiter.message.application.port.in;

import com.everyonewaiter.message.application.port.in.command.AlimTalkSendDetail;

public interface AlimTalkSendExecutor {

	void sendTo(AlimTalkSendDetail command);
}
