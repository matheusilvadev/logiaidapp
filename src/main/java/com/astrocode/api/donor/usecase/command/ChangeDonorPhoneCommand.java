package com.astrocode.api.donor.usecase.command;

import com.astrocode.api.shared.vo.PhoneBR;

import java.util.UUID;

public record ChangeDonorPhoneCommand(
        UUID id,
        PhoneBR newPhone
) {
}
