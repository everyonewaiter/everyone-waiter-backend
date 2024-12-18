package com.everyonewaiter.mail.application.domain.service;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;
import static com.everyonewaiter.common.PreconditionChecker.*;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import com.everyonewaiter.mail.application.port.in.EmailAuthenticationUseCase;
import com.everyonewaiter.mail.application.port.in.EmailSendExecutor;
import com.everyonewaiter.mail.application.port.in.command.EmailAuthenticationCommand;
import com.everyonewaiter.mail.application.port.in.command.EmailSendToCommand;
import com.everyonewaiter.security.ClientOriginRegistry;
import com.everyonewaiter.security.JwtProvider;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserSignUpEvent;
import com.everyonewaiter.user.application.port.out.UserFindPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class EmailAuthenticationService implements EmailAuthenticationUseCase {

	private static final String AUTHENTICATION_URL = "%s/users/activate?email=%s&token=%s";
	private static final Long TEM_MINUTE_MILLISECONDS = 10 * 60 * 1000L;

	private final ClientOriginRegistry clientOriginRegistry;
	private final EmailSendExecutor emailSendExecutor;
	private final ISpringTemplateEngine templateEngine;
	private final JwtProvider jwtProvider;
	private final UserFindPort userFindPort;

	@Override
	public void sendAuthenticationMail(EmailAuthenticationCommand command) {
		Email recipient = command.email();
		User user = userFindPort.findOrElseThrow(recipient);
		check(user.isInactive(), () -> format("already.activated.user", recipient.value(), "Email"));

		String baseUrl = clientOriginRegistry.getBaseUrl();
		String accessToken = jwtProvider.generate(recipient, TEM_MINUTE_MILLISECONDS);

		Context context = new Context();
		context.setVariable("authenticationUrl", AUTHENTICATION_URL.formatted(baseUrl, recipient.value(), accessToken));
		String content = templateEngine.process("email-authentication", context);

		emailSendExecutor.sendTo(new EmailSendToCommand(recipient, "[모두의 웨이터] 이메일 인증 안내드립니다.", content));
	}

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener
	public void handleSignUpUser(UserSignUpEvent event) {
		EmailAuthenticationCommand command = new EmailAuthenticationCommand(event.email());
		sendAuthenticationMail(command);
	}
}
