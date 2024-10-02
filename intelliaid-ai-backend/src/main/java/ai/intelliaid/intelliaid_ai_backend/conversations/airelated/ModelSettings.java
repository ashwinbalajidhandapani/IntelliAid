package ai.intelliaid.intelliaid_ai_backend.conversations.airelated;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class ModelSettings {
    private String prompt;
    @JsonProperty("model")
    private String modelVersion;

    @Min(0) @Max(2)
    private Integer mirostat;
    @JsonProperty("mirostat_eta")
    private Float mirostatEta;
    @JsonProperty("mirostat_tau")
    private Float mirostatTau;
    @JsonProperty("num_ctx")
    private Integer numContext;
    @JsonProperty("repeat_last_n")
    private Integer repeatLastN;
    @JsonProperty("repeat_penalty")
    private Float repeatPenalty;
    private Float temperature;
    private Integer seed;
    private String stop;
    @JsonProperty("tfs_z")
    private Float tfsZ;
    @JsonProperty("num_predict")
    private Integer numPredict;
    @JsonProperty("top_k")
    private Integer topK;
    @JsonProperty("top_p")
    private Float topP;
    @JsonProperty("min_p")
    private Float minP;

    public ModelSettings buildModelRequest(String message, String version, String modelType) {
        ModelSettings modelSettings = new ModelSettings();
        modelSettings.setPrompt(message);
        modelSettings.setModelVersion(version);

        if(modelType.equalsIgnoreCase("casual")){

            modelSettings.setMirostat(1);
            modelSettings.setMirostatEta(0.1f);
            modelSettings.setMirostatTau(5.0f);
            modelSettings.setNumContext(4096);
            modelSettings.setRepeatLastN(64);
            modelSettings.setRepeatPenalty(1.1f);
            modelSettings.setTemperature(0.5f);
            modelSettings.setSeed(42);
            modelSettings.setStop("AI assistant");
            modelSettings.setTfsZ(1.0f);
            modelSettings.setNumPredict(256);
            modelSettings.setTopK(40);
            modelSettings.setTopP(0.9f);
            modelSettings.setMinP(0.0f);

        }else if (modelType.equalsIgnoreCase("knowledgeable")){
            modelSettings.setMirostat(2);
            modelSettings.setMirostatEta(0.2f);
            modelSettings.setMirostatTau(3.0f);
            modelSettings.setNumContext(2048);
            modelSettings.setRepeatLastN(0);
            modelSettings.setRepeatPenalty(1.0f);
            modelSettings.setTemperature(0.9f);
            modelSettings.setSeed(0);
            modelSettings.setStop(null);
            modelSettings.setTfsZ(2.0f);
            modelSettings.setNumPredict(128);
            modelSettings.setTopK(100);
            modelSettings.setTopP(0.95f);
            modelSettings.setMinP(0.05f);
        }
        else{
            return modelSettings;
        }
        return modelSettings;
    }
}
