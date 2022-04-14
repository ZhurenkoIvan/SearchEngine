package searchEngineApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngineApp.DTO.SiteDTO;
import searchEngineApp.entity.Page;
import searchEngineApp.entity.Site;
import searchEngineApp.repo.PageRepo;
import searchEngineApp.supporting_classes.LinkParser;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
public class PageService {

    @Autowired
    private PageRepo pageRepo;

    public List<Page> addPages(Site entity) {
        pageRepo.deleteAllBySiteId(entity.getId());
        LinkParser parser = new LinkParser(entity);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Set<Page> setPages = forkJoinPool.invoke(parser);
        List<Page> listPages = pageRepo.saveAllAndFlush(setPages);
        return listPages;
    }
}
