package nki.ClimCue.model.api.openAi;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode // 테스트 코드용
@NoArgsConstructor
public class OpenAIRequestMessage {
    private String role;
    private String content;

    public OpenAIRequestMessage(OpenAIRole role, String content) {
        this.role = role.getValue();
        this.content = content;
    }
}
