package com.intellidev.app.mashroo3k.ui.currency;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 13/03/2018.
 */

public class CurrencyPresenter <V extends CurrencyMvpView> extends BasePresenter<V> implements CurrencyMvpPresenter<V> {
    public CurrencyPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
