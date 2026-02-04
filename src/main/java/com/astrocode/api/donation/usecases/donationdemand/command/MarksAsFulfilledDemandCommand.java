package com.astrocode.api.donation.usecases.donationdemand.command;

import java.util.UUID;

public record MarksAsFulfilledDemandCommand(UUID demandId,
                                            UUID beneficiaryId) {
}
