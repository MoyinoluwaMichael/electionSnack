package services.profileService;

import com.sun.jdi.request.DuplicateRequestException;
import data.models.Profile;
import dtos.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Mapper;

import javax.management.InstanceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceImpTest {
    ProfileService profileService;
    Profile profile;
    RegisterRequest registerRequest;

    @BeforeEach void startWith(){
        profileService = new ProfileServiceImp();
        profile = new Profile();
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Moyinoluwa");
        registerRequest.setLastName("Moyinoluwa");
        registerRequest.setUsername("MoyinoluwaMichael");
        registerRequest.setPassword("password");
        registerRequest.setDayOfBirth("17");
        registerRequest.setMonthOfBirth("11");
        registerRequest.setYearOfBirth("1999");
        profile = profileService.register(Mapper.map(registerRequest));
    }

    @DisplayName("Registration")
    @Test void userCanRegisterTest(){
        assertEquals(1, profile.getId());
    }

    @Test void registerAUser_regnosSignatureIsSuffixedToUserUsername(){
        assertEquals("moyinoluwamichael@regnos.com", profile.getUsername());
    }

    @Test void userCanBeFoundAfterRegistrationTest() throws InstanceNotFoundException {
        assertEquals(profile, profileService.findById(1));
    }

    @Test void findingUserThatDoesNotExistThrowsException(){
        assertThrows(InstanceNotFoundException.class, ()-> profileService.findById(2));
    }

    @Test void registerAUserWithExistingUsernameThrowsException(){
        registerRequest.setUsername("moyinoluwamichael");
        registerRequest.setPassword("password2");
        registerRequest.setDayOfBirth("17");
        registerRequest.setMonthOfBirth("11");
        registerRequest.setYearOfBirth("1999");
        assertThrows(DuplicateRequestException.class, ()-> profileService.register(Mapper.map(registerRequest)));
    }

    @Test void registeringAUserWithInvalidDateOfBirthThrowsException(){
        registerRequest.setUsername("Moyin");
        registerRequest.setDayOfBirth("34");
        registerRequest.setMonthOfBirth("11");
        registerRequest.setYearOfBirth("1999");
        assertThrows(IllegalArgumentException.class,()->profileService.register(Mapper.map(registerRequest)));
    }

    @DisplayName("User can edit profile")
    @Test void editProfile1Username_confirmUsernameIsNowChanged() throws InstanceNotFoundException {
        profile.setUsername("Abdulkareem");
        assertEquals("Abdulkareem", profileService.findById(1).getUsername());
    }

//    @Test void
//    registeringAUserWithWeakPasswordThrowsException_passwordMustContainAtLeastOneCapitalLetterOneSmallLetterOneSpecialCharacterAndMustBeOfMinimumOfSixLength(){
//        registerRequest.setUsername("Moyin");
//        registerRequest.setPassword("password");
//        assertThrows(IllegalArgumentException.class, ()-> profileService.register(Mapper.map(registerRequest)));
//    }
}