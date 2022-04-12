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
import searchEngineApp.supporting_classes.Lemmatizator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Получает данные из таблиц lemma и page. Индексирует их и записывает в таблицу _index
 */
@Service
public class IndexService {

    @Autowired
    private FieldRepo fieldRepo;
    @Autowired
    private IndexRepo indexRepo;

    private final ArrayList<Index> indexes = new ArrayList<>();


    public void addIndexes(List<Lemma> lemmasList, List<Page> pages) throws SQLException, IOException {
        ArrayList<Field> fields = (ArrayList<Field>) fieldRepo.findAll();
        HashMap<String, Integer> lemmas = new HashMap<>();
        for (Lemma lemma : lemmasList) {
            lemmas.put(lemma.getLemma(), lemma.getFrequency());
        }
        for (Page page : pages) {
            Document contentDoc = Jsoup.parse(page.getContent());
            String title = contentDoc.title();
            String body = contentDoc.body().text();
            for(Field field : fields) {
                if (field.getSelector().equals("title"))
                    addNewIndexInList(lemmas, new Lemmatizator(title).getLemmas(), field.getWeight(), page);
                else
                    addNewIndexInList(lemmas, new Lemmatizator(body).getLemmas(), field.getWeight(), page);
            }

        }
        indexRepo.saveAll(indexes);
    }

    private void addNewIndexInList(HashMap<String, Integer> lemmas, HashMap<String, Integer> tagMap, float weight, Page page) throws SQLException {
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
