package com.astrocode.api.user.infrastructure;


import com.astrocode.api.shared.converter.EmailAttributeConverter;
import com.astrocode.api.shared.vo.Email;
import com.astrocode.api.shared.vo.Roles;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class UserJPAEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "auth_user_id", nullable = false, unique = true, length = 255)
    private String authUserId;

    @Convert(converter = EmailAttributeConverter.class)
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private Roles role;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public UserJPAEntity() {
    }

    public UserJPAEntity(UUID id, String authUserId, Email email, Roles role,
                         boolean active, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.authUserId = authUserId;
        this.email = email;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getAuthUserId() {
        return authUserId;
    }

    public Email getEmail() {
        return email;
    }

    public Roles getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
