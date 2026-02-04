package com.astrocode.api.transportjob.usecase.command;

import java.util.UUID;

public record CreateTransportJobCommand(
        UUID donationId,
        UUID transporterId
) {
}
