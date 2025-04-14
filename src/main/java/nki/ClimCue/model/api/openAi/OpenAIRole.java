package nki.ClimCue.model.api.openAi;

public enum OpenAIRole {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String value;

    OpenAIRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
