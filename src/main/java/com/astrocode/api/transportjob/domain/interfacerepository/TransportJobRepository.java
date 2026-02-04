package com.astrocode.api.transportjob.domain.interfacerepository;

import com.astrocode.api.transportjob.domain.TransportJob;

import java.util.Optional;
import java.util.UUID;

public interface TransportJobRepository {
    TransportJob save(TransportJob transportJob);
    Optional<TransportJob> getById(UUID id);
    void deleteById(UUID id);
}
