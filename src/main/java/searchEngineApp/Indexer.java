package searchEngineApp;

import searchEngineApp.Domain.Lemma;
import searchEngineApp.Domain.Page;
import searchEngineApp.SQL.DBConnection;
import searchEngineApp.SQL.SQLEditor;
import searchEngineApp.SQL.SQLGetter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Получает данные из таблиц lemma и page. Индексирует их и записывает в таблицу _index
 */
public class Indexer {
    private static final Connection connection = DBConnection.getConnection();

    private static PreparedStatement prSt_Index;

    static {
        try {
            prSt_Index = connection.prepareStatement("INSERT INTO _index(page_id, lemma_id, _rank) VALUES(?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE _rank = _rank + (?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIndexes() throws SQLException, IOException {
        SQLEditor.createNewIndex();
        HashMap<String, Float> tagsWeight = SQLGetter.getTagsWeight();
        HashSet<Lemma> lemmaSet = SQLGetter.getLemmas();
        HashSet<Page> pages = SQLGetter.getPages();
        HashMap<String, Integer> lemmaIdMap = new HashMap<>();
        for (Lemma lemma : lemmaSet) {
            lemmaIdMap.put(lemma.getLemmasName(), lemma.getId());
        }
        int pathsCount = 1;
        for (Page page : pages) {
            if (pathsCount > 5) {
                prSt_Index.executeBatch();
                pathsCount = 1;
            }
            Document contentDoc = Jsoup.parse(page.getContent());
            String title = contentDoc.title();
            String body = contentDoc.body().text();
            addToPrSt(lemmaIdMap, new Lemmatizator(title).getLemmas(), tagsWeight.get("title"), page.getId() );
            addToPrSt(lemmaIdMap, new Lemmatizator(body).getLemmas(), tagsWeight.get("body"), page.getId() );
            pathsCount++;
        }
        prSt_Index.executeBatch();
    }

    private void addToPrSt(HashMap<String, Integer> lemmaIdMap, HashMap<String, Integer> tagMap, float weight, int page_id ) throws SQLException {
        for (String key : tagMap.keySet()) {
            Integer lemma_id = lemmaIdMap.get(key);
            float rank = tagMap.get(key).floatValue() * weight;
            prSt_Index.setInt(1, page_id);
            prSt_Index.setInt(2, lemma_id.intValue());
            prSt_Index.setFloat(3, rank);
            prSt_Index.setFloat(4, rank);
            prSt_Index.addBatch();
        }
    }
}
