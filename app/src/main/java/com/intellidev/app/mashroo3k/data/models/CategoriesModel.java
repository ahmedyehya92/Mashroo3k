package com.intellidev.app.mashroo3k.data.models;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class CategoriesModel {
    private String catName;
    private String catId;

    public CategoriesModel(String catName, String catId) {
        this.catName = catName;
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public String getCatId() {
        return catId;
    }
}
