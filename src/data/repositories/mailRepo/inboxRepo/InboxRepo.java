package data.repositories.inboxRepo;

import data.models.Mail;
import data.repositories.MailRepo;

public interface InboxRepo extends MailRepo {

    Mail save(Mail mail);
    Mail findById(int profileId, int id);
    long countByProfileId(int profileId);
    long countAll();
    void deleteById();
}
