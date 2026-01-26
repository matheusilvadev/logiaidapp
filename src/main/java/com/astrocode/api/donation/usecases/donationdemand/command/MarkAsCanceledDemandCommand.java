package com.astrocode.api.donation.usecases.donationdemand.command;

import java.util.UUID;

public record MarkAsCanceledDemandCommand(UUID demandId) {
}
