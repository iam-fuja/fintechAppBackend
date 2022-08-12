package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
