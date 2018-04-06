package com.intellidev.app.mashroo3k.ui.searchresult;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by ahmedyehya on 4/6/18.
 */

public interface SearchResultMvpPresenter <V extends SearchResultMvpView> extends MvpPresenter<V> {
    void getSearchResult(String query);
}
