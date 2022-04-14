package searchEngineApp.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngineApp.entity.Lemma;
import searchEngineApp.entity.Page;
import searchEngineApp.entity.Site;
import searchEngineApp.repo.LemmaRepo;
import searchEngineApp.repo.PageRepo;
import searchEngineApp.supporting_classes.Lemmatizator;

import java.io.IOException;
import java.util.*;

/**
 * Берет данные из столбца content таблицы page и вычленяет леммы
 *
 *
 *
 *
 */
@Service
public class LemmaService {

    @Autowired
    private LemmaRepo lemmaRepo;

    public List<Lemma> addLemmas(List<Page> pages, Site entity) throws IOException {
        HashMap<String, Lemma> lemmasMap = new HashMap<>();
        lemmaRepo.deleteAllBySiteId(entity.getId());
        for(Page page : pages) {
            System.out.println("Страничка " + page.getId() + " пошла");
            //Добавление лемм
            Document contentDoc = Jsoup.parse(page.getContent());
            String text = contentDoc.body().text() + " " +contentDoc.title();
            HashMap<String, Integer> map = new Lemmatizator(text).getLemmas();
            for (String key : map.keySet()) {
                Lemma lemma = new Lemma();
                if (lemmasMap.containsKey(key)) {
                    lemma.setLemma(key);
                    lemma.setFrequency(lemmasMap.get(key).getFrequency());
                    lemma.setSiteId(entity.getId());
                    lemmasMap.put(key, lemma);
                }else {
                    lemma.setLemma(key);
                    lemma.setFrequency(1);
                    lemmasMap.put(key, lemma);
                }
            }
        }
        ArrayList<Lemma> lemmaList = new ArrayList<>();

        for (String key: lemmasMap.keySet()){
            lemmaList.add(lemmasMap.get(key));
        }

        return lemmaRepo.saveAll(lemmaList);
    }
}
