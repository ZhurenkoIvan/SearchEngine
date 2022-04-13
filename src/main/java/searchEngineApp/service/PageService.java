package searchEngineApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngineApp.DTO.SiteDTO;
import searchEngineApp.entity.Page;
import searchEngineApp.entity.Site;
import searchEngineApp.repo.PageRepo;
import searchEngineApp.supporting_classes.LinkParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
public class PageService {

    @Autowired
    private PageRepo pageRepo;

    public List<Page> addPages(SiteDTO site, Site entity) {

        LinkParser parser = new LinkParser(entity);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Set<Page> pageSet = forkJoinPool.invoke(parser);
        List<Page> pageList = new ArrayList<>(pageSet);
        pageList = pageRepo.saveAll(pageList);
        return pageList;
    }
}
