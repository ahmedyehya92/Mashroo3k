package com.intellidev.app.mashroo3k.ui.currency;

import android.net.Uri;

import com.intellidev.app.mashroo3k.R;
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
 * Created by Ahmed Yehya on 13/03/2018.
 */

public class CurrencyPresenter <V extends CurrencyMvpView> extends BasePresenter<V> implements CurrencyMvpPresenter<V> {
    public CurrencyPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void convertRequest(final String cash, final String convertFrom, final String convertTo) {
        if (checkFields(cash, convertFrom, convertTo)) {
            getMvpView().startCalculate();

            OkHttpClient client = new OkHttpClient();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(buildUrl(convertFrom, convertTo))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().finishCalculate("خطأ في الإتصال بالشبكة");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String stringResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(stringResponse);
                        String convertingValue = jsonObject.getJSONObject(convertFrom + "_" + convertTo).getString("val");
                        float convertingValueFloat = Float.valueOf(convertingValue);
                        float cashFloat = Float.valueOf(cash);
                        float resaultFloat = convertingValueFloat * cashFloat;
                        String resault = String.valueOf(resaultFloat);
                        getMvpView().finishCalculate(resault);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    String buildUrl (String convertFrom, String convertTo)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_CURRENCE_AOUTHORITY)
                .appendPath("api")
                .appendPath("v5")
                .appendPath("convert")
                .appendQueryParameter("q", convertFrom + "_" + convertTo)
                .appendQueryParameter("compact","y");
        String myUrl = builder.build().toString();
        return myUrl;
    }
    boolean checkFields (String cash, String convertFrom, String convertTo)
    {
        boolean fieldsDone = true;
        if (cash.isEmpty())
        {
            fieldsDone = false;
            getMvpView().showToast(R.string.input_cash);
            return fieldsDone;
        }
        else if (convertFrom == null)
        {
            fieldsDone = false;
            getMvpView().showToast(R.string.invalid_currency_from);
            return fieldsDone;
        }
        else if (convertTo == null)
        {
            fieldsDone = false;
            getMvpView().showToast(R.string.invalid_currency_to);
            return fieldsDone;
        }
        return fieldsDone;
    }
}
