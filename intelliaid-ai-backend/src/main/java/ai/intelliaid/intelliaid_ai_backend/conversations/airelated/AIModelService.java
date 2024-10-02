package ai.intelliaid.intelliaid_ai_backend.conversations.airelated;

import ai.intelliaid.intelliaid_ai_backend.conversations.ChatMessage;
import ai.intelliaid.intelliaid_ai_backend.conversations.Conversation;
import ai.intelliaid.intelliaid_ai_backend.conversations.ConversationRepository;
import ai.intelliaid.intelliaid_ai_backend.profiles.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIModelService {
    @Value("${ollama.model}")
    private String MODEL_VERSION;
    @Value("${ollama.port}")
    private String OLLAMA_PORT;
    private String OLLAMA_URL;
    @Value("${model.mode:default}")
    private String MODEL_SETTINGS;
    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        OLLAMA_URL = "http://localhost:" + OLLAMA_PORT + "/api/generate";
    }

    public AIModelService(ConversationRepository conversationRepository, ProfileRepository profileRepository, RestTemplate restTemplate) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.restTemplate = restTemplate;
    }



    public ChatMessage getModelResponse(ChatMessage chatMessage, Conversation conversation){
        System.out.println("Inside ai service");
        String message = chatMessage.getMessageText();
        ModelSettings modelSettings = new ModelSettings();

        ChatMessage nullResponse = new ChatMessage("Something wrong with Ollama", "007", chatMessage.getAuthorId(), LocalDateTime.now());

        // Creating a Request Entity of Type ModelSettings
        HttpEntity<ModelSettings> requestEntity = new HttpEntity<>(modelSettings.buildModelRequest(message, MODEL_VERSION, MODEL_SETTINGS));
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(OLLAMA_URL, requestEntity, String.class);
            String[] jsonObjects = responseEntity.getBody().split("\n");
            StringBuilder sb = new StringBuilder();
            for(String s : jsonObjects){
                JsonNode node = objectMapper.readTree(s);
                if(node.has("response")) sb.append(node.get("response").asText());
            }

            ChatMessage aiResponse = new ChatMessage(sb.toString(), "007", chatMessage.getAuthorId(), LocalDateTime.now());

            return aiResponse;
        }
        catch(HttpClientErrorException e){
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return nullResponse;

    }

}

