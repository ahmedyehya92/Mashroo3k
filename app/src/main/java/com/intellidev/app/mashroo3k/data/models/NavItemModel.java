package com.intellidev.app.mashroo3k.data.models;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class NavItemModel {
    private String title;
    private int iconResourceId;
    private int id;

    public NavItemModel(int id, String title, int iconResourceId) {
        this.title = title;
        this.iconResourceId = iconResourceId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}
