package com.example.myapplication.model;


import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int type;
    private String name;
    private double price;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;
    private byte[] image4;
    private String detail;
    private float star;
    private String status;
    private Discount discount;

    public Product(int id, int type, String name, double price,
                   byte[] image1, byte[] image2, byte[] image3,
                   byte[] image4, String detail, float star, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.detail = detail;
        this.star = star;
        this.status = status;
    }

    public Product(int id, String name, int type, double price,byte[] image1, String detail) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image1 = image1;
        this.image2 = null;
        this.image3 = null;
        this.image4 = null;
        this.detail = detail;
        this.star = 0.0f;
        this.status = null;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public byte[] getImage4() {
        return image4;
    }

    public void setImage4(byte[] image4) {
        this.image4 = image4;
    }

//    public Product(Integer storeId, Integer type, String name) {
    //        this(-1, storeId, type, name, 0.0, defaultImage, null, 0.0f, null);
    //    }
//
//    public Product(Integer storeId, Integer type, String name, String image) {
//        this(-1, storeId, type, name, 0.0, image, null, 0.0f, null);
//    }
//
//    public Product(Integer storeId, Integer type, String name, Double price) {
//        this(-1, storeId, type, name, price, null, null, 0.0f, null);
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        if (type > 0 && type < 8)
            this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price >= 0)
            this.price = price;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        if (star >= 0)
            this.star = star;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Discount getDiscount() {
        return discount;
    }

    public boolean isDiscounted() {
        if (discount != null)
            return true;
        return false;
    }


    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

}
