package ai.intelliaid.intelliaid_ai_backend.profiles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/profile")
    public ResponseEntity<List<Profile>> getProfiles() {
        return ResponseEntity.ok(profileRepository.findAll());
    }

    @GetMapping("/profile/id={id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable String id) {
        return ResponseEntity.ok(profileRepository.findById(id).orElse(null));
    }
}
