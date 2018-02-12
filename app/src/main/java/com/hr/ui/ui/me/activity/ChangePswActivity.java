package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.ui.me.contract.ChangePswContract;
import com.hr.ui.ui.me.model.ChangePswModel;
import com.hr.ui.ui.me.presenter.ChangePswPresenter;
import com.hr.ui.utils.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/18.
 */

public class ChangePswActivity extends BaseActivity<ChangePswPresenter, ChangePswModel> implements ChangePswContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_changePswNewOldIcon)
    ImageView ivChangePswNewOldIcon;
    @BindView(R.id.et_changePswNewOld)
    EditText etChangePswNewOld;
    @BindView(R.id.iv_changePswNewIcon)
    ImageView ivChangePswNewIcon;
    @BindView(R.id.et_changePswNew)
    EditText etChangePswNew;
    @BindView(R.id.view_getValidCode2)
    View viewGetValidCode2;
    @BindView(R.id.btn_changePswOK)
    Button btnChangePswOK;
    @BindView(R.id.iv_changePswOldDelete)
    ImageView ivChangePswOldDelete;
    @BindView(R.id.iv_changePswOldHiddenPsw)
    ImageView ivChangePswOldHiddenPsw;
    @BindView(R.id.iv_changePswNewDelete)
    ImageView ivChangePswNewDelete;
    @BindView(R.id.iv_changePswNewHiddenPsw)
    ImageView ivChangePswNewHiddenPsw;
    @BindView(R.id.cl_changePsw)
    ConstraintLayout clChangePsw;
    private boolean isHiddenOld, isHiddenNew;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ChangePswActivity.class);
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
    public void getChangePswSuccess() {
        ToastUitl.showShort("修改密码成功");
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_changepsw;
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
        tvToolbarTitle.setText(R.string.resetPsw);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        textChangeAndFocusChange();
    }

    private void textChangeAndFocusChange() {
        etChangePswNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivChangePswNewDelete.setVisibility(View.VISIBLE);
                } else {
                    ivChangePswNewDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etChangePswNew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivChangePswNewDelete.setVisibility(View.GONE);
                } else {
                    if (etChangePswNew.getText().toString() != null && !"".equals(etChangePswNew.getText().toString())) {
                        ivChangePswNewDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etChangePswNewOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivChangePswOldDelete.setVisibility(View.VISIBLE);
                } else {
                    ivChangePswOldDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etChangePswNewOld.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivChangePswOldDelete.setVisibility(View.GONE);
                } else {
                    if (etChangePswNewOld.getText().toString() != null && !"".equals(etChangePswNewOld.getText().toString())) {
                        ivChangePswOldDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @OnClick({R.id.btn_changePswOK, R.id.iv_changePswNewDelete, R.id.iv_changePswOldDelete, R.id.iv_changePswNewHiddenPsw, R.id.iv_changePswOldHiddenPsw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_changePswOK:
                if (etChangePswNewOld.getText().toString() == null || "".equals(etChangePswNewOld.getText().toString())) {
                    ToastUitl.showShort("请填写旧密码");
                    return;
                }
                if (etChangePswNew.getText().toString() == null || "".equals(etChangePswNew.getText().toString())) {
                    ToastUitl.showShort("请填写新密码");
                    return;
                }
                if (etChangePswNew.getText().toString().length() < 6 || etChangePswNew.getText().toString().length() > 25) {
                    ToastUitl.showShort("请输入6-25位新密码");
                    return;
                }
                if (etChangePswNewOld.getText().toString().equals(etChangePswNew.getText().toString())) {
                    ToastUitl.showShort("新旧密码不能相同");
                    return;
                }
                mPresenter.getChangePsw(etChangePswNewOld.getText().toString(), etChangePswNew.getText().toString());
                break;
            case R.id.iv_changePswNewDelete:
                etChangePswNew.setText("");
                break;
            case R.id.iv_changePswOldDelete:
                etChangePswNewOld.setText("");
                break;
            case R.id.iv_changePswNewHiddenPsw:
                if (isHiddenNew) {
                    //设置EditText文本为可见的
                    ivChangePswNewHiddenPsw.setImageResource(R.mipmap.see);
                    etChangePswNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivChangePswNewHiddenPsw.setImageResource(R.mipmap.hidden);
                    etChangePswNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHiddenNew = !isHiddenNew;
                etChangePswNew.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etChangePswNew.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_changePswOldHiddenPsw:
                if (isHiddenOld) {
                    //设置EditText文本为可见的
                    ivChangePswOldHiddenPsw.setImageResource(R.mipmap.see);
                    etChangePswNewOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivChangePswOldHiddenPsw.setImageResource(R.mipmap.hidden);
                    etChangePswNewOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHiddenOld = !isHiddenOld;
                etChangePswNewOld.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence2 = etChangePswNewOld.getText();
                if (charSequence2 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence2;
                    Selection.setSelection(spanText, charSequence2.length());
                }
                break;
        }
    }
}
