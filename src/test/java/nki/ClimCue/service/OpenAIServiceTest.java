package nki.ClimCue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenAIServiceTest {
    private static final Logger log = LoggerFactory.getLogger(OpenAIServiceTest.class);
    @Autowired private OpenAIService openAIService;

/*    @Test
    @DisplayName("Open AI 서비스 테스트 - 최신 프롬프트")
    public void promptTest() {
        OpenAIRequestDto openAIRequestDto = new OpenAIRequestDto().builder()
                .model("gpt-4o-mini").message("안녕? 이름이 뭐야?").build();

        OpenAIResponseCompletion openAIResponseCompletion = openAIService.prompt(openAIRequestDto);

        log.info("PromptTest openAIResponseCompletion: " + openAIResponseCompletion.toString());

    }*/

}
