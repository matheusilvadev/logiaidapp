package com.astrocode.api.api.controller.dtos;

import java.util.UUID;

public record CreateDonationRequest(
        UUID demandId,
        String street,
        String number,
        String district,
        String city,
        String state
) {
}
