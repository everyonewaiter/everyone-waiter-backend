package com.everyonewaiter.user.application.port.in.command;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;
import com.everyonewaiter.user.application.domain.model.RawPassword;

public record UserSignUpCommand(Email email, RawPassword password, PhoneNumber phoneNumber) {
}