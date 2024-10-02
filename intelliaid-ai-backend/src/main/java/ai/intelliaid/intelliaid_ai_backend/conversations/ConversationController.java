package ai.intelliaid.intelliaid_ai_backend.conversations;
import ai.intelliaid.intelliaid_ai_backend.conversations.airelated.AIModelService;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	private final AIModelService aiModelService;
	
	public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, AIModelService aiModelService) {
		this.conversationRepository = conversationRepository;
		this.profileRepository = profileRepository;
		this.aiModelService = aiModelService;
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
	@PutMapping("/conversation/conversation-id={conversationId}")
	public ResponseEntity<Conversation> addNewMessage(@PathVariable String conversationId, @RequestBody ChatMessage chatMessage){
		// Validating if the conversation ID exisits
		Conversation conversation = conversationRepository
				.findById(conversationId)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation Id not valid"));

		// Validating if the author Id for message exists
		profileRepository
		.findById(chatMessage.getAuthorId())
		.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author ID not valid"));
		
		// Setting time stamp for message
		ChatMessage updatedMessage = new ChatMessage(chatMessage.getMessageText(),
				chatMessage.getAuthorId(),
				chatMessage.getProfileId(),
				LocalDateTime.now());

		conversation.getMessages().add(updatedMessage);
		conversation.getMessages().add(aiModelService.getModelResponse(chatMessage, conversation));
		conversationRepository.save(conversation);
		Conversation conversationResponse = conversationRepository
				.findById(conversationId)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation Id not valid"));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(conversationResponse);
		
	}
	
	@GetMapping("/conversation/")
	public ResponseEntity<List<Conversation>> getAllConversations(){
		List<Conversation> allConversations = conversationRepository.findAll();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(allConversations);
	}
	
	@GetMapping("/conversation/conversation-id={conversationId}")
	public ResponseEntity<Conversation> getConversationBasedOnConversationId(@PathVariable String conversationId){
		Conversation conversation = conversationRepository
				.findById(conversationId)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid conversation Id"));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(conversation);
	}
	
	// This record is while creating a new conversation for the very first time
	public record createConversationRequest(
			String profileId) {}


}
