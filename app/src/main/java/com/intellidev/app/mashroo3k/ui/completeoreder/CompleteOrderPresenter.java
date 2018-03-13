package com.intellidev.app.mashroo3k.ui.completeoreder;

import android.util.Log;
import android.util.Patterns;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;

/**
 * Created by Ahmed Yehya on 10/03/2018.
 */

public class CompleteOrderPresenter <V extends CompleteOrderMvpView> extends BasePresenter<V> implements CompleteOrderMvpPresenter<V> {
    public CompleteOrderPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public ArrayList<CartListModel> getCartItems() {
        return getDataManager().getCartItemsList();
    }

    @Override
    public void sendOrder(ArrayList<String> idOfItems, String fullName, String phoneNumber, String email, String address, String note) {
        if (checkFields(fullName,phoneNumber,email,address,note))
        {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15,TimeUnit.SECONDS)
                    .writeTimeout(15,TimeUnit.SECONDS)
                    .readTimeout(15,TimeUnit.SECONDS)
                    .build()
                    ;

            RequestBody body;

           MultipartBody.Builder parts  = new MultipartBody.Builder();
           parts.setType(FORM);
           for (int i = 0 ; i<idOfItems.size() ; i++)
           {
               parts.addFormDataPart("products["+String.valueOf(i)+"]",idOfItems.get(i));
           }

           body = parts.build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.URL_COMPLETE_ORDER)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().showErrorConnectionDialog();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String stringResponse = response.body().string();
                    System.out.println(stringResponse);
                }
            });

        }
    }

    boolean checkFields (String fullName, String phoneNumber, String email, String address, String note)
    {
        boolean fieldsDone = true;
        if (fullName.isEmpty()||phoneNumber.isEmpty()||email.isEmpty()||address.isEmpty()) {
            fieldsDone = false;
            getMvpView().showToast(R.string.fill_fields);
            return fieldsDone;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            fieldsDone = false;
            getMvpView().showToast(R.string.invalid_email);
            return fieldsDone;
        }

        return fieldsDone;
    }
}
