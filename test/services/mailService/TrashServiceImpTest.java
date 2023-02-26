package services.mailService;

import data.models.Mail;
import dtos.request.MailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import javax.management.InstanceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrashServiceImpTest {

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
        mail1 = mailService.sendMail(Mapper.map(mailRequest));
        mailRequest.setProfileId(2);
        mail2 = mailService.sendMail(Mapper.map(mailRequest));
    }

    @DisplayName("Mail in trash can be found")
    @Test void mailCanBeFoundAfterDeletingTest() throws InstanceNotFoundException {
        mailService.deleteMailFromSentMailsById(1, 1);
        assertEquals(mail1, mailService.findFromTrash(1, 1));
    }

    @DisplayName("Mail in trash can be deleted")
    @Test void sendOneMail_deleteOneSentMail_deleteOneMailFromTrash_trashCountIsZero() throws InstanceNotFoundException {
        mailService.deleteMailFromSentMailsById(1, 1);
        mailService.deleteMailFromTrashById(1,1);
        assertEquals(0, mailService.countTrashByProfileId(1));
    }

    @Test void sendOneMail_deleteOneSentMail_trashCountIsOne() throws InstanceNotFoundException {
        mailService.deleteMailFromSentMailsById(1,1);
        assertEquals(1, mailService.countTrashByProfileId(1));
    }
}
