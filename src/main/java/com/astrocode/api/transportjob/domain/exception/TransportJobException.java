package com.astrocode.api.transportjob.domain.exception;

public class TransportJobException extends RuntimeException {
    public TransportJobException(String message) {
        super(message);
    }

    public static TransportJobException required(){
        String msg = "Donation Demand must contain at least one item";
        return new TransportJobException(msg);
    }

    public static TransportJobException invalidStatusTransition(String currentStatus, String nextStatus){
        String msg = "Only %s transport status can be %s".formatted(currentStatus, nextStatus);
        return new TransportJobException(msg);
    }

    public static TransportJobException invalidCancellation(){
        String msg = "Donation Demand must contain at least one item";
        return new TransportJobException(msg);
    }
}
