package com.astrocode.api.transportjob.usecase.exception;

import java.util.UUID;

public class TransportJobUseCaseException extends RuntimeException {
    public TransportJobUseCaseException(String message) {
        super(message);
    }

    public static TransportJobUseCaseException notFoundById(UUID id){
        String msg = "Transporter with beneficiaryId: %s, is not found".formatted(id);
        return new TransportJobUseCaseException(msg);
    }

    public static TransportJobUseCaseException commandNull(String command){
        String msg = "Command %s cannot be null".formatted(command);
        return new TransportJobUseCaseException(msg);
    }

    public static TransportJobUseCaseException invalidStatus(){
        String msg = "Invalid STATUS";
        return new TransportJobUseCaseException(msg);
    }
}
