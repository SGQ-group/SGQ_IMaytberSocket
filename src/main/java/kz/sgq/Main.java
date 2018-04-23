package kz.sgq;

import kz.sgq.jdbc.JDBCGET;
import kz.sgq.jdbc.JDBCPOST;
import kz.sgq.jdbc.JDBCPUT;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args){
        port(getHerokuAssignedPort());
        get("/", (request, response) -> "Server status: Online");

        /**
         * https://example.com/user ?
         * & avatar
         * & nick
         * & login
         * & password
         * & token
         */
        post("/user", (request, response) -> new JDBCPOST().createUser(request));

        /**
         * https://example.com/friend ?
         * & iduser_1
         * & iduser_2
         */
        post("/friend", (request, response) -> new JDBCPOST().createFriends(request));

        /**
         * https://example.com/message ?
         * & iduser_1
         * & iduser_2
         * & content
         */
        post("/message", (request, response) -> new JDBCPOST().createMS(request));

        /**
         * https://example.com/nick ?
         * & iduser
         * & nick
         */
        put("/nick", (request, response) -> new JDBCPUT().putNick(request));

        /**
         * https://example.com/avatar ?
         * & iduser
         * & avatar
         */
        put("/avatar", (request, response) -> new JDBCPUT().putAvatar(request));

        /**
         * https://example.com/password ?
         * & login
         * & avatar
         */
        put("/password", (request, response) -> new JDBCPUT().putPassword(request));

        /**
         * https://example.com/token ?
         * & iduser
         * & token
         */
        put("/token", (request, response) -> new JDBCPUT().putToken(request));

        /**
         * https://example.com/friend ?
         * & iduser
         */
        get("/friend", (request, response) -> new JDBCGET().printFriends(request));

        /**
         * https://example.com/profile ?
         * & iduser
         */
        get("/profile", (request, response) -> new JDBCGET().printProfile(request));

        /**
         * https://example.com/login ?
         * & login
         * & password
         */
        get("/login", (request, response) -> new JDBCGET().printLogin(request));

        /**
         * https://example.com/chats ?
         * & iduser
         */
        get("/chats", (request, response) -> new JDBCGET().printChats(request));

        /**
         * https://example.com/message ?
         * & iduser
         */
        get("/message", (request, response) -> new JDBCGET().printMessage(request));
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
