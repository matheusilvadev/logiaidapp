package com.astrocode.api.user.domain;

import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.Email;
import com.astrocode.api.shared.vo.Roles;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String authUserId;

    private final Email email;
    private final Roles role;
    private final boolean active;

    private final Instant createdAt;
    private final Instant updatedAt;

    private User(UUID id,
                 String authUserId,
                 Email email,
                 Roles role,
                 boolean active,
                 Instant createdAt,
                 Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "beneficiaryId is required");
        this.authUserId = Objects.requireNonNull(authUserId, "authUserId is required");
        this.email = Objects.requireNonNull(email, "email is required");
        this.role = Objects.requireNonNull(role, "role is required");
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt is required");
    }

    public static User create(String authUserId, Email email, Roles role) {
        Instant now = InstantUtils.now();
        return new User(
                UUID.randomUUID(),
                authUserId,
                email,
                role,
                false,
                now,
                now
        );
    }

    public static User restore(UUID id,
                               String authUserId,
                               Email email,
                               Roles role,
                               boolean active,
                               Instant createdAt,
                               Instant updatedAt) {
        return new User(id, authUserId, email, role, active, createdAt, updatedAt);
    }

    public User activate() {
        if (this.active) {
            return this;
        }
        Instant now = InstantUtils.now();
        return new User(
                this.id,
                this.authUserId,
                this.email,
                this.role,
                true,
                this.createdAt,
                now
        );
    }

    public User changeEmail(Email newEmail) {
        Objects.requireNonNull(newEmail, "newEmail is required");
        Instant now = InstantUtils.now();
        return new User(
                this.id,
                this.authUserId,
                newEmail,
                this.role,
                this.active,
                this.createdAt,
                now
        );
    }

    public User changeRole(Roles newRole) {
        Objects.requireNonNull(newRole, "newRole is required");
        Instant now = InstantUtils.now();
        return new User(
                this.id,
                this.authUserId,
                this.email,
                newRole,
                this.active,
                this.createdAt,
                now
        );
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
