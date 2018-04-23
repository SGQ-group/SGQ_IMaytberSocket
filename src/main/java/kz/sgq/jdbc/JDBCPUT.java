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
            if (request.queryParams("iduser") != null &&
                    request.queryParams("nick") != null){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.idusers=" +
                        request.queryParams("iduser"));
                while (resultSet.next()) {
                    statement.execute("UPDATE users SET nick='" +
                            request.queryParams("nick") + "' where idusers=" +
                            request.queryParams("iduser"));
                    check = true;
                }
            }
        } catch (Exception e) {
//            check = false;
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

    public String putToken(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("iduser") != null &&
                    request.queryParams("token") != null){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.idusers=" +
                        request.queryParams("iduser"));
                while (resultSet.next()) {
                    statement.execute("UPDATE users SET token='" +
                            request.queryParams("token") + "' where idusers=" +
                            request.queryParams("iduser"));
                    check = true;
                }
            }
        } catch (Exception e) {
//            check = false;
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

    public String putAvatar(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("iduser") != null &&
                    request.queryParams("avatar") != null){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.idusers=" +
                        request.queryParams("iduser"));
                while (resultSet.next()) {
                    statement.execute("UPDATE users SET avatar='" +
                            request.queryParams("avatar") + "' where idusers=" +
                            request.queryParams("iduser"));
                    check = true;
                }
            }
        } catch (Exception e) {
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

    public String putPassword(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("login") != null &&
                    request.queryParams("password") != null){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.login=" +
                        request.queryParams("login"));
                while (resultSet.next()) {
                    statement.execute("UPDATE users SET password='" +
                            request.queryParams("password") + "' where login=" +
                            request.queryParams("login"));
                    check = true;
                }
            }
        } catch (Exception e) {
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
