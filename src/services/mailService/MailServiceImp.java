package services.mailService;

import data.models.Mail;
import data.repositories.mailRepo.MailRepo;
import data.repositories.mailRepo.inboxRepo.InboxRepoImp;
import data.repositories.mailRepo.sentEmailsRepo.SentMailsRepoImp;
import data.repositories.mailRepo.trashRepo.TrashRepoImp;

import javax.management.InstanceNotFoundException;
import java.util.List;

public class MailServiceImp implements MailService {

    private MailRepo inboxRepo = new InboxRepoImp();
    private MailRepo sentMailsRepo = new SentMailsRepoImp();
    private MailRepo trashRepo = new TrashRepoImp();

    @Override
    public Mail sendMail(Mail mail) {
        return sentMailsRepo.save(mail);
    }

    @Override
    public long countSentMailsByProfileId(int profileId) {
        return sentMailsRepo.countByProfileId(profileId);
    }

    @Override
    public long countAllSentMails() {
        return sentMailsRepo.countAll();
    }

    @Override
    public Mail findFromSentMails(int profileId, int id) throws InstanceNotFoundException {
        Mail mail = sentMailsRepo.findById(profileId, id);
        validateMail(mail);
        return mail;
    }

    @Override
    public void deleteMailFromSentMailsById(int profileId, int id) throws InstanceNotFoundException {
        Mail mail = findFromSentMails(profileId, id);
        validateMail(mail);
        sentMailsRepo.deleteById(profileId, id);
        mail.setId(0);
        trashRepo.save(mail);
    }

    @Override
    public long countTrashByProfileId(int profileId) {
        return trashRepo.countByProfileId(profileId);
    }

    @Override
    public Mail receiveMail(Mail mail) {
        return inboxRepo.save(mail);
    }

    @Override
    public long countInboxByProfileId(int profileId) {
        return inboxRepo.countByProfileId(profileId);
    }

    @Override
    public long countAllInbox() {
        return inboxRepo.countAll();
    }

    @Override
    public Mail findFromInbox(int profileId, int id) throws InstanceNotFoundException {
        Mail mail = inboxRepo.findById(profileId, id);
        validateMail(mail);
        return mail;
    }

    @Override
    public void deleteMailFromInboxById(int profileId, int id) throws InstanceNotFoundException {
        Mail mail = inboxRepo.findById(profileId, id);
        validateMail(mail);
        mail.setId(0);
        trashRepo.save(mail);
        inboxRepo.deleteById(profileId, id);
    }

    @Override
    public List<Mail> getInbox(int profileId) {
        return inboxRepo.findAll(profileId);
    }

    @Override
    public List<Mail> getSentMails(int profileId) {
        return sentMailsRepo.findAll(profileId);
    }

    @Override
    public List<Mail> getTrash(int profileId) {
        return trashRepo.findAll(profileId);
    }

    @Override
    public Mail findFromTrash(int profileId, int id) throws InstanceNotFoundException {
        Mail mail = trashRepo.findById(profileId, id);
        validateMail(mail);
        return mail;
    }

    @Override
    public void deleteMailFromTrashById(int profileId, int id) throws InstanceNotFoundException {
        Mail mail = trashRepo.findById(profileId, id);
        validateMail(mail);
        trashRepo.deleteById(profileId, id);
    }

    private static void validateMail(Mail mail) throws InstanceNotFoundException {
        if (mail == null) throw new InstanceNotFoundException("Mail does not exist or has been deleted");
    }
}
