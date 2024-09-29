package ai.intelliaid.intelliaid_ai_backend.profiles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping("/profile")
    public ResponseEntity<Profile> profile(@RequestBody Profile profile) {
        profileRepository.save(profile);
        return ResponseEntity.ok(profile);
    }
}
