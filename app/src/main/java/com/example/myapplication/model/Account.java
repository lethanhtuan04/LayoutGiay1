package com.example.myapplication.model;

public class Account {
    private Integer id;
    private String username;
    private Integer roleID;
    private String email;
    private byte[] avatar;
    private String status;


    public Account(Integer id, String username, Integer roleID, String email, byte[] avatar, String status) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setRoleID(roleID);
        this.setAvatar(avatar);
        this.setStatus(status);
    }

    public Account(String username, String password, String email) {
        this(-1, username, 2, email, null, null);
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
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
