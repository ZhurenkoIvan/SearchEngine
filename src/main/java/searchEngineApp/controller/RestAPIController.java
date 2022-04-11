package searchEngineApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import searchEngineApp.DTO.Detailed;
import searchEngineApp.DTO.Result;
import searchEngineApp.DTO.Statistics;
import searchEngineApp.DTO.Total;
import searchEngineApp.ENUMS.Status;

import java.util.ArrayList;

@RestController
public class RestAPIController {

    @GetMapping("/admin/statistics")
    public Result statistics(){
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
}
