package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.db.ThirdPartDao;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.ui.login.model.LoginModel;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/8.
 */

public class bindUserLoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_bindUserLoginNumber)
    EditText etBindUserLoginNumber;
    @BindView(R.id.et_bindUserLoginPsw)
    EditText etBindUserLoginPsw;
    @BindView(R.id.iv_bindUserLoginHiddenPsw)
    ImageView ivBindUserLoginHiddenPsw;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_bindUserLoginNumberIcon)
    ImageView ivBindUserLoginNumberIcon;
    @BindView(R.id.iv_bindUserLoginNumberDelete)
    ImageView ivBindUserLoginNumberDelete;
    @BindView(R.id.iv_bindUserLoginPswIcon)
    ImageView ivBindUserLoginPswIcon;
    @BindView(R.id.iv_bindUserLoginPswDelete)
    ImageView ivBindUserLoginPswDelete;
    @BindView(R.id.rl_bindUserLoginHiddenPsw)
    RelativeLayout rlBindUserLoginHiddenPsw;
    @BindView(R.id.btn_bindUserLoginOK)
    Button btnBindUserLoginOK;
    @BindView(R.id.ll_bindUser_middle)
    LinearLayout llBindUserMiddle;
    private boolean isHidden = true;
    private ThirdLoginBean thirdPartBean;
    private String userName, psw, uid;
    private SharedPreferencesUtils sUtils;
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, bindUserLoginActivity.class);
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
    public void sendLoginSuccess(int userId) {
        MobclickAgent.onProfileSignIn(userId+"");
        thirdPartBean = new ThirdLoginBean();
        List<ThirdLoginBean> thirdPartBeanList = ThirdPartDao.queryThirdPart(Constants.TYPE_THIRDPARTLOGIN);
        for (int i = 0; i < thirdPartBeanList.size(); i++) {
            if (thirdPartBeanList.get(i).getType().equals(Constants.TYPE_THIRDPARTLOGIN)) {
                thirdPartBean = thirdPartBeanList.get(i);
                uid = thirdPartBean.getUId();
                thirdPartBean.setSUId(userId + "");
                break;
            }
        }
        mPresenter.getThidBinding(thirdPartBean, userName, psw, 1);
    }

    @Override
    public void thirdPartLoginSuccess(int userId) {

    }


    @Override
    public void thirdPartLoginGoToBind() {

    }

    @Override
    public void bindingSuccess(int userId) {
        MobclickAgent.onEvent(this,"v6_login_thirdPart");
        this.userId = userId;
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        LoginBean loginBean = new LoginBean();
        if ("qq".equals(Constants.TYPE_THIRDPARTLOGIN)) {
            loginBean.setLoginType(2);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 2);
        } else {
            loginBean.setLoginType(3);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 3);
        }
        loginBean.setName(userName);
        loginBean.setPassword(psw);
        loginBean.setThirdPartUid(uid);
        loginBean.setThirdPartLoginType(Constants.TYPE_THIRDPARTLOGIN);
        loginBean.setThirdPartSUid(userId + "");
        LoginDBUtils.insertData(loginBean);
        mPresenter.getResumeList();
        MobclickAgent.onProfileSignIn("WB",userId+"");
    }

    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne(this, multipleResumeBean, userId, imageIds, mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean, this, titles);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_binduser;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils = new SharedPreferencesUtils(this);
        ;
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.bindUserAccount);
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
        onEditViewTextChangeAndFocusChange();
    }

    private void onEditViewTextChangeAndFocusChange() {
        etBindUserLoginPsw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivBindUserLoginPswDelete.setVisibility(View.GONE);
                }else{
                    if(etBindUserLoginPsw.getText().toString()!=null&&!"".equals(etBindUserLoginPsw.getText().toString())){
                        ivBindUserLoginPswDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etBindUserLoginNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivBindUserLoginNumberDelete.setVisibility(View.GONE);
                }else{
                    if(etBindUserLoginNumber.getText().toString()!=null&&!"".equals(etBindUserLoginNumber.getText().toString())) {
                        ivBindUserLoginNumberDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etBindUserLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivBindUserLoginNumberDelete.setVisibility(View.VISIBLE);
                }else{
                    ivBindUserLoginNumberDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etBindUserLoginPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivBindUserLoginPswDelete.setVisibility(View.VISIBLE);
                }else{
                    ivBindUserLoginPswDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.rl_bindUserLoginHiddenPsw, R.id.iv_bindUserLoginPswDelete, R.id.iv_bindUserLoginNumberDelete, R.id.btn_bindUserLoginOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bindUserLoginPswDelete:
                etBindUserLoginPsw.setText("");
                break;
            case R.id.iv_bindUserLoginNumberDelete:
                etBindUserLoginNumber.setText("");
                break;
            case R.id.rl_bindUserLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivBindUserLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etBindUserLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivBindUserLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etBindUserLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etBindUserLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etBindUserLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_bindUserLoginOK:
                doLogin();
                break;
        }
    }

    private void doLogin() {
        userName = etBindUserLoginNumber.getText().toString();
        psw = etBindUserLoginPsw.getText().toString();
        if ("".equals(userName) || userName == null) {
            ToastUitl.showShort("请输入用户名");
            return;
        }
        if ("".equals(psw) || psw == null) {
            ToastUitl.showShort("请输入密码");
            return;
        }
        mPresenter.getLogin(userName, psw, 2);
    }
}
