package com.intellidev.app.mashroo3k.ui.shoppingcart;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 09/03/2018.
 */

public interface ShoppingCartMvpPresenter <V extends ShoppingCartMvpView> extends MvpPresenter<V> {
    ArrayList<CartListModel> getCartList();
    void deleteItemFromCartList(Uri uri);
}
