package ai.intelliaid.intelliaid_ai_backend.conversations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChatMessage{
	String messageText;
	String profileId;
	String authorId;
	LocalDateTime messageTime;
}
