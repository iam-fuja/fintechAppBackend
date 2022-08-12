package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.UsersDTO;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface UsersService {
    String registerUser(UsersDTO usersDTO) throws JSONException;
    void saveToken(String token, Users user);
    void enableUser(String email) throws JSONException;
    Wallet generateWallet(Users user) throws JSONException;

}
