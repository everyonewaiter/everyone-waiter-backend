package com.everyonewaiter.mail.application.domain.model;

import com.github.f4b6a3.tsid.TsidCreator;

public record MailHistoryId(Long value) {

	public MailHistoryId() {
		this(TsidCreator.getTsid().toLong());
	}
}
