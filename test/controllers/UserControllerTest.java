package controllers;

import data.models.Profile;
import dtos.request.LoginRequest;
import dtos.request.MailRequest;
import dtos.request.RegisterRequest;
import dtos.response.LoginResponse;
import dtos.response.MailResponse;
import dtos.response.RegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.profileService.ProfileService;
import services.profileService.ProfileServiceImp;
import utils.Mapper;

import javax.management.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController = new UserController();
    ProfileService profileService;
    RegisterRequest registerRequest;
    RegisterResponse response;
    MailRequest mailRequest;
    MailResponse mailResponse;
    String date;
    String time;

    @BeforeEach void startWith() throws InstanceNotFoundException {
        profileService = new ProfileServiceImp();
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Moyinoluwa");
        registerRequest.setLastName("Michael");
        registerRequest.setDayOfBirth("17");
        registerRequest.setMonthOfBirth("11");
        registerRequest.setYearOfBirth("1999");
        registerRequest.setUsername("MoyinoluwaMichael");
        registerRequest.setPassword("password");
        response = userController.registerUSer(registerRequest);
        mailRequest = new MailRequest();
        mailRequest.setSender(response.getUsername());
        mailRequest.setRecipient("Adesam@regnos.com");
        mailRequest.setSubject("Election");
        mailRequest.setBody("Vote Peter Obi");
        mailRequest.setProfileId(1);
        registerRequest.setUsername("Adesam@regnos.com");
        response = userController.registerUSer(registerRequest);
        mailResponse = userController.sendMail(mailRequest);

        LocalDateTime localDateTime = LocalDateTime.now();
        date = DateTimeFormatter.ofPattern("dd/M/yyyy").format(localDateTime.toLocalDate());
        time = DateTimeFormatter.ofPattern("H:mm").format(localDateTime.toLocalTime());

    }

    @Test void userCanRegisterTest(){
        assertEquals(2, response.getId());
    }

    @Test void userCanBeFoundByUsernameAfterRegistration() throws InstanceNotFoundException {
        RegisterResponse expectedUser = userController.findProfileByUsername("MoyinoluwaMichael@regnos.com");
        assertEquals("moyinoluwamichael@regnos.com", expectedUser.getUsername());
    }

    @Test void userXCanSendAMailToUserY_userXSentMailsCountIsOne_userYInboxCountIsOne() {
        assertEquals(1, userController.getSentMailsCountByProfileId(1));
        assertEquals(1, userController.getInboxCountByProfileId(2));
    }

    @Test void inboxCanBeFoundByUserId(){
        assertEquals(0, userController.findInboxByProfileId(1).size());
    }

    @Test void sentMailsCanBeFoundByUserId(){
        assertEquals(1, userController.findSentMailsByProfileId(1).size());
    }

    @Test void trashCanBeFoundByUserId(){
        assertEquals(0, userController.findTrashByProfileId(1).size());
    }

    @DisplayName("user can login after registration")
    @Test void userXRegisters_userXLogsIn_userXEmailAccountIsFetched() throws InstanceNotFoundException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("MoyinoluwaMichael@regnos.com");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userController.login(loginRequest);
        assertEquals(0, loginResponse.getInbox().size());
        assertEquals(1, loginResponse.getSentMails().size());
        assertEquals(0, loginResponse.getTrash().size());
    }

    @Test void providingIncorrectUsernameAtLoginThrowsException() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Moyinoluwa@regnos.com");
        loginRequest.setPassword("password");
        assertThrows(InstanceNotFoundException.class, ()-> userController.login(loginRequest));
    }

    @Test void providingIncorrectPasswordAtLoginThrowsException() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("MoyinoluwaMichael@regnos.com");
        loginRequest.setPassword("pass");
        assertThrows(IllegalArgumentException.class, ()-> userController.login(loginRequest));
    }

    @Test void mailCanBeViewedTest(){

        String expected = String.format("""
                ============================================
                Date: %s            Time: %s
                ============================================
                From: moyinoluwamichael@regnos.com
                To: Adesam@regnos.com
                ============================================
                Subject: Election
                ============================================
                Vote Peter Obi
                ============================================
                """,date, time+" " );
        assertEquals(expected, userController.findInboxByProfileId(2).get(0).toString());
    }

    @Test void userCanDeleteMailFromInbox() throws InstanceNotFoundException {
        userController.deleteMailFromInboxById(2, 1);
        assertEquals(0, userController.findInboxByProfileId(2).size());
    }

    @Test void userCanDeleteMailFromSentMails() throws InstanceNotFoundException {
        userController.deleteMailFromSentMailsById(1, 1);
        assertEquals(0, userController.findSentMailsByProfileId(1).size());
    }

    @Test void userCanDeleteMailFromTrash() throws InstanceNotFoundException {
        userController.deleteMailFromSentMailsById(1, 1);
        userController.deleteMailFromTrashById(1, 1);
        assertEquals(0, userController.findTrashByProfileId(1).size());
    }

    @Test void userCanViewProfile() throws InstanceNotFoundException {
        String expected = String.format("""
                *****************************************
                            REGNOS EMAIL
                *****************************************
                ACCOUNT PROFILE
                Name: Moyinoluwa Michael
                Date of Birth: 17/11/1999
                Username: moyinoluwamichael@regnos.com
                Created on: %s %s
                *****************************************
                """, date, time);
        assertEquals(expected, userController.findProfileByUsername("moyinoluwamichael@regnos.com").toString());
    }
}