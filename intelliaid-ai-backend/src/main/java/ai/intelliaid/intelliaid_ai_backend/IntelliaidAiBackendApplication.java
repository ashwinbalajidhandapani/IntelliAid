package ai.intelliaid.intelliaid_ai_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ai.intelliaid.intelliaid_ai_backend.profiles.Gender;
import ai.intelliaid.intelliaid_ai_backend.profiles.Profile;
import ai.intelliaid.intelliaid_ai_backend.profiles.ProfileRepository;

@SpringBootApplication
public class IntelliaidAiBackendApplication implements CommandLineRunner{
	
	private ProfileRepository profileRepository;
	
	public IntelliaidAiBackendApplication(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(IntelliaidAiBackendApplication.class, args);
	}
	
	public void run(String... args) {
		
		Profile lordCommandor = new Profile(
				"1", 
				"Ashwin", 
				"Dragon", 
				27, 
				Gender.MALE, 
				"Indian", 
				"Outdoorsy", 
				"image.url", 
				"INTJ");
		profileRepository.save(lordCommandor);
		
		Profile captain = new Profile(
				"2", 
				"Sharon", 
				"Dragon", 
				27, 
				Gender.FEMALE, 
				"Indian", 
				"Master", 
				"image.url", 
				"INTJ");
		profileRepository.save(captain);
		
		System.out.println("Master profile creation complete");
	}
}
