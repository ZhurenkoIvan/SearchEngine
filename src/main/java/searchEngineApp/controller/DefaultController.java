package searchEngineApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import searchEngineApp.service.LemmaService;


@Controller
public class DefaultController {

    @Autowired
    LemmaService lemmaService;

    @GetMapping("/admin")
    public String startPage(){
        return "index";
    }
}
