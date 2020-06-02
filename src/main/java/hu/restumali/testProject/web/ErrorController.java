package hu.restumali.testProject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ErrorController {

    @RequestMapping("/accessdenied")
    public String accessDeniedPage(){
        return "accessDeniedPage";
    }
}
