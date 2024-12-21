package com.everyonewaiter.message.application.port.in.command;

import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public record AuthenticationCodeAlimTalkCommand(PhoneNumber phoneNumber, Integer authenticationCode) {
}
