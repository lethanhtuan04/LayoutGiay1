package com.example.myapplication.model;

import java.io.Serializable;
public class ProductType {
    private Integer id;
    private String name;
    private String image;
    private String status;

    public ProductType(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
//        this.image = image;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getImage() {
//        return image;
//    }


//    public void setImage(String image) {
//        this.image = image;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

