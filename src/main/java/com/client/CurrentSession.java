package com.client;

import java.util.ArrayList;

public class CurrentSession {
    private static ArrayList<String> user = new ArrayList<>();

    public CurrentSession() {

    }

    protected static void setUser(String login) {
        if(!user.isEmpty())
            user.clear();
        user.add(login);
    }

    protected static String getUser() {
        if(!user.isEmpty())
            return user.get(0);
        else
            return null;
    }
}
