package com.astrocode.api.beneficiary.domain.interfacerepository;

import com.astrocode.api.beneficiary.domain.Beneficiary;

import java.util.Optional;
import java.util.UUID;

public interface BeneficiaryRepository {
    Beneficiary save(Beneficiary beneficiary);
    Optional<Beneficiary> findById(UUID id);
    Optional<Beneficiary> findByUserId(UUID userId);
    void deleteById(UUID id);
}
