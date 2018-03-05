package com.intellidev.app.mashroo3k.ui.news;

import com.intellidev.app.mashroo3k.data.models.NewsModel;
import com.intellidev.app.mashroo3k.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 02/03/2018.
 */

public interface NewsMvpView extends MvpView{
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void addMoreToAdapter(ArrayList<NewsModel> list);
    void hideProgressBar();
}
