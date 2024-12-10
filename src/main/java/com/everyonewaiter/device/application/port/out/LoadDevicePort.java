package com.everyonewaiter.device.application.port.out;

import java.util.Optional;

import com.everyonewaiter.device.application.domain.model.Device;
import com.everyonewaiter.device.application.domain.model.DeviceAccessKey;

public interface LoadDevicePort {

	Optional<Device> loadDevice(DeviceAccessKey accessKey);
}
