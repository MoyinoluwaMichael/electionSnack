package data.repositories.mailRepo;

import data.models.Mail;

import java.util.List;

public interface MailRepo {
    Mail save(Mail mail);
    Mail findById(int profileId, int id);
    long countByProfileId(int profileId);
    long countAll();
    void deleteById(int profileId, int id);

    List<Mail> findAll(int profileId);
}
