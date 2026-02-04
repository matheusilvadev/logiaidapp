package com.astrocode.api.transporter.usecase.command;

import com.astrocode.api.shared.vo.Location;

import java.util.UUID;

public record ChangeTransporterAddressCommand(
        UUID id,
        Location newAddress
) {
}
