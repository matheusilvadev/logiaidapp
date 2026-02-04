package com.astrocode.api.transportjob.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface TransportJobJpaRepository extends JpaRepository<TransportJobJpaEntity, UUID> {
}
