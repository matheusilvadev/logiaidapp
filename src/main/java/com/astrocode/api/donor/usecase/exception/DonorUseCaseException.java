package com.astrocode.api.donor.usecase.exception;

import java.util.UUID;

public class DonorUseCaseException extends RuntimeException {
    public DonorUseCaseException(String message) {
        super(message);
    }

    public static DonorUseCaseException notFoundById(UUID id){
        String msg = "Donor with beneficiaryId: %s is not found".formatted(id);
        return new DonorUseCaseException(msg);
    }

    public static DonorUseCaseException notFoundByUserId(UUID id){
        String msg = "Donor with beneficiaryId: %s is not found".formatted(id);
        return new DonorUseCaseException(msg);
    }

    public static DonorUseCaseException commandNull(String command){
        String msg = "Command %s cannot be null".formatted(command);
        return new DonorUseCaseException(msg);
    }

}
