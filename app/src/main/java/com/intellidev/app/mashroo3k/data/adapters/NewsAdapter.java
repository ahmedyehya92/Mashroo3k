package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.models.NewsModel;
import com.intellidev.app.mashroo3k.uiutilities.CustomRecyclerView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 01/03/2018.
 */

public class NewsAdapter extends CustomRecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NewsModel> arrayList;
    private Context context;
    private CustomButtonListener customListener;

    public NewsAdapter(Context context,
                                ArrayList<NewsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.test_view_study_item, parent, false);
        viewHolder = new NewsViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final NewsModel newsModel = arrayList.get(position);
        final NewsViewHolder newsViewHolder = (NewsViewHolder) holder;

        String title = Jsoup.parse(newsModel.getTitle()).text();

        newsViewHolder.tvTitle.setText(title);
        newsViewHolder.containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.onItemNewsClickListner(newsModel.getTitle(),newsModel.getId(),newsModel.getImUrl(),newsModel.getContent(), StaticValues.FLAG_NEWS_INTENT, view, position);
            }
        });
        if(!(newsModel.getImUrl().equals("")|| newsModel.getImUrl()==null))
        {
            Glide.with(context)
                    .load(newsModel.getImUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(newsViewHolder.imViewDescription);
        }
        else {
            Glide.with(context)
                    .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(newsViewHolder.imViewDescription);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    public void add(NewsModel r) {
        arrayList.add(r);
        notifyItemInserted(arrayList.size()-1 );
    }

    public void addAll(ArrayList<NewsModel> opResults) {
        for (NewsModel result : opResults) {
            add(result);
        }
    }

    protected class NewsViewHolder extends RecyclerView.ViewHolder {
        private CardView containerLayout;
        private ImageView imViewDescription;
        private TextView tvTitle;
        public NewsViewHolder(View itemView) {
            super(itemView);
            containerLayout = itemView.findViewById(R.id.layout_study_item);
            imViewDescription = itemView.findViewById(R.id.imv_studies_item);
            tvTitle = itemView.findViewById(R.id.tv_studies_item);
        }
    }


    public interface CustomButtonListener {
        public void onItemNewsClickListner(String title, String id, String imUrl, String content, final int FLAG_INTENT, View buttonView, int position);
    }
    public void setCustomButtonListner(CustomButtonListener listener) {
        this.customListener = listener;
    }
}
