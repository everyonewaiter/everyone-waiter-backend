package com.everyonewaiter.message.adapter.out.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.everyonewaiter.message.application.domain.model.AlimTalkMessage;
import com.everyonewaiter.message.application.port.out.AlimTalkSender;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class NaverAlimTalkSender implements AlimTalkSender {

	private final NaverSensClient naverSensClient;
	private final NaverSensProperties naverSensProperties;

	@Override
	public void sendTo(String templateCode, AlimTalkMessage message) {
		String url = naverSensProperties.getAlimTalkSendUrl();
		String channelId = naverSensProperties.getChannelId();
		AlimTalkSendRequest request = new AlimTalkSendRequest(channelId, templateCode, message);

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

		public AlimTalkSendRequest(String channelId, String templateCode, AlimTalkMessage message) {
			this.messages.add(message);
			this.plusFriendId = channelId;
			this.templateCode = templateCode;
		}
	}
}
