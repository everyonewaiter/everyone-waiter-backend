package com.everyonewaiter.authentication.application.domain.model;

import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public record AuthenticationCodeCreateEvent(PhoneNumber phoneNumber, Integer authenticationCode) {
}
