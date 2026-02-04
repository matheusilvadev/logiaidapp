package com.astrocode.api.transporter.usecase.command;

import com.astrocode.api.shared.vo.PhoneBR;

import java.util.UUID;

public record ChangeTransporterPhoneCommand(
        UUID id,
        PhoneBR newPhone
) {
}
