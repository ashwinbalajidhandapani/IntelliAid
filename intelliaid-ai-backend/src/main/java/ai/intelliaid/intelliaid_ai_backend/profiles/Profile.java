package ai.intelliaid.intelliaid_ai_backend.profiles;

public record Profile(
		String id,
		String firstName,
		String lastName,
		int age,
		Gender gender,
		String ethinicity,
		String bio,
		String imageUrl,
		String myersbrigsPersonality
) {

}
