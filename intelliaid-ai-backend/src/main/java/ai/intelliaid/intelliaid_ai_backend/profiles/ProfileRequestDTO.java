package ai.intelliaid.intelliaid_ai_backend.profiles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String ethnicity;
    private String bio;
    private String imageUrl;
    private String myersbrigsPersonality;
}
