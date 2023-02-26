package data;

import data.models.Profile;
import org.junit.jupiter.api.BeforeEach;

class ProfileRepoImpTest {

    Profile profile;

    @BeforeEach
    public void startWith(){
        profile = new Profile();
    }

}