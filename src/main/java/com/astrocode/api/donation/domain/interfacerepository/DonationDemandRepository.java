package com.astrocode.api.donation.domain.interfacerepository;

import com.astrocode.api.donation.domain.DonationDemand;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonationDemandRepository {
    DonationDemand save(DonationDemand donationDemand);
    Optional<DonationDemand> findById(UUID id);
    List<DonationDemand> findAll();
    void deleteById(UUID id);
    List<DonationDemand> findByBeneficiaryId(UUID beneficiaryId);
}
