package com.astrocode.api.beneficiary.usecases.command;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;

import java.util.UUID;

public record CreateBeneficiaryCommand(
        UUID userId,
        String displayName,
        String document,
        PhoneBR phone,
        Location address
) {
}
