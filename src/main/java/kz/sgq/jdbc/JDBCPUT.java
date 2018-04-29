package kz.sgq.jdbc;

import kz.sgq.utils.SQLStatement;
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
}
