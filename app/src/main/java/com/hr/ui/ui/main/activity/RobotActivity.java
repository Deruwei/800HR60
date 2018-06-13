package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.activity.PhoneLoginActivity;
import com.hr.ui.ui.main.adapter.MyRobotAdapter;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/11.
 */

public class RobotActivity extends BaseNoConnectNetworkAcitivty{
    @BindView(R.id.rv_robot)
    RecyclerView rvRobot;
    @BindView(R.id.rl_robot)
    RelativeLayout rlRobot;
    @BindView(R.id.btn_robotWriteResume)
    Button btnRobotWriteResume;
    private ArrayList<String> titles;
    private MyRobotAdapter mAdapter;
    private String TAG = MultipleResumeActivity.class.getSimpleName();
    private int userId;
    private MyDialog myDialog;
    private SharedPreferencesUtils sUtils;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int userId) {
        Intent intent = new Intent(activity, RobotActivity.class);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    public static void startAction(Activity activity, ArrayList<String> titles) {
        Intent intent = new Intent(activity, RobotActivity.class);
        intent.putStringArrayListExtra("titles", titles);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_rotbot;
    }

    @Override
    public void initView() {
        userId = getIntent().getIntExtra("userId", 0);
        titles = getIntent().getStringArrayListExtra("titles");
        sUtils=new SharedPreferencesUtils(this);
        //Log.i("title",titles.toString());
        if (titles != null && titles.size() != 0) {
            rlRobot.setVisibility(View.VISIBLE);
        }
        initDate();
    }

    private void initDate() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRobot.setLayoutManager(linearLayoutManager);
        mAdapter = new MyRobotAdapter(this, titles);
        rvRobot.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_robotWriteResume)
    public void onViewClicked() {
        if(titles!=null&&titles.size()!=0){
            if("基本信息".equals(titles.get(0))){
                PersonalInformationActivity.startAction(this);
            }else  if("教育背景".equals(titles.get(0))){
                EducationActivity.startAction(this);
            }else  if("工作经验".equals(titles.get(0))||"实习经历".equals(titles.get(0))){
                WorkExpActivity.startAction(this);
            }else  if("求职意向".equals(titles.get(0))){
                JobOrderActivity.startAction(this);
            }
        }else{
            if(sUtils.getBooleanValue(Constants.JUST_ELEGANTRESUME,true)){
                EducationActivity.startAction(this);
            }else {
                SelectResumeTypeActivity.startAction(this);
            }
        }
    }

    private void exitOrFinishActivity(){
            myDialog=new MyDialog(this,2);
            myDialog.setMessage(getString(R.string.exitWarning));
            myDialog.setYesOnclickListener("确定",new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    myDialog.dismiss();
                    PhoneLoginActivity.startAction(RobotActivity.this);
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
                    sUtils.setIntValue(Constants.ISAUTOLOGIN,0);
                    AppManager.getAppManager().finishAllActivity();
                }
            });
            myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitOrFinishActivity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
