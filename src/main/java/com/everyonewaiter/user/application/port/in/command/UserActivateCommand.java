package com.everyonewaiter.user.application.port.in.command;

import com.everyonewaiter.user.application.domain.model.Email;

public record UserActivateCommand(Email email) {
}
