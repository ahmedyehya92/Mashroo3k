package com.intellidev.app.mashroo3k.ui.opportunities;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 24/02/2018.
 */

public interface OpportunitiesMvpPresenter <V extends OpportunitiesMvpView> extends MvpPresenter<V> {
    void loadFirstPage ();
    void loadNextPage(Integer page);
}
