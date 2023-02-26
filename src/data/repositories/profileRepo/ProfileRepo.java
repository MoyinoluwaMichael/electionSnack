package data.repositories.profileRepo;

import data.models.Profile;

public interface ProfileRepo{

    Profile save(Profile profile);
    Profile save(int id);
    Profile findById(int id);
    Profile findAll();
    void deleteById(int id);
    long count();

    Profile findByUsername(String username);
}
