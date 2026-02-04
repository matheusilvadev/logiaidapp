package com.astrocode.api.transportjob.domain;

import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.transportjob.domain.enums.TransportStatus;
import com.astrocode.api.transportjob.domain.exception.TransportJobException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class TransportJob {

    private final UUID id;
    private final UUID donationId;
    private final UUID transporterId;

    private final TransportStatus status;

    private final Instant assignedAt;
    private final Instant pickedUpAt;
    private final Instant deliveredAt;
    private final Instant updatedAt;

    private TransportJob(UUID id, UUID donationId, UUID transporterId, TransportStatus status,
                         Instant assignedAt, Instant pickedUpAt, Instant deliveredAt, Instant updatedAt) {
        this.id = id;
        this.donationId = donationId;
        this.transporterId = transporterId;
        this.status = status;
        this.assignedAt = assignedAt;
        this.pickedUpAt = pickedUpAt;
        this.deliveredAt = deliveredAt;
        this.updatedAt = updatedAt;
    }

    public static TransportJob assign(UUID donationId, UUID transporterId){
        var now = InstantUtils.now();
        return new TransportJob(
                UUID.randomUUID(),
                donationId,
                transporterId,
                TransportStatus.ASSIGNED,
                now,
                null,
                null,
                null
        );
    }

    public static TransportJob  restore(UUID id, UUID donationId, UUID transporterId, TransportStatus status,
                                        Instant assignedAt, Instant pickedUpAt, Instant deliveredAt, Instant updatedAt){

        return new TransportJob(id, donationId, transporterId, status, assignedAt, pickedUpAt, deliveredAt, updatedAt);
    }

    public TransportJob markAsPickedUp(){
        if (this.status != TransportStatus.ASSIGNED){
            throw TransportJobException
                    .invalidStatusTransition("ASSIGNED", "PICKED_UP");
        }

        var now = InstantUtils.now();

        return new TransportJob(
                this.id,
                this.donationId,
                this.transporterId,
                TransportStatus.PICKED_UP,
                this.assignedAt,
                now,
                null,
                now
        );
    }

    public TransportJob markAsInTransit(){
        if (this.status != TransportStatus.PICKED_UP) {
            throw TransportJobException
                    .invalidStatusTransition("PICKED_UP", "IN_TRANSIT");
        }

        var now = InstantUtils.now();

        return new TransportJob(
                this.id,
                this.donationId,
                this.transporterId,
                TransportStatus.IN_TRANSIT,
                this.assignedAt,
                this.pickedUpAt,
                null,
                now
        );
    }

    public TransportJob markAsDelivered(){
        if (this.status != TransportStatus.IN_TRANSIT) {
            throw TransportJobException
                    .invalidStatusTransition("IN_TRANSIT", "DELIVERED");
        }

        var now = InstantUtils.now();

        return new TransportJob(
                this.id,
                this.donationId,
                this.transporterId,
                TransportStatus.DELIVERED,
                this.assignedAt,
                this.pickedUpAt,
                now,
                now
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getDonationId() {
        return donationId;
    }

    public UUID getTransporterId() {
        return transporterId;
    }

    public TransportStatus getStatus() {
        return status;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public Instant getPickedUpAt() {
        return pickedUpAt;
    }

    public Instant getDeliveredAt() {
        return deliveredAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransportJob that = (TransportJob) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransportJob{" +
                "beneficiaryId=" + id +
                ", donationId=" + donationId +
                ", transporterId=" + transporterId +
                ", status=" + status +
                ", assignedAt=" + assignedAt +
                ", pickedUpAt=" + pickedUpAt +
                ", deliveredAt=" + deliveredAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
