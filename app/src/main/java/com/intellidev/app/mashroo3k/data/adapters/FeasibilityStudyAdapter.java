package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.PaginationAdapterCallback;
import com.intellidev.app.mashroo3k.uiutilities.CustomRecyclerView;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.FeasibilityStudyViewHolder;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class FeasibilityStudyAdapter extends CustomRecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private ArrayList<FeasibilityStudyModel> arrayList;
    private Context context;
    private customButtonListener customListener;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;
    private String errorMsg;

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
    public int getItemViewType(int position) {

        return (position == arrayList.size() -1 && isLoadingAdded) ? LOADING : ITEM;
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

    public void remove(FeasibilityStudyModel r) {
        int position = arrayList.indexOf(r);
        if (position > -1) {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public FeasibilityStudyModel getItem(int position) {
        return arrayList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        //add(new OpportunityModel());
        add(getItem(arrayList.size()-1));
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = arrayList.size() - 1;
        FeasibilityStudyModel result = getItem(position);

        if (result != null) {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final FeasibilityStudyModel model = arrayList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final FeasibilityStudyViewHolder mainHolder = (FeasibilityStudyViewHolder) holder;
                mainHolder.tvTitleStudy.setText(model.getTitle());

                mainHolder.loutStudyItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customListener.onItemStudyClickListner(model.getId(), model.getTitle(),model.getContent(),model.getImgUrl(),model.getServices(),model.getMoney(),model.getPrice(),view,position);
                    }
                });

                if (!(model.getImgUrl()== null || model.getImgUrl().equals(""))) {
                    Log.d("FS Adapter", model.getImgUrl());

                    Glide.with(context)
                            .load(model.getImgUrl().toString()).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder)
                            .into(mainHolder.imStudy);
                }
                else
                    Glide.with(context)
                            .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mainHolder.imStudy);

                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) loadingVH.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);


                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }



    }

    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = mInflater.inflate(
                        R.layout.test_view_study_item, viewGroup, false);
                viewHolder = new FeasibilityStudyViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = mInflater.inflate(R.layout.item_progress, viewGroup, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }


        return viewHolder;

    }


    public interface customButtonListener {
        public void onItemStudyClickListner(String id, String title, String content, String imgUrl, String services, String money, String price, View buttonView, int position);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;


        public LoadingVH(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.loadmore_progress);

            if (mProgressBar != null) {
                mProgressBar.setIndeterminate(true);
                mProgressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                mProgressBar.setVisibility(View.VISIBLE);
            }



            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(arrayList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public void setPagingAdapterCallback (PaginationAdapterCallback pagingAdapterCallback)
    {
        this.mCallback = pagingAdapterCallback;
    }
}
