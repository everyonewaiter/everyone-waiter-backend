package com.everyonewaiter.message.application.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AlimTalkMessage {

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
