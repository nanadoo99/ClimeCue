package nki.ClimCue.model.api.openAi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class OpenAIResponseMessage {
    // 역할 - OpenAI = ASSISTANT
    @JsonProperty("role")
    private String role;

    // 생성 응답
    @JsonProperty("content")
    private String content;

    // 거절 응답
    @JsonProperty("refusal")
    private String refusal;
}
