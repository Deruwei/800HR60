package com.hr.ui.ui.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.City;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.activity.JobSerchActivity;
import com.hr.ui.utils.LoadingDialog;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySelectCityLetterAdapter extends RecyclerView.Adapter<MySelectCityLetterAdapter.MyViewHolder> {
    private String[] letters;
    private List<City> mHotCityList,mTotalCityList;
    private List<City> locationList;
    private List<City> selectCityList;
    private Activity context;
    private List<MySelectCityLetterCityAdapter> adapterList=new ArrayList<>();
    public void setLetters(String[] letters) {
        this.letters = letters;
    }
    public MySelectCityLetterAdapter(Activity context){
        this.context=context;
    }
    public void setSelectCityList(List<City> selectCityList) {
        this.selectCityList = selectCityList;
    }

    public void setDate(List<City> locationList, List<City> mHotCityList, List<City> mTotalCityList){
        this.locationList= locationList;
        this.mHotCityList=mHotCityList;
        this.mTotalCityList=mTotalCityList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectcityletter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvItemLetter.setText(letters[position]);
        if(position==0){
            MySelectCityLetterCityAdapter adapter=new MySelectCityLetterCityAdapter();
            adapter.setCityList(locationList);
            holder.rvItemLetter.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void OnItemCLick(View view, int position) {
                    if("定位失败".equals(locationList.get(position).getName())){

                    }else if("定位中".equals(locationList.get(position).getName())){

                    }else{

                    }
                }
            });
            if(!adapterList.contains(adapter)){
                adapterList.add(adapter);
            }
        }else if(position==1){
            final MySelectCityLetterCityAdapter adapter=new MySelectCityLetterCityAdapter();
            adapter.setCityList(mHotCityList);
            holder.rvItemLetter.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void OnItemCLick(View view, int position) {
                    selectCityList.clear();
                    if(mHotCityList.get(position).isCheck()){
                       setListCheck(mHotCityList.get(position),false);
                    }else {
                        setListCheck(mHotCityList.get(position),true);
                        selectCityList.add(mHotCityList.get(position));
                        List<CityBean> cityBeanList=new ArrayList<>();
                        for(int i=0;i<selectCityList.size();i++){
                            CityBean cityBean=new CityBean();
                            cityBean.setId(selectCityList.get(i).getCode());
                            cityBean.setName(selectCityList.get(i).getName());
                            cityBeanList.add(cityBean);
                        }
                        JobSerchActivity.instance.setPlace(cityBeanList);
                        context.finish();
                    }
                    notification();
                }
            });
            if(!adapterList.contains(adapter)){
                adapterList.add(adapter);
            }
        }else{
            final List<City> cityList=new ArrayList<>();
            for(int i=0;i<mTotalCityList.size();i++){
                if(mTotalCityList.get(i).getPinyin().startsWith(letters[position])) {
                    cityList.add(mTotalCityList.get(i));
                }
            }
            if(cityList==null||cityList.size()==0){
                holder.tvItemLetter.setVisibility(View.GONE);
            }else{
                holder.tvItemLetter.setVisibility(View.VISIBLE);
            }
            final MySelectCityLetterCityAdapter adapter=new MySelectCityLetterCityAdapter();
            adapter.setCityList(cityList);
            holder.rvItemLetter.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void OnItemCLick(View view, int position) {
                    selectCityList.clear();
                    if(cityList.get(position).isCheck()){
                        setListCheck(cityList.get(position),false);
                    }else {
                        setListCheck(cityList.get(position),true);
                        selectCityList.add(cityList.get(position));
                        List<CityBean> cityBeanList=new ArrayList<>();
                        for(int i=0;i<selectCityList.size();i++){
                            CityBean cityBean=new CityBean();
                            cityBean.setId(selectCityList.get(i).getCode());
                            cityBean.setName(selectCityList.get(i).getName());
                            cityBeanList.add(cityBean);
                        }
                        JobSerchActivity.instance.setPlace(cityBeanList);
                        context.finish();
                    }
                   notification();
                }
            });
            if(!adapterList.contains(adapter)){
                adapterList.add(adapter);
            }
        }
    }
    @Override
    public int getItemCount() {
        return letters==null?0:letters.length;
    }
    private void setListCheck(City city,boolean b){
        if(mTotalCityList!=null&&mTotalCityList.size()!=0) {
            for (int i = 0; i < mTotalCityList.size(); i++) {
                if (mTotalCityList.get(i).getCode().equals(city.getCode())) {
                    mTotalCityList.get(i).setCheck(b);
                }else{
                    mTotalCityList.get(i).setCheck(false);
                }
            }
        }
    }
    private void notification(){
        if(adapterList!=null){
            for(int i=0;i<adapterList.size();i++){
                adapterList.get(i).notifyDataSetChanged();
            }
        }

    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_letter)
        TextView tvItemLetter;
        @BindView(R.id.rv_item_letter)
        RecyclerView rvItemLetter;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            GridLayoutManager manager=new GridLayoutManager(itemView.getContext(),3){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvItemLetter.setLayoutManager(manager);
        }
    }
}
