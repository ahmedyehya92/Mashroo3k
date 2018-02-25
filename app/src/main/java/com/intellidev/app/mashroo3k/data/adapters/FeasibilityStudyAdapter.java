package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellidev.app.mashroo3k.CustomRecyclerView;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.FeasibilityStudyViewHolder;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class FeasibilityStudyAdapter extends CustomRecyclerView.Adapter<FeasibilityStudyViewHolder>{

    private ArrayList<FeasibilityStudyModel> arrayList;
    private Context context;
    private customButtonListener customListener;

    public FeasibilityStudyAdapter(Context context,
                                ArrayList<FeasibilityStudyModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(FeasibilityStudyViewHolder holder, final int position) {
        final FeasibilityStudyModel model = arrayList.get(position);

        FeasibilityStudyViewHolder mainHolder = holder;// holder


        mainHolder.tvTitleStudy.setText(model.getTitle());

        mainHolder.loutStudyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.onItemStudyClickListner(model.getTitle(),model.getContent(),model.getImgUrl(),model.getServices(),model.getMoney(),model.getPrice(),view,position);
            }
        });

        Picasso.with(context)
                .load(model.getImgUrl().toString())
                .placeholder(R.drawable.placeholder)
                .into(mainHolder.imStudy);


    }

    @Override
    public FeasibilityStudyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.view_study_item, viewGroup, false);
        FeasibilityStudyViewHolder listHolder = new FeasibilityStudyViewHolder(mainGroup);
        return listHolder;

    }
    public interface customButtonListener {
        public void onItemStudyClickListner(String title, String content, String imgUrl, String services, String money, String price, View buttonView, int position);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }
}
