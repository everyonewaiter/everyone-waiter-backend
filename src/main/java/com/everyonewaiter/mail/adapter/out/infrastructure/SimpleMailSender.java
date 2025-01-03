package com.everyonewaiter.mail.adapter.out.infrastructure;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.everyonewaiter.mail.application.port.out.MailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class SimpleMailSender implements MailSender {

	private final JavaMailSender javaMailSender;

	@Override
	public void sendTo(String recipient, String subject, String content) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
			messageHelper.setFrom("모두의 웨이터 <noreply@everyonewaiter.com>");
			messageHelper.setTo(recipient);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException exception) {
			throw new IllegalStateException(exception.getMessage());
		}
	}
}
