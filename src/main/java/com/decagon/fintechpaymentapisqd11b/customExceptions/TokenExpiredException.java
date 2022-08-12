package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(String message) {
        super(message);
    }
}
