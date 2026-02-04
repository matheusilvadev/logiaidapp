package com.astrocode.api.user.usecases.command;

import com.astrocode.api.shared.vo.Email;

import java.util.UUID;

public record UpdateUserEmailCommand(
        UUID userId,
        Email newEmail
) {
}
