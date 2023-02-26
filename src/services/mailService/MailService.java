package services.mailService;

import data.models.Mail;
import dtos.request.RegisterRequest;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface MailService {
    Mail sendMail(Mail mail);

    long countSentMailsByProfileId(int profileId);

    long countAllSentMails();

    Mail findFromSentMails(int profileId, int id) throws InstanceNotFoundException;

    void deleteMailFromSentMailsById(int profileId, int id) throws InstanceNotFoundException;

    long countTrashByProfileId(int profileId);

    Mail receiveMail(Mail mail);

    long countInboxByProfileId(int profileId);

    long countAllInbox();

    Mail findFromInbox(int profileId, int id) throws InstanceNotFoundException;

    void deleteMailFromInboxById(int profileId, int id) throws InstanceNotFoundException;

    List<Mail> getInbox(int profileId);

    List<Mail> getSentMails(int profileId);

    List<Mail> getTrash(int profileId);

    Mail findFromTrash(int profileId, int id) throws InstanceNotFoundException;

    void deleteMailFromTrashById(int profileId, int id) throws InstanceNotFoundException;
}
