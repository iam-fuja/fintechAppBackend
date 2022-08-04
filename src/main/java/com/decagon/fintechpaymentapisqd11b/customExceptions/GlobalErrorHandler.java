package com.decagon.fintechpaymentapisqd11b.customExceptions;

import com.decagon.fintechpaymentapisqd11b.entities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(FailedMailException.class)
    public ResponseEntity<ErrorResponse> handlerForAdminExistException(final FailedMailException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(errorResponse);
    }
}
