package com.intellidev.app.mashroo3k.ui.base;

import com.intellidev.app.mashroo3k.data.DataManager;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public class BasePresenter <V extends MvpView> implements MvpPresenter<V> {

    DataManager mDataManager;
    private V mMvpView;

    public BasePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

}
