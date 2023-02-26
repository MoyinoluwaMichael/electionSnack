package services.profileService;

import data.models.Profile;

import javax.management.InstanceNotFoundException;

public interface ProfileService {
    Profile register(Profile profile);

    Profile findById(int id) throws InstanceNotFoundException;

    Profile findByUsername(String username) throws InstanceNotFoundException;

    void validateLoginRequest(String username, String password) throws InstanceNotFoundException;
}
