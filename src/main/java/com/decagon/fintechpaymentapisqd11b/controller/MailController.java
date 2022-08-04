package com.decagon.fintechpaymentapisqd11b.controller;


import com.decagon.fintechpaymentapisqd11b.dto.SendMailDto;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailServiceImpl mailService;


    @GetMapping()
    public ResponseEntity<String> sendMail(){

        SendMailDto sendMailDto = new SendMailDto();
        sendMailDto.setName("Lawal!");
        sendMailDto.setEmailAddress("lawalmonsaw@gmail.com");

        return ResponseEntity.ok(mailService.sendNotice(sendMailDto));
    }

    }

