package repositoryTest;

import data.models.Profile;
import data.repositories.profileRepo.ProfileRepo;
import data.repositories.profileRepo.ProfileRepoImp;
import dtos.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import static org.junit.jupiter.api.Assertions.*;

class ProfileRepoImpTest {
    ProfileRepo profileRepo;
    RegisterRequest request;
    Profile email;

    @BeforeEach
    public void startWith(){
        request = new RegisterRequest();
        profileRepo = new ProfileRepoImp();
        request.setFirstName("Moyinoluwa");
        request.setLastName("Michael");
        request.setDayOfBirth("17");
        request.setMonthOfBirth("11");
        request.setYearOfBirth("1999");
        request.setUsername("MoyinoluwaMichael");
        request.setPassword("password");
        email = profileRepo.save(Mapper.map(request));
    }

    @Test void saveOneProfile_profileCountIsOneTest(){
        assertEquals(1, profileRepo.count());
    }

    @Test void saveOneProfile_idOfProfileIsOneTest() {
        assertEquals(1, email.getId());
    }

    @Test void saveTwoProfilesWithSameId_countIsOneTest(){
        profileRepo.save(email);
        assertEquals(1, profileRepo.count());
    }

    @Test void saveOneProfile_findProfileByIdTest(){
        assertEquals(email, profileRepo.findById(1));
    }

    @Test void saveOneProfile_deleteOneProfile_countIsZeroTest(){
        profileRepo.deleteById(1);
        assertEquals(0, profileRepo.count());
    }
}