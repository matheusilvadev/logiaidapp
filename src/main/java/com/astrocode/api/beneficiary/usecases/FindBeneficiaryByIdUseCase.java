package com.astrocode.api.beneficiary.usecases;


import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.domain.interfacerepository.BeneficiaryRepository;
import com.astrocode.api.beneficiary.usecases.exception.BeneficiaryUseCasesException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindBeneficiaryByIdUseCase {

    private final BeneficiaryRepository beneficiaryRepository;

    public FindBeneficiaryByIdUseCase(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public Beneficiary execute(UUID id){
        return beneficiaryRepository.findById(id)
                .orElseThrow(() ->BeneficiaryUseCasesException.notFoundById(id));
    }

}
