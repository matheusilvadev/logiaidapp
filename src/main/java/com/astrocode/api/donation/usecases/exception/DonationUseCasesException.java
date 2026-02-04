package com.astrocode.api.donation.usecases.exception;

import java.util.UUID;

public class DonationUseCasesException extends RuntimeException {

    public DonationUseCasesException(String message) {
        super(message);
    }

    public static DonationUseCasesException notFound(String useCase, UUID id){
        String msg = "%s not found: ".formatted(useCase);
        return new DonationUseCasesException(msg + id);
    }

    public static DonationUseCasesException commandNull(String useCaseCommand){
        String msg = "%s cannot be null ".formatted(useCaseCommand);
        return new DonationUseCasesException(msg);
    }

    public static DonationUseCasesException emptyList(String usecase){
        String msg = "%s - No donation demands registered. ".formatted(usecase);
        return new DonationUseCasesException(msg);
    }

    public static DonationUseCasesException notPossibleToDelete(String usecase, String status){
        String msg = "%s - It is not possible to delete requests that do not have a %s status.".formatted(usecase, status);
        return new DonationUseCasesException(msg);
    }

    public static DonationUseCasesException notOwner(String usecase, UUID donationId, UUID donorId){
        String msg = "- %s - The donor with beneficiaryId: %s is not the donor responsible for the donation: %s".formatted(usecase, donorId, donationId);
        return new DonationUseCasesException(msg);
    }

}
