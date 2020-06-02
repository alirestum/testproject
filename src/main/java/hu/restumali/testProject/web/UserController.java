package hu.restumali.testProject.web;

import hu.restumali.testProject.model.UserDTO;
import hu.restumali.testProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Value("${google.recaptcha.client.key}")
    private String GOOGLE_RECAPTCHA_SITEKEY;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('LoggedInUser', 'Administrator')")
    public String loggedInUsersPage(){
        return "user";
    }

    @GetMapping("/register")
    public String register(Map<String, Object> map) {
        map.putIfAbsent("user", new UserDTO());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid UserDTO user,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){
        if (!bindingResult.hasErrors() && user.getPassword().equals(user.getPasswordConfirm()) && !userService.usernameExists(user.getUsername())) {
            userService.registerNewUser(user);
            return "redirect:/";
        } else {
            if (!user.getPassword().equals(user.getPasswordConfirm()))
                bindingResult.addError(new FieldError("user", "passwordConfirm", "Passwords do not match!"));
            else if (userService.usernameExists(user.getUsername()))
                bindingResult.addError(new FieldError("user", "username", "Username already occupied!"));
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", bindingResult);
            return "redirect:/user/register";
        }
    }

    @GetMapping(value = "/login")
    public String login(Map<String, Object> map, HttpSession session){
        map.put("captchaRequired", false);
        if (session.getAttribute("failedAuths") != null)
            if (Integer.parseInt(session.getAttribute("failedAuths").toString()) >= 3)
                map.put("captchaRequired", true);
        map.putIfAbsent("sitekey", GOOGLE_RECAPTCHA_SITEKEY);
        return "login";
    }
}
