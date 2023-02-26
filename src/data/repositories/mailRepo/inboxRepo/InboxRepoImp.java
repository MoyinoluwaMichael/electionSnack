package data.repositories.mailRepo.inboxRepo;

import data.models.Mail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InboxRepoImp implements InboxRepo {

    private List <Mail> inbox = new ArrayList<>();
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
        inbox.add(0, mail);
        count++;
        return mail;
    }
    private int generateId(Mail mail) {
        return (int) (countByProfileId(mail.getProfileId()) + 1);
    }

    @Override
    public Mail findById(int profileId, int id) {
        for (var mail: inbox) {
            if (mail.getProfileId() == profileId && mail.getId() == id) return mail;
        }
        return null;
    }

    @Override
    public long countByProfileId(int profileId) {
        long inboxCount = 0;
        for (var mail : inbox) if (mail.getProfileId() == profileId) inboxCount++;
        return inboxCount;
    }

    @Override
    public long countAll() {
        return count;
    }

    @Override
    public void deleteById(int profileId, int id) {
        for (var mail : inbox) if (mail.getProfileId() == profileId && mail.getId() == id){
            count--;
            inbox.remove(mail);
            return;
        }
    }

    @Override
    public List<Mail> findAll(int profileId) {
        List<Mail> userInbox = new ArrayList<>();
        for (var mail : inbox) if (mail.getProfileId() == profileId) userInbox.add(mail);
        return userInbox;
    }
}
