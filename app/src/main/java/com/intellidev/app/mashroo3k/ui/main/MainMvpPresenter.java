package com.intellidev.app.mashroo3k.ui.main;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public interface MainMvpPresenter <V extends MainMvpView> extends MvpPresenter<V> {
    Integer getNumberOfItemsInCart();
}
