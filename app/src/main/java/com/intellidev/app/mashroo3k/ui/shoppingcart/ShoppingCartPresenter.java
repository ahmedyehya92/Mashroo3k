package com.intellidev.app.mashroo3k.ui.shoppingcart;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 09/03/2018.
 */

public class ShoppingCartPresenter <V extends ShoppingCartMvpView> extends BasePresenter<V> implements ShoppingCartMvpPresenter<V> {
    public ShoppingCartPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public ArrayList<CartListModel> getCartList() {
        return (getDataManager().getCartItemsList());
    }

    @Override
    public int deleteItemFromCartList(Uri uri) {
        getDataManager().deleteItemFromOrderList(uri);
        return getNumberOfItemsCartList();
    }

    @Override
    public int getNumberOfItemsCartList() {

        return getDataManager().getNumberOfItemList();
    }


}
