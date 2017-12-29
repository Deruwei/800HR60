package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.ui.resume.activity.ResumeWorkExpActivity;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/15.
 */

public class ContentActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_contentOK)
    Button btnContentOK;
    @BindView(R.id.tv_textSum)
    TextView tvTextSum;
    private MyDialog myDialog;
    private String tag;

    @Override
    public int getLayoutId() {
        return R.layout.activity_content;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tag=getIntent().getStringExtra("tag");
        if(getIntent().getStringExtra("text")!=null||!"".equals(getIntent().getStringExtra("text"))){
            etContent.setText(getIntent().getStringExtra("text"));
            tvTextSum.setText(etContent.getText().toString().length()+" / 600");
        }else{
            etContent.setText("");
            tvTextSum.setText("0 / 600");
        }
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.jobDescription);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etContent.getText().toString()!=null&&!"".equals(etContent.getText().toString())) {
                    myDialog = new MyDialog(ContentActivity.this, 2);
                    myDialog.setMessage(getString(R.string.exitWarning));
                    myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            finish();
                        }
                    });
                    myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();
                }else{
                    finish();
                }
            }
        });
        etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(600)});
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvTextSum.setText(s.length()+" / 600");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_contentOK)
    public void onViewClicked() {
        if (etContent.getText().toString() == null || "".equals(etContent.getText().toString())) {
            ToastUitl.showShort("请填写职位描述");
            return;
        }
        if(tag.equals(WorkExpActivity.TAG)) {
            WorkExpActivity.instance.setTvResponsibilityDes(etContent.getText().toString());
        }else if(tag.equals(ResumeWorkExpActivity.TAG)){
            ResumeWorkExpActivity.instance.setTvResponsibilityDes(etContent.getText().toString());
        }
        finish();
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,String tag) {
        Intent intent = new Intent(activity, ContentActivity.class);
        intent.putExtra("tag",tag);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    public static void startAction(Activity activity,String s,String tag) {
        Intent intent = new Intent(activity, ContentActivity.class);
        intent.putExtra("tag",tag);
        intent.putExtra("text",s);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myDialog!=null){
            myDialog.dismiss();
        }
    }
}
