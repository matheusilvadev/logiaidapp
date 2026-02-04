package com.astrocode.api.beneficiary.usecases;


import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.domain.interfacerepository.BeneficiaryRepository;
import com.astrocode.api.beneficiary.usecases.command.ChangeDisplayNameCommand;
import com.astrocode.api.beneficiary.usecases.exception.BeneficiaryUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class ChangeDisplayNameUseCase {

    private final BeneficiaryRepository beneficiaryRepository;

    public ChangeDisplayNameUseCase(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public Beneficiary execute(ChangeDisplayNameCommand command){
        if (command == null){
            throw BeneficiaryUseCasesException.commandNull("CHANGE_DISPLAY_NAME");
        }

        var beneficiary = beneficiaryRepository.findById(command.id())
                .orElseThrow(() -> BeneficiaryUseCasesException.notFoundById(command.id()));

        var updatedBeneficiary = beneficiary.changeDisplayName(command.displayName());

        return beneficiaryRepository.save(updatedBeneficiary);
    }

}
