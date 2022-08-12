package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11b.customExceptions.EmailAlreadyConfirmedException;
import com.decagon.fintechpaymentapisqd11b.dto.SendMailDto;
import com.decagon.fintechpaymentapisqd11b.dto.UsersDTO;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.service.ConfirmationTokenService;
import com.decagon.fintechpaymentapisqd11b.service.MailService;
import com.decagon.fintechpaymentapisqd11b.service.UsersService;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RegistrationServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RegistrationServiceImplTest {
    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private MailService mailService;

    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private UsersService usersService;

    /**
     * Method under test: {@link RegistrationServiceImpl#register(UsersDTO)}
     */
    @Test
    void testRegister() throws JSONException, MailException {
        when(usersService.registerUser((UsersDTO) any())).thenReturn("Register User");
        when(mailService.sendMail((SendMailDto) any())).thenReturn("Send Mail");
        assertEquals("Please check your email for account activation link.",
                registrationServiceImpl.register(new UsersDTO("Jane", "Doe", "janedoe", "BVN", "jane.doe@example.org",
                        "4105551212", "iloveyou", "iloveyou", "Pin")));
        verify(usersService).registerUser((UsersDTO) any());
        verify(mailService).sendMail((SendMailDto) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#register(UsersDTO)}
     */
    @Test
    void testRegister2() throws JSONException, MailException {
        when(usersService.registerUser((UsersDTO) any())).thenReturn("Register User");
        when(mailService.sendMail((SendMailDto) any())).thenThrow(new EmailAlreadyConfirmedException("An error occurred"));
        assertThrows(EmailAlreadyConfirmedException.class, () -> registrationServiceImpl.register(new UsersDTO("Jane",
                "Doe", "janedoe", "BVN", "jane.doe@example.org", "4105551212", "iloveyou", "iloveyou", "Pin")));
        verify(usersService).registerUser((UsersDTO) any());
        verify(mailService).sendMail((SendMailDto) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#register(UsersDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegister3() throws JSONException, MailException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.decagon.fintechpaymentapisqd11b.service.serviceImpl.RegistrationServiceImpl.register(RegistrationServiceImpl.java:36)
        //   In order to prevent register(UsersDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   register(UsersDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        when(usersService.registerUser((UsersDTO) any())).thenReturn("Register User");
        when(mailService.sendMail((SendMailDto) any())).thenReturn("Send Mail");
        registrationServiceImpl.register(null);
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#sendMailVerificationLink(String, String, String)}
     */
    @Test
    void testSendMailVerificationLink() throws MailException {
        when(mailService.sendMail((SendMailDto) any())).thenReturn("Send Mail");
        registrationServiceImpl.sendMailVerificationLink("Name", "jane.doe@example.org", "Link");
        verify(mailService).sendMail((SendMailDto) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#sendMailVerificationLink(String, String, String)}
     */
    @Test
    void testSendMailVerificationLink2() throws MailException {
        when(mailService.sendMail((SendMailDto) any())).thenThrow(new EmailAlreadyConfirmedException("An error occurred"));
        assertThrows(EmailAlreadyConfirmedException.class,
                () -> registrationServiceImpl.sendMailVerificationLink("Name", "jane.doe@example.org", "Link"));
        verify(mailService).sendMail((SendMailDto) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#resendVerificationEmail(Users)}
     */
    @Test
    void testResendVerificationEmail() throws JSONException, MailException {
        doNothing().when(usersService).enableUser((String) any());
        doNothing().when(usersService).saveToken((String) any(), (Users) any());
        when(mailService.sendMail((SendMailDto) any())).thenReturn("Send Mail");

        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(null);
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(null);
        wallet.setCreatedAt(null);
        wallet.setId(123L);
        wallet.setModifyAt(null);
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(null);
        wallet.setUsers(new Users());

        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setAccountNumber("42");
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setBankName("Bank Name");
        wallet1.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setId(123L);
        wallet1.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setTransactions(new ArrayList<>());
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setUsers(users);

        Users users1 = new Users();
        users1.setBVN("BVN");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setPin("Pin");
        users1.setRole("Role");
        users1.setToken("ABC123");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUsername("janedoe");
        users1.setUsersStatus(UsersStatus.ACTIVE);
        users1.setWallet(wallet1);
        registrationServiceImpl.resendVerificationEmail(users1);
        verify(usersService).enableUser((String) any());
        verify(usersService).saveToken((String) any(), (Users) any());
        verify(mailService).sendMail((SendMailDto) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#resendVerificationEmail(Users)}
     */
    @Test
    void testResendVerificationEmail2() throws JSONException, MailException {
        doNothing().when(usersService).enableUser((String) any());
        doNothing().when(usersService).saveToken((String) any(), (Users) any());
        when(mailService.sendMail((SendMailDto) any())).thenThrow(new EmailAlreadyConfirmedException("An error occurred"));

        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(null);
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(null);
        wallet.setCreatedAt(null);
        wallet.setId(123L);
        wallet.setModifyAt(null);
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(null);
        wallet.setUsers(new Users());

        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setAccountNumber("42");
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setBankName("Bank Name");
        wallet1.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setId(123L);
        wallet1.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setTransactions(new ArrayList<>());
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setUsers(users);

        Users users1 = new Users();
        users1.setBVN("BVN");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setPin("Pin");
        users1.setRole("Role");
        users1.setToken("ABC123");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUsername("janedoe");
        users1.setUsersStatus(UsersStatus.ACTIVE);
        users1.setWallet(wallet1);
        assertThrows(EmailAlreadyConfirmedException.class, () -> registrationServiceImpl.resendVerificationEmail(users1));
        verify(mailService).sendMail((SendMailDto) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#confirmToken(String)}
     */
    @Test
    void testConfirmToken() throws JSONException {
        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(null);
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(null);
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(new Wallet());

        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setId(123L);
        wallet.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setUsers(users);

        Users users1 = new Users();
        users1.setBVN("BVN");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setPin("Pin");
        users1.setRole("Role");
        users1.setToken("ABC123");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUsername("janedoe");
        users1.setUsersStatus(UsersStatus.ACTIVE);
        users1.setWallet(wallet);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUser(users1);
        Optional<ConfirmationToken> ofResult = Optional.of(confirmationToken);
        when(confirmationTokenService.getToken((String) any())).thenReturn(ofResult);
        assertThrows(EmailAlreadyConfirmedException.class, () -> registrationServiceImpl.confirmToken("ABC123"));
        verify(confirmationTokenService).getToken((String) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#confirmToken(String)}
     */
    @Test
    void testConfirmToken2() throws JSONException {
        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(null);
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(null);
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(new Wallet());

        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setId(123L);
        wallet.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setUsers(users);

        Users users1 = new Users();
        users1.setBVN("BVN");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setPin("Pin");
        users1.setRole("Role");
        users1.setToken("ABC123");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUsername("janedoe");
        users1.setUsersStatus(UsersStatus.ACTIVE);
        users1.setWallet(wallet);

        Users users2 = new Users();
        users2.setBVN("BVN");
        users2.setCreatedAt(null);
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhoneNumber("4105551212");
        users2.setPin("Pin");
        users2.setRole("Role");
        users2.setToken("ABC123");
        users2.setUpdatedAt(null);
        users2.setUsername("janedoe");
        users2.setUsersStatus(UsersStatus.ACTIVE);
        users2.setWallet(new Wallet());

        Wallet wallet1 = new Wallet();
        wallet1.setAccountNumber("42");
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setBankName("Bank Name");
        wallet1.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setId(123L);
        wallet1.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setTransactions(new ArrayList<>());
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setUsers(users2);

        Users users3 = new Users();
        users3.setBVN("BVN");
        users3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setId(123L);
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhoneNumber("4105551212");
        users3.setPin("Pin");
        users3.setRole("Role");
        users3.setToken("ABC123");
        users3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users3.setUsername("janedoe");
        users3.setUsersStatus(UsersStatus.ACTIVE);
        users3.setWallet(wallet1);

        Wallet wallet2 = new Wallet();
        wallet2.setAccountNumber("42");
        wallet2.setBalance(BigDecimal.valueOf(42L));
        wallet2.setBankName("Bank Name");
        wallet2.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setId(123L);
        wallet2.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setTransactions(new ArrayList<>());
        wallet2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setUsers(users3);

        Users users4 = new Users();
        users4.setBVN("BVN");
        users4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setPin("Pin");
        users4.setRole("Role");
        users4.setToken("ABC123");
        users4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setUsername("janedoe");
        users4.setUsersStatus(UsersStatus.ACTIVE);
        users4.setWallet(wallet2);
        ConfirmationToken confirmationToken = mock(ConfirmationToken.class);
        when(confirmationToken.getUser()).thenReturn(users4);
        when(confirmationToken.getConfirmedAt()).thenReturn(null);
        when(confirmationToken.getExpiresAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(confirmationToken).setConfirmedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setCreatedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setExpiresAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setId((Long) any());
        doNothing().when(confirmationToken).setToken((String) any());
        doNothing().when(confirmationToken).setUser((Users) any());
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUser(users1);
        Optional<ConfirmationToken> ofResult = Optional.of(confirmationToken);
        when(confirmationTokenService.getToken((String) any())).thenReturn(ofResult);
        when(usersRepository.findByEmail((String) any()))
                .thenThrow(new EmailAlreadyConfirmedException("An error occurred"));
        assertThrows(EmailAlreadyConfirmedException.class, () -> registrationServiceImpl.confirmToken("ABC123"));
        verify(confirmationTokenService).getToken((String) any());
        verify(confirmationToken).getUser();
        verify(confirmationToken).getConfirmedAt();
        verify(confirmationToken).getExpiresAt();
        verify(confirmationToken).setConfirmedAt((LocalDateTime) any());
        verify(confirmationToken).setCreatedAt((LocalDateTime) any());
        verify(confirmationToken).setExpiresAt((LocalDateTime) any());
        verify(confirmationToken).setId((Long) any());
        verify(confirmationToken).setToken((String) any());
        verify(confirmationToken).setUser((Users) any());
        verify(usersRepository).findByEmail((String) any());
    }
}

