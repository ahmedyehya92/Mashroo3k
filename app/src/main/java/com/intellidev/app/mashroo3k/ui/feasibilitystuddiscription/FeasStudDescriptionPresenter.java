package com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 08/03/2018.
 */

public class FeasStudDescriptionPresenter <V extends FeasStudDescriptionMvpView> extends BasePresenter<V> implements FeasStudDescriptionMvpPresenter<V> {
    public FeasStudDescriptionPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public Integer getNumberOfItemsInCart() {

        return getDataManager().getNumberOfItemList();
    }

    @Override
    public void addItemToCart(String id, String title, String price, String imgUrl) {
        getDataManager().addItemToCart(id, title, price, imgUrl);
    }

    @Override
    public boolean isItemExistingInCart(String itemId) {

        return getDataManager().isItemExistinInCart(itemId);
    }
}
