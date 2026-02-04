package com.astrocode.api.beneficiary.usecases;


import com.astrocode.api.beneficiary.domain.Beneficiary;
import com.astrocode.api.beneficiary.domain.interfacerepository.BeneficiaryRepository;
import com.astrocode.api.beneficiary.usecases.command.CreateBeneficiaryCommand;
import com.astrocode.api.beneficiary.usecases.exception.BeneficiaryUseCasesException;
import org.springframework.stereotype.Service;

@Service
public class CreateBeneficiaryUseCase {

    private final BeneficiaryRepository beneficiaryRepository;

    public CreateBeneficiaryUseCase(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public Beneficiary execute(CreateBeneficiaryCommand command){

        if (command == null) {
            throw BeneficiaryUseCasesException.commandNull("CREATE_BENEFICIARY");
        }

        Beneficiary beneficiary = Beneficiary.create(
                command.userId(),
                command.displayName(),
                command.document(),
                command.phone(),
                command.address()
        );

        return beneficiaryRepository.save(beneficiary);
    }

}
