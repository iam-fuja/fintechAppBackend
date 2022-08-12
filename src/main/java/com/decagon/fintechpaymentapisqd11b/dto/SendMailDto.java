package com.decagon.fintechpaymentapisqd11b.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailDto {
    private String to;
    private String name;
    private String subject;
    private String body;
}

