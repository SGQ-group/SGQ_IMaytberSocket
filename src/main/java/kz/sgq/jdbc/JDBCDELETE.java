package kz.sgq.jdbc;

import kz.sgq.utils.SQLStatement;
import spark.Request;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class JDBCDELETE {
    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
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

}
