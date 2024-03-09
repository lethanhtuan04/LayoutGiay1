package com.example.myapplication.model;

import java.io.Serializable;

public class Discount implements Serializable {
    private Integer id;
    private Integer productId;
    private int value;
    private String status;

    public Discount(Integer id, Integer productId, int value, String status) {
        this.id = id;
        this.productId = productId;
        this.value = value;
        this.status = status;
    }

    public Discount(Integer id, Integer productId, int value) {
        this(id, productId, value, "null");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
