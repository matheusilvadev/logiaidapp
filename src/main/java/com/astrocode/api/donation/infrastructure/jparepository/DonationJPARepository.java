package com.astrocode.api.donation.infrastructure.jparepository;

import com.astrocode.api.donation.infrastructure.jpaentities.DonationJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonationJPARepository extends JpaRepository<DonationJPAEntity, UUID> {
    List<DonationJPAEntity> findByDonorId(UUID donorId);
}
