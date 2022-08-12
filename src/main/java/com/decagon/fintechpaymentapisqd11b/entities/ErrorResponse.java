package com.decagon.fintechpaymentapisqd11b.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String message;
    public String debugMessage;
    private HttpStatus httpStatus;
    private LocalDateTime date = LocalDateTime.now();

}

