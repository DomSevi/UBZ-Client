package com.client;


public class CurrentSession {
    private static String userName = null;
    private static boolean isAdmin = false;

    public static void setUserName(String login) {
        userName = login;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        CurrentSession.isAdmin = isAdmin;
    }
}
