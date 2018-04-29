package kz.sgq.jdbc;

import com.google.gson.Gson;
import kz.sgq.utils.SQLStatement;
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
    private PreparedStatement preparedStatement;

    public JDBCGET() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    public String printFriends(Request request) {
        String reply = null;
        try {
            if (Integer.parseInt(request.queryParams("iduser")) > 0) {
                preparedStatement = connection.prepareStatement(SQLStatement.getFriendsId());
                preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
                ResultSet resultSet = preparedStatement.executeQuery();
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
            preparedStatement = connection.prepareStatement(SQLStatement.getProfileId());
            preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HashMap<String, String> replyMap = new HashMap<>();
                replyMap.put("avatar", resultSet.getString("avatar"));
                replyMap.put("nick", resultSet.getString("nick"));
                replyMap.put("iduser", resultSet.getString("idusers"));
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

    public String printChats(Request request) {
        String reply = null;
        try {
            preparedStatement = connection.prepareStatement(SQLStatement.getChatsId());
            preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
            preparedStatement.setInt(2, Integer.parseInt(request.queryParams("iduser")));
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<HashMap<String, String>> replyList = new ArrayList<>();
            while (resultSet.next()) {
                HashMap<String, String> replyMap = new HashMap<>();
                replyMap.put("idchat", resultSet.getString("idchats"));
                replyMap.put("iduser_1", resultSet.getString("iduser_1"));
                replyMap.put("iduser_2", resultSet.getString("iduser_2"));
                replyMap.put("key", resultSet.getString("key"));
                replyList.add(replyMap);
            }
            reply = new Gson().toJson(replyList);
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
            preparedStatement = connection.prepareStatement(SQLStatement.getLoginLoginPassword());
            preparedStatement.setString(1, request.queryParams("login"));
            preparedStatement.setString(2, request.queryParams("password"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HashMap<String, String> replyMap = new HashMap<>();
                replyMap.put("iduser", resultSet.getString("idusers"));
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

    public String printMessage(Request request) {
        List<HashMap<String, Integer>> chatList = new ArrayList<>();
        ArrayList<HashMap<String, String>> replyList = new ArrayList<>();
        String reply;
        try {
            preparedStatement = connection.prepareStatement(SQLStatement.getMessagesId());
            preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
            preparedStatement.setInt(2, Integer.parseInt(request.queryParams("iduser")));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HashMap<String, Integer> replyMap = new HashMap<>();
                replyMap.put("idchats", resultSet.getInt("idchats"));
                chatList.add(replyMap);
            }
            preparedStatement = connection.prepareStatement(SQLStatement.getMessagesChat());
            for (int i = 0; i < chatList.size(); i++) {

                preparedStatement.setInt(1, chatList.get(i).get("idchats"));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    HashMap<String, String> replyMap = new HashMap<>();
                    replyMap.put("idmessage", resultSet.getString("idmessages"));
                    replyMap.put("idchat", resultSet.getString("idchats"));
                    replyMap.put("iduser", resultSet.getString("iduser"));
                    replyMap.put("content", resultSet.getString("content"));
                    replyList.add(replyMap);

                }
            }
            reply = new Gson().toJson(replyList);
        } catch (Exception e) {
            reply = e.getMessage();
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
