package ai.intelliaid.intelliaid_ai_backend.conversations;

// This class holds the response from OpenLLaMA
public class ModelResponse {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
