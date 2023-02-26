package data.repositories.mailRepo.outboxRepo;

import data.models.Mail;

import java.util.ArrayList;
import java.util.List;

public class outboxRepoImp implements outboxRepo {

    private List<Mail> outbox = new ArrayList<>();
    private int count;
    @Override
    public Mail save(Mail mail) {
        boolean mailHasNotBeenSaved = mail.getId() == 0;
        if (mailHasNotBeenSaved) return saveNewMail(mail);
        return mail;
    }

    private Mail saveNewMail(Mail mail) {
        mail.setId(generateId(mail));
        outbox.add(mail);
        count++;
        return mail;
    }
    private int generateId(Mail mail) {
        return (int) (countByProfileId(mail.getProfileId()) + 1);
    }

    @Override
    public Mail findById(int profileId, int id) {
        for (var mail: outbox) {
            System.out.println(mail.getProfileId());
            System.out.println(mail.getId());
            if (mail.getProfileId() == profileId && mail.getId() == id) return mail;
        }
        return null;
    }

    @Override
    public long countByProfileId(int profileId) {
        long outboxCount = 0;
        for (var mail : outbox) if (mail.getProfileId() == profileId) outboxCount++;
        return outboxCount;
    }

    @Override
    public long countAll() {
        return count;
    }

    @Override
    public void deleteById(int profileId, int id) {
        for (var mail : outbox) if (mail.getProfileId() == profileId && mail.getId() == id){
            count--;
            outbox.remove(mail);
            return;
        }
    }
}
