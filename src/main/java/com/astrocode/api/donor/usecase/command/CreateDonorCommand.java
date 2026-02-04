package com.astrocode.api.donor.usecase.command;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;

import java.util.UUID;

public record CreateDonorCommand(
        UUID userId,
        String displayName,
        String document,
        PhoneBR phone,
        Location address
) {
}
