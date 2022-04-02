package com.client;

public class DataGetter {

    public static boolean checkCredentials(String login, String password) {
        /*
         * Bedzie wysylalo zapywanie do serwera z (login, password) i dostane true/false od serwera
         * gdy sie zgadzaja z tym co jest w bazie
         */
        boolean authSuccessful = false;
        if(login.equals("r") && password.equals("r"))
            authSuccessful = true;
        else if (login.equals("root") && password.equals("root"))
            authSuccessful = true;

        return authSuccessful;
    }
}
