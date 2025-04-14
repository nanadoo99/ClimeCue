package nki.ClimCue.model.api.vilageFcst;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

// 초단기실황조회
@Data
@AllArgsConstructor // 기본 생성자 생성
@NoArgsConstructor
public class VilageFcstUltraSrtItems {
    private String resultCode;

    private VilageFcstResultMsgCode resultMsg;

    private List<VilageFcstUltraSrtItem> vilageFcstUltraSrtItems;

    @JsonCreator
    public VilageFcstUltraSrtItems(@JsonProperty("response") JsonNode responseNode) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode headerNode = responseNode.path("header");
        this.resultCode = headerNode.path("resultCode").asText();
        this.resultMsg = VilageFcstResultMsgCode.fromString(headerNode.path("resultMsg").asText());

        JsonNode itemNode = responseNode.findValue("item");
        // JsonNode
        // get(): 노드 필드 찾고 없으면 null
        // path(): 노드 필드 찾고 없으면 MissingNode
        // findValue(): 노드와 자식 노드들에서 필드 찾고 없으면 null

        if (itemNode != null && !itemNode.isNull()) {
            this.vilageFcstUltraSrtItems = Arrays.asList(mapper.treeToValue(itemNode, VilageFcstUltraSrtItem[].class));
        } else {
            this.vilageFcstUltraSrtItems = List.of();
        }
    }

}
