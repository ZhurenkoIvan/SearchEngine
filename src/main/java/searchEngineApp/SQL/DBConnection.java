package searchEngineApp.SQL;
import java.sql.*;

//Используется для создания соединения с базой данных
public class DBConnection {

    private static Connection connection;

    private final static String dbName = "search_engine";
    private final static String dbUser = "root";
    private final static String dbPass = "Vitylin97";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);


                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS page (" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "path TINYTEXT NOT NULL, " +
                        "code INT NOT NULL, " +
                        "content MEDIUMTEXT NOT NULL, " +
                        "site_id INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY path(path(50)))");


                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS field (" +
                        "PRIMARY KEY(id), id INT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "selector VARCHAR(255) NOT NULL, " +
                        "weight FLOAT NOT NULL, " +
                        "UNIQUE (name))");
                connection.createStatement().execute("INSERT INTO field(name, selector, weight) VALUES(\"title\", \"title\", 1.0) " +
                        "ON DUPLICATE KEY UPDATE weight = 1.0");
                connection.createStatement().execute("INSERT INTO field(name, selector, weight) VALUES(\"body\", \"body\", 0.8) " +
                        "ON DUPLICATE KEY UPDATE weight = 0.8");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
