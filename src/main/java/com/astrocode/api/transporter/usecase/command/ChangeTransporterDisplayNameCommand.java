package com.astrocode.api.transporter.usecase.command;

import java.util.UUID;

public record ChangeTransporterDisplayNameCommand(
        UUID id,
        String newDisplayName
) {
}
