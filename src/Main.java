import com.sun.jdi.request.DuplicateRequestException;
import controllers.UserController;
import dtos.request.LoginRequest;
import dtos.request.MailRequest;
import dtos.request.RegisterRequest;
import dtos.response.LoginResponse;
import dtos.response.MailResponse;

import javax.management.InstanceNotFoundException;
import javax.swing.*;

public class Main {
    static UserController user = new UserController();
    static LoginResponse profile = new LoginResponse();

    public static void main(String[] args) throws InstanceNotFoundException, InterruptedException {
        goToMainMenu();
    }

    private static void goToMainMenu() throws InstanceNotFoundException, InterruptedException {
        String menu = """
                =============================
                REGNOS EMAIL APP
                =============================
                1. Register
                2. Login
                =============================
                """;
        String menuInput = input(menu);
        switch (menuInput.charAt(0)) {
            case '1' -> registerUser();
            case '2' -> loginUser();
            default -> goToMainMenu();
        }
    }

    private static void loginUser() throws InstanceNotFoundException, InterruptedException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(input("Enter your username"));
        loginRequest.setPassword(input("Enter your password"));
        try {
            profile = user.login(loginRequest);
        } catch (InstanceNotFoundException | IllegalArgumentException e) {
            display(e.getMessage());
            goToMainMenu();
        }
        display("Welcome, "+profile.getUsername());
        gotoLoginPage();
    }

    private static void gotoLoginPage() throws InstanceNotFoundException, InterruptedException {
        String loginPage = String.format("""
                ===============================
                %s
                ===============================
                1. Send mail
                2. Inbox
                3. Sent mails
                4. Trash
                5. Profile
                6. Logout
                7. Exit app
                """, profile.getName());
        String login = input(loginPage);
        switch (login.charAt(0)) {
            case '1' -> composeMail();
            case '2' -> viewInbox();
            case '3' -> viewSentMails();
            case '4' -> viewTrash();
            case '5' -> viewProfile();
            case '6' -> logout();
            case '7' -> exitApp();
            default -> gotoLoginPage();
        }
        gotoLoginPage();
    }

    private static void exitApp() throws InterruptedException {
        display("See you later.");
        System.exit(1);
    }

    private static void logout() throws InstanceNotFoundException, InterruptedException {
        goToMainMenu();
    }

    private static void viewProfile() throws InstanceNotFoundException, InterruptedException {
        display(user.findProfileByUsername(profile.getUsername()).toString());
        gotoLoginPage();
    }

    private static void viewTrash() throws InstanceNotFoundException, InterruptedException {
        String mailIndex = input(profile.getTrashHeadlines()+"0. Back");
        if (mailIndex.equals("0")) gotoLoginPage();
        MailResponse mail = profile.getTrash().get(Integer.parseInt(mailIndex)-1);
        switch (getMailOptionInput().charAt(0)) {
            case '1' -> display(mail.toString());
            case '2' -> deleteMailFromTrash(mail);
            default -> viewTrash();
        }
        gotoLoginPage();
    }

    private static void viewSentMails() throws InstanceNotFoundException, InterruptedException {
        String mailIndex = input(profile.getSentMailsHeadlines()+"0. Back");
        if (mailIndex.equals("0")) gotoLoginPage();
        MailResponse mail = profile.getSentMails().get(Integer.parseInt(mailIndex)-1);
        switch (getMailOptionInput().charAt(0)) {
            case '1' -> display(mail.toString());
            case '2' -> deleteMailFromSentMails(mail);
            default -> viewSentMails();
        }
        gotoLoginPage();
    }

    private static void viewInbox() throws InstanceNotFoundException, InterruptedException {
        String mailIndex = input(profile.getInboxHeadlines()+"0. Back");
        if (mailIndex.equals("0")) gotoLoginPage();
        MailResponse mail = profile.getInbox().get(Integer.parseInt(mailIndex)-1);
        switch (getMailOptionInput().charAt(0)) {
            case '1' -> display(mail.toString());
            case '2' -> deleteMailFromInbox(mail);
            default -> viewInbox();
        }
        gotoLoginPage();
    }

    private static void deleteMailFromTrash(MailResponse mail) {
        try {
            user.deleteMailFromTrashById(profile.getId(), mail.getId());
        } catch (InstanceNotFoundException e) {
            display(e.getMessage());
        }
        profile.setTrash(user.findTrashByProfileId(profile.getId()));
        display("Mail deleted successfully");
    }

    private static void deleteMailFromSentMails(MailResponse mail) {
        try {
            user.deleteMailFromSentMailsById(profile.getId(), mail.getId());
        } catch (InstanceNotFoundException e) {
            display(e.getMessage());
        }
        profile.setSentMails(user.findSentMailsByProfileId(profile.getId()));
        profile.setTrash(user.findTrashByProfileId(profile.getId()));
        display("Mail deleted successfully");
    }

    private static void deleteMailFromInbox(MailResponse mail) {
        try {
            user.deleteMailFromInboxById(profile.getId(), mail.getId());
        } catch (InstanceNotFoundException e) {
            display(e.getMessage());
        }
        profile.setInbox(user.findInboxByProfileId(profile.getId()));
        profile.setTrash(user.findTrashByProfileId(profile.getId()));
        display("Mail deleted successfully");
    }

    private static String getMailOptionInput() throws InstanceNotFoundException, InterruptedException {
        String mailOption = """
                1. View mail
                2. Delete mail
                """;
        return input(mailOption);
    }

    private static void composeMail() throws InstanceNotFoundException, InterruptedException {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setProfileId(profile.getId());
        mailRequest.setSender(profile.getUsername());
        mailRequest.setRecipient(input("Enter recipient username"));
        mailRequest.setSubject(input("Enter the subject of the mail"));
        mailRequest.setBody(input("Enter the body of the mail"));
        try {
            user.sendMail(mailRequest);
        } catch (InstanceNotFoundException e) {
            display("Recipient "+e.getMessage());
            gotoLoginPage();
        }
        profile.setSentMails(user.findSentMailsByProfileId(profile.getId()));
        display("Sent successfully!");
        gotoLoginPage();
    }

    private static void registerUser() throws InstanceNotFoundException, InterruptedException {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName(input("Enter your first name"));
        request.setLastName(input("Enter your last name"));
        request.setDayOfBirth(input("Enter your day of birth i.e: 'dd'"));
        request.setMonthOfBirth(input("Enter your month of birth i.e: 'mm'"));
        request.setYearOfBirth(input("Enter your year of birth i.e: 'yyyy'"));
        request.setUsername(input("Enter your username"));
        request.setPassword(input("Enter your password"));
        try {
            user.registerUSer(request);
        } catch (IllegalArgumentException | DuplicateRequestException e) {
            display(e.getMessage());
            goToMainMenu();
        }
        display("Account registered successfully!");
        goToMainMenu();
    }

    private static String input(String message) throws InstanceNotFoundException, InterruptedException {
        String input =  JOptionPane.showInputDialog(null, message);
        if (input == null) {
            if (profile.getId() == 0) goToMainMenu();
            else gotoLoginPage();
        }
        return input;
    }

    private static void display(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
