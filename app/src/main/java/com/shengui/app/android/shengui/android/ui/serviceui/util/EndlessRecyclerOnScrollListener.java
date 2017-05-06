package com.shengui.app.android.shengui.android.ui.serviceui.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by zyy on 2017/4/19.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        Log.e("test", "onScrolled: " + visibleItemCount + "   " + totalItemCount + "    " + firstVisibleItem + "  " + loading + "  " + previousTotal);

        if (totalItemCount%10 !=0) {

        } else {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (firstVisibleItem == 0) {
                previousTotal = totalItemCount - 1;
            }
            if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
                currentPage++;
                onLoadMore(currentPage);
                loading = true;

            }
        }
    }

    public abstract void onLoadMore(int currentPage);
}
