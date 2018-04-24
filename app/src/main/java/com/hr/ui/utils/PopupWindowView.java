package com.hr.ui.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.activity.JobSearchResultActivity;
import com.hr.ui.ui.main.adapter.MyCityAdapter;
import com.hr.ui.ui.main.adapter.MyProvinceAdapter;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.view.MyFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdr on 2018/1/9.
 */

public class PopupWindowView {
    private List<CityBean> cityBeanList2;
    private List<CityBean> cityBeanList;
    private MyProvinceAdapter myProvinceAdapter;
    private MyCityAdapter myCityAdapter;
    private String tag;
    private Activity context;
    private List<CityBean> provinceCityList=new ArrayList<>();
    private List<CityBean> selectCityList=new ArrayList<>();
    private ListView lvSelectProvince;
    private ListView lvSelectCity;
    private List<CityBean> cityList=new ArrayList<>();
    private int currentPosition;
    private int type;
    private MyFlowLayout llSelectedCity;
    private RelativeLayout rlSelectCityShow;
    private TextView tvSelectCityNum;
    private TextView tvCancel,tvOK;
    private PopupWindow popupWindow;
    private int sum;
    private int num;
    private int page;
    public PopupWindowView(final PopupWindow popupWindow, final int page1, final List<CityBean> selectCityList, TextView tvCancel, TextView tvOK, MyFlowLayout llSelectedCity, ListView lvSelectCity,
                           ListView lvSelectProvince, final Activity context, RelativeLayout rlSelectCityShow, TextView tvSelectCityNum){
            this.tvCancel=tvCancel;
            this.selectCityList=selectCityList;
            this.tvOK=tvOK;
            this.page=page1;
            this.llSelectedCity=llSelectedCity;
            this.lvSelectCity= lvSelectCity;
            this.lvSelectProvince=lvSelectProvince;
            this.context=context;
            this.rlSelectCityShow=rlSelectCityShow;
            this.popupWindow=popupWindow;
            this.tvSelectCityNum=tvSelectCityNum;
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(popupWindow!=null){
                        popupWindow.dismiss();
                    }
                    selectCityList.clear();
                }
            });
            tvOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JobSearchResultActivity.instance.setPlaceId(selectCityList);
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    JobSearchResultActivity.instance.page = 1;
                    JobSearchResultActivity.instance.doSearch(true);
                }
            });
            initSelectCity();
    }
    private void initSelectCity() {
        cityBeanList = FromStringToArrayList.getInstance().getCityList();
        cityBeanList2 = FromStringToArrayList.getInstance().getCityList();
        /**
         * 找出直辖市和省份，左边的数据
         */
        for (int i = 0; i < cityBeanList.size(); i++) {
            if (cityBeanList.get(i).getId().endsWith("00")) {
                provinceCityList.add(cityBeanList.get(i));
            }
        }
        if(selectCityList!=null&&selectCityList.size()!=0) {
            for (int i = 0; i < selectCityList.size(); i++) {
                addView(selectCityList.get(i));
                for (int j = 0; j < 4; j++) {
                    if (selectCityList.get(i).getId().equals(provinceCityList.get(j).getId())) {
                        provinceCityList.get(j).setCheck(true);
                    }
                }
                for(int j=4;j<provinceCityList.size();j++){
                    if(selectCityList.get(i).getId().substring(0,2).equals(provinceCityList.get(j).getId().substring(0,2))){
                        provinceCityList.get(j).setCheck(true);
                    }
                }
            }
        }

        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //Log.i("职位信息",provinceCityList.toString());
                    myProvinceAdapter = new MyProvinceAdapter(provinceCityList, type);
                    lvSelectProvince.setAdapter(myProvinceAdapter);
                    if(selectCityList!=null&&selectCityList.size()!=0){
                        cityList=new ArrayList<>();
                        for(int i=4;i<provinceCityList.size();i++){
                            if(provinceCityList.get(i).isCheck()==true){
                                provinceCityList.get(i).setCheck(false);
                                currentPosition=i;
                            }
                        }
                        if(currentPosition>=4) {
                            provinceCityList.get(currentPosition).setCheck(true);
                        }
                        for (int i = 0; i < cityBeanList2.size(); i++) {
                            if (provinceCityList.get(currentPosition).getId().substring(0, 2).equals(cityBeanList2.get(i).getId().substring(0, 2))&&currentPosition>=4) {
                                cityList.add(cityBeanList2.get(i));
                            }
                        }
                        for (int i = 0; i < cityList.size(); i++) {
                            cityList.get(i).setCheck(false);
                        }
                        Message message = Message.obtain();
                        message.what = 2;
                        handler.sendMessage(message);

                    }
                    lvSelectProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            cityList = new ArrayList<>();
                            currentPosition = position;
                            //省级
                            if (position >= 4) {
                                for (int i = 4; i < provinceCityList.size(); i++) {
                                    provinceCityList.get(i).setCheck(false);
                                }
                                provinceCityList.get(position).setCheck(true);
                                for (int i = 0; i < cityBeanList2.size(); i++) {
                                    if (provinceCityList.get(position).getId().substring(0, 2).equals(cityBeanList2.get(i).getId().substring(0, 2))) {
                                        cityList.add(cityBeanList2.get(i));
                                    }
                                }
                                for (int i = 0; i < cityList.size(); i++) {
                                    cityList.get(i).setCheck(false);
                                }
                            } else {
                                    if (provinceCityList.get(position).isCheck() == false) {
                                        if (selectCityList.size() >= 5) {
                                            ToastUitl.showShort("选择城市已经达到最大值");
                                            return;
                                        }
                                        for (int i = 4; i < provinceCityList.size(); i++) {
                                            provinceCityList.get(i).setCheck(false);
                                        }
                                        provinceCityList.get(position).setCheck(true);
                                        selectCityList.add(provinceCityList.get(position));
                                        addView(provinceCityList.get(position));
                                    } else {
                                        for (int i = 4; i < provinceCityList.size(); i++) {
                                            provinceCityList.get(i).setCheck(false);
                                        }
                                        for(int i=0;i<selectCityList.size();i++){
                                            if(selectCityList.get(i).getId().equals(provinceCityList.get(position).getId())){
                                                selectCityList.remove(i);
                                                break;
                                            }
                                        }
                                        // Log.i("职位信息",selectCityList.toString()+"呵呵"+provinceCityList.get(position).toString());
                                        selectCityList.remove(provinceCityList.get(position));
                                        provinceCityList.get(position).setCheck(false);
                                        removeView(provinceCityList.get(position));
                                    }
                                    JobSearchResultActivity.instance.setPlaceId(selectCityList);
                            }
                            myProvinceAdapter.notifyDataSetChanged();
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);


                        }
                    });
                    break;
                case 2:
                    if (selectCityList != null && selectCityList.size() != 0) {
                        for (int i = 0; i < selectCityList.size(); i++) {
                            for (int j = 0; j < cityList.size(); j++) {
                                if (selectCityList.get(i).getId().equals(cityList.get(j).getId())) {
                                    cityList.get(j).setCheck(true);
                                }
                            }
                        }
                    }
                    myCityAdapter = new MyCityAdapter(cityList);
                    lvSelectCity.setAdapter(myCityAdapter);
                    lvSelectCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                if (cityList.get(position).isCheck() == false) {
                                    List<CityBean> removeList=new ArrayList<>();
                                    if(cityList.get(position).getId().endsWith("00")){
                                        for(int i=0;i<selectCityList.size();i++){
                                            if(selectCityList.get(i).getId().substring(0,2).equals(cityList.get(position).getId().substring(0,2))){
                                                removeList.add(selectCityList.get(i));
                                                removeView(selectCityList.get(i));
                                            }
                                        }
                                    }else{
                                        cityList.get(0).setCheck(false);
                                        for(int i=0;i<selectCityList.size();i++){
                                            if(selectCityList.get(i).getId().equals(cityList.get(0).getId())){
                                                removeList.add(selectCityList.get(i));
                                                removeView(selectCityList.get(i));
                                            }
                                        }
                                    }
                                    for(int i=0;i<removeList.size();i++){
                                        for(int j=0;j<cityList.size();j++){
                                            if(removeList.get(i).getId().equals(cityList.get(j).getId())){
                                                cityList.get(j).setCheck(false);
                                            }
                                        }
                                    }
                                    selectCityList.removeAll(removeList);
                                    if (selectCityList.size() >= 5) {
                                        ToastUitl.showShort("选择城市已经达到最大值");
                                        return;
                                    }
                                    cityList.get(position).setCheck(true);
                                    selectCityList.add(cityList.get(position));
                                    addView(cityList.get(position));
                                } else {
                                    for(int i=0;i<selectCityList.size();i++){
                                        if(cityList.get(position).getId().equals(selectCityList.get(i).getId())){
                                            selectCityList.remove(selectCityList.get(i));
                                            break;
                                        }
                                    }
                                    cityList.get(position).setCheck(false);
                                    removeView(cityList.get(position));

                                }
                                JobSearchResultActivity.instance.setPlaceId(selectCityList);
                                myCityAdapter.notifyDataSetChanged();
                        }
                    });
                    break;
            }
        }
    };

    /**
     * 添加已选职位视图
     *
     * @param cityBean
     */
    private void addView(final CityBean cityBean) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 15;
        params.topMargin = 12;
        params.bottomMargin = 12;
        params.rightMargin = 15;
        final LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.item_textview_selected, null, false);
        ll.setLayoutParams(params);
        TextView tv=ll.findViewById(R.id.item_select);
        tv.setText(cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCityList.remove(cityBean);
                llSelectedCity.removeView(llSelectedCity.findViewWithTag(cityBean.getId()));
                sum = selectCityList.size();
                getRightDataFresh();
                getLeftDataFresh();
                if (sum == 0) {
                    rlSelectCityShow.setVisibility(View.GONE);
                }
                setNum();
            }
        });
        ll.setTag(cityBean.getId());
        llSelectedCity.addView(ll);
        setNum();
    }

    private void getLeftDataFresh() {
        for (int i = 0; i < provinceCityList.size(); i++) {
            provinceCityList.get(i).setCheck(false);
            for (int j = 0; j < selectCityList.size(); j++) {
                if (provinceCityList.get(i).getId().equals(selectCityList.get(j).getId())) {
                    provinceCityList.get(i).setCheck(true);
                }
            }
        }
        if (currentPosition >= 4) {
            provinceCityList.get(currentPosition).setCheck(true);
        }
        myProvinceAdapter.notifyDataSetChanged();
    }

    private void getRightDataFresh() {
        if (cityList!=null&&!"".equals(cityList)) {
            for(int i=0;i<cityList.size();i++){
                cityList.get(i).setCheck(false);
            }
            for (int i = 0; i < cityList.size(); i++) {
                for (int j = 0; j < selectCityList.size(); j++) {
                    if (cityList.get(i).getId().equals(selectCityList.get(j).getId())) {
                        cityList.get(i).setCheck(true);
                    }
                }
            }
            myCityAdapter.notifyDataSetChanged();
        }
    }

    private void removeView(CityBean cityBean) {
        llSelectedCity.removeView(llSelectedCity.findViewWithTag(cityBean.getId()));
        sum = selectCityList.size();
        if (sum == 0) {
            rlSelectCityShow.setVisibility(View.GONE);
        }
        setNum();
    }

    private void setNum() {
        sum = selectCityList.size();
        if (sum > 0) {
            rlSelectCityShow.setVisibility(View.VISIBLE);
        }else{
            rlSelectCityShow.setVisibility(View.GONE);
        }
        tvSelectCityNum.setText(sum + "");
    }
}
