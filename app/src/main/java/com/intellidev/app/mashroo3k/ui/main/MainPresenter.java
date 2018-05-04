package com.intellidev.app.mashroo3k.ui.main;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public Integer getNumberOfItemsInCart() {
        return getDataManager().getNumberOfItemList();
    }


}
