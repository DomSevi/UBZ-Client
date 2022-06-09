package com.client.conn.employee;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface EmployeeAcc {

    @GET("employees/list")
    Call<List<Employee>> findAllEmployees();

    @GET("employees/{login}")
    Call<Employee> findEmployeeByLogin(@Path("login") String login);

    @POST("employees/add")
    Call<Employee> addNewEmployee(@Body Employee newEmployee);

    @FormUrlEncoded
    @PUT("employees/edit/{id}")
    Call<Employee> editEmployee(@Path("id") Long id, @Field("name") String name, @Field("job") String job);

    @DELETE("employees/delete/{id}")
    Call<Employee> deleteEmployee(@Path("id") Long id);
}
