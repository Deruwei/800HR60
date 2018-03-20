package com.hr.ui.ui.main.adapter;

import android.graphics.Typeface;
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

import javax.crypto.spec.IvParameterSpec;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/12/14.
 */

public class MyCityAdapter extends BaseAdapter {
    private List<CityBean> cityBeanList;
    private ViewHolder viewHolder;
    public MyCityAdapter(List<CityBean> cityBeanList){
        this.cityBeanList=cityBeanList;
    }
    @Override
    public int getCount() {
        return cityBeanList==null?0:cityBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(HRApplication.getAppContext(), R.layout.item_selectcity, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvItemSelectCityName.setText(cityBeanList.get(position).getName());
        viewHolder.rlItemSelectCity.setBackgroundColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.resumeContent_bg));
        if(position==0) {
            viewHolder.tvItemSelectCityName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else{
            viewHolder.tvItemSelectCityName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        if(cityBeanList.get(position).isCheck()==true){
            viewHolder.ivItemSelectCityCheck.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ivItemSelectCityCheck.setVisibility(View.GONE);
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
