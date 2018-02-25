package com.intellidev.app.mashroo3k.ui.base;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public interface MvpPresenter <V extends MvpView> {
    void onAttach(V mvpView);
}
