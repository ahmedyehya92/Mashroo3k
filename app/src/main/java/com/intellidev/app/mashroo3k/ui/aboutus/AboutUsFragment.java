package com.intellidev.app.mashroo3k.ui.aboutus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.base.BaseFragment;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;

import org.jsoup.Jsoup;

public class AboutUsFragment extends BaseFragment implements AboutUsMvpView {

    private LinearLayout btnCall, btnUrl, errorLayout;
    private Button btnTryAgain;
    private ProgressBar progressBar;
    private RelativeLayout loadingLayout;
    CustomTextView tvError;
    final int PHONE_CALL_PERMISION_REQUIST_CODE = 103;
    Handler handler;


    private CustomTextView tvAboutUs, tvPhoneNumber, tvUrlLink;
    private boolean mLocationPermissionGranted = false;
    String phone;

    AboutUsPresenter presenter;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new AboutUsPresenter(dataManager);
        presenter.onAttach(this);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        loadingLayout = rootView.findViewById(R.id.loading_layout);
        tvAboutUs = rootView.findViewById(R.id.tv_about_us);
        tvPhoneNumber = rootView.findViewById(R.id.tv_phone);
        tvUrlLink = rootView.findViewById(R.id.tv_url);
        btnCall = rootView.findViewById(R.id.btn_call);
        btnUrl = rootView.findViewById(R.id.btn_url);
        errorLayout = rootView.findViewById(R.id.error_layout);
        btnTryAgain = rootView.findViewById(R.id.error_btn_retry);
        tvError = rootView.findViewById(R.id.error_txt_cause);
        progressBar = rootView.findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoadingLayout();
        presenter.reqAboutUsDetails();

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = tvPhoneNumber.getText().toString();
                getPhoneCallPermisions();

            }
        });

        btnUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = tvUrlLink.getText().toString();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideErrorLayout();
                presenter.reqAboutUsDetails();
            }
        });
    }

    private void getPhoneCallPermisions() {
        String permition [] = {Manifest.permission.CALL_PHONE};
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), permition[0]) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), permition, PHONE_CALL_PERMISION_REQUIST_CODE);
        }
        if (mLocationPermissionGranted)
            callPhone(phone);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PHONE_CALL_PERMISION_REQUIST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    callPhone(phone);
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void callPhone(String phone) {
        Intent callintent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phone));
        startActivity(callintent);
    }

    @Override
    public void showLoadingLayout() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingLyout() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showErrorLayout() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (loadingLayout.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                    tvError.setText(fetchErrorMessage());

                }
            }
        });
    }


    @Override
    public void setTextOfAboutUs(final String details) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                String finalAboutUs = Jsoup.parse(details).text();
                tvAboutUs.setText(finalAboutUs);
            }
        });

    }

    @Override
    public void hideErrorLayout() {
        if (loadingLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String fetchErrorMessage() {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        }

        return errorMsg;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    // TODO: Rename method, update argument and hook method into UI event




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
