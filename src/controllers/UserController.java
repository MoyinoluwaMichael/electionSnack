package controllers;

import dtos.request.LoginRequest;
import dtos.request.MailRequest;
import dtos.request.RegisterRequest;
import dtos.response.LoginResponse;
import dtos.response.MailResponse;
import dtos.response.RegisterResponse;
import services.mailService.MailService;
import services.mailService.MailServiceImp;
import services.profileService.ProfileService;
import services.profileService.ProfileServiceImp;
import utils.Mapper;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserController {
    private ProfileService profileService = new ProfileServiceImp();
    private MailService mailService = new MailServiceImp();

    public RegisterResponse registerUSer(RegisterRequest request) {
        return Mapper.map(profileService.register(Mapper.map(request)));
    }

    public MailResponse sendMail(MailRequest mailRequest) throws InstanceNotFoundException {
        findProfileByUsername(mailRequest.getRecipient());
        mailService.sendMail(Mapper.map(mailRequest));
        mailRequest.setProfileId(findProfileByUsername(mailRequest.getRecipient()).getId());
        return Mapper.map(mailService.receiveMail(Mapper.map(mailRequest)));
    }

    public long getSentMailsCountByProfileId(int profileId) {
        return mailService.countSentMailsByProfileId(profileId);
    }

    public long getInboxCountByProfileId(int profileId) {
        return mailService.countInboxByProfileId(profileId);
    }

    public RegisterResponse findProfileByUsername(String username) throws InstanceNotFoundException {
        return Mapper.map(profileService.findByUsername(username));
    }

    public LoginResponse login(LoginRequest loginRequest) throws InstanceNotFoundException {
        profileService.validateLoginRequest(loginRequest.getUsername(),loginRequest.getPassword());
        LoginResponse loginResponse = Mapper.map(Mapper.map(profileService.findByUsername(loginRequest.getUsername())));
        findAllUserMails(loginResponse);
        return loginResponse;
    }

    private void findAllUserMails(LoginResponse loginResponse) {
        loginResponse.setInbox(findInboxByProfileId(loginResponse.getId()));
        loginResponse.setSentMails(findSentMailsByProfileId(loginResponse.getId()));
        loginResponse.setTrash(findTrashByProfileId(loginResponse.getId()));
    }

    public List<MailResponse> findInboxByProfileId(int profileId) {
        return Mapper.map(mailService.getInbox(profileId));
    }

    public List<MailResponse> findSentMailsByProfileId(int profileId) {
        return Mapper.map(mailService.getSentMails(profileId));
    }

    public List<MailResponse> findTrashByProfileId(int profileId) {
        return Mapper.map(mailService.getTrash(profileId));
    }

    public void deleteMailFromInboxById(int profileId, int id) throws InstanceNotFoundException {
        mailService.deleteMailFromInboxById(profileId, id);
    }

    public void deleteMailFromSentMailsById(int profileId, int id) throws InstanceNotFoundException {
        mailService.deleteMailFromSentMailsById(profileId, id);
    }

    public void deleteMailFromTrashById(int profileId, int id) throws InstanceNotFoundException {
        mailService.deleteMailFromTrashById(profileId, id);
    }
}
