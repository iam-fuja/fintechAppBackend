package com.decagon.fintechpaymentapisqd11b.customExceptions;
import com.decagon.fintechpaymentapisqd11b.entities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FailedMailException.class)
    public ResponseEntity<ErrorResponse> handlerForFailedMailException(final FailedMailException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Email not sent!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ErrorResponse> handlerForEmailTakenException(final EmailTakenException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Email already taken!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerForUserAlreadyExistsException(final UserAlreadyExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Users already exists!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFound(final UserNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Users not found!");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handlerTokenExpiredException(final TokenExpiredException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Token expired. Generate new token to activate account.");
        return new ResponseEntity<>(errorResponse, HttpStatus.GONE);
    }

    @ExceptionHandler(EmailAlreadyConfirmedException.class)
    public ResponseEntity<ErrorResponse> handlerEmailAlreadyConfirmedException(final EmailAlreadyConfirmedException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Email already confirmed.");
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerTokenNotFoundException(final TokenNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Token not found.");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchingException.class)
    public ResponseEntity<ErrorResponse> handlerForPasswordNotMatchingException(final PasswordNotMatchingException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage("Password mismatch!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

