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

    @GetMapping("/admin/statistics")
    public Result statistics(){
        System.out.println("Дошло до статистики");
        Result result = new Result();
        Total total = new Total();
        total.setIndexing(false);
        total.setLemmas(1000000);
        total.setPages(20303);
        total.setSites(1);
        Detailed detailed = new Detailed();
        detailed.setError("Ошибка");
        detailed.setName("Playback");
        detailed.setStatus(Status.INDEXED);
        detailed.setLemmas(1000000);
        detailed.setStatusTime(1600160357);
        detailed.setPages(20303);
        detailed.setUrl("http://www.playback.ru/");
        Statistics statistics = new Statistics();
        statistics.setTotal(total);
        ArrayList<Detailed> detaileds = new ArrayList<>();
        detaileds.add(detailed);
        statistics.setDetailed(detaileds);
        result.setResult(true);
        result.setStatistics(statistics);
        return result;

    }

    @GetMapping("/admin")
    public String startPage(){
        System.out.println("Запрос получен");
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
