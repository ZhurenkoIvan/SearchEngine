package searchEngineApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import searchEngineApp.DTO.*;
import searchEngineApp.ENUMS.Status;
import searchEngineApp.service.MainService;
import searchEngineApp.service.StatisticsService;

import java.util.ArrayList;

@RestController
public class RestAPIController {

    @Autowired
    private MainService mainService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/admin/statistics")
    public Result statistics(){

        return statisticsService.getFullStatistic();
    }

    @GetMapping("/admin/startIndexing")
    public ServiceResult startIndexing() {
        ServiceResult serviceResult = mainService.startIndexing();
        return serviceResult;
    }

    @GetMapping("/api/stopIndexing")
    public ServiceResult stopIndexing() {
        return null;
    }

}
