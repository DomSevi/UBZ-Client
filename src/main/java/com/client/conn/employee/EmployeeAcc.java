package com.client.conn.employee;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface EmployeeAcc {

    @GET("employees/list")
    Call<List<Employee>> findAllEmployees();

    @GET("employees/{login}")
    Call<Employee> findEmployeeByLogin(@Path("login") String login);
}
