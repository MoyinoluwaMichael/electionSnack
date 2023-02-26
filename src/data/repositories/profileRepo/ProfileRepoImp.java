package data.repositories.profileRepo;

import data.models.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileRepoImp implements ProfileRepo{
    private List <Profile> profiles = new ArrayList<>();
    private int count;
    @Override
    public Profile save(Profile profile) {
        boolean profileHasNotBeenSaved = profile.getId() == 0;
        if (profileHasNotBeenSaved) return saveNewProfile(profile);
        return profile;
    }

    private Profile saveNewProfile(Profile profile) {
        profile.setId(generateId());
        count++;
        profiles.add(profile);
        return profile;
    }

    private int generateId() {
        return count + 1;
    }

    @Override
    public Profile save(int id) {
        return null;
    }

    @Override
    public Profile findById(int id) {
        for (var profile: profiles) if (profile.getId() == id) return profile;
        return null;
    }

    @Override
    public Profile findAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {
        for (var profile: profiles) if (profile.getId() == id){
            profiles.remove(profile);
            count--;
            return;
        }
    }

    @Override
    public long count() {
        return count;
    }
}
