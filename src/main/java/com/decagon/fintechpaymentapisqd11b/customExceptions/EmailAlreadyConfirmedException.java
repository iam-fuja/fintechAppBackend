package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class EmailAlreadyConfirmedException extends RuntimeException{
    public EmailAlreadyConfirmedException (String message) {
        super(message);
    }
}
