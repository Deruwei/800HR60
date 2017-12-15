package com.hr.ui.ui.main.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.CityBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/12/14.
 */

public class MySelectPositionRightAdapter extends BaseAdapter {
    private List<CityBean> positionList;
    private ViewHolder holder;

    public MySelectPositionRightAdapter(List<CityBean> positionList) {
        this.positionList = positionList;
    }

    @Override
    public int getCount() {
        return positionList==null?0:positionList.size();
    }

    @Override
    public Object getItem(int position) {
        return positionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(HRApplication.getAppContext(), R.layout.item_selectcity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemSelectCityName.setText(positionList.get(position).getName());
        holder.rlItemSelectCity.setBackgroundColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.resumeContent_bg));
        if(positionList.get(position).isCheck()==true){
            holder.ivItemSelectCityCheck.setVisibility(View.VISIBLE);
        }else{
            holder.ivItemSelectCityCheck.setVisibility(View.GONE);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_itemSelectCityName)
        TextView tvItemSelectCityName;
        @BindView(R.id.iv_itemSelectCityCheck)
        ImageView ivItemSelectCityCheck;
        @BindView(R.id.rl_itemSelectCity)
        RelativeLayout rlItemSelectCity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
