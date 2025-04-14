package nki.ClimCue.model.api.openAi;

import lombok.*;

// 호출 DTO
@Getter
@ToString
@NoArgsConstructor
public class OpenAILegacyRequestDto {
    private String model;
    private String prompt;
    private float temperature = 1; // 프롬포트의 다양성 조절
    private int max_tokens = 16; // 최대 사용 토큰

    @Builder
    OpenAILegacyRequestDto(String model, String prompt, float temperature, int max_tokens) {
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }
}
