package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(String message) {
        super(message);
    }
}
