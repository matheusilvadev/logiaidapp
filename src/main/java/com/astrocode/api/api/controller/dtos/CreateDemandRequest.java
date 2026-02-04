package com.astrocode.api.api.controller.dtos;

import com.astrocode.api.shared.vo.DonationDemandItem;
import com.astrocode.api.shared.vo.Location;

import java.util.List;
import java.util.UUID;

public record CreateDemandRequest(
        UUID beneficiaryId,
        Location deliveryLocation,
        List<DonationDemandItem> items,
        String notes
) {
}
