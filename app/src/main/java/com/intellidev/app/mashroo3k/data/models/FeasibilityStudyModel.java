package com.intellidev.app.mashroo3k.data.models;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class FeasibilityStudyModel {
    private String id;
    private String title;
    private String content;
    private String imgUrl;
    private String services;
    private String money;
    private String price;

    public FeasibilityStudyModel(String id, String title, String content, String imgUrl, String services, String money, String price) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.services = services;
        this.money = money;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getServices() {
        return services;
    }

    public String getMoney() {
        return money;
    }

    public String getPrice() {
        return price;
    }
}
