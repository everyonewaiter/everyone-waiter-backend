package com.everyonewaiter.message.adapter.out.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.everyonewaiter.message.application.port.out.AlimTalkSender;
import com.everyonewaiter.message.application.port.out.dto.AlimTalkButton;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class NaverAlimTalkSender implements AlimTalkSender {

	private final NaverSensClient naverSensClient;
	private final NaverSensProperties naverSensProperties;

	@Override
	public void sendTo(String templateCode, String recipient, String content) {
		String url = naverSensProperties.getAlimTalkSendUrl();
		String channelId = naverSensProperties.getChannelId();
		AlimTalkSendRequest request = new AlimTalkSendRequest(channelId, templateCode, recipient, content);

		naverSensClient.initialize(HttpMethod.POST, url)
			.post()
			.uri(url)
			.body(request)
			.retrieve()
			.toBodilessEntity();
	}

	@Data
	static class AlimTalkSendRequest {

		private final List<AlimTalkMessage> messages = new ArrayList<>();
		private final String plusFriendId;
		private final String templateCode;

		public AlimTalkSendRequest(String channelId, String templateCode, String recipient, String content) {
			this.messages.add(new AlimTalkMessage(recipient, content));
			this.plusFriendId = channelId;
			this.templateCode = templateCode;
		}
	}

	@Data
	static class AlimTalkMessage {

		private final List<AlimTalkButton> buttons = new ArrayList<>();
		private final String content;
		private final String to;
		private final boolean useSmsFailover;

		public AlimTalkMessage(String recipient, String content) {
			this.content = content;
			this.to = recipient;
			this.useSmsFailover = true;
		}
	}
}
