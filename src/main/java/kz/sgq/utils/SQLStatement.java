package kz.sgq.utils;

public class SQLStatement {
    public static String putNick(){
        return "UPDATE users SET nick=? where idusers=?";
    }
    public static String putToken(){
        return "UPDATE users SET token=? where idusers=?";
    }
    public static String putAvatar(){
        return "UPDATE users SET avatar=? where idusers=?";
    }
    public static String putPassword(){
        return "UPDATE users SET password=? where login=?";
    }
    public static String getUserLogin(){
        return "SELECT * FROM users WHERE users.login=?";
    }
    public static String getUserId(){
        return "SELECT * FROM users WHERE users.idusers=?";
    }
    public static String getFriendsId(){
        return "SELECT * FROM friends WHERE friends.iduser_1=?";
    }
    public static String getProfileId(){
        return "SELECT * FROM users WHERE idusers=?";
    }
    public static String getChatsId(){
        return "SELECT * FROM chats WHERE iduser_1=? OR iduser_2=?";
    }
    public static String getLoginLoginPassword(){
        return "SELECT * FROM users WHERE users.login=? AND users.password=?";
    }
    public static String getMessagesId(){
        return "SELECT * FROM chats WHERE iduser_1=? OR iduser_2=?";
    }

    public static String getMessagesChat(){
        return "SELECT * FROM messages WHERE idchats=?";
    }
}
