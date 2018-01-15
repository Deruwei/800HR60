package com.hr.ui.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.ui.me.adapter.MainRecyclerAdapter;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

/**
 * Created by wdr on 2018/1/12.
 */


public  class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof MainRecyclerAdapter.ItemNoSwipeViewHolder) {
            return 0;
        }
        return makeMovementFlags(0, ItemTouchHelper.START);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        MainRecyclerAdapter adapter = (MainRecyclerAdapter) recyclerView.getAdapter();
        adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        MainRecyclerAdapter.ItemBaseViewHolder holder = (MainRecyclerAdapter.ItemBaseViewHolder) viewHolder;
        if (viewHolder instanceof MainRecyclerAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
            if (dX < -holder.mActionContainer.getWidth()) {
                dX = -holder.mActionContainer.getWidth();
            }
            holder.mViewContent.setTranslationX(dX);
            return;
        }
        if (viewHolder instanceof MainRecyclerAdapter.ItemBaseViewHolder)
            holder.mViewContent.setTranslationX(dX);
    }
}