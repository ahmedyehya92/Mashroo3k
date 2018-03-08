package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomRecyclerView;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.PaginationAdapterCallback;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.models.OpportunityModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 24/02/2018.
 */

public class OppertunitiesAdapter extends CustomRecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<OpportunityModel> arrayList;
    private Context context;
    private customButtonListener customListener;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    public OppertunitiesAdapter(Context context,
                                   ArrayList<OpportunityModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.view_opert_item, parent, false);
                viewHolder = new OpportVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final OpportunityModel opportunityModel = arrayList.get(position);

        switch (getItemViewType(position)) {

            case ITEM:
                final OpportVH opportVH = (OpportVH) holder;


                opportVH.tvTitle.setText(opportunityModel.getName());

                opportVH.btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customListener.onItemOppertClickListner(opportunityModel.getName(),opportunityModel.getId(),view,position);
                    }
                });

                Glide.with(context)
                        .load(opportunityModel.getImgUrl().toString()).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(opportVH.imItem);
                /*Glide.with(context)
                        .load(opportunityModel.getImgUrl().toString())
                        .into(opportVH.imItem); */

                String firstString = opportunityModel.getLocation().replace("<p>","");
                String lastString = firstString.replace("</p>","").replace("\n","");

                opportVH.tvLocation.setText(lastString);

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
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

            return (position == arrayList.size() -1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(OpportunityModel r) {
        arrayList.add(r);
        notifyItemInserted(arrayList.size()-1 );
    }

    public void addAll(ArrayList<OpportunityModel> opResults) {
        for (OpportunityModel result : opResults) {
            add(result);
        }
    }

    public void remove(OpportunityModel r) {
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
    public OpportunityModel getItem(int position) {
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
        OpportunityModel result = getItem(position);

        if (result != null) {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected class OpportVH extends RecyclerView.ViewHolder {
        private ImageView imItem;
        private CustomTextView tvTitle;
        private CustomTextView tvLocation;
        private CustomButtonTextFont btnOrder;

        public OpportVH(View itemView) {
            super(itemView);
            imItem = itemView.findViewById(R.id.imv_opport_item);
            tvTitle = itemView.findViewById(R.id.tv_opport_item);
            tvLocation = itemView.findViewById(R.id.tv_location);
            btnOrder = itemView.findViewById(R.id.btn_order);
        }
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

    public interface customButtonListener {
        public void onItemOppertClickListner(String title, String id, View buttonView, int position);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }

    public void setPagingAdapterCallback (PaginationAdapterCallback pagingAdapterCallback)
    {
        this.mCallback = pagingAdapterCallback;
    }
}
