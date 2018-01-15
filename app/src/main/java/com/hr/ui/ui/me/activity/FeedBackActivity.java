package com.hr.ui.ui.me.activity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.ui.me.contract.FeedBackContract;
import com.hr.ui.ui.me.model.FeedBackModel;
import com.hr.ui.ui.me.presenter.FeedbackPresenter;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/13.
 */

public class FeedBackActivity extends BaseActivity<FeedbackPresenter, FeedBackModel> implements FeedBackContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_feedBackContent)
    EditText etFeedBackContent;
    @BindView(R.id.tv_feedBackTextSum)
    TextView tvFeedBackTextSum;
    @BindView(R.id.et_feedbackEmail)
    EditText etFeedbackEmail;
    @BindView(R.id.btn_feedBackOK)
    Button btnFeedBackOK;
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, FeedBackActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
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
    public void feedBackSuccess() {
        ToastUitl.showShort("投递意见反馈成功");
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
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
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.feedback);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvToolbarSave.setVisibility(View.VISIBLE);
        tvToolbarSave.setText(R.string.submit);
        tvFeedBackTextSum.setText(0+" / 500");
        etFeedBackContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
        etFeedBackContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvFeedBackTextSum.setText(s.length()+" / 500");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_toolbarSave, R.id.btn_feedBackOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbarSave:
                doFeedBack();
                break;
            case R.id.btn_feedBackOK:
                doFeedBack();
                break;
        }
    }

    private void doFeedBack() {
        if(etFeedBackContent.getText().toString()==null||"".equals(etFeedBackContent.getText().toString())){
            ToastUitl.showShort("请填写意见反馈");
            return;
        }
        if(etFeedbackEmail.getText().toString()==null||"".equals(etFeedbackEmail.getText().toString())){
            ToastUitl.showShort("请填写电子邮件");
            return;
        }
        if(RegularExpression.isEmail(etFeedbackEmail.getText().toString())==false){
            ToastUitl.showShort("请填写正确的电子邮件");
            return;
        }
        mPresenter.feedBack(etFeedBackContent.getText().toString(),etFeedbackEmail.getText().toString());
    }
}
