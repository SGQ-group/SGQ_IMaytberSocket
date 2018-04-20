package kz.sgq.jdbc;

import com.google.gson.Gson;
import spark.Request;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCGET {
    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
    private final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    private final String login = dbUri.getUserInfo().split(":")[0];
    private final String password = dbUri.getUserInfo().split(":")[1];
    private Connection connection;
    private Statement statement;

    public JDBCGET() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    public String printFriends(Request request) {
        String reply;
        try {
            if (Integer.parseInt(request.queryParams("iduser_1")) > 0) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM friends WHERE friends.iduser_1=" +
                        request.queryParams("iduser_1"));
                ArrayList<HashMap<String, String>> replyList = new ArrayList<>();
                while (resultSet.next()) {
                    HashMap<String, String> replyMap = new HashMap<>();
                    replyMap.put("idfriends", resultSet.getString("idfriends"));
                    replyMap.put("iduser_1", resultSet.getString("iduser_1"));
                    replyMap.put("iduser_2", resultSet.getString("iduser_2"));
                    replyList.add(replyMap);
                }
                reply = new Gson().toJson(replyList);
            } else {
                reply = null;
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

    public String printProfile(Request request) {
        String reply = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE idusers=" +
                    request.queryParams("idusers"));
            while (resultSet.next()) {
                HashMap<String, String> replyMap = new HashMap<>();
                replyMap.put("avatar", resultSet.getString("avatar"));
                replyMap.put("nick", resultSet.getString("nick"));
                replyMap.put("idusers", resultSet.getString("idusers"));
                reply = new Gson().toJson(replyMap);
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

    public String printLogin(Request request) {
        String reply = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE users.login='" +
                    request.queryParams("login") + "' AND users.password='" +
                    request.queryParams("password") + "'");
            while (resultSet.next()) {
                HashMap<String, String> replyMap = new HashMap<>();
                replyMap.put("idusers", resultSet.getString("idusers"));
                replyMap.put("avatar", resultSet.getString("avatar"));
                replyMap.put("nick", resultSet.getString("nick"));
                replyMap.put("login", resultSet.getString("login"));
                replyMap.put("password", resultSet.getString("password"));
                reply = new Gson().toJson(replyMap);
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

}
