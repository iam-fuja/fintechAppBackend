package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.FailedMailException;
import com.decagon.fintechpaymentapisqd11b.dto.SendMailDto;
import com.decagon.fintechpaymentapisqd11b.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;



    @Override
    public String sendNotice(SendMailDto sendMailDto) throws MailException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(sendMailDto.getEmailAddress());
        simpleMailMessage.setSubject("Account Approval");
        simpleMailMessage.setText("Hi , " + sendMailDto.getName() +
                "\nPlease click on the link in less than 5 " +
                "minutes." +
                "\nhttps://www.youtube.com \n\n Regards\n Transact Team!");
        try{

            javaMailSender.send(simpleMailMessage);
            return "Mail Sent Successfully ";
        } catch (MailException ex)

        {
            throw new FailedMailException("Mail could not send because : "+ex.getMessage());
        }

    }
}
