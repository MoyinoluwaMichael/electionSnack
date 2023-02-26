package dtos.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MailResponse {
    private int id;

    private String subject;
    private String body;
    private String sender;
    private String recipient;
    private String dateGenerated;
    private String timeGenerated;

    public String getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(LocalDate dateGenerated) {
        String day = String.valueOf(dateGenerated.getDayOfMonth());
        String month = String.valueOf(dateGenerated.getMonth().getValue());
        String year = String.valueOf(dateGenerated.getYear());
        this.dateGenerated = day+"/"+month+"/"+year;
    }

    public String getTimeGenerated() {
        return timeGenerated;
    }

    public void setTimeGenerated(LocalTime timeGenerated) {
        String hour = String.valueOf(timeGenerated.getHour());
        String minute = String.valueOf(timeGenerated.getMinute());
        this.timeGenerated = hour+":"+minute;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return String.format("""
                ============================================
                Date: %-20s Time: %-5s
                ============================================
                From: %s
                To: %s
                ============================================
                Subject: %s
                ============================================
                %s
                ============================================
                """, dateGenerated, timeGenerated, sender, recipient, subject, body);
    }
}
