package com.everyonewaiter.device.application.domain.model;

import com.github.f4b6a3.tsid.TsidCreator;

public record DeviceAccessKey(String accessKey) {

	public DeviceAccessKey() {
		this(TsidCreator.getTsid().toString());
	}
}
