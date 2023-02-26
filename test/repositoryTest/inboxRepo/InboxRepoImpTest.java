package repositoryTest.inboxRepo;

import data.models.Mail;
import data.models.Profile;
import data.repositories.mailRepo.MailRepo;
import data.repositories.mailRepo.inboxRepo.InboxRepoImp;
import data.repositories.profileRepo.ProfileRepo;
import data.repositories.profileRepo.ProfileRepoImp;
import dtos.request.MailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import static org.junit.jupiter.api.Assertions.*;

class InboxRepoImpTest {
    MailRepo inboxRepo;
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
        inboxRepo = new InboxRepoImp();
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Pressure");
        mailRequest.setBody("Dear Mike, pressure ti wa oo.");
        mailRequest.setProfileId(profile.getId());
        mail = inboxRepo.save(Mapper.map(mailRequest));
        profile2 = profileRepo.save(new Profile());
        mail2 = new Mail();
        mail2.setProfileId(profile2.getId());
        inboxRepo.save(mail2);
    }

    @Test void saveOneMailInInboxForUserX_inboxMailCountIsOne(){
        assertEquals(1, inboxRepo.countByProfileId(1));
    }

    @Test void saveOneUser_X_Mail_idOfProfileMailIsOneTest(){
        assertEquals(1, mail.getId());
    }

    @Test void saveTwoMailsWithSameId_countIsOne(){
        mail = inboxRepo.save(mail);
        assertEquals(1, inboxRepo.countByProfileId(1));
    }
    @Test void saveOneMailForUserXAndYEach_userXAndYMailIdIsOneEach_totalInboxCountIsTwo(){
        assertEquals(1, mail.getId());
        assertEquals(1, mail2.getId());
        assertEquals(2, inboxRepo.countAll());
    }

    @Test void saveOneMailForUserXAndYEach_findBothMailByProfileIdAndMailId(){
        assertEquals(mail, inboxRepo.findById(1, 1));
        assertEquals(mail2, inboxRepo.findById(2, 1));
        assertEquals(2, inboxRepo.countAll());
    }

    @Test void saveOneMailForUserXAndYEach_deleteUserXMail_UserXAndYInboxCountIsZeroAndOneConsecutively_totalInboxCountIsOne(){
        inboxRepo.deleteById(1, 1);
        assertEquals(0, inboxRepo.countByProfileId(1));
        assertEquals(1, inboxRepo.countByProfileId(2));
        assertEquals(1, inboxRepo.countAll());
    }
}