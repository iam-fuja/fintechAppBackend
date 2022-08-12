package com.decagon.fintechpaymentapisqd11b.service;

import com.decagon.fintechpaymentapisqd11b.customExceptions.WalletNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;

public interface WalletService {

    WalletDto viewWalletDetails() throws WalletNotFoundException;
}
