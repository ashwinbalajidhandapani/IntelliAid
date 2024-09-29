package ai.intelliaid.intelliaid_ai_backend.conversations;

import ai.intelliaid.intelliaid_ai_backend.profiles.Profile;
import ai.intelliaid.intelliaid_ai_backend.profiles.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIModelService {
    private static final String OLLAMA_PORT = "11434";
    private static final String OLLAMA_URL ="http://localhost:"+OLLAMA_PORT+"/api/generate";
    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();


    public AIModelService(ConversationRepository conversationRepository, ProfileRepository profileRepository, RestTemplate restTemplate) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.restTemplate = restTemplate;
    }



    public ChatMessage getModelResponse(ChatMessage chatMessage, Conversation conversation){
        String message = chatMessage.getMessageText();

        ChatMessage nullResponse = new ChatMessage("Something wrong with Ollama", "intelli-bot", "intelli-bot", LocalDateTime.now());

        Map<String, String> promptReqBody = new HashMap<>();
        promptReqBody.put("prompt", message);
        promptReqBody.put("model", "llama3.1");

        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(OLLAMA_URL, promptReqBody, String.class);
            String[] jsonObjects = responseEntity.getBody().split("\n");
            StringBuilder sb = new StringBuilder();
            for(String s : jsonObjects){
                JsonNode node = objectMapper.readTree(s);
                if(node.has("response")) sb.append(node.get("response").asText());
            }

            ChatMessage aiResponse = new ChatMessage(sb.toString(), "1", "1", LocalDateTime.now());

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
