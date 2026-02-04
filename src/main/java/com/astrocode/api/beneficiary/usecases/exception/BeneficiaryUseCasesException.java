package com.astrocode.api.beneficiary.usecases.exception;

import java.util.UUID;

public class BeneficiaryUseCasesException extends RuntimeException {
    public BeneficiaryUseCasesException(String message) {
        super(message);
    }

    public static BeneficiaryUseCasesException notFoundById(UUID id){
        String msg = "Beneficiary with beneficiaryId: %s is not found".formatted(id);
        return new BeneficiaryUseCasesException(msg);
    }

    public static BeneficiaryUseCasesException notFoundByUserId(UUID id){
        String msg = "Beneficiary with beneficiaryId: %s is not found".formatted(id);
        return new BeneficiaryUseCasesException(msg);
    }

    public static BeneficiaryUseCasesException commandNull(String command){
        String msg = "Command %s cannot be null".formatted(command);
        return new BeneficiaryUseCasesException(msg);
    }
}
