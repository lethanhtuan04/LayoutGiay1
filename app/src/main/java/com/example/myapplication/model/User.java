package com.example.myapplication.model;



public class User {

    private Integer id;
    private Integer accountId;
    private String fullname;
    private String sex;
    private String phone;
    private String address;
    private String avatar;
    private String status;

    public User(Integer id, Integer accountId, String fullname, String sex, String phone,
                String address, String avatar, String status) {
        this.id = id;
        this.accountId = accountId;
        this.fullname = fullname;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;

        this.status = status;
    }

    public User(Integer accountId, String fullname, String sex,
                String phone, String address, String avatar) {
        this(-1, accountId, fullname, sex, phone, address, avatar, null);
    }

    public User(Integer id, Integer accountId, String fullname, String sex,
                String phone, String address,  String status) {
        this.id = id;
        this.accountId = accountId;
        this.fullname = fullname;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.setAvatar(sex);
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

//    public void setAvatar(String sex) {
//        int avatar = DEFAULT_AVT_UNDEFINED;
//        if (sex != null) {
//            if (sex.startsWith("F"))
//                avatar = DEFAULT_AVT_FEMALE;
//            else if (sex.startsWith("M"))
//                avatar = DEFAULT_AVT_MALE;
//        }
//        this.avatar = BitmapFactory.decodeResource(MainActivity.mainResources, avatar);
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
