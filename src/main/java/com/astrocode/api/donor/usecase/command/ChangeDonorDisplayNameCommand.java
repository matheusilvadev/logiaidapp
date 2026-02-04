package com.astrocode.api.donor.usecase.command;

import java.util.UUID;

public record ChangeDonorDisplayNameCommand(
        UUID id,
        String newDisplayName
) {
}
