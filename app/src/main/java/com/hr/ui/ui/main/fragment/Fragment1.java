package com.hr.ui.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.activity.CompanyDetailActivity;
import com.hr.ui.ui.main.activity.ShowMsgActivity;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.DialogView;
import com.hr.ui.view.MyResumeScoreProgressBar;
import com.hr.ui.view.PieChartView;
import com.hr.ui.view.SnailBar;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdr on 2017/11/22.
 */

public class Fragment1 extends Fragment {
    @BindView(R.id.tv_fragment)
    TextView tvFragment;
    @BindView(R.id.pb)
    MyResumeScoreProgressBar pb;
    Unbinder unbinder;
    @BindView(R.id.tv_fragment2)
    TextView tvFragment2;
    @BindView(R.id.dv_view)
    DialogView dvView;
    @BindView(R.id.sb_fragment)
    SnailBar sbFragment;
    @BindView(R.id.pcv)
    PieChartView pcv;
    private PopupWindow popupWindow,popupWindow2;
    private int len;

    public static Fragment1 newInstance(String s) {
        Fragment1 navigationFragment = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.layout_fragment1, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        dvView.setListener(new DialogView.OnViewClickListener() {
            @Override
            public void onViewClick(int x, int y) {
                dvView.setVisibility(View.GONE);
            }
        });
        dvView.setVisibility(View.GONE);
        pb.setProgram(60);
        sbFragment.setMaxCount(100);
        sbFragment.setCurrentCount(60);
        pcv.SetProgram(80);
        return rootView;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    pb.setProgram(60);
                    sbFragment.setMaxCount(100);
                    sbFragment.setCurrentCount(60);
                    pcv.SetProgram(80);
                    //ToastUitl.showShort("你好");
                    break;
            }
        }
    };
    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        //Log.i("当前的数据",hidden+"");
        if (hidden==false) {
         Message message=Message.obtain();
         message.what=1;
         handler.sendMessage(message);
        }
    }
    private void initPopWindow() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pop, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        Button mBtnOK = popView.findViewById(R.id.btn_go);
        popupWindow.setOutsideTouchable(true);
        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CompanyDetailActivity.startAction(getActivity());*/
                initPopWindow2();
            }
        });
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    private void initPopWindow2() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pop, null);

        popupWindow2 = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);  popupWindow2.setOutsideTouchable(true);
        Button mBtnOK = popView.findViewById(R.id.btn_go);
        Button mBtnCancle = popView.findViewById(R.id.btn_cancle);
        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanyDetailActivity.startAction(getActivity());
            }
        });
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow2.dismiss();
            }
        });
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        popupWindow2.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser== true) {
            //相当于Fragment的onResume
            pb.setProgram(60);
            sbFragment.setMaxCount(100);
            sbFragment.setCurrentCount(60);
            pcv.SetProgram(80);
        } else {
            //相当于Fragment的onPause
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_fragment2,R.id.tv_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fragment2:
                //SelectOptionsActivity.startAction(getActivity());
                ShowMsgActivity.startAction(getActivity());
                getActivity().finish();
                break;
            case R.id.tv_fragment:
               SplashActivity.startAction(getActivity(),1);
                SharedPreferencesUtils sUtils=new SharedPreferencesUtils(getActivity());
                sUtils.setIntValue(Constants.ISAUTOLOGIN,0);
                break;

        }
    }
}
