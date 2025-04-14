package nki.ClimCue.test;

import nki.ClimCue.OpenAIConfig;
import nki.ClimCue.model.api.openAi.*;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItems;
import nki.ClimCue.model.member.PersonalConditionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiTestService {

    private final OpenAIConfig openAIConfig;

    public OpenAiTestService(OpenAIConfig openAIConfig) {
        this.openAIConfig = openAIConfig;
    }

    @Value("${open-api.open-ai.url.prompt}")
    private String promptUrl;

    // 최신 프롬프트 검색
    public OpenAIResponseCompletion prompt() {

        OpenAIRequestDto openAIRequestDto = createOpenAIRequestDto();
        System.out.println("openAIRequestDto = " + openAIRequestDto);
        OpenAIRequestDto openAiRequestDto = openAIRequestDto;

        return getOpenAIResponseCompletion(openAiRequestDto);
    }

    private OpenAIResponseCompletion getOpenAIResponseCompletion(OpenAIRequestDto openAiRequestDto) {
        // 요청을 보낸다
        Map<String, Object> resultMap = new HashMap<>();
        OpenAIResponseCompletion responseMessage = null;

        HttpHeaders httpHeaders = openAIConfig.httpHeaders();
        ResponseEntity<OpenAIResponseCompletion> response = openAIConfig.restTemplate()
                .exchange(promptUrl, HttpMethod.POST, new HttpEntity<>(openAiRequestDto, httpHeaders), OpenAIResponseCompletion.class);
        System.out.println("getOpenAIResponseCompletion response = " + response);
        return response.getBody();
    }

    private OpenAIRequestDto createOpenAIRequestDto() {
        OpenAIRequestMessage message1 = new OpenAIRequestMessage(OpenAIRole.SYSTEM, "인사해");
        OpenAIRequestMessage message2 = new OpenAIRequestMessage(OpenAIRole.USER, "hi");

        List<OpenAIRequestMessage> messages = new ArrayList<>();;
        messages.add(message1);
        messages.add(message2);

        return new OpenAIRequestDto(messages);


    }
}
