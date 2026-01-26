package com.astrocode.api.donation.domain.exceptions;

public class DonationException extends RuntimeException {

    public DonationException(String message) {
        super(message);
    }

    public static  DonationException invalidStatusTransition(String currentStatus, String newStatus){
        String msg = "Donation must be %s to go %s".formatted(currentStatus, newStatus);
        return new DonationException(msg);
    }

    public static  DonationException cannotBeDelivered(){
        String msg = "Donation cannot be delivered from current status";
        return new DonationException(msg);
    }

    public static  DonationException impossibleCancellation(String currentStatus){
        String msg = "Cannot cancel a %s donation".formatted(currentStatus);
        return new DonationException(msg);
    }
}
