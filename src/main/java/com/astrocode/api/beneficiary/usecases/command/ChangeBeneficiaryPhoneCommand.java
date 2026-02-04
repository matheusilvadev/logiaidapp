package com.astrocode.api.beneficiary.usecases.command;

import com.astrocode.api.shared.vo.PhoneBR;

import java.util.UUID;

public record ChangeBeneficiaryPhoneCommand(
        UUID id,
        PhoneBR newPhone
) {
}
