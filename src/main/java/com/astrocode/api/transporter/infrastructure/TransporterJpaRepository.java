package com.astrocode.api.transporter.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransporterJpaRepository extends JpaRepository<TransporterJPAEntity, UUID> {
    Optional<TransporterJPAEntity> findByUserId(UUID userId);
}
