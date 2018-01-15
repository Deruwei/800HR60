package com.hr.ui.utils.recyclerviewutils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by wdr on 2018/1/13.
 */

public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
