package com.astrocode.api.donor.usecase;


import com.astrocode.api.donor.domain.Donor;
import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.usecase.exception.DonorUseCaseException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindDonorByIdUseCase {

    private final DonorRepository donorRepository;

    public FindDonorByIdUseCase(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor execute(UUID id){
        return donorRepository.findById(id)
                .orElseThrow(() -> DonorUseCaseException.notFoundById(id));
    }

}
