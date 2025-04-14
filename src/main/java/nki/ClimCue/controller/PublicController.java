package nki.ClimCue.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import nki.ClimCue.exception.VilagaFcstApiException;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItem;
import nki.ClimCue.model.exception.VilageFcstExceptionStatus;
import nki.ClimCue.model.member.PersonalConditionDto;
import nki.ClimCue.model.member.UserDetailsImpl;
import nki.ClimCue.model.api.openAi.OpenAIResponseChoices;
import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItems;
import nki.ClimCue.service.OpenAIService;
import nki.ClimCue.service.PersonalConditionService;
import nki.ClimCue.service.VilageFcstService;
import nki.ClimCue.util.ValidVilageFcstPoints;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final PersonalConditionService personalConditionService;
    private final VilageFcstService vilageFcstService;
    private final OpenAIService openAIService;

    public PublicController(PersonalConditionService personalConditionService, VilageFcstService vilageFcstService,
                            OpenAIService openAIService) {
        this.personalConditionService = personalConditionService;
        this.vilageFcstService = vilageFcstService;
        this.openAIService = openAIService;
    }

    @ResponseBody
    @GetMapping("/weather")
    public ResponseEntity<List<VilageFcstUltraSrtItem>> weather(@ModelAttribute @NotNull @Valid VilageFcstNxNyDto vilageFcstNxNy, BindingResult result, Model model) throws Exception {
        System.out.println("BindingResult result = " + result);

        if (result.hasErrors()) {
            throw new VilagaFcstApiException(VilageFcstExceptionStatus.INVALID_LOCATION, vilageFcstNxNy.toString(), vilageFcstNxNy);
        }

        VilageFcstUltraSrtItems vilageFcstUltraSrtItems  = vilageFcstService.ultraSrtNcst(vilageFcstNxNy);
        System.out.println("vilageFcstNxNy = " + vilageFcstNxNy);

        return ResponseEntity.ok(vilageFcstUltraSrtItems.getVilageFcstUltraSrtItems());
    }

    @ResponseBody
    @GetMapping("/personal")
    public ResponseEntity<PersonalConditionDto> personal(@AuthenticationPrincipal UserDetailsImpl user, Model model) throws Exception {
        PersonalConditionDto personalConditionDto = new PersonalConditionDto();
        // 로그인 되어 있으면, PersonalCondition을 받아온다.
        if (user != null) {
            personalConditionDto = personalConditionService.findByUserId(user.getId());
            System.out.println("user is " + user);
        }
        return ResponseEntity.ok(personalConditionDto);
    }

    @ResponseBody
    @PostMapping("/openAi")
    public ResponseEntity<OpenAIResponseChoices> openAi(@RequestBody List<VilageFcstUltraSrtItem> vilageFcstUltraSrtItems,
                                                       @AuthenticationPrincipal UserDetailsImpl user, Model model) throws Exception {
        OpenAIResponseChoices openAIResponseChoice;
        PersonalConditionDto personalConditionDto = new PersonalConditionDto();

        if (user != null) {
            System.out.println("user is " + user);
            personalConditionDto = personalConditionService.findByUserId(user.getId());
            System.out.println("publicController.openAi.personalConditionDto = " + personalConditionDto);
        }
        openAIResponseChoice = openAIService.prompt(personalConditionDto, vilageFcstUltraSrtItems).getChoices().get(0);

        System.out.println("openAIResponseChoice = " + openAIResponseChoice);
        return ResponseEntity.ok(openAIResponseChoice);
    }
}
