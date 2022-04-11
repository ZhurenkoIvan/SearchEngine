package searchEngineApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import searchEngineApp.entity.Field;
import searchEngineApp.entity.Index;
import searchEngineApp.entity.Lemma;
import searchEngineApp.entity.Page;
import searchEngineApp.repo.FieldRepo;
import searchEngineApp.repo.IndexRepo;
import searchEngineApp.repo.LemmaRepo;
import searchEngineApp.repo.PageRepo;
import searchEngineApp.supporting_classes.Lemmatizator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Получает данные из таблиц lemma и page. Индексирует их и записывает в таблицу _index
 */
@Service
public class IndexService {

    @Autowired
    private FieldRepo fieldRepo;
    @Autowired
    private LemmaRepo lemmaRepo;
    @Autowired
    private PageRepo pageRepo;
    @Autowired
    private IndexRepo indexRepo;

    private final ArrayList<Index> indexes = new ArrayList<>();


    public void addIndexes() throws SQLException, IOException {
        ArrayList<Field> fields = (ArrayList<Field>) fieldRepo.findAll();
        HashMap<String, Integer> lemmas = null;
        for (Lemma lemma : lemmaRepo.findAll()) {
            lemmas.put(lemma.getLemma(), lemma.getFrequency());
        }
        ArrayList<Page> pages = (ArrayList<Page>) pageRepo.findAll();
        for (Page page : pages) {
            Document contentDoc = Jsoup.parse(page.getContent());
            String title = contentDoc.title();
            String body = contentDoc.body().text();
            for(Field field : fields) {
                if (field.getSelector().equals("title"))
                    addToPrSt(lemmas, new Lemmatizator(title).getLemmas(), field.getWeight(), page);
                else
                    addToPrSt(lemmas, new Lemmatizator(body).getLemmas(), field.getWeight(), page);
            }

        }
        indexRepo.saveAll(indexes);
    }

    private void addToPrSt(HashMap<String, Integer> lemmas, HashMap<String, Integer> tagMap, float weight, Page page) throws SQLException {
        for (String key : tagMap.keySet()) {

            int lemma_id = lemmas.get(key);
            float rank = tagMap.get(key).floatValue() * weight;
            Index index = new Index();
            index.setRank(rank);
            index.setLemmaId(lemma_id);
            index.setPageId(page.getId());
            indexes.add(index);
        }
    }
}
