package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface WalletService {

    Wallet createWallet(Users walletRequestDetails) throws JSONException;
    WalletDto viewWalletDetails();
}
