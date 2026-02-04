package com.astrocode.api.donor.domain.interfacerepository;


import com.astrocode.api.donor.domain.Donor;

import java.util.Optional;
import java.util.UUID;

public interface DonorRepository {
    Donor save(Donor donor);
    Optional<Donor> findById(UUID id);
    Optional<Donor> findByUserId(UUID userId);
    void deleteById(UUID id);
}
