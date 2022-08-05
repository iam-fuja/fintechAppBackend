package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.decagon.fintechpaymentapisqd11b.customExceptions.FailedMailException;
import com.decagon.fintechpaymentapisqd11b.dto.SendMailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MailServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MailServiceImplTest {
    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MailServiceImpl mailServiceImpl;


    @Test
    void testSendMail() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());

        SendMailDto sendMailDto = new SendMailDto();
        sendMailDto.setBody("Decagon is the best place to fast track your tech journey");
        sendMailDto.setName("Makanjuola");
        sendMailDto.setSubject("Account Approval");
        sendMailDto.setTo("makanjuolao@decagon.dev");
        assertEquals("Mail Sent Successfully ", mailServiceImpl.sendMail(sendMailDto));
        verify(javaMailSender).send((SimpleMailMessage) any());
    }




    @Test
    void testSendMailAgainstException() throws MailException {
        doThrow(new FailedMailException("An error occurred, Please check your network!")).when(javaMailSender).send((SimpleMailMessage) any());

        SendMailDto sendMailDto = new SendMailDto();
        sendMailDto.setBody("Decagon is the best place to fast track your tech journey");
        sendMailDto.setName("Lawal");
        sendMailDto.setSubject("Account Verification");
        sendMailDto.setTo("monsuru.lawal@decagon.dev");
        assertThrows(FailedMailException.class, () -> mailServiceImpl.sendMail(sendMailDto));
        verify(javaMailSender).send((SimpleMailMessage) any());
    }
}

