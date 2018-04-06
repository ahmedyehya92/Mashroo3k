package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.intellidev.app.mashroo3k.uiutilities.CustomRecyclerView;

import org.jsoup.Jsoup;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 4/6/18.
 */

public class SearchResultAdapter extends CustomRecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FeasibilityStudyModel> arrayList;
    private Context context;
    private CustomButtonListener customListener;

    public SearchResultAdapter(Context context,
                       ArrayList<FeasibilityStudyModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.test_view_study_item, parent, false);
        viewHolder = new ResultViewHolder(viewItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final FeasibilityStudyModel model = arrayList.get(position);
        final ResultViewHolder newsViewHolder = (ResultViewHolder) holder;

        newsViewHolder.tvTitle.setText(model.getTitle());
        newsViewHolder.containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.onItemClickListner(model.getId(), model.getTitle(),model.getContent(),model.getImgUrl(),model.getServices(),model.getMoney(),model.getPrice(),view,position);
            }
        });
        if(!(model.getImgUrl().equals("")|| model.getImgUrl()==null))
        {
            Glide.with(context)
                    .load(model.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
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

    public void add(FeasibilityStudyModel r) {
        arrayList.add(r);
        notifyItemInserted(arrayList.size()-1 );
    }

    public void addAll(ArrayList<FeasibilityStudyModel> opResults) {
        for (FeasibilityStudyModel result : opResults) {
            add(result);
        }
    }


    protected class ResultViewHolder extends RecyclerView.ViewHolder {
        private CardView containerLayout;
        private ImageView imViewDescription;
        private TextView tvTitle;
        public ResultViewHolder(View itemView) {
            super(itemView);
            containerLayout = itemView.findViewById(R.id.layout_study_item);
            imViewDescription = itemView.findViewById(R.id.imv_studies_item);
            tvTitle = itemView.findViewById(R.id.tv_studies_item);
        }
    }

    public interface CustomButtonListener {
        public void onItemClickListner(String id, String title, String content, String imgUrl, String services, String money, String price, View buttonView, int position);
    }
    public void setCustomButtonListner(CustomButtonListener listener) {
        this.customListener = listener;
    }
}
