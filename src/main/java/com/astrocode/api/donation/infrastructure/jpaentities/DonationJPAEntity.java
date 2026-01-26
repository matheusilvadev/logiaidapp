package com.astrocode.api.donation.infrastructure.jpaentities;

import com.astrocode.api.donation.domain.enums.DonationStatus;
import com.astrocode.api.shared.converter.LocationAttributeConverter;
import com.astrocode.api.shared.vo.Location;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "donations")
@Table(name = "tb_donations")
public class DonationJPAEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id",nullable = false, updatable = false)
    private UUID id;

    @Column(name = "demand_id")
    private UUID demandId;

    @Column(name = "donor_id")
    private UUID donorId;

    @Column(name = "transporter_id")
    private UUID transporterId;

    @Convert(converter = LocationAttributeConverter.class)
    @Column(name = "pickup_location", nullable = false)
    private Location pickupLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private DonationStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "delivered_at")
    private Instant deliveredAt;

    @Column(name = "canceled_at")
    private Instant canceledAt;

    public DonationJPAEntity(){}

    public DonationJPAEntity(UUID id, UUID demandId, UUID donorId, UUID transporterId, Location pickupLocation,
                             DonationStatus status, Instant createdAt, Instant updatedAt,
                             Instant deliveredAt, Instant canceledAt) {
        this.id = id;
        this.demandId = demandId;
        this.donorId = donorId;
        this.transporterId = transporterId;
        this.pickupLocation = pickupLocation;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deliveredAt = deliveredAt;
        this.canceledAt = canceledAt;
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
        DonationJPAEntity that = (DonationJPAEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DonationJPAEntity{" +
                "id=" + id +
                ", demandId=" + demandId +
                ", donorId=" + donorId +
                ", transporterId=" + transporterId +
                ", pickupLocation=" + pickupLocation +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deliveredAt=" + deliveredAt +
                ", canceledAt=" + canceledAt +
                '}';
    }
}
