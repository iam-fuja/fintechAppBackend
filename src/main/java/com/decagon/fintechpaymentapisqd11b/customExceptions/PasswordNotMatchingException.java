package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class PasswordNotMatchingException extends RuntimeException{

    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
