package com.astrocode.api.donation.infrastructure.jpaentities;


import com.astrocode.api.shared.vo.DonationDemandItem;
import com.astrocode.api.donation.domain.enums.DemandStatus;
import com.astrocode.api.shared.converter.DonationDemandItemsAttributeConverter;
import com.astrocode.api.shared.converter.LocationAttributeConverter;
import com.astrocode.api.shared.vo.Location;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_donation_demands")
public class DonationDemandJPAEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "beneficiary_id", nullable = false)
    private UUID beneficiaryId;

    @Column(name = "donor_id")
    private UUID donorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "demand_status", nullable = false, length = 30)
    private DemandStatus demandStatus;

    @Convert(converter = LocationAttributeConverter.class)
    @Column(name = "delivery_location", nullable = false)
    private Location deliveryLocation;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Convert(converter = DonationDemandItemsAttributeConverter.class)
    @Column(name = "items", columnDefinition = "jsonb")
    private List<DonationDemandItem> items;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "accepted_at")
    private Instant acceptedAt;

    @Column(name = "fulfilled_at")
    private Instant fulfilledAt;

    @Column(name = "canceled_at")
    private Instant canceledAt;

    public DonationDemandJPAEntity(){}

    public DonationDemandJPAEntity(UUID id, UUID beneficiaryId, UUID donorId, DemandStatus demandStatus, Location deliveryLocation,
                                   String notes, List<DonationDemandItem> items, Instant createdAt, Instant updatedAt,
                                   Instant acceptedAt, Instant fulfilledAt, Instant canceledAt) {
        this.id = id;
        this.beneficiaryId = beneficiaryId;
        this.donorId = donorId;
        this.demandStatus = demandStatus;
        this.deliveryLocation = deliveryLocation;
        this.notes = notes;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.acceptedAt = acceptedAt;
        this.fulfilledAt = fulfilledAt;
        this.canceledAt = canceledAt;
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
        DonationDemandJPAEntity that = (DonationDemandJPAEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DonationDemandJPAEntity{" +
                "beneficiaryId=" + id +
                ", beneficiaryId=" + beneficiaryId +
                ", donorId=" + donorId +
                ", demandStatus=" + demandStatus +
                ", deliveryLocation=" + deliveryLocation +
                ", notes='" + notes + '\'' +
                ", items=" + items +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", acceptedAt=" + acceptedAt +
                ", fulfilledAt=" + fulfilledAt +
                ", canceledAt=" + canceledAt +
                '}';
    }
}
