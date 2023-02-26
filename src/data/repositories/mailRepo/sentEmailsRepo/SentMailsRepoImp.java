package data.repositories.mailRepo.sentEmailsRepo;

import data.models.Mail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SentMailsRepoImp implements SentMailsRepo {

    private List<Mail> sentMails = new ArrayList<>();
    private int count;
    @Override
    public Mail save(Mail mail) {
        boolean mailHasNotBeenSaved = mail.getId() == 0;
        if (mailHasNotBeenSaved) return saveNewMail(mail);
        return mail;
    }

    private Mail saveNewMail(Mail mail) {
        mail.setId(generateId(mail));
        mail.setTimeCreated(LocalDateTime.now());
        sentMails.add(mail);
        count++;
        return mail;
    }
    private int generateId(Mail mail) {
        return (int) (countByProfileId(mail.getProfileId()) + 1);
    }

    @Override
    public Mail findById(int profileId, int id) {
        for (var mail: sentMails) {
            if (mail.getProfileId() == profileId && mail.getId() == id) return mail;
        }
        return null;
    }

    @Override
    public long countByProfileId(int profileId) {
        long outboxCount = 0;
        for (var mail : sentMails) if (mail.getProfileId() == profileId) outboxCount++;
        return outboxCount;
    }

    @Override
    public long countAll() {
        return count;
    }

    @Override
    public void deleteById(int profileId, int id) {
        for (var mail : sentMails) if (mail.getProfileId() == profileId && mail.getId() == id){
            count--;
            sentMails.remove(mail);
            return;
        }
    }

    @Override
    public List<Mail> findAll(int profileId) {
        List<Mail> userSentMails = new ArrayList<>();
        for (var mail : sentMails) if (mail.getProfileId() == profileId) userSentMails.add(mail);
        return sentMails;
    }
}
