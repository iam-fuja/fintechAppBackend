package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.UsersRegistrationDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import org.springframework.boot.configurationprocessor.json.JSONException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UsersService {
    String registerUser(UsersRegistrationDto userRegistrationDto) throws JSONException;
    void saveToken(String token, Users user);
    void enableUser(String email) throws JSONException;
    Wallet generateWallet(Users user) throws JSONException;
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException;

}
