package com.everyonewaiter.user.application.domain.model;

import com.github.f4b6a3.tsid.TsidCreator;

public record UserId(Long value) {

	public UserId() {
		this(TsidCreator.getTsid().toLong());
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
