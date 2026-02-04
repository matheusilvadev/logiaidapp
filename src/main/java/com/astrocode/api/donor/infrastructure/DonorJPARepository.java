package com.astrocode.api.donor.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonorJPARepository extends JpaRepository<DonorJPAEntity, UUID> {
    Optional<DonorJPAEntity> findByUserId(UUID userId);
}
