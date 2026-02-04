package com.astrocode.api.transporter.domain.exception;

public class TransporterException extends RuntimeException {

    public TransporterException(String message) {
        super(message);
    }

    public static TransporterException required(String field){
        String msg = "%s cannot be null or blank.".formatted(field);
        return new TransporterException(msg);
    }

    public static TransporterException length(String field, int details){
        String msg = "%s must have at least %s characters".formatted(field, details);
        return new TransporterException(msg);
    }

}
