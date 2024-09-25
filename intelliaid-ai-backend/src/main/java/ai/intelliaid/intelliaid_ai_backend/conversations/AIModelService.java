package ai.intelliaid.intelliaid_ai_backend.conversations;

import ai.intelliaid.intelliaid_ai_backend.profiles.Profile;
import ai.intelliaid.intelliaid_ai_backend.profiles.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIModelService {
    private static final String OLLAMA_PORT = "5000";
    private static final String OLLAMA_URL ="http://localhost:"+OLLAMA_PORT;
    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;


    public AIModelService(ConversationRepository conversationRepository, ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    public ChatMessage getModelResponse(ChatMessage chatMessage, Conversation conversation){
        StringBuilder promptBuilder = new StringBuilder();

        for(ChatMessage msg : conversation.messages()){
            String authorName = profileRepository.findById(msg.authorId())
                    .map(Profile::firstName)
                    .orElse("Unknown");
            promptBuilder.append(authorName)
                    .append(": ")
                    .append(msg.messageText())
                    .append("\n");
        }

        promptBuilder.append("IntelliAid: ");
        String prompt = promptBuilder.toString();
        Map<String, String> promptReqBody = new HashMap<>();
        promptReqBody.put("prompt", prompt);
        WebClient webClient = WebClient.create(OLLAMA_URL);

        // Send prompt to OpenLLaMA model
        Mono<ModelResponse> responseMono = webClient.post().uri("/generate")
                .bodyValue(promptReqBody)
                .retrieve()
                .bodyToMono(ModelResponse.class);

        // Recieve the response from the model
        ModelResponse modelResponse = responseMono.block();
        String assitantReply = modelResponse.getText();

        // Storing the prompt as a new Chat message object (Have to create new profile at profile start)
        ChatMessage assitantMessage = new ChatMessage(assitantReply, chatMessage.authorId(), chatMessage.profileId(), LocalDateTime.now());

        return assitantMessage;

    }
}
