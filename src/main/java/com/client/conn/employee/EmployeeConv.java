package com.client.conn.employee;

import com.client.conn.config.Configuration;

import java.io.IOException;
import java.util.List;

public class EmployeeConv {
    private final EmployeeAcc employeeAcc = Configuration.getRetrofit()
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

    public Employee createNewEmployee(Employee newEmployee) throws IOException {
        return employeeAcc.addNewEmployee(newEmployee)
                .execute()
                .body();
    }

    public Employee updateEmployeeById(Long id, String newName, String newSurname, String newJob) throws IOException {
        return employeeAcc.editEmployeeById(id, newName, newSurname, newJob)
                .execute()
                .body();
    }

    public Employee removeEmployeeById(Long id) throws IOException {
        return employeeAcc.deleteEmployeeById(id)
                .execute()
                .body();
    }
}
