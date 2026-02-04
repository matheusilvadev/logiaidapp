package com.astrocode.api.api.controller.dtos;

import com.astrocode.api.shared.vo.Location;


public record ChangeAddressRequest(AddressPayload newAddress) {
    public record AddressPayload(
            String street,
            String number,
            String district,
            String city,
            String state
    ) {
        public Location toDomain() {
            return Location.of(street, number, district, city, state);
        }
    }
}
