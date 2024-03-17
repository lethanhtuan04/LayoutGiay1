package com.example.myapplication.model;


import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int type;
    private String name;
    private double price;
    private byte[] image;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    private String detail;
    private float star;
    private String status;
    private Discount discount;


    public Product(int id, int type, String name, double price, byte[] image,
                   String image1, String image2, String image3, String image4,
                   String detail, float star, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.detail = detail;
        this.star = star;
        this.status = status;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

//    public double neucoDiscount(Integer pro_id) {
//        DiscountDBHelper discountDBHelper = new DiscountDBHelper(this);
//        Discount discount = discountDBHelper.getDiscountByProductID(pro_id);
//        if (discount != null && discount.getValue() > 0) {
//            double priceDiscount = getPrice() - getPrice() * discount.getValue() / 100;
//            return priceDiscount;
//        } else {
//            return 0;
//        }
//    }


    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public boolean isDiscounted() {
        if (getId() == getDiscount().getProductId())
            return true;
        return false;
    }
}
