package com.intellidev.app.mashroo3k.data.models;

/**
 * Created by Ahmed Yehya on 08/03/2018.
 */

public class CartListModel {
    private String id;
    private String title;
    private String price;
    private String img_url;
    private String dpId;

    public CartListModel(String depId, String id, String title, String price, String img_url) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.img_url = img_url;
        this.dpId = depId;

    }

    public CartListModel() {

    }

    public String getDpId() {
        return dpId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImg_url() {
        return img_url;
    }
}
