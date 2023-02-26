package repositoryTest.sentMailsRepo;

import data.models.Mail;
import data.models.Profile;
import data.repositories.mailRepo.MailRepo;
import data.repositories.mailRepo.sentEmailsRepo.SentMailsRepoImp;
import data.repositories.profileRepo.ProfileRepo;
import data.repositories.profileRepo.ProfileRepoImp;
import dtos.request.MailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import static org.junit.jupiter.api.Assertions.*;

class sentMailsRepoImpTest {
    MailRepo outboxRepo;
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
        outboxRepo = new SentMailsRepoImp();
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Pressure");
        mailRequest.setBody("Dear Mike, pressure ti wa oo.");
        mailRequest.setProfileId(profile.getId());
        mail = outboxRepo.save(Mapper.map(mailRequest));
        profile2 = profileRepo.save(new Profile());
        mail2 = new Mail();
        mail2.setProfileId(profile2.getId());
        outboxRepo.save(mail2);
    }

    @Test
    void saveOneMailInOutboxForUserX_OutboxCountIsOne(){
        assertEquals(1, outboxRepo.countByProfileId(1));
    }

    @Test void saveOneUser_X_Mail_idOfProfileMailIsOneTest(){
        assertEquals(1, mail.getId());
    }

    @Test void saveTwoMailsWithSameId_countIsOne(){
        mail = outboxRepo.save(mail);
        assertEquals(1, outboxRepo.countByProfileId(1));
    }
    @Test void saveOneMailForUserXAndYEach_userXAndYMailIdIsOneEach_totalOutboxCountIsTwo(){
        assertEquals(1, mail.getId());
        assertEquals(1, mail2.getId());
        assertEquals(2, outboxRepo.countAll());
    }

    @Test void saveOneMailForUserXAndYEach_findBothMailByProfileIdAndMailId(){
        assertEquals(mail, outboxRepo.findById(1, 1));
        assertEquals(mail2, outboxRepo.findById(2, 1));
        assertEquals(2, outboxRepo.countAll());
    }

    @Test void saveOneMailForUserXAndYEach_deleteUserXMail_UserXAndYOutboxCountIsZeroAndOneConsecutively_totalOutboxCountIsOne(){
        outboxRepo.deleteById(1, 1);
        assertEquals(0, outboxRepo.countByProfileId(1));
        assertEquals(1, outboxRepo.countByProfileId(2));
        assertEquals(1, outboxRepo.countAll());
    }

}