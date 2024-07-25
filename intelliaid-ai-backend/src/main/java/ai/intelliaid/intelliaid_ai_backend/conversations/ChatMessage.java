package ai.intelliaid.intelliaid_ai_backend.conversations;

import java.time.LocalDateTime;

public record ChatMessage(
		String messageText,
		String profileId,
		String authorId,
		LocalDateTime messageTime) {

}
