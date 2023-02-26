package data.repositories.mailRepo.trashRepo;

import data.models.Mail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrashRepoImp implements TrashRepo {

    private List<Mail> trash = new ArrayList<>();
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
        trash.add(mail);
        count++;
        return mail;
    }

    private int generateId(Mail mail) {
        return (int) (countByProfileId(mail.getProfileId()) + 1);
    }

    @Override
    public Mail findById(int profileId, int id) {
        for (var mail : trash) {
            System.out.println(mail.getProfileId());
            System.out.println(mail.getId());
            if (mail.getProfileId() == profileId && mail.getId() == id) return mail;
        }
        return null;
    }

    @Override
    public long countByProfileId(int profileId) {
        long trashCount = 0;
        for (var mail : trash){
            if (mail.getProfileId() == profileId) trashCount++;
        }
        return trashCount;
    }

    @Override
    public long countAll() {
        return count;
    }

    @Override
    public void deleteById(int profileId, int id) {
        for (var mail : trash)
            if (mail.getProfileId() == profileId && mail.getId() == id) {
                count--;
                trash.remove(mail);
                return;
            }
    }

    @Override
    public List<Mail> findAll(int profileId) {
        List<Mail> userTrash = new ArrayList<>();
        for (var mail : trash) if (mail.getProfileId() == profileId) userTrash.add(mail);
        return userTrash;
    }
}
