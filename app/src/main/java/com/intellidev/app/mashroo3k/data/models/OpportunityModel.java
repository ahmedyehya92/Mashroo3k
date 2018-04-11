package com.intellidev.app.mashroo3k.data.models;

/**
 * Created by Ahmed Yehya on 24/02/2018.
 */

public class OpportunityModel {
    private String id;
    private String name;
    private String imgUrl;
    private String location;
    private String content;

    public OpportunityModel() {

    }

    public OpportunityModel(String id, String name, String imgUrl, String location, String content) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.location = location;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getContent() {
        return content;
    }
}
