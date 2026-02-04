package com.astrocode.api.transporter.infrastructure.repositoryimpl;

import com.astrocode.api.transporter.domain.Transporter;
import com.astrocode.api.transporter.domain.interfacerepository.TransporterRepository;
import com.astrocode.api.transporter.infrastructure.TransporterJpaRepository;
import com.astrocode.api.transporter.mapper.TransporterMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
public class TransporterRepositoryImpl implements TransporterRepository {

    private final TransporterJpaRepository jpaRepository;

    public TransporterRepositoryImpl(TransporterJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Transporter save(Transporter transporter) {
        var entity = TransporterMapper.toJPA(transporter);
        var savedEntity = jpaRepository.save(entity);
        return TransporterMapper.toModel(savedEntity);
    }

    @Override
    public Optional<Transporter> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(TransporterMapper::toModel);
    }

    @Override
    public Optional<Transporter> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(TransporterMapper::toModel);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
