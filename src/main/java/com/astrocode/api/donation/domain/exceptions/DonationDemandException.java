package com.astrocode.api.donation.domain.exceptions;

public class DonationDemandException extends RuntimeException {

    public DonationDemandException(String message) {
        super(message);
    }

    public static DonationDemandException required(){
        String msg = "Donation Demand must contain at least one item";
        return new DonationDemandException(msg);
    }

    public static DonationDemandException invalidStatusTransition(String currentStatus, String nextStatus){
        String msg = "Only %s demands can be %s".formatted(currentStatus, nextStatus);
        return new DonationDemandException(msg);
    }

    public static DonationDemandException invalidCancellation(){
        String msg = "Cannot cancel a fulfilled demand";
        return new DonationDemandException(msg);
    }
}
