package com.everyonewaiter.device.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.everyonewaiter.device.application.domain.model.Device;
import com.everyonewaiter.device.application.domain.model.DeviceAccessKey;
import com.everyonewaiter.device.application.port.out.DeviceFindPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class DeviceFindPersistenceAdapter implements DeviceFindPort {

	@Override
	public Optional<Device> find(DeviceAccessKey accessKey) {
		return Optional.empty();
	}
}
