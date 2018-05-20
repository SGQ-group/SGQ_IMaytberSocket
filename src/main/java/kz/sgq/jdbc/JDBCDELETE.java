package kz.sgq.jdbc;

import kz.sgq.utils.SQLStatement;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import spark.Request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class JDBCDELETE {
    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final String keyA = "key=AAAAwog6t5c:APA91bH_UOXr_cu6cWTdneopBUQDh_rQHRAjHCTo_" +
            "Oo6JKEzXwua7AcouDaXilxwfaRh19DvtZkVxfLlojDt5tYw_" +
            "u9S4jwqpMDcurRttBy36SGpBp9YkI5mPqh9JyaSsysfyDS3_okL";
    private final String URL_FCM = "https://fcm.googleapis.com/fcm/send";
    private final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    private final String login = dbUri.getUserInfo().split(":")[0];
    private final String password = dbUri.getUserInfo().split(":")[1];
    private Connection connection;
    private PreparedStatement preparedStatement;

    public JDBCDELETE() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    public String deleteFriend(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("idfriends") != null) {
                preparedStatement = connection.prepareStatement(SQLStatement.getFriend());
                preparedStatement.setInt(1, Integer.parseInt(request.queryParams("idfriends")));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    preparedStatement = connection.prepareStatement(SQLStatement.deleteFriend());
                    preparedStatement.setInt(1, Integer.parseInt(request.queryParams("idfriends")));
                    preparedStatement.executeUpdate();
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

    public String deleteMessage(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("idmessage") != null &&
                    request.queryParams("iduser") != null) {
                preparedStatement = connection.prepareStatement(SQLStatement.getMessages());
                preparedStatement.setInt(1, Integer.parseInt(request.queryParams("idmessage")));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    preparedStatement = connection.prepareStatement(SQLStatement.deleteMessage());
                    preparedStatement.setInt(1, Integer.parseInt(request.queryParams("idmessage")));
                    preparedStatement.executeUpdate();
                    check = true;
                }
                preparedStatement = connection.prepareStatement(SQLStatement.getUserId());
                preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    messageFCM(resultSet.getString("token"),
                            request.queryParams("idmessage"));
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

    private void messageFCM(String token, String idmessage) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "{" +
                "  \"to\": \"" + token + "\", " +
                "  \"data\": {" +
                "    \"idmessage\":\"" + idmessage + "\"" +
                "  }" +
                "}";
        RequestBody requestBody = RequestBody.create(JSON, json);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .header("Authorization", keyA)
                .url(URL_FCM)
                .post(requestBody)
                .build();
        client.newCall(request).execute();
    }

}
