package com.client.conn.credentials;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface CredentialsAcc {

    @GET("credentials/list")
    Call<List<Credentials>> findAllCredentials();

    @GET("credentials/{login}")
    Call<Credentials> findCredentialsByLogin(@Path("login") String login);
}
