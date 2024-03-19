package com.example.myapplication.model;

public class Notification {

    public static final String NOTIFY_UNREAD = "Unread";
    public static final String NOTIFY_READ = "Read";
    public static final String NOTIFY_ORDER_PRODUCT = "Đặt hàng thành công ";
    private Integer id;
    private Integer userId;
    private String type;
    private String message;
    private String status;
    private byte[] image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Notification(Integer id, Integer userId, String type, String message, String status, byte[] image) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.status = status;
        this.image = image;
    }

    public Notification(Integer userId, String type, String message, byte[] image) {
        this(-1, userId, type, message, NOTIFY_UNREAD, image);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusRead() {
        this.status = NOTIFY_READ;
    }
}
