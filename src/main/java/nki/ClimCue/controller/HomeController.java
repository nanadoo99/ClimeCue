package nki.ClimCue.controller;

import nki.ClimCue.model.api.vilageFcst.VilageFcstLatLngDto;
import nki.ClimCue.model.member.*;
import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import nki.ClimCue.service.PersonalConditionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    private final PersonalConditionService personalConditionService;
    public HomeController(PersonalConditionService personalConditionService) {
        this.personalConditionService = personalConditionService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK) // AWS EC2 Health check
    public String home(@AuthenticationPrincipal UserDetailsImpl user, Model model, @ModelAttribute("previousLatLng") VilageFcstLatLngDto vilageFcstLatLngDto) {
        model.addAttribute("personalConditionDto", new PersonalConditionDto());
        model.addAttribute("vilageFcstLatLngDto", new VilageFcstLatLngDto());
        model.addAttribute("previousLatLng", vilageFcstLatLngDto);
        return "home";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
