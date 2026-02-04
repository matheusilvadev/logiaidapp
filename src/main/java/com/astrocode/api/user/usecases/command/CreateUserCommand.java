package com.astrocode.api.user.usecases.command;

import com.astrocode.api.shared.vo.Email;
import com.astrocode.api.shared.vo.Roles;

public record CreateUserCommand(
        String authUserId,
        Email email,
        Roles role
) {
}
