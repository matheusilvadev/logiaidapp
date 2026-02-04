package com.astrocode.api.beneficiary.usecases;


import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.domain.interfacerepository.BeneficiaryRepository;
import com.astrocode.api.beneficiary.usecases.command.ChangeBeneficiaryPhoneCommand;
import com.astrocode.api.beneficiary.usecases.exception.BeneficiaryUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class ChangeBeneficiaryPhoneUseCase {

    private final BeneficiaryRepository beneficiaryRepository;

    public ChangeBeneficiaryPhoneUseCase(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public Beneficiary execute(ChangeBeneficiaryPhoneCommand command){
        if (command == null){
            throw BeneficiaryUseCasesException.commandNull("CHANGE_PHONE");
        }

        var beneficiary = beneficiaryRepository.findById(command.id())
                .orElseThrow(() -> BeneficiaryUseCasesException.notFoundById(command.id()));

        var updatedBeneficiary = beneficiary.changePhone(command.newPhone());

        return beneficiaryRepository.save(updatedBeneficiary);
    }

}
