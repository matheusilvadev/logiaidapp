package com.astrocode.api.donor.infrastructure.repositoryimpl;

import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.infrastructure.DonorJPARepository;
import com.astrocode.api.donor.mapper.DonorMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public class DonorRepositoryImpl implements DonorRepository {

    private final DonorJPARepository jpaRepository;

    public DonorRepositoryImpl(DonorJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Donor save(Donor donor) {
        var entity = DonorMapper.toJPA(donor);
        var savedEntity = jpaRepository.save(entity);
        return DonorMapper.toModel(savedEntity);
    }

    @Override
    public Optional<Donor> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(DonorMapper::toModel);
    }

    @Override
    public Optional<Donor> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(DonorMapper::toModel);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
