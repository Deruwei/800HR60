package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.ui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/12/11.
 */

public class MyRobotAdapter extends RecyclerView.Adapter<MyRobotAdapter.RobotViewHolder> {
    private List<String> titles;
    private Context context;
    private RobotViewHolder robotViewHolder;

    public MyRobotAdapter(Context context, ArrayList<String> titles) {
        this.context = context;
        this.titles = titles;
    }

    @Override
    public RobotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_text, parent, false);
        return new RobotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RobotViewHolder holder, int position) {
        holder.tvItemText.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    class RobotViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemText)
        TextView tvItemText;
        public RobotViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
