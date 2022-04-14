package searchEngineApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngineApp.DTO.SiteDTO;
import searchEngineApp.DTO.SitesList;
import searchEngineApp.DTO.ServiceResult;
import searchEngineApp.ENUMS.Status;
import searchEngineApp.entity.Lemma;
import searchEngineApp.entity.Page;
import searchEngineApp.entity.Site;
import searchEngineApp.repo.SiteRepo;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
@Service
public class MainService {

    @Autowired
    private SiteRepo siteRepo;

    @Autowired
    private PageService pageService;

    @Autowired
    private LemmaService lemmaService;

    @Autowired
    private IndexService indexService;


    public ServiceResult startIndexing() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        SitesList sitesList;
        try {
            sitesList = mapper.readValue(new File("src/main/resources/sitesList.yaml"), SitesList.class);
            System.out.println(sitesList.getSites());
            for (SiteDTO site : sitesList.getSites()) {
                try {
                    Site test = siteRepo.getById(1);
                    if (test.getStatus() != Status.INDEXING){
                        ServiceResult serviceResult = new ServiceResult();
                        serviceResult.setResult("false");
                        serviceResult.setError("Already indexing");
                        return serviceResult;
                    }
                    IndexingThread thread = new IndexingThread(site);
                    thread.start();
                }catch (EntityNotFoundException e) {
                        ServiceResult serviceResult = new ServiceResult();
                        serviceResult.setResult("false");
                        serviceResult.setError("Already indexing");
                        IndexingThread thread = new IndexingThread(site);
                        thread.start();
                }
            }
        } catch (IOException e) {
            ServiceResult serviceResult = new ServiceResult();
            serviceResult.setResult("false");
            serviceResult.setError("Parsing error");
            return serviceResult;
        }

        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setResult("true");
        return serviceResult;
    }

    private Site createSiteEntity(SiteDTO site) {

        Site entity = siteRepo.findByUrl(site.getUrl());
        if (entity == null) {
            entity = new Site();
            entity.setStatus(Status.INDEXING);
            entity.setLastError(null);
            entity.setStatusTime(new Date(System.currentTimeMillis()));
            entity.setUrl(site.getUrl());
            entity.setName(site.getName());
            entity = siteRepo.saveAndFlush(entity);
        }
        return entity;
    }

    public ServiceResult stopIndexing() {
        Thread.getAllStackTraces();
        return null;
    }


    class IndexingThread extends Thread {

        private SiteDTO site;
        private ServiceResult serviceResult;

        public IndexingThread(SiteDTO site) {
            this.site = site;
        }

        @Override
        public void run() {
            try {
                Site entity = createSiteEntity(site);
                List<Page> pages = pageService.addPages(entity);
                List<Lemma> lemmas = lemmaService.addLemmas(pages, entity);
                indexService.addIndexes(lemmas, pages);
            } catch (SQLException e) {
                serviceResult.setResult("false");
                serviceResult.setError("SQL exception in Index Service");
            } catch (IOException e) {
                serviceResult.setResult("false");
                serviceResult.setError("Input output exception");
            }
        }

        public ServiceResult getServiceResult() {
            return serviceResult;
        }
    }
}
