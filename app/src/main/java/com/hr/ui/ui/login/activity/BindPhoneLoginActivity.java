package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
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
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/8.
 */

public class BindPhoneLoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_bindPhoneLoginNumber)
    EditText etBindPhoneLoginNumber;
    @BindView(R.id.et_bindPhoneLoginPsw)
    EditText etBindPhoneLoginPsw;
    @BindView(R.id.iv_bindPhoneLoginHiddenPsw)
    ImageView ivBindPhoneLoginHiddenPsw;
    private boolean isHidden = true;
    private ThirdLoginBean thirdPartBean;
    private String phoneNum,psw,uid;
    private SharedPreferencesUtils sUtils;
    private int[] imageIds={R.mipmap.resume1,R.mipmap.resume2,R.mipmap.resume3,R.mipmap.resume4,R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, BindPhoneLoginActivity.class);
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
        thirdPartBean=new ThirdLoginBean();
        List<ThirdLoginBean> thirdPartBeanList= ThirdPartDao.queryThirdPart(Constants.TYPE_THIRDPARTLOGIN);
       // List<ThirdLoginBean> thirdPartBeanList= HRApplication.getDaoSession().getThirdLoginBeanDao().queryBuilder().where(ThirdPartBeanDao.Properties.Type.eq(Constants.TYPE_THIRDPARTLOGIN)).list();
        for(int i=0;i<thirdPartBeanList.size();i++){
            if(thirdPartBeanList.get(i).getType().equals(Constants.TYPE_THIRDPARTLOGIN)){
                thirdPartBean=thirdPartBeanList.get(i);
                uid=thirdPartBean.getUId();
                thirdPartBean.setSUId(userId+"");
                break;
            }
        }
        mPresenter.getThidBinding(thirdPartBean,phoneNum,psw,0);
    }

    @Override
    public void thirdPartLoginSuccess(int userId) {

    }

    @Override
    public void thirdPartLoginGoToBind() {

    }

    @Override
    public void bindingSuccess(int userId) {
        sUtils.setIntValue(Constants.ISAUTOLOGIN,1);
        LoginBean loginBean=new LoginBean();
        if("QQ".equals(Constants.TYPE_THIRDPARTLOGIN)) {
            loginBean.setLoginType(2);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE,2);
        }else{
            loginBean.setLoginType(3);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE,3);
        }
        loginBean.setName(phoneNum);
        loginBean.setPassword(psw);
        loginBean.setThirdPartUid(uid);
        loginBean.setThirdPartLoginType(Constants.TYPE_THIRDPARTLOGIN);
        loginBean.setThirdPartSUid(userId+"");
        LoginDBUtils.insertData(loginBean);
        this.userId=userId;
      mPresenter.getResumeList();
    }

    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne(this, multipleResumeBean,userId,imageIds,mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean,this,titles);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bindphone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils=new SharedPreferencesUtils(this);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.bindPhoneAccount);
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
    }

    @OnClick({R.id.rl_bindPhoneLoginHiddenPsw, R.id.btn_bindPhoneLoginOK, R.id.tv_bindPhoneToFindUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bindPhoneLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivBindPhoneLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etBindPhoneLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivBindPhoneLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etBindPhoneLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etBindPhoneLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etBindPhoneLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_bindPhoneLoginOK:
                doLogin();
                break;
            case R.id.tv_bindPhoneToFindUser:
                bindUserLoginActivity.startAction(this);
                break;
        }
    }
    /**
     * 登录
     */
    private void doLogin() {
         phoneNum = etBindPhoneLoginNumber.getText().toString();
         psw = etBindPhoneLoginPsw.getText().toString();
        if ("".equals(phoneNum) || phoneNum == null) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if (RegularExpression.isCellphone(phoneNum) == false) {
            ToastUitl.showShort("请输入正确的手机号码");
            return;
        }
        if ("".equals(psw) || psw == null) {
            ToastUitl.showShort("请输入密码");
            return;
        }
        if (psw.length() < 6 || psw.length() > 16) {
            ToastUitl.showShort("请输入6-16位长度密码");
            return;
        }
        mPresenter.getLogin(phoneNum, psw, 1);
    }
}
