package com.intellidev.app.mashroo3k.ui.opportunities;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.OpportunityModel;
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
 * Created by Ahmed Yehya on 24/02/2018.
 */

public class OpportunityPresenter <V extends OpportunitiesMvpView> extends BasePresenter<V> implements OpportunitiesMvpPresenter<V> {
    public OpportunityPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadFirstPage() {
        getMvpView().hideErrorView();

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl("1"))
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
                if (stringResponse.charAt(0) == '{')
                {
                    getMvpView().setLastPageTrue();
                    getMvpView().removeLoadingFooter();
                }
                else
                {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<OpportunityModel> list = new ArrayList<>();

                        for (int i = 0; i<jsonArray.length();i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            OpportunityModel opportunityModel = new OpportunityModel(jo.getString("id"), jo.getJSONObject("title").getString("rendered"), jo.getString("image"),jo.getJSONObject("content").getString("rendered"));
                            list.add(opportunityModel);
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



    String buildUrl (String page)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY_GET_OPPORTUNITIES)
                .appendPath("wp-json")
                .appendPath("wp")
                .appendPath("v2")
                .appendPath("oppert")
                .appendQueryParameter("page", page)
                .appendQueryParameter("per_page", "20");
        String myUrl = builder.build().toString();
        return myUrl;
    }

    @Override
    public void loadNextPage(Integer page) {
        String stringPage = page.toString();

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(stringPage))
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
                if (stringResponse.charAt(0) == '{')
                {
                    getMvpView().setLastPageTrue();
                    getMvpView().removeLoadingFooter();
                }
                else
                {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<OpportunityModel> list = new ArrayList<>();

                        for (int i = 0; i<jsonArray.length();i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            OpportunityModel opportunityModel = new OpportunityModel(jo.getString("id"), jo.getJSONObject("title").getString("rendered"), jo.getString("image"),jo.getJSONObject("content").getString("rendered"));
                            list.add(opportunityModel);
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
}
