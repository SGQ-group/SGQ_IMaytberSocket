package kz.sgq;

import kz.sgq.jdbc.JDBCDELETE;
import kz.sgq.jdbc.JDBCGET;
import kz.sgq.jdbc.JDBCPOST;
import kz.sgq.jdbc.JDBCPUT;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args){
        port(getHerokuAssignedPort());
//        staticFiles.location("/public");
        post("/user", (request, response) -> new JDBCPOST().createUser(request));
        post("/friend", (request, response) -> new JDBCPOST().createFriends(request));
        post("/message", (request, response) -> new JDBCPOST().createMS(request));
        put("/nick", (request, response) -> new JDBCPUT().putNick(request));
        put("/read", (request, response) -> new JDBCPUT().putRead(request));
        put("/avatar", (request, response) -> new JDBCPUT().putAvatar(request));
        put("/password", (request, response) -> new JDBCPUT().putPassword(request));
        put("/token", (request, response) -> new JDBCPUT().putToken(request));
        put("/bio", (request, response) -> new JDBCPUT().putBio(request));
        get("/friend", (request, response) -> new JDBCGET().printFriends(request));
        get("/profile", (request, response) -> new JDBCGET().printProfile(request));
        get("/login", (request, response) -> new JDBCGET().printLogin(request));
        get("/chats", (request, response) -> new JDBCGET().printChats(request));
        get("/message", (request, response) -> new JDBCGET().printMessage(request));
        delete("/friend", (request, response) -> new JDBCDELETE().deleteFriend(request));
        delete("/message", (request, response) -> new JDBCDELETE().deleteMessage(request));
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
