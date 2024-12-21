package com.everyonewaiter.message.application.port.out;

import java.util.List;

import com.everyonewaiter.message.application.domain.model.MessageHistory;

public interface MessageHistoryCreatePort {

	void create(List<MessageHistory> messageHistories);
}
