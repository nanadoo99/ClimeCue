package nki.ClimCue.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import nki.ClimCue.exception.AuthenticationRequiredException;
import nki.ClimCue.model.member.CheckUserDto;
import nki.ClimCue.model.member.RegisterUserDto;
import nki.ClimCue.model.member.UserDetailsImpl;
import nki.ClimCue.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/join")
public class JoinController {

    private static final Logger log = LoggerFactory.getLogger(JoinController.class);
    private final UserService userService;

    public JoinController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "join/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("registerUserDto", new RegisterUserDto());
        return "join/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid @ModelAttribute("registerUserDto") RegisterUserDto registerUserDto, BindingResult result, Model model) {
        if(!registerUserDto.isPasswordMatching()) {
            result.rejectValue("confirmPassword", "error.registerUserDto", "비밀번호가 일치하지 않습니다.");
        }

        userService.checkUserDuplication(new CheckUserDto(registerUserDto), result);

        if(result.hasErrors()) {
            log.info("result has errors: {}", result.toString());
            return "join/signup";
        }
        userService.join(registerUserDto);
        return "redirect:/join/login";
    }

    @GetMapping("/delete")
    public String delete(@AuthenticationPrincipal UserDetailsImpl user,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         RedirectAttributes redirectAttributes) {
        if(user == null) {
            throw new AuthenticationRequiredException("회원 탈퇴");
        }
        userService.delete(user.getId());
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        redirectAttributes.addFlashAttribute("alertMessage", "회원탈퇴 성공");
        return "redirect:/";
    }

}
