package com.intellidev.app.mashroo3k.data.models;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class NavItemModel {
    private String title;
    private int iconResourceId;

    public NavItemModel(String title, int iconResourceId) {
        this.title = title;
        this.iconResourceId = iconResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}
