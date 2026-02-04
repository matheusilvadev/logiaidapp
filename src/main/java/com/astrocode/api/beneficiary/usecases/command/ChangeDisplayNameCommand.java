package com.astrocode.api.beneficiary.usecases.command;

import java.util.UUID;

public record ChangeDisplayNameCommand(
        UUID id,
        String displayName
) {
}
