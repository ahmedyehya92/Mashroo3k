package com.intellidev.app.mashroo3k.ui.news;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.NewsModel;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Ahmed Yehya on 02/03/2018.
 */

public class NewsPresenter <V extends NewsMvpView> extends BasePresenter<V> implements NewsMvpPresenter<V> {
    public NewsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadNews() {
        getMvpView().hideErrorView();

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showErrorView();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getMvpView().hideErrorView();
                getMvpView().hideProgressBar();
                String stringResponse = response.body().string();

                try {
                    JSONArray jsonArray = new JSONArray(stringResponse);
                    ArrayList<NewsModel> list = new ArrayList<>();

                    for (int i = 0; i<jsonArray.length();i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        NewsModel newsModel = new NewsModel(jo.getString("id"),jo.getJSONObject("title").getString("rendered"),jo.getJSONObject("content").getString("rendered"),jo.getString("image"));
                        list.add(newsModel);
                    }
                    getMvpView().addMoreToAdapter(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private String buildUrl()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("wp-json")
                .appendPath("wp")
                .appendPath("v2")
                .appendPath("posts")
                .appendQueryParameter("categories", "3605");
        String myUrl = builder.build().toString();
        return myUrl;
    }
}
