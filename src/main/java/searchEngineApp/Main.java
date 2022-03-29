package searchEngineApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        SpringApplication.run(Main.class, args);

//        LinkParser parser = new LinkParser("http://www.playback.ru/");
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        forkJoinPool.invoke(parser);
//
//        LemmaParser lemmaParser = new LemmaParser();
//        lemmaParser.addLemmas();
//
//        Indexer indexer = new Indexer();
//        indexer.addIndexes();
//
//        Searcher searcher = new Searcher();
//        ArrayList<LinkInfo> linksList = searcher.listOfLinks("Купить смартфон apple");
//        linksList.forEach(System.out::println);

//        String text = "Повторное появление леопарда в Осетии позволяет предположить, что леопард ПОСТоянно обитает в НЕКОТОРЫХ райоНАх Северного Кавказа.";
//        Lemmatizator lemmatizator = new Lemmatizator(text);
//        HashMap<String, Integer> map = lemmatizator.morphText();
//        for (String key : map.keySet()) {
//            System.out.println(key + " - " + map.get(key));
//        }

//        Document doc = Jsoup.connect("http://www.playback.ru/")
//                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                .referrer("http://www.google.com")
//                .get();
//        System.out.println(doc.text());
    }
}
