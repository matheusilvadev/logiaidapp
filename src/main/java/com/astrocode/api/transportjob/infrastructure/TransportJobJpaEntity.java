package com.astrocode.api.transportjob.infrastructure;


import com.astrocode.api.transportjob.domain.enums.TransportStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table
public class TransportJobJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(name = "donation_id", nullable = false)
    private UUID donationId;

    @Column(name = "transporter_id", nullable = false)
    private UUID transporterId;

    @Column(name = "status", nullable = false)
    private TransportStatus status;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @Column(name = "picked_up__at")
    private Instant pickedUpAt;

    @Column(name = "delivered_at")
    private Instant deliveredAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public TransportJobJpaEntity() {
    }

    public TransportJobJpaEntity(UUID id, UUID donationId, UUID transporterId, TransportStatus status,
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
}
