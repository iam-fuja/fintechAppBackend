package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.EmailTakenException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.PasswordNotMatchingException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.UsersRegistrationDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.service.UsersService;
import com.decagon.fintechpaymentapisqd11b.service.WalletService;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final static String USER_EMAIL_ALREADY_EXISTS_MSG = "Users with email %s already exists!";
    private final UsersRepository userRepository;
    private final ConfirmationTokenServiceImpl confirmTokenService;
    private final WalletService walletService;
    private final WalletRepository walletRepository;

    @Override
    public String registerUser(UsersRegistrationDto userRegistrationDto) throws JSONException {

        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            throw new PasswordNotMatchingException("Passwords do not match!");
        }

        boolean userExists = userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent();

        if (userExists) {
            throw new EmailTakenException(
                    String.format(USER_EMAIL_ALREADY_EXISTS_MSG, userRegistrationDto.getEmail()));
        }

        Users user = new Users();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
        user.setBVN(userRegistrationDto.getBVN());
        user.setPassword(userRegistrationDto.getPassword());
        user.setPin(userRegistrationDto.getPin());
        user.setCreatedAt(LocalDateTime.now());
        user.setUserStatus(UsersStatus.INACTIVE);
        user.setRole("USER");
        user.setUsername(userRegistrationDto.getUsername());
        Users user1 = userRepository.save(user);

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
    public void enableUser(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() ->  new UserNotFoundException("Users not found."));
        user.setUserStatus(UsersStatus.ACTIVE);
        userRepository.save(user);
    }
}
