package com.everyonewaiter.message.application.domain.service;

import static com.everyonewaiter.common.PreconditionChecker.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;
import com.everyonewaiter.message.application.domain.model.MessageHistory;
import com.everyonewaiter.message.application.port.in.AlimTalkSendExecutor;
import com.everyonewaiter.message.application.port.in.command.AlimTalkSendDetail;
import com.everyonewaiter.message.application.port.out.AlimTalkSender;
import com.everyonewaiter.message.application.port.out.MessageHistoryCreatePort;
import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class AlimTalkSendExecutorImpl implements AlimTalkSendExecutor {

	private final AlimTalkSender alimTalkSender;
	private final MailProperties mailProperties;
	private final MessageHistoryCreatePort messageHistoryCreatePort;

	@Override
	public void sendTo(AlimTalkSendDetail command) {
		Email sender = new Email(mailProperties.getUsername());
		List<MessageHistory> messageHistories = execute(sender, actionSendTo(), command);
		messageHistoryCreatePort.create(messageHistories);
	}

	private List<MessageHistory> execute(Email sender, Consumer<AlimTalkSendDetail> action, AlimTalkSendDetail detail) {
		String templateCode = detail.templateCode();
		List<AlimTalkMessage> messages = detail.messages();
		List<MessageHistory> messageHistories = new ArrayList<>();

		try {
			action.accept(detail);
			messages.forEach(message -> messageHistories.add(createSuccessHistory(sender, message, templateCode)));
		} catch (Exception exception) {
			messages.forEach(message ->
				messageHistories.add(createFailHistory(sender, message, templateCode, exception.getMessage())));
		}
		return messageHistories;
	}

	private MessageHistory createSuccessHistory(Email sender, AlimTalkMessage message, String templateCode) {
		return MessageHistory.success(sender, new PhoneNumber(message.getTo()), message.getContent(), templateCode);
	}

	private MessageHistory createFailHistory(Email sender, AlimTalkMessage message, String templateCode, String cause) {
		return MessageHistory.fail(sender, new PhoneNumber(message.getTo()), message.getContent(), templateCode, cause);
	}

	private Consumer<AlimTalkSendDetail> actionSendTo() {
		return detail -> {
			require(detail.messages().size() == 1, () -> "알림톡 메시지 본문이 없거나 1개보다 많습니다.");
			alimTalkSender.sendTo(detail.templateCode(), detail.messages().getFirst());
		};
	}
}
