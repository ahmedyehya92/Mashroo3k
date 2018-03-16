package com.intellidev.app.mashroo3k.ui.order;

import android.util.Patterns;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.base.BasePresenter;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;

/**
 * Created by Ahmed Yehya on 27/02/2018.
 */

public class OrderPresenter <V extends OrderMvpView> extends BasePresenter<V> implements OrderMvpPresenter<V> {
    public OrderPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void sendOrder(String projectName, String orderType, String mobNum, String fullName, String money, String email, String subject, String productsAndServices, String details) {
        if (checkFields(projectName,orderType,mobNum,fullName,money,email,subject,productsAndServices,details))
        {
            getMvpView().changeViewEffectForStartSendOrder();
            OkHttpClient client = new OkHttpClient();
            RequestBody body;

            body = new MultipartBody.Builder()
                    .setType(FORM)
                    .addFormDataPart("your-name", fullName)
                    .addFormDataPart("menu-26", orderType)
                    .addFormDataPart("tel-498", mobNum)
                    .addFormDataPart("your-email", email)
                    .addFormDataPart("text-312", projectName)
                    .addFormDataPart("number-743", money)
                    .addFormDataPart("text-313", subject)
                    .addFormDataPart("text-314", productsAndServices)
                    .addFormDataPart("your-message", details)
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.URL_SEND_ORDER)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().changeViewEffectForResponceSendOrder();
                    getMvpView().showAlertConnectionError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    getMvpView().changeViewEffectForResponceSendOrder();
                    String stringResponse = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(stringResponse);
                        int title;
                        String message = jsonObject.getString("message");
                        String status = jsonObject.getString("status");
                        if (status.equals("mail_sent"))
                            title = R.string.sent;
                        else
                            title = R.string.sent_error;
                        getMvpView().showAlert(title,message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    boolean checkFields (String projectName, String orderType, String mobNum, String fullName,
                         String money, String email, String subject, String productsAndServices, String details)
    {
        boolean fieldsDone = true;
        if (projectName.isEmpty() || mobNum.isEmpty() || fullName.isEmpty() || money.isEmpty() ||
                email.isEmpty() || subject.isEmpty() || productsAndServices.isEmpty() || details.isEmpty())
        {
            fieldsDone = false;
            getMvpView().showToast(R.string.fill_fields);
            return fieldsDone;
        }
        else if (orderType == null)
        {
            fieldsDone = false;
            getMvpView().showToast(R.string.select_order_type);
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
