package data.repositories.mailRepo.trashRepo;

import data.models.Mail;
import data.models.Profile;
import data.repositories.MailRepo;
import data.repositories.profileRepo.ProfileRepo;
import data.repositories.profileRepo.ProfileRepoImp;
import dtos.request.MailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import static org.junit.jupiter.api.Assertions.*;

class TrashRepoImpTest {

    MailRepo trashRepo;
    Mail mail;
    Profile profile;
    ProfileRepo profileRepo;
    Profile profile2;
    Mail mail2;

    @BeforeEach
    void startWith(){
        profile = new Profile();
        profileRepo = new ProfileRepoImp();
        profileRepo.save(profile);
        trashRepo = new TrashRepoImp();
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Pressure");
        mailRequest.setBody("Dear Mike, pressure ti wa oo.");
        mailRequest.setProfileId(profile.getId());
        mail = trashRepo.save(Mapper.map(mailRequest));
        profile2 = profileRepo.save(new Profile());
        mail2 = new Mail();
        mail2.setProfileId(profile2.getId());
        trashRepo.save(mail2);
    }

    @Test
    void saveOneMailInTrashForUserX_TrashCountIsOne(){
        assertEquals(1, trashRepo.countByProfileId(1));
    }

    @Test void saveOneUser_X_Mail_idOfProfileMailIsOneTest(){
        assertEquals(1, mail.getId());
    }

    @Test void saveTwoMailsWithSameId_countIsOne(){
        mail = trashRepo.save(mail);
        assertEquals(1, trashRepo.countByProfileId(1));
    }

    @Test void saveOneMailForUserXAndYEach_userXAndYMailIdIsOneEach_totalTrashCountIsTwo(){
        assertEquals(1, mail.getId());
        assertEquals(1, mail2.getId());
        assertEquals(2, trashRepo.countAll());
    }

    @Test void saveOneMailForUserXAndYEach_findBothMailByProfileIdAndMailId(){
        assertEquals(mail, trashRepo.findById(1, 1));
        assertEquals(mail2, trashRepo.findById(2, 1));
        assertEquals(2, trashRepo.countAll());
    }

    @Test void saveOneMailForUserXAndYEach_deleteUserXMail_UserXAndYTrashCountIsZeroAndOneConsecutively_totalTrashCountIsOne(){
        trashRepo.deleteById(1, 1);
        assertEquals(0, trashRepo.countByProfileId(1));
        assertEquals(1, trashRepo.countByProfileId(2));
        assertEquals(1, trashRepo.countAll());
    }

}