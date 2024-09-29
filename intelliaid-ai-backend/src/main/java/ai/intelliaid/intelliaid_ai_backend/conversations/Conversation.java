package ai.intelliaid.intelliaid_ai_backend.conversations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@AllArgsConstructor
@Setter
public class Conversation {
	String id;
	String profileId;
	List<ChatMessage> messages;

	public String getId() {
		return id;
	}

	public String getProfileId() {
		return profileId;
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}
}
