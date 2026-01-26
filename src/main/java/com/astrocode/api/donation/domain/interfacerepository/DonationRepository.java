package com.astrocode.api.donation.domain.interfacerepository;

import com.astrocode.api.donation.domain.Donation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonationRepository {
    Donation save(Donation donation);
    Optional<Donation> findById(UUID id);
    List<Donation> findAll();
    void deleteById(UUID id);
    List<Donation> findByDonorId(UUID donorId);
}
