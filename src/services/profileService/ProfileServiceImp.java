package services.profileService;

import com.sun.jdi.request.DuplicateRequestException;
import data.models.Profile;
import data.repositories.profileRepo.ProfileRepo;
import data.repositories.profileRepo.ProfileRepoImp;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;

public class ProfileServiceImp implements ProfileService{

    private ProfileRepo profileRepo = new ProfileRepoImp();
    @Override
    public Profile register(Profile profile) {
        validateProfile(profile);
        return profileRepo.save(profile);
    }

    private void validateProfile(Profile profile) {
        validateUsername(profile);
        validateDob(profile);
        validatePassword(profile.getPassword());
        validateFirstname(profile.getFirstName());
        validateLastname(profile.getLastName());
    }

    private void validateLastname(String lastName) {
        if (lastName == null) throw new IllegalArgumentException("You must specify your first name");
    }

    private void validateFirstname(String firstname) {
        if (firstname == null) throw new IllegalArgumentException("You must specify your first name");
    }

    private void validateDob(Profile profile) {
        validateYear(profile);
        validateMonth(profile);
        validateDay(profile);
    }

    private void validateMonth(Profile profile) {
        if (profile.getMonthOfBirth().length() != 2) throw new IllegalArgumentException("Wrong format, month format should be: 'mm'");
        int month = Integer.parseInt(profile.getMonthOfBirth());
        if (month < 1 || month > 12) throw new IllegalArgumentException("Month should be between 1 and 12");
    }

    private void validateYear(Profile profile) {
        if (profile.getYearOfBirth().length() != 4) throw new IllegalArgumentException("Wrong format, Year format should be 'yyyy'");
    }

    private void validateDay(Profile profile) {
        if (profile.getMonthOfBirth().length() != 2) throw new IllegalArgumentException("Wrong format, day format should be: 'dd'");
        int year = Integer.parseInt(profile.getYearOfBirth());
        int month = Integer.parseInt(profile.getMonthOfBirth());
        int day = Integer.parseInt(profile.getDayOfBirth());
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        if (day < 1 || day > lengthOfMonth) throw new IllegalArgumentException("Day specified is not within month range (1 - "+lengthOfMonth+")");
    }

    private void validatePassword(String password) {

    }

    private void validateUsername(Profile profile) {
        if (profileRepo.findByUsername(profile.getUsername()) != null) {
            throw new DuplicateRequestException("Username already exist!");
        }
    }

    @Override
    public Profile findById(int id) throws InstanceNotFoundException {
        Profile profile = profileRepo.findById(id);
        if (profile == null) throw new InstanceNotFoundException("Profile not found or has been deleted.");
        return profile;
    }

    @Override
    public Profile findByUsername(String username) throws InstanceNotFoundException {
        Profile profile = profileRepo.findByUsername(username);
        if (profile == null) {
            throw new InstanceNotFoundException("Account does not exist");
        }
        return profile;
    }

    @Override
    public void validateLoginRequest(String username, String password) throws InstanceNotFoundException {
        Profile profile = findByUsername(username);
        if (!profile.getPassword().equals(password)) throw new IllegalArgumentException("Username or password is incorrect");
    }
}
