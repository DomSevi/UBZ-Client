package com.client;


public class CurrentSession {
    private static String userName = null;

    public static void setUserName(String login) {
        userName = login;
    }

    public static String getUserName() {
        return userName;
    }
}
