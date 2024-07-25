package ai.intelliaid.intelliaid_ai_backend.conversations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ai.intelliaid.intelliaid_ai_backend.profiles.ProfileRepository;

@RestController
public class ConversationController {
	
	private final ConversationRepository conversationRepository;
	private final ProfileRepository profileRepository;
	
	
	public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository) {
		this.conversationRepository = conversationRepository;
		this.profileRepository = profileRepository;
	}

	// Creates new Conversation for the very first time
	@PostMapping("/conversation")
	public ResponseEntity<Conversation> createNewConversation(@RequestBody createConversationRequest conversationRequest) {
		profileRepository.findById(conversationRequest.profileId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Conversation conversation = new Conversation(
				UUID.randomUUID().toString(), 
				conversationRequest.profileId, 
				new ArrayList<>());
		conversationRepository.save(conversation);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(conversation);
		
	}
	
	
	// Add message in a conversation
	@PutMapping("/conversation/{conversationId}")
	public ResponseEntity<Conversation> addNewMessage(@PathVariable String conversationId, @RequestBody ChatMessage chatMessage){
		// Validating if the conversation ID exisits
		Conversation conversation = conversationRepository
				.findById(conversationId)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation Id not valid"));
		
		// Validating if the author Id for message exists
		profileRepository
		.findById(chatMessage.authorId())
		.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author ID not valid"));
		
		// Setting time stamp for message
		ChatMessage updatedMessage = new ChatMessage(chatMessage.messageText(), chatMessage.authorId(), chatMessage.profileId(), LocalDateTime.now());
		conversation.messages().add(updatedMessage);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(conversation);
		
	}
	
	// This record is while creating a new conversation for the very first time
	public record createConversationRequest(
			String profileId) {}
}
