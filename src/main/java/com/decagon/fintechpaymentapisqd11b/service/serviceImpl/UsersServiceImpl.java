package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.EmailTakenException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.PasswordNotMatchingException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.UsersNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.UsersDTO;
import com.decagon.fintechpaymentapisqd11b.dto.UsersResponse;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.security.filter.JwtUtils;
import com.decagon.fintechpaymentapisqd11b.service.UsersService;
import com.decagon.fintechpaymentapisqd11b.service.WalletService;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;


import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;


@Service @Transactional
@RequiredArgsConstructor @Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private String USER_EMAIL_ALREADY_EXISTS_MSG = "Users with email %s already exists!";
    private final ConfirmationTokenServiceImpl confirmTokenService;
    private final WalletService walletService;

    private final JwtUtils jwtUtils;
    private final WalletServiceImpl walletServices;
    private final WalletRepository walletRepository;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public String registerUser(UsersDTO usersDTO) throws JSONException {

        if (!usersDTO.getPassword().equals(usersDTO.getConfirmPassword())) {
            throw new PasswordNotMatchingException("Passwords do not match!");
        }

        boolean userExists = usersRepository.findByEmail(usersDTO.getEmail()).isPresent();

        if (userExists) {
            throw new EmailTakenException(
                    String.format(USER_EMAIL_ALREADY_EXISTS_MSG, usersDTO.getEmail()));
        }

        Users user = new Users();
        user.setFirstName(usersDTO.getFirstName());
        user.setLastName(usersDTO.getLastName());
        user.setEmail(usersDTO.getEmail());
        user.setPhoneNumber(usersDTO.getPhoneNumber());
        user.setBVN(usersDTO.getBVN());
        user.setPassword(bCryptPasswordEncoder.encode(usersDTO.getPassword()));
        user.setPin(usersDTO.getPin());
        user.setCreatedAt(LocalDateTime.now());
        user.setUsersStatus(UsersStatus.INACTIVE);
        user.setRole("USER");
        user.setUsername(usersDTO.getUsername());
        Users user1 = usersRepository.save(user);

        Wallet wallet = generateWallet(user1);
        wallet.setUsers(user1);
        walletRepository.save(wallet);

        String token = UUID.randomUUID().toString();
        saveToken(token, user);

        return token;
    }

    @Override
    public void saveToken(String token, Users user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(24),
                user
        );
        confirmTokenService.saveConfirmationToken(confirmationToken);
    }

    @Override
    public Wallet generateWallet(Users user) throws JSONException {
        return walletService.createWallet(user);
    }

    @Override
    public UsersResponse getUser() {
        String email = WalletServiceImpl.userToken;
        Users users1 = usersRepository.findUsersByEmail(jwtUtils.extractUsername(email));
        UsersResponse usersResponse = UsersResponse.builder()
                .firstName(users1.getFirstName())
                .lastName(users1.getLastName())
                .email(users1.getEmail())
                .phoneNumber(users1.getPhoneNumber())
                .BVN(users1.getBVN())
                .build();
        return usersResponse;

    }


    @Override
    public void enableUser(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() ->  new UserNotFoundException("Users not found."));
        user.setUsersStatus(UsersStatus.ACTIVE);
        usersRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findUsersByEmail(email);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        if(user == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }else {
            log.info("User Found");

            return new User(user.getEmail(), user.getPassword(), Collections.singleton(authority));
        }
    }
}
