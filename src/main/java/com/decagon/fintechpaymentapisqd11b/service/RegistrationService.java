package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.UsersDTO;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface RegistrationService {

    String register(UsersDTO usersDTO) throws JSONException;
    void sendMailVerificationLink(String name, String email, String link);
    void resendVerificationEmail(Users user) throws JSONException;
    String confirmToken(String token) throws JSONException;
}
