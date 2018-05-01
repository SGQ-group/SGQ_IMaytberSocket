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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JDBCPUT {
    private final Logger logger = Logger.getLogger(JDBCPUT.class.getName());
    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final String keyA = "key=AAAAwog6t5c:APA91bH_UOXr_cu6cWTdneopBUQDh_rQHRAjHCTo_" +
            "Oo6JKEzXwua7AcouDaXilxwfaRh19DvtZkVxfLlojDt5tYw_" +
            "u9S4jwqpMDcurRttBy36SGpBp9YkI5mPqh9JyaSsysfyDS3_okL";
    private final String URL_FCM = "https://fcm.googleapis.com/fcm/send";
    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
    private final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    private final String login = dbUri.getUserInfo().split(":")[0];
    private final String password = dbUri.getUserInfo().split(":")[1];
    private Connection connection;
    private PreparedStatement preparedStatement;

    public JDBCPUT() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    public String putNick(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("iduser") != null &&
                    request.queryParams("nick") != null) {
                logger.info("putNick");
                preparedStatement = connection.prepareStatement(SQLStatement.getUserId());
                preparedStatement.setInt(1,Integer.parseInt(request.queryParams("iduser")));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    preparedStatement = connection.prepareStatement(SQLStatement.putNick());
                    preparedStatement.setString(1,request.queryParams("nick"));
                    preparedStatement.setInt(2,Integer.parseInt(request.queryParams("iduser")));
                    preparedStatement.executeUpdate();
                    check = true;
                }
                if (check){
                    logger.info("check");
                    List<Integer> idUserList = new ArrayList<>();
                    List<String> tokenList = new ArrayList<>();
                    preparedStatement = connection.prepareStatement(SQLStatement.getFriendsId());
                    preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
                    logger.info(preparedStatement.toString());
                    logger.info(request.queryParams("iduser"));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        logger.info("idUserList.add");
                        idUserList.add(resultSet.getInt("iduser_2"));
                    }
                    logger.info("idUserList item: "+idUserList.get(1));
                    for (int i = 0; i < idUserList.size(); i++) {
                        preparedStatement = connection.prepareStatement(SQLStatement.getUserId());
                        preparedStatement.setInt(1, idUserList.get(i));
                        while (resultSet.next()) {
                            logger.info("token add");
                            tokenList.add(resultSet.getString("token"));
                        }
                    }
                    logger.info("tokenList: "+tokenList.size());
                    for (int i = 0; i < tokenList.size(); i++) {
                        nickFCM(tokenList.get(i), request.queryParams("iduser"), request.queryParams("nick"));
                    }
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

    public String putToken(Request request) {
        boolean check = false;
        try {
            if (request.queryParams("iduser") != null &&
                    request.queryParams("token") != null) {
                preparedStatement = connection.prepareStatement(SQLStatement.getUserId());
                preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    preparedStatement = connection.prepareStatement(SQLStatement.putToken());
                    preparedStatement.setString(1,request.queryParams("token"));
                    preparedStatement.setInt(2,Integer.parseInt(request.queryParams("iduser")));
                    preparedStatement.executeUpdate();
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
                    request.queryParams("avatar") != null) {
                preparedStatement = connection.prepareStatement(SQLStatement.getUserId());
                preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    preparedStatement = connection.prepareStatement(SQLStatement.putAvatar());
                    preparedStatement.setString(1,request.queryParams("avatar"));
                    preparedStatement.setInt(2,Integer.parseInt(request.queryParams("iduser")));
                    preparedStatement.executeUpdate();
                    check = true;
                }
                if (check){
                    List<Integer> idUserList = new ArrayList<>();
                    List<String> tokenList = new ArrayList<>();
                    preparedStatement = connection.prepareStatement(SQLStatement.getFriendsId());
                    preparedStatement.setInt(1, Integer.parseInt(request.queryParams("iduser")));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        idUserList.add(resultSet.getInt("iduser_2"));
                    }
                    for (int i = 0; i < idUserList.size(); i++) {
                        preparedStatement = connection.prepareStatement(SQLStatement.getUserId());
                        preparedStatement.setInt(1, idUserList.get(i));
                        while (resultSet.next()) {
                            tokenList.add(resultSet.getString("token"));
                        }
                    }
                    for (int i = 0; i < tokenList.size(); i++) {
                        avatarFCM(tokenList.get(i), request.queryParams("iduser"), request.queryParams("avatar"));
                    }
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
                    request.queryParams("password") != null) {
                preparedStatement = connection.prepareStatement(SQLStatement.getUserLogin());
                preparedStatement.setString(1, request.queryParams("login"));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    preparedStatement = connection.prepareStatement(SQLStatement.putPassword());
                    preparedStatement.setString(1,request.queryParams("password"));
                    preparedStatement.setString(2,request.queryParams("login"));
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

    private void nickFCM(String token, String iduser, String nick) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "{" +
                "  \"to\": \"" + token + "\", " +
                "  \"data\": {" +
                "    \"iduser\":\"" + iduser + "\"," +
                "    \"nick\":\"" + nick + "\"" +
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

    private void avatarFCM(String token, String iduser, String avatar) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "{" +
                "  \"to\": \"" + token + "\", " +
                "  \"data\": {" +
                "    \"iduser\":\"" + iduser + "\"," +
                "    \"avatar\":\"" + avatar + "\"" +
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
