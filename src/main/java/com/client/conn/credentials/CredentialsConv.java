package com.client.conn.credentials;

import com.client.conn.config.Configuration;

import java.io.IOException;
import java.util.List;

public class CredentialsConv {
    private final CredentialsAcc credentialsAcc = Configuration.getRetrofit()
            .create(CredentialsAcc.class);

    public List<Credentials> getAllCredentials() throws IOException {
        return credentialsAcc.findAllCredentials()
                .execute()
                .body();
    }

    public Credentials getCredentialsByLogin(String login) throws IOException {
        return credentialsAcc.findCredentialsByLogin(login)
                .execute()
                .body();
    }

    public Boolean isCorrectLoginAndPassword(String login, String password) throws IOException{
        return credentialsAcc.checkCorrectnessOfCredentials(login, password)
                .execute()
                .body();
    }

    public void createNewCredentials(Credentials newCredentials) throws IOException{
        credentialsAcc.addNewCredentials(newCredentials)
                .execute()
                .body();
    }

    public Credentials updateCredentialsPasswordByLogin(String login, String newPassword) throws IOException{
        return credentialsAcc.editCredentialsPasswordByLogin(login, newPassword)
                .execute()
                .body();
    }

    public Credentials removeCredentialsByLogin(String login) throws IOException{
        return credentialsAcc.deleteCredentialsByLogin(login)
                .execute()
                .body();
    }
}
