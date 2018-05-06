package kz.sgq.utils;

public class SQLStatement {
    public static String putNick() {
        return "UPDATE users SET nick=? where idusers=?";
    }

    public static String putRead() {
        return "UPDATE chats SET read=? where idchats=?";
    }

    public static String putToken() {
        return "UPDATE users SET token=? where idusers=?";
    }

    public static String putBio() {
        return "UPDATE users SET bio=? where idusers=?";
    }

    public static String putAvatar() {
        return "UPDATE users SET avatar=? where idusers=?";
    }

    public static String putPassword() {
        return "UPDATE users SET password=? where login=?";
    }

    public static String getUserLogin() {
        return "SELECT * FROM users WHERE users.login=?";
    }

    public static String getUserId() {
        return "SELECT * FROM users WHERE users.idusers=?";
    }

    public static String getFriendsId() {
        return "SELECT * FROM friends WHERE friends.iduser_1=?";
    }

    public static String getFriendsId2() {
        return "SELECT * FROM friends WHERE friends.iduser_2=?";
    }

    public static String getChatsId() {
        return "SELECT * FROM chats WHERE iduser_1=? OR iduser_2=?";
    }
    public static String getChat() {
        return "SELECT * FROM chats WHERE idchats=?";
    }

    public static String getLoginLoginPassword() {
        return "SELECT * FROM users WHERE users.login=? AND users.password=?";
    }

    public static String getMessagesId() {
        return "SELECT * FROM chats WHERE iduser_1=? OR iduser_2=?";
    }

    public static String getMessagesChat() {
        return "SELECT * FROM messages WHERE idchats=?";
    }

    public static String createUser() {
        return "INSERT INTO users (avatar,nick,login,password,token) VALUES (?,?,?,?,?)";
    }

    public static String checkFriend() {
        return "SELECT * FROM friends WHERE friends.iduser_1=? AND friends.iduser_2=?";
    }

    public static String createFriend() {
        return "INSERT INTO friends (iduser_1,iduser_2) VALUES (?,?)";
    }

    public static String checkChat() {
        return "SELECT * FROM chats WHERE (chats.iduser_1=? AND chats.iduser_2=?) OR " +
                "(chats.iduser_1=? AND chats.iduser_2=?)";
    }

    public static String createChat() {
        return "INSERT INTO chats (iduser_1, iduser_2, chats.key, chats.read) VALUES (?,?,?,?)";
    }

    public static String createMessage() {
        return "INSERT INTO messages (idchats,iduser,content,time) VALUES (?,?,?,?)";
    }

    public static String getLastMessageUser() {
        return "SELECT * FROM messages WHERE messages.iduser=? ORDER BY messages.idmessages DESC LIMIT 1";
    }
}
