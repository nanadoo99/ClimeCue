package nki.ClimCue.test;

import nki.ClimCue.model.api.openAi.OpenAIResponseCompletion;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenAiTestController {

    @Autowired
    OpenAiTestService service;

    @GetMapping("/public/test/openAi")
    public ResponseEntity<OpenAIResponseCompletion> test() {
        OpenAIResponseCompletion response = service.prompt();
        return ResponseEntity.ok(response);

    }
}
