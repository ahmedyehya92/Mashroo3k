package com.intellidev.app.mashroo3k.data;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.intellidev.app.mashroo3k.uiutilities.CustomRecyclerView;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.R;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class FeasibilityStudyViewHolder extends CustomRecyclerView.ViewHolder {

    public ImageView imStudy;
    public CustomTextView tvTitleStudy;
    public CardView loutStudyItem;

    public FeasibilityStudyViewHolder(View itemView) {
        super(itemView);
        this.imStudy = itemView.findViewById(R.id.imv_studies_item);
        this.tvTitleStudy = itemView.findViewById(R.id.tv_studies_item);
        this.loutStudyItem = itemView.findViewById(R.id.layout_study_item);
    }
}
