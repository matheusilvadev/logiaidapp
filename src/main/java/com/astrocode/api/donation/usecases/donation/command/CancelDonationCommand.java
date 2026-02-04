package com.astrocode.api.donation.usecases.donation.command;

import java.util.UUID;

public record CancelDonationCommand(
        UUID donationId,
        UUID donorId
) {
}
