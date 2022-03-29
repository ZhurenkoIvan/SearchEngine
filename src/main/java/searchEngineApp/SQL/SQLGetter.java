package searchEngineApp.SQL;

import searchEngineApp.Domain.Index;
import searchEngineApp.Domain.Lemma;
import searchEngineApp.Domain.Page;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class SQLGetter {
    private static Connection connection = DBConnection.getConnection();

    public static HashMap<String, Float> getTagsWeight() throws SQLException {
        ResultSet tagsList = connection.createStatement().executeQuery("SELECT selector, weight FROM search_engine.field");
        HashMap<String, Float> tagsWeight = new HashMap<>();
        while (tagsList.next()) {
            String tag = tagsList.getString(1);
            Float weight = tagsList.getFloat(2);
            tagsWeight.put(tag, weight);
        }
        return tagsWeight;
    }

    public static HashSet<Lemma> getLemmas() throws SQLException {
        ResultSet rsLemmas = connection.createStatement().executeQuery("SELECT * FROM search_engine.lemma");
        HashSet<Lemma> lemmasSet = new HashSet<>();
        while (rsLemmas.next()) {
            Lemma lemma = new Lemma();
            lemma.setLemmasName(rsLemmas.getString("lemma"));
            lemma.setId(rsLemmas.getInt("id"));
            lemma.setFrequency(rsLemmas.getInt("frequency"));
            lemmasSet.add(lemma);
        }
        return lemmasSet;
    }

    public static HashSet<Page> getPages() throws SQLException {
        HashSet<Page> pages = new HashSet<>();
        ResultSet rsPages = connection.createStatement().executeQuery("SELECT * FROM search_engine.page");
        while (rsPages.next()) {
            Page page = new Page();
            page.setId(rsPages.getInt("id"));
            page.setPath(rsPages.getString("path"));
            page.setCode(rsPages.getInt("code"));
            page.setContent(rsPages.getString("content"));
            pages.add(page);
        }
        return pages;
    }

    public static HashSet<Index> getIndexes() throws SQLException {
        ResultSet rsIndex = connection.createStatement().executeQuery("SELECT * FROM search_engine._index");
        HashSet<Index> indexes = new HashSet<>();
        while (rsIndex.next()) {
            Index index = new Index();
            index.setIndexId(rsIndex.getInt("id"));
            index.setPageId(rsIndex.getInt("page_id"));
            index.setLemmaId(rsIndex.getInt("lemma_id"));
            index.setRank(rsIndex.getFloat("_rank"));
            indexes.add(index);
        }
        return indexes;
    }
}
