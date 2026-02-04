package com.astrocode.api.beneficiary.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeneficiaryJPARepository extends JpaRepository<BeneficiaryJPAEntity, UUID> {
    Optional<BeneficiaryJPAEntity> findByUserId(UUID userId);
}
