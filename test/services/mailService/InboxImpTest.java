package services.mailService;

import data.models.Mail;
import dtos.request.MailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import javax.management.InstanceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InboxImpTest {
    MailService mailService;
    Mail mail1;
    Mail mail2;
    MailRequest mailRequest;

    @BeforeEach
    void startWith(){
        mailService = new MailServiceImp();
        mailRequest = new MailRequest();
        mailRequest.setProfileId(1);
        mailRequest.setSubject("What's going on in this life");
        mailRequest.setBody("Wahala! Wahala!! Wahala!!! Ilu leee!!!");
        mail1 = mailService.receiveMail(Mapper.map(mailRequest));
        mailRequest.setProfileId(2);
        mail2 = mailService.receiveMail(Mapper.map(mailRequest));
    }

    @DisplayName("Mail can be composedAndSent")
    @Test void sendOneMail_mailIdIsOneTest(){
        assertEquals(1, mail1.getId());
    }

    @Test void receiveOneMail_userInboxCountIsOneTest(){
        assertEquals(1, mailService.countInboxByProfileId(1));
    }

    @Test void sendOneMailEachForUserXAndY_inboxCountIsOneEachForBothUsers_totalInboxCountIsTwoTest(){
        assertEquals(1, mailService.countInboxByProfileId(1));
        assertEquals(1, mailService.countInboxByProfileId(2));
        assertEquals(2, mailService.countAllInbox());
    }

    @DisplayName("Mail can be found")
    @Test void mailCanBeFoundAfterReceivingTest() throws InstanceNotFoundException {
        assertEquals(mail1, mailService.findFromInbox(1, 1));
    }

    @DisplayName("Mail can be deleted")
    @Test void receiveOneMail_deleteOneInboxMail_inboxCountIsZero() throws InstanceNotFoundException {
        mailService.deleteMailFromInboxById(1,1);
        assertEquals(0, mailService.countSentMailsByProfileId(1));
    }

    @Test void receiveOneMail_deleteOneInboxMail_trashCountIsOne() throws InstanceNotFoundException {
        mailService.deleteMailFromInboxById(1,1);
        assertEquals(1, mailService.countTrashByProfileId(1));
    }

    @Test void
    receiveTwoMailsEachForUserXAndY_deleteOneMailForUserX_inboxCountIsOneForUserXAndTwoForUserY_totalInboxCountIsThreeTest() throws InstanceNotFoundException {
        mailRequest.setProfileId(1);
        mailService.receiveMail(Mapper.map(mailRequest));
        mailRequest.setProfileId(2);
        mailService.receiveMail(Mapper.map(mailRequest));
        assertEquals(2, mailService.countInboxByProfileId(1));
        assertEquals(2, mailService.countInboxByProfileId(2));
        assertEquals(4, mailService.countAllInbox());

        mailService.deleteMailFromInboxById(1, 1);
        assertEquals(1, mailService.countInboxByProfileId(1));
        assertEquals(2, mailService.countInboxByProfileId(2));
        assertEquals(3, mailService.countAllInbox());
    }

}
