package com.astrocode.api.transportjob.infrastructure.repositoryimpl;

import com.astrocode.api.transportjob.domain.TransportJob;
import com.astrocode.api.transportjob.domain.interfacerepository.TransportJobRepository;
import com.astrocode.api.transportjob.infrastructure.TransportJobJpaRepository;
import com.astrocode.api.transportjob.mapper.TransportJobMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TransportJobRepositoryImpl implements TransportJobRepository {

    private final TransportJobJpaRepository jpaRepository;

    public TransportJobRepositoryImpl(TransportJobJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TransportJob save(TransportJob transportJob) {
        var entity = TransportJobMapper.toJpa(transportJob);
        var savedEntity = jpaRepository.save(entity);
        return TransportJobMapper.toModel(savedEntity);
    }

    @Override
    public Optional<TransportJob> getById(UUID id) {
        return jpaRepository.findById(id)
                .map(TransportJobMapper::toModel);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
