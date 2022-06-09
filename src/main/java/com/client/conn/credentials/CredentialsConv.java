package com.client.conn.credentials;

import com.client.conn.config.Configuration;

import java.io.IOException;
import java.util.List;

public class CredentialsConv {
    private final CredentialsAcc credentialsAcc = Configuration
            .getRetrofit()
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
}
