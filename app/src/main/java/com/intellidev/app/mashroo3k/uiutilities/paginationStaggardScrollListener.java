package com.intellidev.app.mashroo3k.uiutilities;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Ahmed Yehya on 26/02/2018.
 */

public abstract class paginationStaggardScrollListener extends RecyclerView.OnScrollListener {

    StaggeredGridLayoutManager layoutManager;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    public paginationStaggardScrollListener (StaggeredGridLayoutManager layoutManager)
    {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        hideCatList();
        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        int[] firstVisibleItems = null;
        firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
        }


        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount ) {

                loadMoreItems();
            }
        }
    }

    protected  abstract void hideCatList();

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}
