package com.intellidev.app.mashroo3k.ui.searchresult;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
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
 * Created by ahmedyehya on 4/6/18.
 */

public class SearchResultPresenter <V extends SearchResultMvpView> extends BasePresenter<V> implements SearchResultMvpPresenter<V>  {
    public SearchResultPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getFirstSearchResult(String query) {
        getMvpView().hideErrorView();

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(query, "1"))
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

                if (stringResponse.charAt(0) == '{') {
                    getMvpView().setLastPageTrue();
                    getMvpView().removeLoadingFooter();
                }
                else {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<FeasibilityStudyModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            FeasibilityStudyModel newsModel = new FeasibilityStudyModel(jo.getString("ID"), jo.getString("post_title"), jo.getString("post_content"), jo.getString("image"), jo.getString("srv"), jo.getString("money"), jo.getString("price"));
                            list.add(newsModel);
                        }
                        getMvpView().addMoreToAdapter(list);
                        getMvpView().addLoadingFooter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void getNextSearchResult(String query, String page) {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(query, page))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showRetryAdapter();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getMvpView().removeLoadingFooter();
                getMvpView().setIsLoadingFalse();

                String stringResponse = response.body().string();

                if (stringResponse.charAt(0) == '{') {
                    getMvpView().setLastPageTrue();
                }
                else {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<FeasibilityStudyModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            FeasibilityStudyModel newsModel = new FeasibilityStudyModel(jo.getString("ID"), jo.getString("post_title"), jo.getString("post_content"), jo.getString("image"), jo.getString("srv"), jo.getString("money"), jo.getString("price"));
                            list.add(newsModel);
                        }
                        getMvpView().addMoreToAdapter(list);
                        getMvpView().addLoadingFooter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String buildUrl(String query, String page)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("wp-json")
                .appendPath("wp")
                .appendPath("v2")
                .appendPath("product_search")
                .appendQueryParameter("q", query)
                .appendQueryParameter("page", page)
                .appendQueryParameter("per_page","20");
        String myUrl = builder.build().toString();
        return myUrl;
    }
}
