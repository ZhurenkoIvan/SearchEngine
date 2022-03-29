package searchEngineApp.SQL;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLEditor {
    private static Connection connection = DBConnection.getConnection();

    public static void createNewSite() throws SQLException {
        connection.createStatement().execute("DROP TABLE site");
        connection.createStatement().execute("CREATE TABLE site (" +
                "PRIMARY KEY(id), id INT NOT NULL AUTO_INCREMENT, " +
                "status ENUM(\"INDEXING\", \"INDEXED\", \"FAILED\") NOT NULL, " +
                "status_time DATETIME NOT NULL, " +
                "last_error TEXT, " +
                "url VARCHAR(255) NOT NULL, " +
                "name VARCHAR(255) NOT NULL)");
    }

    public static void createNewIndex() throws SQLException {
        connection.createStatement().execute("DROP TABLE _index");
        connection.createStatement().execute("CREATE TABLE _index (" +
                "PRIMARY KEY(id), id INT NOT NULL AUTO_INCREMENT, " +
                "page_id INT NOT NULL, " +
                "lemma_id INT NOT NULL, " +
                "_rank FLOAT NOT NULL, " +
                "UNIQUE (page_id, lemma_id))");
    }

    public static void createNewPage() throws SQLException {
        connection.createStatement().execute("DROP TABLE page");
        connection.createStatement().execute("CREATE TABLE page (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "path TINYTEXT NOT NULL, " +
                "code INT NOT NULL, " +
                "content MEDIUMTEXT NOT NULL, " +
                "PRIMARY KEY(id), " +
                "UNIQUE KEY path(path(50)))");
    }

    public static void createNewLemma() throws SQLException {
        connection.createStatement().execute("DROP TABLE lemma");
        connection.createStatement().execute("CREATE TABLE lemma (" +
                "PRIMARY KEY(id), id INT NOT NULL AUTO_INCREMENT, " +
                "lemma VARCHAR(255) NOT NULL, " +
                "frequency INT NOT NULL, " +
                "site_id INT NOT NULL, " +
                "UNIQUE (lemma))");
    }

    public static void createNewField() throws SQLException {
        connection.createStatement().execute("DROP TABLE field");
        connection.createStatement().execute("CREATE TABLE field (" +
                "PRIMARY KEY(id), id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "selector VARCHAR(255) NOT NULL, " +
                "weight FLOAT NOT NULL, " +
                "UNIQUE (name))");
    }

}
