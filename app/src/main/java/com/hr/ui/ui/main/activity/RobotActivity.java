package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.ui.main.adapter.MyRobotAdapter;

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
        if (titles != null && titles.size() != 0) {
            rlRobot.setVisibility(View.VISIBLE);
        }
        initDate();
    }

    private void initDate() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRobot.setLayoutManager(linearLayoutManager);
        mAdapter = new MyRobotAdapter(this, titles);
        rvRobot.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_robotWriteResume)
    public void onViewClicked() {
        PersonalInformationActivity.startAction(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            MainActivity.startAction(RobotActivity.this, userId);
            AppManager.getAppManager().finishAllActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
