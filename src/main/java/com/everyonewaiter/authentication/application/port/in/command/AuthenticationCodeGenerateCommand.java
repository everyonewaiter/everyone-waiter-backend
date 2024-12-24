package com.everyonewaiter.authentication.application.port.in.command;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public record AuthenticationCodeGenerateCommand(PhoneNumber phoneNumber, AuthenticationPurpose purpose) {
}
