package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.request.FlwWalletRequest;
import com.decagon.fintechpaymentapisqd11b.response.FlwVirtualAccountResponse;
import com.decagon.fintechpaymentapisqd11b.service.WalletService;
import com.decagon.fintechpaymentapisqd11b.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Override
    public Wallet createWallet(Users user) throws JSONException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        FlwWalletRequest payload = generatePayload(user);

        HttpEntity<FlwWalletRequest> request = new HttpEntity<>(payload, headers);

        FlwVirtualAccountResponse body = restTemplate.exchange(
                Constant.CREATE_VIRTUAL_ACCOUNT_API,
                HttpMethod.POST,
                request,
                FlwVirtualAccountResponse.class
        ).getBody();

        return Wallet.builder()
                .accountNumber(body.getData().getAccountNumber())
                .balance(BigDecimal.valueOf(0.00))
                .bankName(body.getData().getBankName())
                .createAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
                .build();
    }

    private FlwWalletRequest generatePayload(Users user) {
        FlwWalletRequest jsono = new FlwWalletRequest();
        jsono.setEmail(user.getEmail());
        jsono.set_permanent(true);
        jsono.setBvn(user.getBVN());
        jsono.setPhonenumber(user.getPhoneNumber());
        jsono.setFirstname(user.getFirstName());
        jsono.setLastname(user.getLastName());
        jsono.setTx_ref("fintech app");
        jsono.setNarration("fintech");

        return jsono;
    }


}
