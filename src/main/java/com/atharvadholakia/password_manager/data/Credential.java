package com.atharvadholakia.password_manager.data;

import org.springframework.data.annotation.Id;

public class Credential {

    @Id
    private String id;

    private String servicename;

    private String username;

    private String password;

    public Credential(String servicename, String username, String password) {
        this.servicename = servicename;
        this.username = username;
        this.password = password;
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

}