package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.EmailAlreadyConfirmedException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.TokenNotFoundException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.SendMailDto;
import com.decagon.fintechpaymentapisqd11b.dto.UsersRegistrationDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.service.ConfirmationTokenService;
import com.decagon.fintechpaymentapisqd11b.service.MailService;
import com.decagon.fintechpaymentapisqd11b.service.RegistrationService;
import com.decagon.fintechpaymentapisqd11b.service.UsersService;
import com.decagon.fintechpaymentapisqd11b.util.Constant;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UsersService usersService;
    private final MailService mailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UsersRepository usersRepository;

    @Override
    public String register(UsersRegistrationDto userRegistrationDto) throws JSONException {
        String token = usersService.registerUser(userRegistrationDto);

        String link = Constant.EMAIL_VERIFICATION_LINK + token;
        sendMailVerificationLink(userRegistrationDto.getFirstName(), userRegistrationDto.getEmail(), link);

        return "Please check your email for account activation link.";
    }

    @Override
    public void sendMailVerificationLink(String name, String email, String link) {
        String subject = "Email Verification";
        String body = "Click the link below to verify your email \n" + link;
        SendMailDto sendMailDto = new SendMailDto(email, name, subject, body);
        mailService.sendMail(sendMailDto);
    }

    @Override
    public void resendVerificationEmail(Users user) throws JSONException {
        String token = UUID.randomUUID().toString();
        String link = Constant.EMAIL_VERIFICATION_LINK + token;
        sendMailVerificationLink(user.getFirstName(), user.getEmail(), link);

        usersService.saveToken(token, user);
        usersService.enableUser(user.getEmail());
    }

    @Override
    public String confirmToken(String token) throws JSONException {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found."));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed.");
        }
        
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            Users user = usersRepository.findByEmail(confirmationToken.getUser().getEmail()).orElseThrow(
                    ()-> new UserNotFoundException("Users not found"));
            resendVerificationEmail(user);
            return "Previous verification token expired. Check email for new token.";
        }

        confirmationTokenService.setConfirmedAt(token);
        usersService.enableUser(confirmationToken.getUser().getEmail());

        return "Email confirmed!";
    }
}
