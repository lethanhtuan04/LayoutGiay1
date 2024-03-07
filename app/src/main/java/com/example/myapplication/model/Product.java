package com.example.myapplication.model;


import java.sql.Blob;

public class Product {
    private String id;
    private String type;
    private String name;
    private Double price;
    private String image;

    private String detail;
    private Float star;
    private String status;
    private Discount discount;



    public Product(String id, String type, String name, Double price, String image,
                   String detail, Float star, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.detail = detail;
        this.star = star;
        this.status = status;
    }

    //public String getImage1() {
      //  return image1;
   // }

    //public void setImage1(String image1) {
     //   this.image1 = image1;
    //}

    //  public String getImage2() {
    //     return image2;
    //  }

    //  public void setImage2(String image2) {
    //   this.image2 = image2;
    // }

    //  public String getImage3() {
    //    return image3;
    // }

    //  public void setImage3(String image3) {
    //  this.image3 = image3;
    //  }

    //   public String getImage4() {
    //    return image4;
    // }

    //  public void setImage4(String image4) {
    //     this.image4 = image4;
    // }



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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
       // if (type > 0 && type < 8)
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

    // public String getImage() {
    //     return image;
    // }

    // public void setImage(String image) {
    //      this.image = image;
    //  }



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

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

}
