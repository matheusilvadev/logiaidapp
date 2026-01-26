package com.astrocode.api.donation.domain;

import com.astrocode.api.donation.domain.enums.DemandStatus;
import com.astrocode.api.donation.domain.exceptions.DonationDemandException;
import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.DonationDemandItem;
import com.astrocode.api.shared.vo.Location;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DonationDemand {

    private final UUID id;
    private final UUID beneficiaryId;
    private final UUID donorId;
    private final DemandStatus demandStatus;
    private final Location deliveryLocation;
    private final String notes;
    private final List<DonationDemandItem> items;


    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant acceptedAt;
    private final Instant fulfilledAt;
    private final Instant canceledAt;

    private DonationDemand(UUID id,
                           UUID beneficiaryId, UUID donorId,
                           DemandStatus demandStatus,
                           Location deliveryLocation,
                           String notes,
                           List<DonationDemandItem> items,
                           Instant createdAt,
                           Instant updatedAt,
                           Instant acceptedAt,
                           Instant fulfilledAt,
                           Instant canceledAt){

        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.beneficiaryId = Objects.requireNonNull(beneficiaryId, "beneficiaryId cannot be null");
        this.donorId = donorId;
        this.demandStatus = Objects.requireNonNull(demandStatus, "status cannot be null");
        this.deliveryLocation = Objects.requireNonNull(deliveryLocation, "deliveryLocation cannot be null");
        this.notes = notes;
        this.items = List.copyOf(Objects.requireNonNull(items, "items cannot be null"));
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt cannot be null");
        this.acceptedAt = acceptedAt;
        this.fulfilledAt = fulfilledAt;
        this.canceledAt = canceledAt;
    }

    public static DonationDemand create(UUID beneficiaryId,
                                        Location deliveryLocation,
                                        List<DonationDemandItem> items,
                                        String notes) {

        if (items == null || items.isEmpty()) {
            throw DonationDemandException.required();
        }

        return new DonationDemand(
                UUID.randomUUID(),
                beneficiaryId,
                null,
                DemandStatus.OPEN,
                deliveryLocation,
                notes,
                items,
                InstantUtils.now(),
                InstantUtils.now(),
                null,
                null,
                null
        );
    }

    public static DonationDemand restore(UUID id,
                                         UUID beneficiaryId,
                                         UUID donorId,
                                         DemandStatus demandStatus,
                                         Location deliveryLocation,
                                         String notes,
                                         List<DonationDemandItem> items,
                                         Instant createdAt,
                                         Instant updatedAt,
                                         Instant acceptedAt,
                                         Instant fulfilledAt,
                                         Instant canceledAt){
        return new DonationDemand(id, beneficiaryId, donorId, demandStatus, deliveryLocation, notes, items, createdAt,
                updatedAt, acceptedAt, fulfilledAt, canceledAt);
    }

    public DonationDemand accept(UUID donorId) {
        if (demandStatus != DemandStatus.OPEN) {
            throw DonationDemandException.invalidStatusTransition("OPEN", "ACCEPTED");
        }

        Objects.requireNonNull(donorId, "DonorId is required to accept a demand");

        if (this.beneficiaryId.equals(donorId)) {
            throw new RuntimeException("Beneficiary cannot be the donor");
        }

        return new DonationDemand(
                this.id,
                this.beneficiaryId,
                donorId,
                DemandStatus.ACCEPTED,
                this.deliveryLocation,
                this.notes,
                this.items,
                this.createdAt,
                InstantUtils.now(),
                InstantUtils.now(),
                this.fulfilledAt,
                this.canceledAt
        );
    }

    public DonationDemand markFulfilled() {
        if (demandStatus != DemandStatus.ACCEPTED) {
            throw DonationDemandException.invalidStatusTransition("ACCEPTED", "FULFILLED");
        }

        return new DonationDemand(
                this.id,
                this.beneficiaryId,
                this.donorId,
                DemandStatus.FULFILLED,
                this.deliveryLocation,
                this.notes,
                this.items,
                this.createdAt,
                InstantUtils.now(),
                this.acceptedAt,
                InstantUtils.now(),
                this.canceledAt
        );
    }

    public DonationDemand cancel() {
        if (demandStatus == DemandStatus.FULFILLED) {
            throw DonationDemandException.invalidCancellation();
        }
        if (demandStatus == DemandStatus.CANCELED) {
            return this;
        }

        return new DonationDemand(
                this.id,
                this.beneficiaryId,
                this.donorId,
                DemandStatus.CANCELED,
                this.deliveryLocation,
                this.notes,
                this.items,
                this.createdAt,
                InstantUtils.now(),
                this.acceptedAt,
                this.fulfilledAt,
                InstantUtils.now()
        );


    }

    public UUID getId() {
        return id;
    }

    public UUID getBeneficiaryId() {
        return beneficiaryId;
    }

    public UUID getDonorId() {
        return donorId;
    }

    public DemandStatus getDemandStatus() {
        return demandStatus;
    }

    public Location getDeliveryLocation() {
        return deliveryLocation;
    }

    public String getNotes() {
        return notes;
    }

    public List<DonationDemandItem> getItems() {
        return items;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getAcceptedAt() {
        return acceptedAt;
    }

    public Instant getFulfilledAt() {
        return fulfilledAt;
    }

    public Instant getCanceledAt() {
        return canceledAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DonationDemand that = (DonationDemand) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DonationDemand{" +
                "id=" + id +
                ", beneficiaryId=" + beneficiaryId +
                ", donorId=" + donorId +
                ", demandStatus=" + demandStatus +
                ", deliveryLocation=" + deliveryLocation +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", acceptedAt=" + acceptedAt +
                ", fulfilledAt=" + fulfilledAt +
                ", canceledAt=" + canceledAt +
                '}';
    }
}
