package com.decagon.fintechpaymentapisqd11b.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor

public class SendMailDto {

    private String to;
    private String name;
    private String subject;
    private String body;
}
