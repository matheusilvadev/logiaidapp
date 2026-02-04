package com.astrocode.api.beneficiary.domain.exception;

public class BeneficiaryException extends RuntimeException {

    public BeneficiaryException(String message) {
        super(message);
    }

    public static BeneficiaryException required(String field){
        String msg = "%s cannot be null or blank.".formatted(field);
        return new BeneficiaryException(msg);
    }

    public static BeneficiaryException length(String field, int details){
        String msg = "%s must have at least %s characters".formatted(field, details);
        return new BeneficiaryException(msg);
    }

}
