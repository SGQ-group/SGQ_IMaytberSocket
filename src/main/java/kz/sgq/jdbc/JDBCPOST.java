package kz.sgq.jdbc;

import com.google.gson.Gson;
import spark.Request;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.sql.SQLException;
import java.util.HashMap;

public class JDBCPOST {
    private final int LENGTH_AVATAR = 4;
    private final int LENGTH_TOKEN = 40;
    private final int LENGTH_NICK = 4;
    private final int LENGTH_LOGIN = 4;
    private final int LENGTH_PASSWORD = 4;
    private final int LENGTH_IDUSER = 1;
    private final int LENGTH_CONTENT = 1;

    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
    private final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    private final String login = dbUri.getUserInfo().split(":")[0];
    private final String password = dbUri.getUserInfo().split(":")[1];
    private Connection connection;
    private Statement statement;

    public JDBCPOST() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    public String createUser(Request request) {
        String reply = null;
        boolean check = false;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.login='" +
                    request.queryParams("login") + "'");
            while (resultSet.next()) {
                check = true;
            }
            if (!check) {
                if (request.queryParams("avatar").length() >= LENGTH_AVATAR &&
                        request.queryParams("nick").length() >= LENGTH_NICK &&
                        request.queryParams("login").length() >= LENGTH_LOGIN &&
                        request.queryParams("token").length() >= LENGTH_TOKEN &&
                        request.queryParams("password").length() >= LENGTH_PASSWORD) {
                    statement.execute("INSERT INTO users (avatar,nick,login,password,token) VALUES ('" +
                            request.queryParams("avatar") + "', '" +
                            request.queryParams("nick") + "', '" +
                            request.queryParams("login") + "', '" +
                            request.queryParams("password") + "', '" +
                            request.queryParams("token") + "')");
                    resultSet = statement.executeQuery("SELECT * FROM users WHERE users.login='" +
                            request.queryParams("login") + "' AND users.password='" +
                            request.queryParams("password") + "'");
                    while (resultSet.next()) {
                        HashMap<String, String> replyMap = new HashMap<>();
                        replyMap.put("idusers", resultSet.getString("idusers"));
                        replyMap.put("avatar", resultSet.getString("avatar"));
                        replyMap.put("nick", resultSet.getString("nick"));
                        replyMap.put("login", resultSet.getString("login"));
                        replyMap.put("password", resultSet.getString("password"));
                        replyMap.put("token", resultSet.getString("token"));
                        reply = new Gson().toJson(replyMap);
                    }
                }
            }
        } catch (Exception e) {
            reply = null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reply;
    }

    public String createFriends(Request request) {
        String reply = null;
        if (request.queryParams("iduser_1").length() >= LENGTH_IDUSER &&
                request.queryParams("iduser_2").length() >= LENGTH_IDUSER) {
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.idusers=" +
                        request.queryParams("iduser_2"));
                while (resultSet.next()) {
                    statement.execute("INSERT INTO friends (iduser_1,iduser_2) VALUES (" +
                            request.queryParams("iduser_1") + ", " +
                            request.queryParams("iduser_2") + ")");
                    resultSet = statement.executeQuery("SELECT * FROM friends WHERE friends.iduser_1=" +
                            request.queryParams("iduser_1") + " AND friends.iduser_2=" +
                            request.queryParams("iduser_2"));
                    while (resultSet.next()) {
                        HashMap<String, String> replyMap = new HashMap<>();
                        replyMap.put("iduser_1", resultSet.getString("iduser_1"));
                        replyMap.put("iduser_2", resultSet.getString("iduser_2"));
                        replyMap.put("idfriends", resultSet.getString("idfriends"));
                        reply = new Gson().toJson(replyMap);
                    }
                }
            } catch (Exception e) {
                reply = null;
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return reply;
        } else {
            return reply;
        }
    }
}
