package com.astrocode.api.donor.domain.exception;


public class DonorException extends RuntimeException {

    public DonorException(String message) {
        super(message);
    }

    public static DonorException required(String field){
        String msg = "%s cannot be null or blank.".formatted(field);
        return new DonorException(msg);
    }

    public static DonorException length(String field, int details){
        String msg = "%s must have at least %s characters".formatted(field, details);
        return new DonorException(msg);
    }
}
