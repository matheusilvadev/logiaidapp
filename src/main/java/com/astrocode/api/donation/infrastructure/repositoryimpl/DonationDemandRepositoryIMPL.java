package com.astrocode.api.donation.infrastructure.repositoryimpl;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.infrastructure.jpaentities.DonationDemandJPAEntity;
import com.astrocode.api.donation.infrastructure.jparepository.DonationDemandJPARepository;
import com.astrocode.api.donation.mapper.DonationDemandMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class DonationDemandRepositoryIMPL implements DonationDemandRepository {

    private final DonationDemandJPARepository jpaRepository;

    public DonationDemandRepositoryIMPL(DonationDemandJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public DonationDemand save(DonationDemand donationDemand) {
        final var entity = DonationDemandMapper.toJPA(donationDemand);
        final var savedEntity = jpaRepository.save(entity);
        return DonationDemandMapper.toModel(savedEntity);
    }

    @Override
    public Optional<DonationDemand> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(DonationDemandMapper::toModel);
    }

    @Override
    public List<DonationDemand> findAll() {
        List<DonationDemandJPAEntity> demands = jpaRepository.findAll();
        return demands.stream()
                .map(DonationDemandMapper::toModel)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<DonationDemand> findByBeneficiaryId(UUID beneficiaryId) {
        List<DonationDemandJPAEntity> demands = jpaRepository.findByBeneficiaryId(beneficiaryId);
        return demands.stream()
                .map(DonationDemandMapper::toModel)
                .toList();
    }
}
