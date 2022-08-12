package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.WalletNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.security.filter.JwtUtils;
import com.decagon.fintechpaymentapisqd11b.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UsersRepository usersRepository;
    private final JwtUtils jwtUtils;

    @Override
    public WalletDto viewWalletDetails() throws WalletNotFoundException {
     WalletDto walletDto = new WalletDto();
     Wallet wallet = new Wallet();
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Users users = usersRepository.findUsersByEmail(jwtUtils.extractUsername(auth.getName()));
    if(users == null){
        throw new UsernameNotFoundException("User not found");
    }
     walletRepository.findWalletByUsersId(wallet.getId()).
             orElseThrow(()-> new WalletNotFoundException("Wallet Not Found"));
     walletDto.setAccountNumber(wallet.getAccountNumber());
     walletDto.setBalance(wallet.getBalance());
        return walletDto;
    }
}
