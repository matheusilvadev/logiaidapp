package com.astrocode.api.beneficiary.usecases.command;

import com.astrocode.api.shared.vo.Location;

import java.util.UUID;

public record ChangeBeneficiaryAddressCommand(
        UUID beneficiaryId,
        Location newAddress) {
}
