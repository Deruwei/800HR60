package com.hr.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.activity.SelectOptionsActivity;
import com.hr.ui.ui.main.activity.SelectPositionActivity;
import com.hr.ui.ui.main.adapter.MyCityAdapter;
import com.hr.ui.ui.main.adapter.MyPositionClassAdapter;
import com.hr.ui.ui.main.adapter.MyProvinceAdapter;
import com.hr.ui.ui.main.fragment.JobSearchFragment;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;
import com.hr.ui.view.MyFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdr on 2018/1/9.
 */

public class PopupWindowPositonClassView {
    private Activity activity;
    private PopupWindow popupWindow;
    private RecyclerView rv;
    private List<CityBean> positionClassList=new ArrayList<>();
    private MyPositionClassAdapter adapter;
    private List<CityBean> selectPositionClassList=new ArrayList<>();
    private View viewBase;
    public PopupWindowPositonClassView(PopupWindow popupWindow, Activity activity,View viewBase){
        this.popupWindow=popupWindow;
        this.activity=activity;
        this.viewBase=viewBase;
        initData();
    }

    private void initData() {
        View view = activity.getLayoutInflater().inflate(R.layout.layout_positionclass, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rv=view.findViewById(R.id.rv_positionClass);
        GridLayoutManager manager=new GridLayoutManager(activity,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        int[] location = new int[2];
        int x = location[0];
        int y = location[1];
        viewBase.getLocationOnScreen(location);
       /* WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        popupWindow.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindow.setHeight(wm.getDefaultDisplay().getHeight() - y);*/
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.style_pop_animation);
        popupWindow.showAtLocation(viewBase,
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        initUI();
    }

    private void initUI() {
        positionClassList= FromStringToArrayList.getInstance().getPositionClassList();
        adapter=new MyPositionClassAdapter(activity,positionClassList);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                    for(int i=0;i<positionClassList.size();i++){
                        positionClassList.get(i).setCheck(false);
                    }
                    selectPositionClassList.clear();
                    positionClassList.get(position).setCheck(true);
                    selectPositionClassList.add(positionClassList.get(position));
                    adapter.notifyDataSetChanged();
                    if(activity instanceof SelectOptionsActivity) {
                        SelectOptionsActivity.instance.setPositionClassList(selectPositionClassList);
                    }else if(activity instanceof SelectPositionActivity){
                        SelectPositionActivity.instance.setPositionClassList(selectPositionClassList);
                    }
                    popupWindow.dismiss();
            }
        });
    }
}
