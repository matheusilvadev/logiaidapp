package com.astrocode.api.beneficiary.infrastructure.repositoryimpl;

import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.domain.interfacerepository.BeneficiaryRepository;
import com.astrocode.api.beneficiary.infrastructure.BeneficiaryJPARepository;
import com.astrocode.api.beneficiary.mapper.BeneficiaryMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public class BeneficiaryRepositoryImpl implements BeneficiaryRepository {

    private final BeneficiaryJPARepository jpaRepository;

    public BeneficiaryRepositoryImpl(BeneficiaryJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }


    @Override
    public Beneficiary save(Beneficiary beneficiary) {
        var entity = BeneficiaryMapper.toJPA(beneficiary);
        var savedEntity = jpaRepository.save(entity);
        return BeneficiaryMapper.toModel(savedEntity);
    }

    @Override
    public Optional<Beneficiary> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(BeneficiaryMapper::toModel);
    }

    @Override
    public Optional<Beneficiary> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(BeneficiaryMapper::toModel);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
