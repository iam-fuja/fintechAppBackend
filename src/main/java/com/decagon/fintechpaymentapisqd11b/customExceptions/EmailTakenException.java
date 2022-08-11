package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class EmailTakenException extends RuntimeException{

    public EmailTakenException(String message) {
        super(message);
    }
}
