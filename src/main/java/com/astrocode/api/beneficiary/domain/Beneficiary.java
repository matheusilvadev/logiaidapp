package com.astrocode.api.beneficiary.domain;


import com.astrocode.api.beneficiary.domain.exception.BeneficiaryException;
import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Beneficiary {

    private final UUID id;
    private final UUID userId;

    private final String displayName;
    private final String document;
    private final PhoneBR phone;
    private final Location address;

    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant activatedAt;

    private Beneficiary(UUID id,
                        UUID userId,
                        String displayName,
                        String document,
                        PhoneBR phone,
                        Location address,
                        Instant createdAt,
                        Instant updatedAt,
                        Instant activatedAt) {

        this.id = Objects.requireNonNull(id, "beneficiaryId is required");
        this.userId = Objects.requireNonNull(userId, "userId is required");

        if (displayName == null || displayName.isBlank()) {
            throw BeneficiaryException.required("DISPLAY_NAME");
        }
        this.displayName = displayName;

        this.document = Objects.requireNonNull(document, "document is required");
        this.phone = Objects.requireNonNull(phone, "phone is required");
        this.address = Objects.requireNonNull(address, "address is required");

        this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt is required");
        this.activatedAt = activatedAt;
    }

    public static Beneficiary create(UUID userId,
                                     String displayName,
                                     String document,
                                     PhoneBR phone,
                                     Location address) {
        Instant now = InstantUtils.now();
        return new Beneficiary(
                UUID.randomUUID(),
                userId,
                displayName,
                document,
                phone,
                address,
                now,
                now,
                null
        );
    }

    public static Beneficiary restore(UUID id,
                                      UUID userId,
                                      String displayName,
                                      String document,
                                      PhoneBR phone,
                                      Location address,
                                      Instant createdAt,
                                      Instant updatedAt,
                                      Instant activatedAt) {
        return new Beneficiary(
                id,
                userId,
                displayName,
                document,
                phone,
                address,
                createdAt,
                updatedAt,
                activatedAt
        );
    }


    public Beneficiary activate() {
        if (this.activatedAt != null) {
            return this;
        }
        Instant now = InstantUtils.now();
        return new Beneficiary(
                this.id,
                this.userId,
                this.displayName,
                this.document,
                this.phone,
                this.address,
                this.createdAt,
                now,
                now
        );
    }

    public Beneficiary changeDisplayName(String newDisplayName) {
        if (newDisplayName == null || newDisplayName.isBlank()) {
            throw BeneficiaryException.required("NEW_DISPLAY_NAME");
        }

        Instant now = InstantUtils.now();
        return new Beneficiary(
                this.id,
                this.userId,
                newDisplayName,
                this.document,
                this.phone,
                this.address,
                this.createdAt,
                now,
                this.activatedAt
        );
    }

    public Beneficiary changePhone(PhoneBR newPhone) {
        if (newPhone == null) {
            throw BeneficiaryException.required("NEW_PHONE");
        }

        Instant now = InstantUtils.now();
        return new Beneficiary(
                this.id,
                this.userId,
                this.displayName,
                this.document,
                newPhone,
                this.address,
                this.createdAt,
                now,
                this.activatedAt
        );
    }

    public Beneficiary changeAddress(Location newAddress) {
        if (newAddress == null) {
            throw BeneficiaryException.required("NEW_ADDRESS");
        }

        Instant now = InstantUtils.now();
        return new Beneficiary(
                this.id,
                this.userId,
                this.displayName,
                this.document,
                this.phone,
                newAddress,
                this.createdAt,
                now,
                this.activatedAt
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDocument() {
        return document;
    }

    public PhoneBR getPhone() {
        return phone;
    }

    public Location getAddress() {
        return address;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getActivatedAt() {
        return activatedAt;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "beneficiaryId=" + id +
                ", userId=" + userId +
                ", displayName='" + displayName + '\'' +
                ", document='" + document + '\'' +
                ", phone=" + phone +
                ", address=" + address +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", activatedAt=" + activatedAt +
                '}';
    }
}
