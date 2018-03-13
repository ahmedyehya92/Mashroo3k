package com.intellidev.app.mashroo3k.data;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.dphelper.SqliteHandler;
import com.intellidev.app.mashroo3k.data.models.CartListModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public class DataManager {

    SharedPrefsHelper mSharedPrefsHelper;
    private SqliteHandler mSqliteHandler;

    public DataManager(SqliteHandler mSqliteHandler, SharedPrefsHelper sharedPrefsHelper) {
        mSharedPrefsHelper = sharedPrefsHelper;
        this.mSqliteHandler = mSqliteHandler;
    }
    public void addItemToCart(String id, String title, String price, String imgUrl)
    {
        mSqliteHandler.addItemsToCart(id,title,price,imgUrl);
    }

    public ArrayList<CartListModel> getCartItemsList()
    {
        return mSqliteHandler.getCartList();
    }

    public boolean isItemExistinInCart (String itemId)
    {
        ArrayList<CartListModel> cartItemsList = getCartItemsList();
        boolean isExist = false;
        for (int i = 0 ; i<cartItemsList.size() ; i++)
        {
            if ((itemId).equals(cartItemsList.get(i).getId())) {
                isExist = true;
                break;
            }

        }
        return isExist;
    }

    public Integer getNumberOfItemList ()
    {
        return mSqliteHandler.getCartList().size();
    }

    public void deleteItemFromOrderList(Uri uri)
    {
        mSqliteHandler.deleteItemsfromCart(uri);
    }

}
