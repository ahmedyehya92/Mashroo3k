package com.intellidev.app.mashroo3k.ui.newsdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.main.MainActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

public class NewsDetailsActivity extends BaseActivity {

    ImageView imvNews;
    WebView webViewDetails;
    Toolbar toolbar;
    CustomButtonTextFont btnOrderNow;

    String title, imgUrl, details;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initViews();
        getIntentExtras();
        setupActionBar();
        setupImgAndDetailsNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.description_action_menu,menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupImgAndDetailsNews() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                webViewDetails.loadDataWithBaseURL("", buildHtml(details), "text/html", "utf-8", "");

                if (!(imgUrl== null || imgUrl.equals(""))) {
                    Glide.with(NewsDetailsActivity.this)
                            .load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder)
                            .into(imvNews);
                }
                else
                    Glide.with(NewsDetailsActivity.this)
                            .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imvNews);
            }
        });
    }

    private String buildHtml (String text)
    {
        String resultHtml = "<html dir=\"rtl\" lang=\"ar\">\n" +
                "  <head>\n" +
                "    <link rel=\"stylesheet\"\n" +
                "          href=\"https://fonts.googleapis.com/css?family=Cairo\">\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: 'Cairo', sans-serif;\n" +

                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body bgcolor=\"#e6e9ec\">\n" +
                "    <div>" + text + "</div>\n" +
                "  </body>\n" +
                "</html>";
        return resultHtml;
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar_title, null);

        changeLang(NewsDetailsActivity.this,"ar");
        ((CustomTextView)v.findViewById(R.id.title)).setText(title);

        actionBar.setCustomView(v);
    }

    private void getIntentExtras() {
        Intent intent;
        intent = getIntent();
        title = intent.getStringExtra(StaticValues.KEY_NEWS_TITLE);
        imgUrl = intent.getStringExtra(StaticValues.KEY_NEWS_IMGURL);
        details = intent.getStringExtra(StaticValues.KEY_NEWS_DETAILS);
        int flagIntent = intent.getIntExtra(StaticValues.KEY_FLAG_INTENT,0);
        if (flagIntent == StaticValues.FLAG_OPPORT_INTENT) {
            btnOrderNow.setVisibility(View.VISIBLE);
            btnOrderNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MainActivity.getStartIntent(NewsDetailsActivity.this, 3));
                    finish();
                }
            });
        }
        else
            btnOrderNow.setVisibility(View.GONE);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar_main);
        imvNews = findViewById(R.id.imv_news);
        webViewDetails = findViewById(R.id.webViewFace);
        btnOrderNow = findViewById(R.id.btn_order_now);
    }

    public static Intent getStartIntent(Context context, String title, String imgUrl, String details, int FLOG_INTENT)
    {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(StaticValues.KEY_NEWS_TITLE, title);
        intent.putExtra(StaticValues.KEY_NEWS_IMGURL, imgUrl);
        intent.putExtra(StaticValues.KEY_NEWS_DETAILS, details);
        intent.putExtra(StaticValues.KEY_FLAG_INTENT, FLOG_INTENT);
        return intent;
    }
}
