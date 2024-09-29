package ai.intelliaid.intelliaid_ai_backend.profiles;

import java.util.UUID;

public class ProfileMapper {

    public static Profile map(ProfileRequestDTO profilereq) {
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID().toString());
        profile.setFirstName(profilereq.getFirstName());
        profile.setLastName(profilereq.getLastName());
//        if(profilereq.getGender() != null) profile.setGender(profilereq.getGender());
//        else profile.setGender(null);
        return profile;
    }
}
