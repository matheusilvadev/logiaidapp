package com.astrocode.api.donor.usecase;


import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.usecase.exception.DonorUseCaseException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindDonorByUserIdUseCase {

    private final DonorRepository donorRepository;

    public FindDonorByUserIdUseCase(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor execute(UUID authUserId){
        return donorRepository.findByUserId(authUserId)
                .orElseThrow(() -> DonorUseCaseException.notFoundByUserId(authUserId));
    }
}
