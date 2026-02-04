package com.astrocode.api.transportjob.usecase.command;

import com.astrocode.api.transportjob.domain.enums.TransportStatus;

import java.util.UUID;

public record ChangeTransportStatusCommand(
        UUID transportJobId,
        UUID transporterId) {
}
