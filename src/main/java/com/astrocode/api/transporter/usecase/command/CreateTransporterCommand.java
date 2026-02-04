package com.astrocode.api.transporter.usecase.command;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;

import java.util.UUID;

public record CreateTransporterCommand(
        UUID userId,
        String displayName,
        String document,
        PhoneBR phone,
        Location address
) {
}
