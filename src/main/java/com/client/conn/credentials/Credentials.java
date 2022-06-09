package com.client.conn.credentials;

public class Credentials {
    private Long employeeId;
    private String employeeLogin;
    private String password;
    private Boolean isAdmin;

    public Credentials() {
    }

    public Credentials(Long employeeId, String employeeLogin, String password, Boolean isAdmin) {
        this.employeeId = employeeId;
        this.employeeLogin = employeeLogin;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "employeeId=" + employeeId +
                ", employeeLogin='" + employeeLogin + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
