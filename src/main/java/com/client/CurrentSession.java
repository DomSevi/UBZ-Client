package com.client;


public class CurrentSession {
    private static String userName = null;

    public static void setUser(String login) {
        userName = login;
    }

    public static String getUser() {
        return userName;
    }
}
