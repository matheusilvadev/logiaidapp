package com.astrocode.api.shared.vo;

import com.astrocode.api.donation.domain.enums.DonationItemCategory;
import com.astrocode.api.shared.exception.VOException;

import java.util.Objects;

public class DonationDemandItem {

    private final DonationItemCategory category;
    private final String description;
    private final int quantity;
    private final String unit;

    private DonationDemandItem(
            DonationItemCategory category,
            String description,
            int quantity,
            String unit
    ) {
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
    }

    public static DonationDemandItem of(
            DonationItemCategory category,
            String description,
            int quantity,
            String unit
    ) {
        if (category == null) {
            throw VOException.required("DonationDemandItem", "category");
        }

        if (description == null || description.isBlank()) {
            throw VOException.required("DonationDemandItem", "description");
        }

        if (quantity <= 0) {
            throw VOException.invalidValue(
                    "DonationDemandItem",
                    "quantity",
                    "Quantity must be greater than zero"
            );
        }

        if (unit == null || unit.isBlank()) {
            throw VOException.required("DonationDemandItem", "unit");
        }

        if (category == DonationItemCategory.OTHER && description.length() < 5) {
            throw VOException.invalidValue(
                    "DonationDemandItem",
                    "description",
                    "Description for OTHER category must be more descriptive"
            );
        }

        return new DonationDemandItem(
                category,
                description.trim(),
                quantity,
                unit.trim().toUpperCase()
        );
    }

    public DonationItemCategory category() {
        return category;
    }

    public String description() {
        return description;
    }

    public int quantity() {
        return quantity;
    }

    public String unit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DonationDemandItem other)) return false;
        return quantity == other.quantity
                && category == other.category
                && description.equals(other.description)
                && unit.equals(other.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, description, quantity, unit);
    }

    @Override
    public String toString() {
        return "%d %s de %s (%s)".formatted(quantity, unit, description, category);
    }
}
