package com.astrocode.api.user.infrastructure;


import com.astrocode.api.shared.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJPARepository extends JpaRepository<UserJPAEntity, UUID> {
    Optional<UserJPAEntity> findByAuthUserId(String authUserId);
    Optional<UserJPAEntity> findByEmail(Email email);
    boolean existsByAuthUserId(String authUserId);
    boolean existsByEmail(Email email);
}
