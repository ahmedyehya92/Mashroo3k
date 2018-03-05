package com.intellidev.app.mashroo3k.data.models;

/**
 * Created by Ahmed Yehya on 01/03/2018.
 */

public class NewsModel {
    private String id;
    private String title;
    private String content;
    private String imUrl;

    public NewsModel(String id, String title, String content, String imUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imUrl = imUrl;
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

    public String getImUrl() {
        return imUrl;
    }
}
