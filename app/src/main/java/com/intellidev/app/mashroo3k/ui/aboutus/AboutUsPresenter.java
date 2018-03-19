package com.intellidev.app.mashroo3k.ui.aboutus;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Ahmed Yehya on 19/03/2018.
 */

public class AboutUsPresenter <V extends AboutUsMvpView> extends BasePresenter<V> implements AboutUsMvpPresenter<V> {


    public AboutUsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void reqAboutUsDetails() {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showErrorLayout();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(stringResponse);
                    String details = jsonObject.getJSONObject("content").getString("rendered");
                    getMvpView().setTextOfAboutUs(details);
                    getMvpView().hideLoadingLyout();
                } catch (JSONException e) {
                    getMvpView().showErrorLayout();
                    e.printStackTrace();
                }
            }
        });
    }

    private String buildUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("wp-json")
                .appendPath("wp")
                .appendPath("v2")
                .appendPath("pages")
                .appendPath("7287");
        String myUrl = builder.build().toString();
        return myUrl;
    }
}
