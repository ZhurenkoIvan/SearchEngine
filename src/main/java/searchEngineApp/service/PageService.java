package searchEngineApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngineApp.DTO.SiteDTO;
import searchEngineApp.entity.Page;
import searchEngineApp.entity.Site;
import searchEngineApp.repo.PageRepo;
import searchEngineApp.supporting_classes.LinkParser;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
public class PageService {

    @Autowired
    private PageRepo pageRepo;

    public List<Page> addPages(SiteDTO site, Site entity) {

        LinkParser parser = new LinkParser(site.getUrl(), entity.getId());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Page> pageList = forkJoinPool.invoke(parser);
        pageList = pageRepo.saveAll(pageList);
        return pageList;
    }
}
