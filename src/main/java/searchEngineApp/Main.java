package searchEngineApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import searchEngineApp.DTO.LinkInfoDTO;
import searchEngineApp.entity.Page;
import searchEngineApp.service.IndexService;
import searchEngineApp.service.LemmaService;
import searchEngineApp.supporting_classes.Lemmatizator;
import searchEngineApp.supporting_classes.LinkParser;
import searchEngineApp.supporting_classes.Searcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        SpringApplication.run(Main.class, args);

//        LinkParser parser = new LinkParser("http://www.playback.ru/");
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        List<Page> pageList = forkJoinPool.invoke(parser);
//        pageList.forEach(System.out::println);



////
//        IndexService indexer = new IndexService();
//        indexer.addIndexes();
//
//        Searcher searcher = new Searcher();
//        ArrayList<LinkInfoDTO> linksList = searcher.listOfLinks("Купить смартфон apple");
//        linksList.forEach(System.out::println);
    }
}
