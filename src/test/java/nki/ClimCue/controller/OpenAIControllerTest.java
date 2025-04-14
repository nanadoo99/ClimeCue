/*
package nki.ClimCue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nki.ClimCue.model.api.openAi.OpenAIRequestDto;
import nki.ClimCue.service.OpenAIService;
import nki.ClimCue.service.OpenAIServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // Spring MVC의 Http 요청 및 응답 처리 테스트, 자동으로 MockMvc 객체 설정.
public class OpenAIControllerTest {
    private static final Logger log = LoggerFactory.getLogger(OpenAIControllerTest.class);
    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("Open AI 통신 테스트 - 모델 목록")
    public void selectModelList() throws Exception {

        this.mockMvc.perform(get("/api/openAi/modelList"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Open AI 통신 테스트 - 레거시 프롬프트")
    public void selectLegacyPrompt() throws Exception {

        OpenAIRequestDto openAIRequestDto = new OpenAIRequestDto().builder().model("gpt-4o-2024-08-06").prompt("안녕? 이름이 뭐야?").build();

        // ObjectMapper를 사용하여 DTO를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsJson = objectMapper.writeValueAsString(openAIRequestDto);

        log.info("test dtoAsJson: {}", dtoAsJson);

        // MockMvc를 사용하여 POST 요청 실행
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/openAi/legacyPrompt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJson))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
*/
