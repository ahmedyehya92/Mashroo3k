package com.intellidev.app.mashroo3k.ui.feasibilitystudies;

import android.net.Uri;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.CategoriesModel;
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
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class FeasibilitiesStudiesPresenter <V extends FeasibilityStudiesMvpView> extends BasePresenter<V> implements FeasibilityStudiesMvpPresenter<V>{
    public FeasibilitiesStudiesPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void reqCategoriesList() {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(StaticValues.GET_CATEGORIES)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(stringResponse);
                    ArrayList<CategoriesModel> list = new ArrayList<>();
                    for (int i = 0; i<jsonArray.length();i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        CategoriesModel categoriesModel = new CategoriesModel(jo.getString("cat_name"),jo.getString("cat_ID"));
                        list.add(categoriesModel);
                    }
                    getMvpView().setupListView(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void loadFirstStudiesByCat(String id) {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildProductByCatUrl("1",id))
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
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<FeasibilityStudyModel> list = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                FeasibilityStudyModel feasibilityStudyModel = new FeasibilityStudyModel(jo.getString("ID"), jo.getString("post_title"), jo.getString("post_content"), jo.getString("image"), jo.getString("srv"), jo.getString("money"), jo.getString("price"));
                                list.add(feasibilityStudyModel);
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
    public void loadNextStudiesByCat(String id, Integer page) {

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildProductByCatUrl(page.toString(),id))
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
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<FeasibilityStudyModel> list = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            FeasibilityStudyModel feasibilityStudyModel = new FeasibilityStudyModel(jo.getString("ID"), jo.getString("post_title"), jo.getString("post_content"), jo.getString("image"), jo.getString("srv"), jo.getString("money"), jo.getString("price"));
                            list.add(feasibilityStudyModel);
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
    public void loadFirstShowAllStudies() {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildShowAllUrl("1"))
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
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<FeasibilityStudyModel> list = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            FeasibilityStudyModel feasibilityStudyModel = new FeasibilityStudyModel(jo.getString("id"), jo.getJSONObject("title").getString("rendered"), jo.getJSONObject("content").getString("rendered"), jo.getString("image"), jo.getString("Srv"), jo.getString("money"), jo.getString("price"));
                            list.add(feasibilityStudyModel);
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
    public void loadNextShowAllStudies(Integer page) {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildShowAllUrl(page.toString()))
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
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(stringResponse);
                        ArrayList<FeasibilityStudyModel> list = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            FeasibilityStudyModel feasibilityStudyModel = new FeasibilityStudyModel(jo.getString("id"), jo.getJSONObject("title").getString("rendered"), jo.getJSONObject("content").getString("rendered"), jo.getString("image"), jo.getString("Srv"), jo.getString("money"), jo.getString("price"));
                            list.add(feasibilityStudyModel);
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

    String buildShowAllUrl (String page)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("wp-json")
                .appendPath("wp")
                .appendPath("v2")
                .appendPath("product")
                .appendQueryParameter("page", page)
                .appendQueryParameter("per_page", "20");
        String myUrl = builder.build().toString();
        return myUrl;
    }

    String buildProductByCatUrl (String page, String id)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("wp-json")
                .appendPath("wp")
                .appendPath("v2")
                .appendPath("product_cat")
                .appendPath(id)
                .appendQueryParameter("page", page)
                .appendQueryParameter("per_page", "20");
        String myUrl = builder.build().toString();
        return myUrl;
    }




}
