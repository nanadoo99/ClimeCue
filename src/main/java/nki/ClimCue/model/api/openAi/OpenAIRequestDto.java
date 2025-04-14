package nki.ClimCue.model.api.openAi;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

// 호출 DTO
@Getter
@ToString
@EqualsAndHashCode // 테스트 코드용
@NoArgsConstructor
public class OpenAIRequestDto {
    private final String model = "gpt-4o-mini";
    private List<OpenAIRequestMessage> messages =  new ArrayList<>();
    private final float temperature = 1; // 프롬포트의 다양성 조절
    private final int max_tokens = 80; // 최대 사용 토큰

    public OpenAIRequestDto(List<OpenAIRequestMessage> messages) {
        this.messages = messages;
    }

}
