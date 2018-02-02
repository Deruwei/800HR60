package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/11/21.
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.vv_welcome)
    VideoView vvWelcome;
    @BindView(R.id.cl_bg)
    ConstraintLayout clBg;
    private SharedPreferencesUtils sUtils;

    public static int ResultCode = 1001;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        sUtils=new SharedPreferencesUtils(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.welcome;
        vvWelcome.setVideoURI(Uri.parse(uri));
        vvWelcome.start();
        vvWelcome.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sUtils.setBooleanValue(Constants.IS_GUIDE, true);
                Intent intent=new Intent(WelcomeActivity.this,SplashActivity.class);
                setResult(ResultCode,intent);
               finish();
            }
        });
        vvWelcome.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                sUtils.setBooleanValue(Constants.IS_GUIDE, true);
                Intent intent=new Intent(WelcomeActivity.this,SplashActivity.class);
                setResult(ResultCode,intent);
                finish();
                return false;
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        ButterKnife.bind(this);
    }
}
