package com.everyonewaiter.message.application.domain.model;

import com.github.f4b6a3.tsid.TsidCreator;

public record MessageHistoryId(Long value) {

	public MessageHistoryId() {
		this(TsidCreator.getTsid().toLong());
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
