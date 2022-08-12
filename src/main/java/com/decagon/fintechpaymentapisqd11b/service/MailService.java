package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.SendMailDto;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    String sendMail(SendMailDto sendMailDto) throws MailException;
}

