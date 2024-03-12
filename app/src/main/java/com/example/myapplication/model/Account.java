package com.example.myapplication.model;

public class    Account {
    private Integer id;
    private String username;
    private String email;
    private Integer roleID;
    private String status;

    public Account() {
    }

    public Account(Integer id, String username, Integer roleID, String email, String status) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setRoleID(roleID);
        this.setStatus(status);
    }

    public Account(String username,  String email) {
        this(-1, username,  2, email, "null");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.contains("@"))
            this.email = email;
    }



    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        if (roleID > 0)
            this.roleID = roleID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
