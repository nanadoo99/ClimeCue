package nki.ClimCue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nki.ClimCue.OpenAIConfig;
import nki.ClimCue.model.api.openAi.OpenAIRequestDto;
import nki.ClimCue.model.api.openAi.OpenAIRequestMessageBuilder;
import nki.ClimCue.model.api.openAi.OpenAIResponseCompletion;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItem;
import nki.ClimCue.model.member.PersonalConditionDto;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItems;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public OpenAIServiceImpl(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    @Value("${open-api.open-ai.url.prompt}")
    private String promptUrl;

    // 최신 프롬프트 검색
    @Override
    public OpenAIResponseCompletion prompt(PersonalConditionDto personalConditionDto, List<VilageFcstUltraSrtItem> vilageFcstUltraSrtItems) {

        OpenAIRequestDto openAIRequestDto = OpenAIRequestMessageBuilder.buildRequest(personalConditionDto, vilageFcstUltraSrtItems);
        System.out.println("openAIRequestDto = " + openAIRequestDto);
        OpenAIRequestDto openAiRequestDto = openAIRequestDto;

        return getOpenAIResponseCompletion(openAiRequestDto);
    }

    private OpenAIResponseCompletion getOpenAIResponseCompletion(OpenAIRequestDto openAiRequestDto) {
        // 요청을 보낸다
        Map<String, Object> resultMap = new HashMap<>();
        OpenAIResponseCompletion responseMessage = null;

        ResponseEntity<OpenAIResponseCompletion> response = restTemplate
                .exchange(promptUrl, HttpMethod.POST, new HttpEntity<>(openAiRequestDto, httpHeaders), OpenAIResponseCompletion.class);
        System.out.println("getOpenAIResponseCompletion response = " + response);
        return response.getBody();
    }

}
