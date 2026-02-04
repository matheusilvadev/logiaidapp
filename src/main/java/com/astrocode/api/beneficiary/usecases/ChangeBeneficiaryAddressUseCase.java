package com.astrocode.api.beneficiary.usecases;

import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.domain.interfacerepository.BeneficiaryRepository;
import com.astrocode.api.beneficiary.usecases.command.ChangeBeneficiaryAddressCommand;
import com.astrocode.api.beneficiary.usecases.exception.BeneficiaryUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class ChangeBeneficiaryAddressUseCase {

    private final BeneficiaryRepository beneficiaryRepository;

    public ChangeBeneficiaryAddressUseCase(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public Beneficiary execute(ChangeBeneficiaryAddressCommand command){

        if (command == null){
            throw BeneficiaryUseCasesException.commandNull("CHANGE_ADDRESS");
        }

        var beneficiary = beneficiaryRepository.findById(command.beneficiaryId())
                .orElseThrow(() -> BeneficiaryUseCasesException.notFoundById(command.beneficiaryId()));

        var updatedBeneficiary = beneficiary.changeAddress(command.newAddress());

        return beneficiaryRepository.save(updatedBeneficiary);
    }
}
