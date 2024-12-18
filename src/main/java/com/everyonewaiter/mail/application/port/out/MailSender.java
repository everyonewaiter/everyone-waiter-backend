package com.everyonewaiter.mail.application.port.out;

public interface MailSender {

	void sendTo(String recipient, String subject, String content);
}
