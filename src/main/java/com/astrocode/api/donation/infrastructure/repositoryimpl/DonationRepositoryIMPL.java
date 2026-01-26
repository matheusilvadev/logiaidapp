package com.astrocode.api.donation.infrastructure.repositoryimpl;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.infrastructure.jpaentities.DonationJPAEntity;
import com.astrocode.api.donation.infrastructure.jparepository.DonationJPARepository;
import com.astrocode.api.donation.mapper.DonationMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class DonationRepositoryIMPL implements DonationRepository {

    private final DonationJPARepository jpaRepository;

    public DonationRepositoryIMPL(DonationJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Donation save(Donation donation) {
        final var entity = DonationMapper.toJPA(donation);
        final var savedEntity = jpaRepository.save(entity);
        return DonationMapper.toModel(savedEntity);
    }

    @Override
    public Optional<Donation> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(DonationMapper::toModel);
    }

    @Override
    public List<Donation> findAll() {
        List<DonationJPAEntity> donations = jpaRepository.findAll();
        return donations.stream()
                .map(DonationMapper::toModel)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Donation> findByDonorId(UUID donorId) {
        List<DonationJPAEntity> donations = jpaRepository.findByDonorId(donorId);
        return donations.stream()
                .map(DonationMapper::toModel)
                .toList();
    }
}
