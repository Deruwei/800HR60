package com.hr.ui.ui.resume.activity;

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
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.resume.contract.ResumeIntroductionContract;
import com.hr.ui.ui.resume.model.ResumeIntroductionModel;
import com.hr.ui.ui.resume.presenter.ResumeIntroductionPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeIntroductionActivity extends BaseActivity<ResumeIntroductionPresenter, ResumeIntroductionModel> implements ResumeIntroductionContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_textSum)
    TextView tvTextSum;
    @BindView(R.id.btn_contentOK)
    Button btnContentOK;
    private MyDialog myDialog;
    private SharedPreferencesUtils sUtils;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeIntroductionActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    public static void startAction(Activity activity, String s) {
        Intent intent = new Intent(activity, ResumeIntroductionActivity.class);
        intent.putExtra("text", s);
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
    public void getIntroductionSuccess() {

    }

    @Override
    public void addOrReplaceIntroductionSuccess() {
        ToastUitl.showShort(R.string.saveSuccess);
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_content;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        sUtils=new SharedPreferencesUtils(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etContent.setMaxEms(400);
        if (getIntent().getStringExtra("text") != null || !"".equals(getIntent().getStringExtra("text"))) {
            etContent.setText(getIntent().getStringExtra("text"));
            tvTextSum.setText(etContent.getText().toString().length() + " / 400");
        } else {
            etContent.setText("");
            tvTextSum.setText("0 / 400");
        }
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.introduction);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etContent.getText().toString() != null && !"".equals(etContent.getText().toString())) {
                    myDialog = new MyDialog(ResumeIntroductionActivity.this, 2);
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
                } else {
                    finish();
                }
            }
        });
        etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(400)});
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvTextSum.setText(s.length() + " / 400");
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

    @OnClick(R.id.btn_contentOK)
    public void onViewClicked() {
        if (etContent.getText().toString() == null || "".equals(etContent.getText().toString())) {
            ToastUitl.showShort("请填写职位描述");
            return;
        }
       mPresenter.addOrReplaceIntroduction(etContent.getText().toString());
    }
}
