package com.astrocode.api.donation.infrastructure.jparepository;


import com.astrocode.api.donation.infrastructure.jpaentities.DonationDemandJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonationDemandJPARepository extends JpaRepository<DonationDemandJPAEntity, UUID> {
    List<DonationDemandJPAEntity> findByBeneficiaryId(UUID beneficiaryId);
}
