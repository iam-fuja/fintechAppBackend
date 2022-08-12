package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;


import com.decagon.fintechpaymentapisqd11b.repository.ConfirmationTokenRepository;
import com.decagon.fintechpaymentapisqd11b.service.ConfirmationTokenService;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
