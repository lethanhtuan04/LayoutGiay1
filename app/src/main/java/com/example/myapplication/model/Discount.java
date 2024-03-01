package com.example.myapplication.model;

public class Discount {
    private Integer id;
    private Integer productId;
    private Float value;
    private String status;

    public Discount(Integer id, Integer productId,  Float value, String status) {
        this.id = id;
        this.productId = productId;
        this.value = value;
        this.status = status;
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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
