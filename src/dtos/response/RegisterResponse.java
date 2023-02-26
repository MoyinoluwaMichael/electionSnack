package dtos.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RegisterResponse {

    private int id;
    private String name;
    private String username;
    private String dob;
    private LocalTime timeRegistered;
    private LocalDate dateRegistered;
    private long inboxCount;
    private long sentMailCount;
    private long trashCount;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public long getInboxCount() {
        return inboxCount;
    }

    public long getSentMailCount() {
        return sentMailCount;
    }

    public long getTrashCount() {
        return trashCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setInboxCount(long inboxCount) {
        this.inboxCount = inboxCount;
    }

    public void setSentMailCount(long sentMailCount) {
        this.sentMailCount = sentMailCount;
    }

    public LocalTime getTimeRegistered() {
        return timeRegistered;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setTimeRegistered(LocalTime timeRegistered) {
        this.timeRegistered = timeRegistered;
    }

    public void setTrashCount(long trashCount) {
        this.trashCount = trashCount;
    }

    public int getId() {
        return id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String toString(){
        String day = String.valueOf(dateRegistered.getDayOfMonth());
        String month = String.valueOf(dateRegistered.getMonth().getValue());
        String year = String.valueOf(dateRegistered.getYear());
        String hour = String.valueOf(timeRegistered.getHour());
        String minute = String.valueOf(timeRegistered.getMinute());
        return String.format("""
                *****************************************
                            REGNOS EMAIL
                *****************************************
                ACCOUNT PROFILE
                Name: %s
                Date of Birth: %s
                Username: %s
                Created on: %s/%s/%s %s:%s
                *****************************************
                """, name, dob, username, day, month, year, hour, minute);
    }
}
