package com.astrocode.api.donor.usecase;


import com.astrocode.api.donor.domain.interfacerepository.DonorRepository;
import com.astrocode.api.donor.usecase.exception.DonorUseCaseException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteDonorByIdUseCase {

    private final DonorRepository donorRepository;

    public DeleteDonorByIdUseCase(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public void execute(UUID id){
        if (donorRepository.findById(id).isEmpty()){
            throw DonorUseCaseException.notFoundById(id);
        }

        donorRepository.deleteById(id);
    }
}
