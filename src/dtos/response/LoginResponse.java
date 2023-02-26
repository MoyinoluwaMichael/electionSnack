package dtos.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LoginResponse {
    private int id;
    private String name;
    private String username;
    private String dob;
    private LocalDate dateRegistered;
    private LocalTime timeRegistered;
    private List<MailResponse> inbox = new ArrayList<>();
    private String inboxHeadlines = "";
    private List<MailResponse> sentMails = new ArrayList<>();;
    private String sentMailsHeadlines = "";
    private List<MailResponse> trash = new ArrayList<>();;
    private String trashHeadlines = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public LocalTime getTimeRegistered() {
        return timeRegistered;
    }

    public void setTimeRegistered(LocalTime timeRegistered) {
        this.timeRegistered = timeRegistered;
    }

    public List<MailResponse> getInbox() {
        return inbox;
    }

    public void setInbox(List<MailResponse> inbox) {
        setInboxHeadlines(inbox);
        this.inbox = inbox;
    }

    public List<MailResponse> getSentMails() {
        return sentMails;
    }

    public void setSentMails(List<MailResponse> sentMails) {
        setSentMailsHeadlines(sentMails);
        this.sentMails = sentMails;
    }

    public List<MailResponse> getTrash() {
        return trash;
    }

    public void setTrash(List<MailResponse> trash) {
        setTrashHeadlines(trash);
        this.trash = trash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getInboxHeadlines() {
        return inboxHeadlines;
    }

    public void setInboxHeadlines(List<MailResponse> list) {
        inboxHeadlines = "";
        int count = 1;
        for (var mail : list) {
            String sender = mail.getSender().split("@")[0].toLowerCase();
            sender = sender.replace(sender.charAt(0), Character.toUpperCase(sender.charAt(0)));
            inboxHeadlines += String.format("""
                            %s. From: %s   -   %s - %s
                            %s
                            """, count, sender, mail.getDateGenerated(), mail.getTimeGenerated(),
                    mail.getSubject());
            count++;
        }
    }

    public String getSentMailsHeadlines() {
        return sentMailsHeadlines;
    }

    public void setSentMailsHeadlines(List<MailResponse> list) {
        sentMailsHeadlines = "";
        int count = 1;
        for (var mail : list) {
            String recipient = mail.getRecipient().split("@")[0].toLowerCase();
            recipient = recipient.replace(recipient.charAt(0), Character.toUpperCase(recipient.charAt(0)));
            sentMailsHeadlines += String.format("""
                            %s. To: %s   -   %s - %s
                            %s
                            """, count, recipient, mail.getDateGenerated(), mail.getTimeGenerated(),
                    mail.getSubject());
            count++;
        }
    }

    public String getTrashHeadlines() {
        return trashHeadlines;
    }

    public void setTrashHeadlines(List<MailResponse> list) {
        trashHeadlines = "";
        int count = 1;
        for (var mail : list) {
            String recipient = mail.getRecipient().split("@")[0].toLowerCase();
            recipient = recipient.replace(recipient.charAt(0), Character.toUpperCase(recipient.charAt(0)));
            trashHeadlines += String.format("""
                            %s. To: %s   -   %s - %s
                            %s
                            """, count, recipient, mail.getDateGenerated(), mail.getTimeGenerated(),
                    mail.getSubject());
            count++;
        }
    }
}
