package kz.sgq;

import kz.sgq.jdbc.JDBCPOST;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args){
        port(getHerokuAssignedPort());
        get("/", (request, response) -> "Server status: Online");

        post("/user", (request, response) -> new JDBCPOST().createUser(request));
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
