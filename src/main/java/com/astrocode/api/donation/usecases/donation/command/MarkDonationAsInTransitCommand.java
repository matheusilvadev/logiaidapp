package com.astrocode.api.donation.usecases.donation.command;

import java.util.UUID;

public record MarkDonationAsInTransitCommand(
        UUID donationId,
        UUID transporterId
) {
}
