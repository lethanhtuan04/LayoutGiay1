package com.example.myapplication.model;

public class Cart {
    public static final String CART_UNPAID = "Unpaid";//Chưa thanh toán
    public static final String CART_PAID = "Paid"; //Thanh toán
    public static final String CART_ORDERED = "Ordered"; //

    private Integer id;
    private Integer accId;
    private Integer productId;
    private Integer quantity;
    private String status;
    private Product product;

    public Cart(Integer id, Integer accId, Integer productId, Integer quantity, String status) {
        this.setId(id);
        this.setAccId(accId);
        this.setProductId(productId);
        this.setQuantity(quantity);
        this.setStatus(status);
    }

    public Cart(Integer accId, Integer productId ) {
                this(-1, accId, productId, 1, "wait");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity >= 0)
            this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }



    public void setCartOrdered() {
        this.setStatus(CART_ORDERED);
    }

    public void setCartPaid() {
        this.setStatus(CART_PAID);
    }
}
