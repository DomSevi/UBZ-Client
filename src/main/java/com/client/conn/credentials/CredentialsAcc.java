package com.client.conn.credentials;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CredentialsAcc {

    @GET("/credentials/list")
    Call<List<Credentials>> findAllCredentials();

    @GET("/credentials/{login}")
    Call<Credentials> findCredentialsByLogin(@Path("login") String login);

    @GET("/credentials/checkCredentials/{login}/{password}")
    Call<Boolean> checkCorrectnessOfCredentials(@Path("login") String login, @Path("password") String password);

    @POST("/credentials/add")
    Call<Void> addNewCredentials(@Body Credentials newCredentials);

    @FormUrlEncoded
    @PUT("/credentials/editPassword/{login}")
    Call<Void> editCredentialsPasswordByLogin(@Path("login") String login, @Field("password") String password);

    @DELETE("/credentials/delete/{login}")
    Call<Void> deleteCredentialsByLogin(@Path("login") String login);
}
