package utils;

import data.models.Mail;
import data.models.Profile;
import dtos.request.MailRequest;
import dtos.request.RegisterRequest;
import dtos.response.LoginResponse;
import dtos.response.MailResponse;
import dtos.response.RegisterResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static Profile map(RegisterRequest request) {
        Profile profile = new Profile();
        profile.setFirstName(convertToSentenceCase(request.getFirstName()));
        profile.setLastName(convertToSentenceCase(request.getLastName()));
        profile.setDayOfBirth(request.getDayOfBirth());
        profile.setMonthOfBirth(request.getMonthOfBirth());
        profile.setYearOfBirth(request.getYearOfBirth());
        String username = !request.getUsername().contains("@regnos.com") ? (request.getUsername()+"@regnos.com").toLowerCase() : request.getUsername();
        profile.setUsername(username);
        profile.setPassword(request.getPassword());
        return profile;
    }

    public static Mail map(MailRequest mailRequest) {
        Mail mail = new Mail();
        mail.setSubject(mailRequest.getSubject());
        mail.setBody(mailRequest.getBody());
        mail.setProfileId(mailRequest.getProfileId());
        mail.setSender(mailRequest.getSender());
        mail.setRecipient(mailRequest.getRecipient());
        return mail;
    }

    public static RegisterResponse map(Profile profile) {
        RegisterResponse response = new RegisterResponse();
        response.setName(profile.getFirstName().concat(" ").concat(profile.getLastName()));
        response.setId(profile.getId());
        response.setUsername(profile.getUsername());
        response.setDob(profile.getDayOfBirth()+"/"+profile.getMonthOfBirth()+"/"+profile.getYearOfBirth());
        response.setDateRegistered(profile.getTimeCreated().toLocalDate());
        response.setTimeRegistered(profile.getTimeCreated().toLocalTime());
        return response;
    }

    public static MailResponse map(Mail mail) {
        MailResponse response = new MailResponse();
        response.setId(mail.getId());
        response.setSubject(mail.getSubject());
        response.setBody(mail.getBody());
        response.setRecipient(mail.getRecipient());
        response.setSender(mail.getSender());
        response.setDateGenerated(mail.getTimeCreated().toLocalDate());
        response.setTimeGenerated(mail.getTimeCreated().toLocalTime());
        return response;
    }

    public static List<MailResponse> map(List<Mail> mails) {
        List <MailResponse> response = new ArrayList<>();
        for (var mail: mails) {
            response.add(map(mail));
        }
        return response;
    }

    public static LoginResponse map(RegisterResponse registerResponse){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(registerResponse.getId());
        loginResponse.setName(registerResponse.getName());
        loginResponse.setDob(registerResponse.getDob());
        loginResponse.setDateRegistered(registerResponse.getDateRegistered());
        loginResponse.setTimeRegistered(registerResponse.getTimeRegistered());
        loginResponse.setUsername(registerResponse.getUsername());
        loginResponse.setInboxHeadlines(loginResponse.getInbox());
        loginResponse.setSentMailsHeadlines(loginResponse.getInbox());
        loginResponse.setTrashHeadlines(loginResponse.getInbox());
        return loginResponse;
    }
    private static String convertToSentenceCase(String word){
        return word.replace(word.charAt(0), Character.toUpperCase(word.charAt(0)));
    }
}
