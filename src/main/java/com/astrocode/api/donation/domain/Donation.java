package com.astrocode.api.donation.domain;

import com.astrocode.api.donation.domain.enums.DonationStatus;
import com.astrocode.api.donation.domain.exceptions.DonationException;
import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.Location;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Donation {

    private final UUID id;
    private final UUID demandId;
    private final UUID donorId;
    private final UUID transporterId;
    private final Location pickupLocation;
    private final DonationStatus status;

    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant deliveredAt;
    private final Instant canceledAt;

    private Donation(UUID id,
                     UUID demandId,
                     UUID donorId,
                     UUID transporterId,
                     Location pickupLocation,
                     DonationStatus status,
                     Instant createdAt,
                     Instant updatedAt,
                     Instant deliveredAt,
                     Instant canceledAt) {

        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.demandId = Objects.requireNonNull(demandId, "demandId cannot be null");
        this.donorId = Objects.requireNonNull(donorId, "donorId cannot be null");
        this.transporterId = transporterId;
        this.pickupLocation = Objects.requireNonNull(pickupLocation, "pickupLocation cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt cannot be null");
        this.deliveredAt = deliveredAt;
        this.canceledAt = canceledAt;
    }

    public static Donation create(UUID demandId,
                                  UUID donorId,
                                  Location pickupLocation) {

        return new Donation(
                UUID.randomUUID(),
                demandId,
                donorId,
                null,
                pickupLocation,
                DonationStatus.PENDING_PICKUP,
                InstantUtils.now(),
                InstantUtils.now(),
                null,
                null
        );
    }

    public static Donation restore(UUID id,
                                   UUID demandId,
                                   UUID donorId,
                                   UUID transporterId,
                                   Location pickupLocation,
                                   DonationStatus status,
                                   Instant createdAt,
                                   Instant updatedAt,
                                   Instant deliveredAt,
                                   Instant canceledAt){
        return new Donation(id, demandId, donorId,transporterId, pickupLocation, status, createdAt, updatedAt, deliveredAt, canceledAt);
    }

    public Donation markInTransit(UUID transporterId) {
        if (status != DonationStatus.PENDING_PICKUP) {
            throw DonationException.invalidStatusTransition("PENDING_PICKUP", "IN_TRANSIT");
        }

        return new Donation(
                this.id,
                this.demandId,
                this.donorId,
                transporterId,
                this.pickupLocation,
                DonationStatus.IN_TRANSIT,
                this.createdAt,
                InstantUtils.now(),
                this.deliveredAt,
                this.canceledAt
        );
    }

    public Donation markDelivered() {
        if (status != DonationStatus.IN_TRANSIT && status != DonationStatus.PENDING_PICKUP) {
            throw DonationException.cannotBeDelivered();
        }
        return new Donation(
                this.id,
                this.demandId,
                this.donorId,
                this.transporterId,
                this.pickupLocation,
                DonationStatus.DELIVERED,
                this.createdAt,
                InstantUtils.now(),
                InstantUtils.now(),
                this.canceledAt
        );
    }

    public Donation cancel() {
        if (status == DonationStatus.DELIVERED) {
            throw DonationException.impossibleCancellation("DELIVERED");
        }
        if (status == DonationStatus.CANCELED) {
            return this;
        }

        return new Donation(
                this.id,
                this.demandId,
                this.donorId,
                this.transporterId,
                this.pickupLocation,
                DonationStatus.CANCELED,
                this.createdAt,
                InstantUtils.now(),
                this.deliveredAt,
                InstantUtils.now()
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getDemandId() {
        return demandId;
    }

    public UUID getDonorId() {
        return donorId;
    }

    public UUID getTransporterId() {
        return transporterId;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeliveredAt() {
        return deliveredAt;
    }

    public Instant getCanceledAt() {
        return canceledAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return Objects.equals(id, donation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Donation{" +
                "demandId=" + demandId +
                ", donorId=" + donorId +
                ", trasnporterId=" + transporterId +
                ", pickupLocation=" + pickupLocation +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deliveredAt=" + deliveredAt +
                ", canceledAt=" + canceledAt +
                ", id=" + id +
                '}';
    }
}
