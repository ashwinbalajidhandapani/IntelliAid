package ai.intelliaid.intelliaid_ai_backend.profiles;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile{
	String id;
	String firstName;
	String lastName;
	int age;
	Gender gender;
	String ethinicity;
	String bio;
	String imageUrl;
	String myersbrigsPersonality;
}
