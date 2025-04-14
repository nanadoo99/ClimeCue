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
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
