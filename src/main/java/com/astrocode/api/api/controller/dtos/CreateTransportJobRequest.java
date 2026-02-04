package com.astrocode.api.api.controller.dtos;

import java.util.UUID;

public record CreateTransportJobRequest(
        UUID donationId,
        UUID transporterId
) {
}
