package com.atharvadholakia.password_manager.data;

import org.springframework.data.annotation.Id;

public class Credential {

    @Id
    private String id;

    private String servicename;

    private String username;

    private String password;

    public Credential() {
    };

    public Credential(String servicename, String username, String password, String id) {
        this.servicename = servicename;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getServiceName() {
        return servicename;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

}