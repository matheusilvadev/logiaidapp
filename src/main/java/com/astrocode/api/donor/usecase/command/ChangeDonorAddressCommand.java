package com.astrocode.api.donor.usecase.command;

import com.astrocode.api.shared.vo.Location;

import java.util.UUID;

public record ChangeDonorAddressCommand(
        UUID id,
        Location newAddress
) {
}
