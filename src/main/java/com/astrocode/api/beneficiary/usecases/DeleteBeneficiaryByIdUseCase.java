package com.astrocode.api.beneficiary.usecases;


import com.astrocode.api.beneficiary.domain.interfacerepository.BeneficiaryRepository;
import com.astrocode.api.beneficiary.usecases.exception.BeneficiaryUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteBeneficiaryByIdUseCase {

    private final BeneficiaryRepository beneficiaryRepository;

    public DeleteBeneficiaryByIdUseCase(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public void execute(UUID id){

        if (beneficiaryRepository.findById(id).isEmpty()){
            throw BeneficiaryUseCasesException.notFoundById(id);
        }

        beneficiaryRepository.deleteById(id);
    }
}
