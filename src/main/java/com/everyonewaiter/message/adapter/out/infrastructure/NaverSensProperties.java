package com.everyonewaiter.message.adapter.out.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConfigurationProperties(prefix = "naver.sens")
@RequiredArgsConstructor
class NaverSensProperties {

	private static final String ALIM_TALK_SEND_URL = "/alimtalk/v2/services/%s/messages";

	private final String accessKey;
	private final String secretKey;
	private final String serviceId;
	private final String channelId;

	String getAlimTalkSendUrl() {
		return ALIM_TALK_SEND_URL.formatted(serviceId);
	}
}
