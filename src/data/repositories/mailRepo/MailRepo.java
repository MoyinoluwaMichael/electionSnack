package data.repositories;

import data.models.Mail;

public interface MailRepo {
    Mail save(Mail mail);
    Mail findById(int profileId, int id);
    long countByProfileId(int profileId);
    long countAll();
    void deleteById(int profileId, int id);
}
