package searchEngineApp.supporting_classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchEngineApp.DTO.LinkInfoDTO;
import org.jsoup.Jsoup;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Осуществляет поиск списка страницы в порядке убывания релевантности
 * по получаемому запросу на основе данных таблиц _index, page и lemma
 */
@Component
public class Searcher {

    @Autowired
    private DataSource dataSource;

    public ArrayList<LinkInfoDTO> listOfLinks(String searchRequest) throws IOException, SQLException {
        if (searchRequest == null || searchRequest.isEmpty()) {
            throw new IOException();
        }
        Connection connection = dataSource.getConnection();
        Lemmatizator lemmatizator = new Lemmatizator(searchRequest);
        HashMap<String, Integer> lemmas = lemmatizator.getLemmas();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WITH T1 as \n" +
                "(SELECT page_id, _rank, path, content FROM search_engine._index\n" +
                "INNER JOIN search_engine.page\n" +
                "ON _index.page_id = page.id\n" +
                "INNER JOIN search_engine.lemma \n" +
                "ON _index.lemma_id = lemma.id\n" +
                "WHERE lemma IN (");
        for (String lemma : lemmas.keySet()) {
            if (!lemma.contains("купить")) {
                stringBuilder.append("\"");
                stringBuilder.append(lemma);
                stringBuilder.append("\"");
                stringBuilder.append(", ");
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(")),\n" +
                "T2 as(\n" +
                "SELECT page_id, path, round(sum(_rank), 1) as abs_rank, content FROM T1\n" +
                "GROUP BY page_id)\n" +
                "SELECT path, content, (abs_rank/max(abs_rank) OVER()) as rel_rank FROM T2\n" +
                "ORDER BY rel_rank DESC;");
        ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(stringBuilder.toString());
        ArrayList<LinkInfoDTO> linksList = new ArrayList<>();
        if (!isResultSetEmpty(rs)) {
            while (rs.next()) {
                LinkInfoDTO linkInfoDTO = new LinkInfoDTO();
                float rel_rank = rs.getFloat("rel_rank");
                String content =rs.getString("content");
                String title = Jsoup.parse(content).title();
                linkInfoDTO.setUri(rs.getString("path"));
                linkInfoDTO.setTitle(title);
                linkInfoDTO.setRank(rel_rank);
                linksList.add(linkInfoDTO);
            }
        }
        return linksList;
    }

    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();
    }
}
