package com.astrocode.api.donation.usecases.donation.command;

import com.astrocode.api.shared.vo.Location;

import java.util.UUID;

public record CreateDonationCommand(
        UUID demandId,
        UUID donorId,
        LocationCommand location
) {
    public record LocationCommand(
            String street,
            String number,
            String district,
            String city,
            String state
    ){}

}
