package com.astrocode.api.user.usecases.exception;

import java.util.UUID;

public class UserUseCasesException extends RuntimeException {
    public UserUseCasesException(String message) {
        super(message);
    }

    public static UserUseCasesException notFoundById(UUID id){
        String msg = "%s not found".formatted(id);
        return new UserUseCasesException(msg);
    }

    public static UserUseCasesException notFoundByAuthId(String id){
        String msg = "%s not found".formatted(id);
        return new UserUseCasesException(msg);
    }

    public static UserUseCasesException commandNull(String useCaseCommand){
        String msg = "%s cannot be null".formatted(useCaseCommand);
        return new UserUseCasesException(msg);
    }

    public static UserUseCasesException userAlreadyExists(String id){
        String msg = "User with beneficiaryId: %s , already exists".formatted(id);
        return new UserUseCasesException(msg);
    }


}
