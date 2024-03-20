package com.example.myapplication.model;

import com.example.myapplication.utilities.AppUtilities;

public class Bill {
    public static final String BILL_UNPAID = "Unpaid";
    public static final String BILL_PAID = "Paid";
    private Integer id;
    private Integer userId;
    private Integer cartId;
    private String phone;
    private String address;
    private String date;
    private String status;
    private int price;
    private Cart cart;

    public Bill(Integer id, Integer userId, Integer cartId, String phone, String address, int price, String date, String status) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.status = status;
        this.price = price;
    }

    public Bill(Integer userId, Integer cartId, String phone, String address, int price) {
        this.userId = userId;
        this.cartId = cartId;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.date = AppUtilities.getDateTimeNow();
        this.status = BILL_UNPAID;
    }


//    public Bill(Integer userId, Integer cartId, String address, Float discount, Float price) {
//        this(-1, userId, cartId, user.getPhone(), address, AppUtilities.getDateTimeNow(), discount, price, BILL_UNPAID);
//    }
//    public Bill(Integer userId, Integer cartId, String address, Float discount, Float price, String status) {
//        this(-1, userId, cartId, user.getPhone(), address, AppUtilities.getDateTimeNow(), discount, price, status);
//    }
//
//    public Bill(Integer userId, Integer cartId, Float discount, Float price) {
//        this(-1, userId, cartId, user.getPhone(), user.getAddress(), AppUtilities.getDateTimeNow(), discount, price, BILL_UNPAID);
//    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", userId=" + userId +
                ", cartId=" + cartId +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", cart=" + cart +
                '}';
    }
}
