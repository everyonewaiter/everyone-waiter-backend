package com.everyonewaiter.message.application.port.out;

import java.util.List;

import com.everyonewaiter.message.application.domain.model.MessageHistory;

public interface MessageHistoryCreatePort {

	List<MessageHistory> create(List<MessageHistory> messageHistories);
}
