package com.client;


public class CurrentSession {
    private static String userName = null;

    protected static void setUser(String login) {
        userName = login;
    }

    protected static String getUser() {
        return userName;
    }
}
