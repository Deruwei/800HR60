package com.hr.ui.ui.main.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.City;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySelectCityLetterCityAdapter extends RecyclerView.Adapter<MySelectCityLetterCityAdapter.MyViewHolder> {
    private List<City> cityList;
    private OnItemClickListener onItemClickListener;
    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectcitybyletter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        if(cityList.get(position).isCheck()){
            holder.tvItemSelectCityByLetterCityName.setBackgroundResource(R.drawable.edit_bg_citysearch_orange);
            holder.tvItemSelectCityByLetterCityName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.new_main));
        }else{
            holder.tvItemSelectCityByLetterCityName.setBackgroundResource(R.drawable.edit_bg_citysearch);
            holder.tvItemSelectCityByLetterCityName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.color_333));
        }
        holder.tvItemSelectCityByLetterCityName.setText(cityList.get(position).getName());
        if(onItemClickListener!=null){
            final int pos=position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemCLick(v,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemSelectCityByLetter_CityName)
        TextView tvItemSelectCityByLetterCityName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
