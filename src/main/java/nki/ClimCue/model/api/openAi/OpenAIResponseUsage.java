package nki.ClimCue.model.api.openAi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponseUsage {
    @JsonProperty("prompt_tokens")
    private int prompt_tokens;

    @JsonProperty("completion_tokens")
    private int completion_tokens;

    @JsonProperty("total_tokens")
    private int total_tokens;
}
