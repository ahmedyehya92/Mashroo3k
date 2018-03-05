package com.intellidev.app.mashroo3k.ui.news;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 02/03/2018.
 */

public interface NewsMvpPresenter <V extends NewsMvpView> extends MvpPresenter<V> {
    void loadNews();
}
