package kz.sgq.jdbc;
import spark.Request;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
public class JDBCPUT {
    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
    private final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    private final String login = dbUri.getUserInfo().split(":")[0];
    private final String password = dbUri.getUserInfo().split(":")[1];
    private Connection connection;
    private Statement statement;

    public JDBCPUT() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    public String putNick(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("idusers") != null &&
                    request.queryParams("nick") != null){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.idusers=" +
                        request.queryParams("idusers"));
                while (resultSet.next()) {
                    statement.execute("UPDATE users SET nick='" +
                            request.queryParams("nick") + "' where idusers=" +
                            request.queryParams("idusers"));
                    check = true;
                }
            }
        } catch (Exception e) {
            check = false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (check)
            return "200 OK";
        return null;
    }
}
