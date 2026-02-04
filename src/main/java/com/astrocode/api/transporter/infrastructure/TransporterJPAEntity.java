package com.astrocode.api.transporter.infrastructure;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "tb_transporters")
public class TransporterJPAEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId; // FK para tb_users.beneficiaryId

    @Column(name = "display_name", nullable = false, length = 150)
    private String displayName;

    @Column(name = "document", nullable = false, unique = true, length = 30)
    private String document;

    @Embedded
    private PhoneBR phone;

    @Embedded
    private Location address;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "activated_at")
    private Instant activatedAt;

    public TransporterJPAEntity(UUID id, UUID userId, String displayName, String document,
                                PhoneBR phone, Location address, Instant createdAt,
                                Instant updatedAt, Instant activatedAt) {
        this.id = id;
        this.userId = userId;
        this.displayName = displayName;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.activatedAt = activatedAt;
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
}
