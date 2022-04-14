package searchEngineApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngineApp.DTO.*;
import searchEngineApp.entity.Site;
import searchEngineApp.repo.LemmaRepo;
import searchEngineApp.repo.PageRepo;
import searchEngineApp.repo.SiteRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {
    @Autowired
    private PageRepo pageRepo;
    @Autowired
    private LemmaRepo lemmaRepo;
    @Autowired
    private SiteRepo siteRepo;

    public Result getFullStatistic() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        SitesList sitesList;
        Result result = new Result();
        try {
            sitesList = mapper.readValue(new File("src/main/resources/sitesList.yaml"), SitesList.class);
            List<SiteDTO> sitesDTO = new ArrayList<>(sitesList.getSites());
            Statistics statistics = new Statistics();
            //Общая статистика
            Total total = new Total();
            total.setIndexing(false);
            total.setLemmas(lemmaRepo.count());
            total.setPages(pageRepo.count());
            total.setSites(sitesDTO.size());
            statistics.setTotal(total);
            ArrayList<Detailed> detaileds = new ArrayList<>();

            //Статистика по каждому сайту
            sitesDTO.forEach(siteDTO -> {
                Detailed detailed = new Detailed();
                Site entity = siteRepo.findByUrl(siteDTO.getUrl());

                detailed.setError(entity.getLastError());
                detailed.setName(siteDTO.getName());
                detailed.setStatus(entity.getStatus());
                detailed.setLemmas(lemmaRepo.countBySiteId(entity.getId()));
                detailed.setStatusTime(entity.getStatusTime());
                detailed.setPages(pageRepo.countBySiteId(entity.getId()));
                detailed.setUrl(entity.getUrl());
                detaileds.add(detailed);
            });

            statistics.setDetailed(detaileds);
            result.setResult(true);
            result.setStatistics(statistics);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
