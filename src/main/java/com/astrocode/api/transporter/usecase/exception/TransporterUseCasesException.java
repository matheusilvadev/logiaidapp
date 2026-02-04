package com.astrocode.api.transporter.usecase.exception;


import java.util.UUID;

public class TransporterUseCasesException extends RuntimeException {
    public TransporterUseCasesException(String message) {
        super(message);
    }

    public static TransporterUseCasesException notFoundById(UUID id){
        String msg = "Transporter with beneficiaryId: %s is not found".formatted(id);
        return new TransporterUseCasesException(msg);
    }

    public static TransporterUseCasesException notFoundByUserId(UUID id){
        String msg = "Transporter with beneficiaryId: %s is not found".formatted(id);
        return new TransporterUseCasesException(msg);
    }

    public static TransporterUseCasesException commandNull(String command){
        String msg = "Command %s cannot be null".formatted(command);
        return new TransporterUseCasesException(msg);
    }

}
