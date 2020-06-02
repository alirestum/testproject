package hu.restumali.testProject.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@PreAuthorize("hasAnyRole('User', 'ContentManager', 'Administrator')")
public class HomeController {
}
