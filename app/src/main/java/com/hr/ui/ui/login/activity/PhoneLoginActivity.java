package com.hr.ui.ui.login.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.base.Base3Activity;
import com.hr.ui.base.Base4Activity;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.ui.login.model.LoginModel;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ThirdPartLoginUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.PopupWindowComment;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.GenericDeclaration;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Window.FEATURE_ACTIVITY_TRANSITIONS;

public class PhoneLoginActivity extends Base4Activity<LoginPresenter, LoginModel> implements LoginContract.View {
    @BindView(R.id.iv_phoneLoginRegisterIcon)
    ImageView ivPhoneLoginRegisterIcon;
    @BindView(R.id.rl_phoneLoginRegister)
    RelativeLayout rlPhoneLoginRegister;
    @BindView(R.id.iv_phoneLoginFindJobIcon)
    ImageView ivPhoneLoginFindJobIcon;
    @BindView(R.id.iv_phoneLoginNumberIcon)
    ImageView ivPhoneLoginNumberIcon;
    @BindView(R.id.et_phoneLoginNumber)
    EditText etPhoneLoginNumber;
    @BindView(R.id.iv_phoneLoginPhoneDelete)
    ImageView ivPhoneLoginPhoneDelete;
    @BindView(R.id.iv_phoneLoginPswIcon)
    ImageView ivPhoneLoginPswIcon;
    @BindView(R.id.et_phoneLoginPsw)
    EditText etPhoneLoginPsw;
    @BindView(R.id.iv_phoneLoginPswDelete)
    ImageView ivPhoneLoginPswDelete;
    @BindView(R.id.iv_phoneLoginHiddenPsw)
    ImageView ivPhoneLoginHiddenPsw;
    @BindView(R.id.rl_phoneLoginHiddenPsw)
    RelativeLayout rlPhoneLoginHiddenPsw;
    @BindView(R.id.ll_phoneLogin)
    LinearLayout llPhoneLogin;
    @BindView(R.id.tv_phoneLoginAnotherLoginWay)
    TextView tvPhoneLoginAnotherLoginWay;
    @BindView(R.id.tv_phoneLoginForgetPsw)
    TextView tvPhoneLoginForgetPsw;
    @BindView(R.id.btn_phoneLoginOK)
    Button btnPhoneLoginOK;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.iv_phoneLoginQQ)
    ImageView ivPhoneLoginQQ;
    @BindView(R.id.iv_phoneLoginWeChat)
    ImageView ivPhoneLoginWeChat;
    @BindView(R.id.rl_phoneLoginThirdPart)
    RelativeLayout rlPhoneLoginThirdPart;
    private boolean isHidden=true;
    private String phoneLoginNumber,phoneLoginPsw;
    private SharedPreferencesUtils sUtils;
    public static String VIEW_LOGO="logo";
    private int userId;
    private ArrayList<String> titles;
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, PhoneLoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils=new SharedPreferencesUtils(this);
        Utils.setEditViewTextChangeAndFocus(etPhoneLoginNumber,ivPhoneLoginPhoneDelete);
        Utils.setEditViewTextChangeAndFocus(etPhoneLoginPsw, ivPhoneLoginPswDelete);
       /* ViewCompat.se*/
        ViewCompat.setTransitionName(ivPhoneLoginFindJobIcon, VIEW_LOGO);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            slide.setSlideEdge(Gravity.LEFT);
            getWindow().setEnterTransition(slide);
            getWindow().setReenterTransition(new Explode().setDuration(1000));
            getWindow().setExitTransition(new Fade().setDuration(1000));
                //设置允许通过ActivityOptions.makeSceneTransitionAnimation发送或者接收Bundle
           /* getWindow().setEnterTransition(initContentEnterTransition());
            getWindow().setReenterTransition(initContentEnterTransition());*/
                //getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.return_slide));
        }

    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    //弹出提示，可以有多种方式
                    //PopupWindowComment popupWindowComment=new PopupWindowComment(popupWindowGiveComment,this,idMenu);
                    ToastUitl.showShort("再点一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    AppManager.getAppManager().exitApp();
                }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void sendLoginSuccess(int userId) {
        this.userId=userId;
        MobclickAgent.onEvent(this, "v6_login_phone");
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        sUtils.setIntValue(Constants.AUTOLOGINTYPE, 0);
        sUtils.setStringValue(Constants.USERPHONE, phoneLoginNumber);
        mPresenter.getResumeList();
    }

    @Override
    public void thirdPartLoginSuccess(int userId) {
        this.userId = userId;
        MobclickAgent.onEvent(this, "v6_login_thirdPart");
        MobclickAgent.onProfileSignIn("WB", userId + "");
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        if ("qq".equals(Constants.TYPE_THIRDPARTLOGIN)) {
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 2);
        } else {
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 3);
        }
        mPresenter.getResumeList();
    }

    @Override
    public void thirdPartLoginGoToBind() {
        BindNewAccountAcitvity.startAction(this);
    }

    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne(this, multipleResumeBean, userId, mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean, this, titles);
        sUtils.setIntValue(Constants.RESUME_ID, Integer.parseInt(resumeBean.getResume_info().getTitle_info().get(0).getResume_id()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    protected void doAtBeforeSetcontentView() {
    }

    @OnClick({R.id.rl_phoneLoginRegister, R.id.iv_phoneLoginPhoneDelete, R.id.iv_phoneLoginPswDelete, R.id.rl_phoneLoginHiddenPsw, R.id.tv_phoneLoginAnotherLoginWay, R.id.tv_phoneLoginForgetPsw, R.id.btn_phoneLoginOK, R.id.iv_phoneLoginQQ, R.id.iv_phoneLoginWeChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_phoneLoginRegister:
                RegisterActivity.startAction(this);
                break;
            case R.id.iv_phoneLoginPhoneDelete:
                etPhoneLoginNumber.setText("");
                break;
            case R.id.iv_phoneLoginPswDelete:
                etPhoneLoginPsw.setText("");
                break;
            case R.id.rl_phoneLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivPhoneLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etPhoneLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivPhoneLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etPhoneLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etPhoneLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etPhoneLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.tv_phoneLoginAnotherLoginWay:
                LoginActivity.startAction(this);
                finish();
                break;
            case R.id.tv_phoneLoginForgetPsw:
                FindPhonePasswordActivity.startAction(this);
                break;
            case R.id.btn_phoneLoginOK:
                doLogin();
                break;
            case R.id.iv_phoneLoginQQ:
                ThirdPartLoginUtils.LoginQQ(mPresenter);
                break;
            case R.id.iv_phoneLoginWeChat:
                ThirdPartLoginUtils.LoginWeChat(mPresenter);
                break;
        }
    }

    private void doLogin() {
        phoneLoginNumber=etPhoneLoginNumber.getText().toString();
        phoneLoginPsw=etPhoneLoginPsw.getText().toString();
        if(phoneLoginNumber==null||"".equals(phoneLoginNumber)){
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if(!RegularExpression.isCellphone(phoneLoginNumber)){
            ToastUitl.showShort("请输入正确的手机号码");
            return;
        }
        if(phoneLoginPsw==null||"".equals(phoneLoginPsw)){
            ToastUitl.showShort("请输入密码");
            return;
        }
        mPresenter.getLogin(phoneLoginNumber, phoneLoginPsw, 1);
    }
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        Utils.getConnect();
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void needToGetAutoCode() {

    }

    @Override
    public void phoneIsExit(String flag) {

    }

    @Override
    public void sendValidCode(int code) {

    }

    @Override
    public void sendAutoCode(String autoCode) {

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
    public void bindingSuccess(int userId) {

    }

    @Override
    protected void onDestroy() {
        ivPhoneLoginFindJobIcon.setVisibility(View.GONE);
        super.onDestroy();
    }
}
