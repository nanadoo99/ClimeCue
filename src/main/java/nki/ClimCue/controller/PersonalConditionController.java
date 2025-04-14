package nki.ClimCue.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import nki.ClimCue.model.api.vilageFcst.VilageFcstLatLngDto;
import nki.ClimCue.model.member.PersonalConditionDto;
import nki.ClimCue.model.member.UserDetailsImpl;
import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import nki.ClimCue.service.PersonalConditionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/p-condition")
public class PersonalConditionController {

    private final PersonalConditionService pcService;
    public PersonalConditionController(PersonalConditionService pcService) {
        this.pcService = pcService;
    }

    @PostMapping("/create")
    public String create(@NotNull VilageFcstLatLngDto vilageFcstLatLngDto, BindingResult vilageFcstNxNyDtoResult
            , @AuthenticationPrincipal UserDetailsImpl user
            , @Valid PersonalConditionDto personalConditionDto, BindingResult personalConditionDtoResult
            , RedirectAttributes redirectAttributes) {
        if (personalConditionDtoResult.hasErrors()) {
            return "home"; // 뷰 불러오는 곳에서 new PersonalCondition() 객체 모델에 넣어줘야함.
        }
        pcService.createOrUpdatePersonalCondition(user, personalConditionDto);

        redirectAttributes.addFlashAttribute("previousLatLng", vilageFcstLatLngDto);

        return "redirect:/"; // 날씨와 정보를 다시 받아온다.

    }

/*    @PostMapping("/create")
    public String create(@Valid VilageFcstNxNyDto vilageFcstNxNyDto, BindingResult vilageFcstNxNyDtoResult
            , @AuthenticationPrincipal UserDetailsImpl user
            , @Valid PersonalConditionDto personalConditionDto, BindingResult personalConditionDtoResult
            , RedirectAttributes redirectAttributes) {
        if (personalConditionDtoResult.hasErrors() || vilageFcstNxNyDtoResult.hasErrors()) {
            return "home"; // 뷰 불러오는 곳에서 new PersonalCondition() 객체 모델에 넣어줘야함.
        }
        pcService.createOrUpdatePersonalCondition(user, personalConditionDto);

        redirectAttributes.addFlashAttribute("previousNxNy", vilageFcstNxNyDto);

        return "redirect:/"; // 날씨와 정보를 다시 받아온다.

    }*/
}
