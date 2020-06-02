package hu.restumali.testProject.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contentmanager")
@PreAuthorize("hasAnyRole('ContentMangaer', 'Administrator')")
public class ContentManagerController {

    @GetMapping("")
    public String contentManagerPage(){
        return "contentManager";
    }
}
