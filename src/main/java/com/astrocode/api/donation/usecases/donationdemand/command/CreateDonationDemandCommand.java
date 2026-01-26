package com.astrocode.api.donation.usecases.donationdemand.command;

import com.astrocode.api.shared.vo.DonationDemandItem;
import com.astrocode.api.shared.vo.Location;

import java.util.List;
import java.util.UUID;

public record CreateDonationDemandCommand(
        UUID beneficiaryId,
        Location deliveryLocation,
        List<DonationDemandItem> items,
        String notes
) {
}
