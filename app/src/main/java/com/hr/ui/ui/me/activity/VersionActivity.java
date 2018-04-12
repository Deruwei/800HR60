package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hr.ui.BuildConfig;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.VersionBean;
import com.hr.ui.ui.me.contract.VersionContract;
import com.hr.ui.ui.me.model.VersionModel;
import com.hr.ui.ui.me.presenter.VersionPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.PopWindowUpdate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/2/6.
 */

public class VersionActivity extends BaseActivity<VersionPresenter, VersionModel> implements VersionContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.cl_version)
    ConstraintLayout clVersion;
    private WebSettings webSettings;
    private String url = "http://m.800hr.com/app/upgrade/6.0/";
    private PopWindowUpdate popWindowUpdate;
    private String agreementUrl;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, VersionActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    public static void startAction(Activity activity,String url) {
        Intent intent = new Intent(activity, VersionActivity.class);
        intent.putExtra("url",url);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public int getLayoutId() {
        return R.layout.layout_web;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        agreementUrl=getIntent().getStringExtra("url");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wvContent.canGoBack()) {
                    wvContent.goBack();
                } else {
                    finish();
                }
            }
        });
        tvToolbarSave.setText(R.string.updateNow);
        tvToolbarSave.setVisibility(View.VISIBLE);
        webSettings = wvContent.getSettings();
        tvToolbarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getVersion(BuildConfig.VERSION_NAME);
            }
        });
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        if(agreementUrl==null||"".equals(agreementUrl)) {
            wvContent.loadUrl(url);
        }else{
            wvContent.loadUrl(agreementUrl);
            tvToolbarSave.setVisibility(View.GONE);
        }
        wvContent.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //设置不用系统浏览器打开,直接显示在当前Webview
        wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        wvContent.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //System.out.println("标题在这里");
                tvToolbarTitle.setText("行业找工作（"+BuildConfig.VERSION_NAME+"）");
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                  /*  loading.setText(progress);*/
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    /*loading.setText(progress);*/
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void getVersionSuccess(VersionBean.AndroidBean androidBean) {
        if(Utils.checkVersion(androidBean.getVer(),BuildConfig.VERSION_NAME)==false){
            ToastUitl.showShort("已经是最新版本了");
        }else {
            popWindowUpdate = new PopWindowUpdate(this, new PopupWindow(this), androidBean, clVersion);
        }
    }
}
