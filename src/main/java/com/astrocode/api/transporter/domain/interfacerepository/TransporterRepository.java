package com.astrocode.api.transporter.domain.interfacerepository;

import com.astrocode.api.transporter.domain.Transporter;

import java.util.Optional;
import java.util.UUID;

public interface TransporterRepository {
    Transporter save(Transporter transporter);
    Optional<Transporter> findById(UUID id);
    Optional<Transporter> findByUserId(UUID userId);
    void deleteById(UUID id);
}
