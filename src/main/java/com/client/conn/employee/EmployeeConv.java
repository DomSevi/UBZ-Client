package com.client.conn.employee;

import com.client.conn.config.Configuration;

import java.io.IOException;
import java.util.List;

public class EmployeeConv {
    private EmployeeAcc employeeAcc = Configuration
            .getRetrofit()
            .create(EmployeeAcc.class);

    public List<Employee> getAllEmployees() throws IOException {
        return employeeAcc.findAllEmployees()
                .execute()
                .body();
    }

    public Employee getEmployeeByLogin(String login) throws IOException {
        return employeeAcc.findEmployeeByLogin(login)
                .execute()
                .body();
    }
}
