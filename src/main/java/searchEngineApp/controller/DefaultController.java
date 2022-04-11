package searchEngineApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import searchEngineApp.DTO.Detailed;
import searchEngineApp.DTO.Result;
import searchEngineApp.DTO.Statistics;
import searchEngineApp.DTO.Total;
import searchEngineApp.ENUMS.Status;
import searchEngineApp.service.LemmaService;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    LemmaService lemmaService;

    @GetMapping("/admin")
    public String startPage(){
        return "index";
    }

    @GetMapping("/lemma")
    public String lemma() {
        try {
            lemmaService.addLemmas();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
