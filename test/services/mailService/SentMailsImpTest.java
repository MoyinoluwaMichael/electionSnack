package services.mailService;

import data.models.Mail;
import dtos.request.MailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import static org.junit.jupiter.api.Assertions.*;

class MailServiceImpTest {
    MailService mailService;
    Mail mail1;
    Mail mail2;
    MailRequest mailRequest;

    @BeforeEach void startWith(){
        mailService = new MailServiceImp();
        mailRequest = new MailRequest();
        mailRequest.setProfileId(1);
        mailRequest.setSubject("What's going on in this life");
        mailRequest.setBody("Wahala! Wahala!! Wahala!!! Ilu leee!!!");
        mail1 = mailService.sendMail(Mapper.map(mailRequest));
        mailRequest.setProfileId(2);
        mail2 = mailService.sendMail(Mapper.map(mailRequest));
    }

    @DisplayName("Mail can be composedAndSent")
    @Test void sendOneMail_mailIdIsOneTest(){
        assertEquals(1, mail1.getId());
    }

    @Test void sendOneMail_userOutboxCountIsOneTest(){
        assertEquals(1, mailService.countSentMailsByProfileId(1));
    }

    @Test void sendOneMailEachForUserXAndY_outboxCountIsOneEachForBothUsers_totalOutboxCountIsTwoTest(){
        assertEquals(1, mailService.countSentMailsByProfileId(1));
        assertEquals(1, mailService.countSentMailsByProfileId(2));
        assertEquals(2, mailService.countAllSentMails());
    }

    @DisplayName("Mail can be found")
    @Test void mailCanBeFoundAfterSendingTest(){
        assertEquals(mail1, mailService.findFromSentMails(1, 1));
    }

    @DisplayName("Mail can be deleted")
    @Test void sendOneMail_deleteOneSentMail_sentMailsCountIsZero(){
        mailService.deleteMailById(1,1);
        assertEquals(0, mailService.countSentMailsByProfileId(1));
    }

    @Test void sendOneMail_deleteOneSentMail_trashCountIsOne(){
        mailService.deleteMailById(1,1);
        assertEquals(1, mailService.countTrashByProfileId(1));
    }

    @Test void
    sendTwoMailsEachForUserXAndY_deleteOneMailForUserX_outboxCountIsOneForUserXAndTwoForUserY_totalOutboxCountIsThreeTest(){
        mailRequest.setProfileId(1);
        mailService.sendMail(Mapper.map(mailRequest));
        mailRequest.setProfileId(2);
        mailService.sendMail(Mapper.map(mailRequest));
        assertEquals(2, mailService.countSentMailsByProfileId(1));
        assertEquals(2, mailService.countSentMailsByProfileId(2));
        assertEquals(4, mailService.countAllSentMails());

        mailService.deleteMailById(1, 1);
        assertEquals(1, mailService.countSentMailsByProfileId(1));
        assertEquals(2, mailService.countSentMailsByProfileId(2));
        assertEquals(3, mailService.countAllSentMails());
    }

    @DisplayName("Mail can be received")
    @Test void receiveOneMail_mailIdIsOneTest(){
//        assertEquals(1, );
    }

}