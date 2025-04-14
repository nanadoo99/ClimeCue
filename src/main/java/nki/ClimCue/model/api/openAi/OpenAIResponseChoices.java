package nki.ClimCue.model.api.openAi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponseChoices {
    @JsonProperty("index")
    public int index;

    @JsonProperty("message")
    public OpenAIResponseMessage message;

    @JsonProperty("logprobs")
    public String logprobs;

    @JsonProperty("finish_reason")
    public String finish_reason;

    @JsonCreator
    public OpenAIResponseChoices(JsonNode node) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        this.index = node.get("index").asInt();
        this.logprobs = node.get("logprobs").asText();
        this.finish_reason = node.get("finish_reason").asText();

        JsonNode messageNode = node.get("message");
        if(messageNode != null && !messageNode.isNull()) {
            this.message = mapper.treeToValue(messageNode, OpenAIResponseMessage.class);
        } else {
            this.message = null;
        }
    }
}
