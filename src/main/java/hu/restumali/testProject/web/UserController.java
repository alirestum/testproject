package hu.restumali.testProject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/register")
    public ModelAndView register(){ return null;}

    @PostMapping("/register")
    public ModelAndView register(Model newUser){ return null;}

    @GetMapping("/login")
    public ModelAndView login(){return null;}
    
}
