package com.everyonewaiter.mail.application.port.in;

import com.everyonewaiter.mail.application.port.in.command.EmailSendDetail;

public interface EmailSendExecutor {

	void sendTo(EmailSendDetail command);
}
