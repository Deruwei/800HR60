package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.Base3Activity;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.ui.login.model.LoginModel;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.utils.CodeTimer;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ThirdPartLoginUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.VerificationAction;
import com.hr.ui.view.VerificationCodeEditText;
import com.hr.ui.view.dialog.ValidDialog;
import com.service.CodeTimerService;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/6.
 */

public class LoginActivity extends Base3Activity<LoginPresenter, LoginModel> implements LoginContract.View {

    @BindView(R.id.iv_loginRegisterIcon)
    ImageView ivLoginRegisterIcon;
    @BindView(R.id.rl_loginRegister)
    RelativeLayout rlLoginRegister;
    @BindView(R.id.iv_loginFindJobIcon)
    ImageView ivLoginFindJobIcon;
    @BindView(R.id.rb_userLogin)
    RadioButton rbUserLogin;
    @BindView(R.id.rb_validCodeLogin)
    RadioButton rbValidCodeLogin;
    @BindView(R.id.rl_loginTitle)
    RelativeLayout rlLoginTitle;
    @BindView(R.id.iv_phoneLoginNumberIcon2)
    ImageView ivPhoneLoginNumberIcon2;
    @BindView(R.id.et_phoneLoginNumber2)
    EditText etPhoneLoginNumber2;
    @BindView(R.id.iv_phoneLoginPhoneDelete2)
    ImageView ivPhoneLoginPhoneDelete2;
    @BindView(R.id.iv_phoneLoginValidIcon)
    ImageView ivPhoneLoginValidIcon;
    @BindView(R.id.iv_phoneLoginValidDelete)
    ImageView ivPhoneLoginValidDelete;
    @BindView(R.id.tv_sendValidCode)
    TextView tvSendValidCode;
    @BindView(R.id.tv_loginOr)
    TextView tvLoginOr;
    @BindView(R.id.tv_sendVoiceCode)
    TextView tvSendVoiceCode;
    @BindView(R.id.ll_validLogin)
    LinearLayout llValidLogin;
    @BindView(R.id.iv_userLoginNumberIcon)
    ImageView ivUserLoginNumberIcon;
    @BindView(R.id.et_userLoginNumber)
    EditText etUserLoginNumber;
    @BindView(R.id.iv_userLoginNumberDelete)
    ImageView ivUserLoginNumberDelete;
    @BindView(R.id.iv_userLoginPswIcon)
    ImageView ivUserLoginPswIcon;
    @BindView(R.id.et_userLoginPsw)
    EditText etUserLoginPsw;
    @BindView(R.id.iv_userLoginPswDelete)
    ImageView ivUserLoginPswDelete;
    @BindView(R.id.iv_userLoginHiddenPsw)
    ImageView ivUserLoginHiddenPsw;
    @BindView(R.id.rl_userLoginHiddenPsw)
    RelativeLayout rlUserLoginHiddenPsw;
    @BindView(R.id.ll_userLogin_middle)
    LinearLayout llUserLoginMiddle;
    @BindView(R.id.tv_phoneLoginAccountLogin)
    TextView tvPhoneLoginAccountLogin;
    @BindView(R.id.tv_phoneLoginFindPsw)
    TextView tvPhoneLoginFindPsw;
    @BindView(R.id.btn_phoneLoginOK)
    Button btnPhoneLoginOK;
    @BindView(R.id.ll_phoneLogin_middle)
    LinearLayout llPhoneLoginMiddle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.iv_phoneLoginQQ)
    ImageView ivPhoneLoginQQ;
    @BindView(R.id.iv_phoneLoginWeChat)
    ImageView ivPhoneLoginWeChat;
    @BindView(R.id.rl_phoneLoginThirdPart)
    RelativeLayout rlPhoneLoginThirdPart;
    @BindView(R.id.cl_phoneLogin)
    ConstraintLayout clPhoneLogin;
    @BindView(R.id.tv_phoneLoginValid)
    TextView tvPhoneLoginValid;
    @BindView(R.id.vi_loginValidCode)
    VerificationCodeEditText viLoginValidCode;
    @BindView(R.id.rl_loginDelete)
    RelativeLayout rlLoginDelete;
    @BindView(R.id.iv_sendVoiceCode)
    ImageView ivSendVoiceCode;
    @BindView(R.id.rl_sendVoiceCode)
    FrameLayout rlSendVoiceCode;
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private boolean isHidden = true, isHiddleUser = true;
    private String phoneNum, userNum, phoneNumberValid = "", psw, psw2, validCode;
    private SharedPreferencesUtils sUtils;
    private ArrayList<String> titles;
    private int userId, code, validType = 1, type, isPswLogin = 1; // 1代表密码登录  2 代表验证码登录;
    private Intent mCodeTimerServiceIntent, mVoiseTimerServiceIntent;
    public static final String CODE = "codeValidLogin", VOICECODE = "codeVoiceLogin";
    private PopupWindow popupWindow;
    private MyDialog dialog;
    private ValidDialog validDialog;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
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
        MobclickAgent.onProfileSignIn(userId + "");
        this.userId = userId;
        sUtils.setStringValue(Constants.USERID, userId + "");
        if (isPswLogin == 1) {
            MobclickAgent.onEvent(this, "v6_login_user");
            sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 1);
        } else {
            sUtils.setIntValue(Constants.ISAUTOLOGIN, 0);
            MobclickAgent.onEvent(this, "v6_login_validCode");
        }
        sUtils.setStringValue(Constants.USERPHONE, phoneNum);
        mPresenter.getResumeList();
    }

    @Override
    public void thirdPartLoginSuccess(int userId) {
        this.userId = userId;
        sUtils.setStringValue(Constants.USERID, userId + "");
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
    public void bindingSuccess(int userId) {

    }


    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne(this, multipleResumeBean, userId, mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        // Log.i("ok","你好");
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean, this, titles);
        sUtils.setIntValue(Constants.RESUME_ID, Integer.parseInt(resumeBean.getResume_info().getTitle_info().get(0).getResume_id()));
    }

    @Override
    public void needToGetAutoCode() {
        ToastUitl.showShort("图形验证码错误");
        if (validDialog != null) {
            validDialog.setText("");
        }
        mPresenter.getAutoCode();
    }

    @Override
    public void phoneIsExit(String flag) {
        if ("0".equals(flag)) {
            dialog = new MyDialog(this, 2);
            dialog.setMessage(getString(R.string.error_phoneNotExit2));
            dialog.setYesOnclickListener("去注册", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    RegisterActivity.startAction(LoginActivity.this, phoneNumberValid);
                    dialog.dismiss();
                }
            });
            dialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            if (type == 0) {
                mPresenter.getLogin(phoneNumberValid, validCode, 3);
                //mPresenter.getRegister(phoneNumber, validCode, password);
            } else {
                if (sUtils.getIntValue("code", 0) >= 1) {
                    initPopWindow();
                    mPresenter.getAutoCode();
                } else {
                    mPresenter.getValidCode(phoneNumberValid, "", 0, Constants.VALIDCODE_LOGIN_YTPE, validType);
                }
            }
        }
    }

    /**
     * 图形验证码界面
     */
    public void initPopWindow() {
        if (validDialog != null) {
            validDialog.show();
        } else {
            validDialog = new ValidDialog(this);
            validDialog.setOnConfirmListener(new ValidDialog.OnConfirmListener() {
                @Override
                public void onConfirm(String autoCode) {
                    mPresenter.getValidCode(etPhoneLoginNumber2.getText().toString(), autoCode, 1, Constants.VALIDCODE_LOGIN_YTPE, validType);
                }
            });
            validDialog.setOnRefleshClickListener(new ValidDialog.OnRefleshClickListener() {
                @Override
                public void doReflesh() {
                    mPresenter.getAutoCode();
                }
            });
            validDialog.show();
        }

    }

    @Override
    public void sendValidCode(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        tvSendValidCode.setEnabled(false);
        ivSendVoiceCode.setEnabled(false);
        if (validType == 2) {
            ToastUitl.showShort(R.string.voiceAlreadySend);
            tvSendValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            tvSendVoiceCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            ivSendVoiceCode.setVisibility(View.GONE);
            tvSendVoiceCode.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mVoiseTimerServiceIntent);
            } else {
                startService(mVoiseTimerServiceIntent);
            }
        } else {
            ToastUitl.showShort(R.string.validAlreadySend);
            ivSendVoiceCode.setImageResource(R.mipmap.voice_grey);
            tvSendValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceIntent);
            } else {
                startService(mCodeTimerServiceIntent);
            }
        }
        if (validDialog != null) {
            validDialog.dismiss();
        }
    }

    @Override
    public void sendAutoCode(String autoCode) {
        if (validDialog != null) {
            validDialog.setImageBitMap(EncryptUtils.stringtoBitmap(autoCode));
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
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils = new SharedPreferencesUtils(this);
        viLoginValidCode.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivPhoneLoginValidDelete.setVisibility(View.VISIBLE);
                } else {
                    ivPhoneLoginValidDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onInputCompleted(CharSequence s) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //验证码计时器服务
        mCodeTimerServiceIntent = new Intent(this, CodeTimerService.class);
        mCodeTimerServiceIntent.setAction(CODE);
        //语音验证码计时服务
        mVoiseTimerServiceIntent = new Intent(this, CodeTimerService.class);
        mVoiseTimerServiceIntent.setAction(VOICECODE);
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);

        IntentFilter filter1 = new IntentFilter(VOICECODE);
        registerReceiver(mCodeTimerReceiver, filter1);
        setFocusChangeAndEditViewTextChange();
    }

    /**
     * 验证码倒计时的广播
     */
    private BroadcastReceiver mCodeTimerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean isEnable = intent.getBooleanExtra(CodeTimer.IS_ENABLE, false);
            if(isEnable){
                ivSendVoiceCode.setVisibility(View.VISIBLE);
                ivSendVoiceCode.setImageResource(R.mipmap.voice_orange);
                tvSendVoiceCode.setVisibility(View.GONE);
                tvSendValidCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.new_main));
            }
            if (CODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvSendValidCode.setEnabled(isEnable);
                ivSendVoiceCode.setEnabled(isEnable);
                tvSendValidCode.setText(message);
            } else if (VOICECODE.equals(action)) {
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvSendValidCode.setEnabled(isEnable);
                ivSendVoiceCode.setEnabled(isEnable);
                tvSendVoiceCode.setText(message);
            }
        }
    };

    private void setFocusChangeAndEditViewTextChange() {
        Utils.setEditViewTextChangeAndFocus(etPhoneLoginNumber2, ivPhoneLoginPhoneDelete2);
        Utils.setEditViewTextChangeAndFocus(etUserLoginNumber, ivUserLoginNumberDelete);
        Utils.setEditViewTextChangeAndFocus(etUserLoginPsw, ivUserLoginPswDelete);
    }

    @OnClick({R.id.rl_userLoginHiddenPsw, R.id.tv_phoneLoginValid,R.id.rl_loginRegister, R.id.iv_userLoginNumberDelete, R.id.iv_userLoginPswDelete, R.id.rb_userLogin, R.id.tv_sendValidCode, R.id.iv_sendVoiceCode, R.id.iv_phoneLoginPhoneDelete2, R.id.iv_phoneLoginValidDelete, R.id.rb_validCodeLogin, R.id.btn_phoneLoginOK, R.id.tv_phoneLoginAccountLogin, R.id.tv_phoneLoginFindPsw, R.id.iv_phoneLoginQQ, R.id.iv_phoneLoginWeChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_loginRegister:
                RegisterActivity.startAction(this);
                break;
            case R.id.iv_userLoginNumberDelete:
                etUserLoginNumber.setText("");
                break;
            case R.id.iv_userLoginPswDelete:
                etUserLoginPsw.setText("");
                break;
            case R.id.rl_userLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivUserLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etUserLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivUserLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etUserLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etUserLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etUserLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.rb_validCodeLogin:
                isPswLogin = 2;
                rbValidCodeLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                rbUserLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                llValidLogin.setVisibility(View.VISIBLE);
                llUserLoginMiddle.setVisibility(View.GONE);
                /*viewPswLogin.setVisibility(View.GONE);
                viewValidLogin.setVisibility(View.VISIBLE);*/
                break;
            case R.id.rb_userLogin:
                isPswLogin = 1;
                rbValidCodeLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                rbUserLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                llValidLogin.setVisibility(View.GONE);
                llUserLoginMiddle.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_phoneLoginOK:
                doLogin();
                //MainActivity.startAction(this,0);
                break;
            case R.id.iv_phoneLoginPhoneDelete2:
                etPhoneLoginNumber2.setText("");
                break;
            case R.id.iv_phoneLoginValidDelete:
                viLoginValidCode.setValue("");
                break;
            case R.id.iv_sendVoiceCode:
                validType = 2;
                doSendValidCode();
                break;
            case R.id.tv_sendValidCode:
                validType = 1;
                doSendValidCode();
                break;
            case R.id.tv_phoneLoginAccountLogin:
                PhoneLoginActivity.startAction(this);
                finish();
                break;
            case R.id.tv_phoneLoginFindPsw:
                if (isPswLogin == 1) {
                    FindUserPasswordActivity.startAction(this);
                } else {
                    FindPhonePasswordActivity.startAction(this);
                }
                break;
            case R.id.iv_phoneLoginQQ:
                ThirdPartLoginUtils.LoginQQ(mPresenter);
                break;
            case R.id.tv_phoneLoginValid:
                tvPhoneLoginValid.setVisibility(View.GONE);
                viLoginValidCode.setVisibility(View.VISIBLE);
                Utils.setIM(viLoginValidCode,this);
                break;
            case R.id.iv_phoneLoginWeChat:
                ThirdPartLoginUtils.LoginWeChat(mPresenter);
                break;
        }
    }

    private void doSendValidCode() {
        tvPhoneLoginValid.setVisibility(View.GONE);
        viLoginValidCode.setVisibility(View.VISIBLE);
        type = 1;
        String phoneNumber1 = etPhoneLoginNumber2.getText().toString();
        Utils.setIM(viLoginValidCode,this);
        if (!"".equals(phoneNumber1) && phoneNumber1 != null) {
            if (RegularExpression.isCellphone(phoneNumber1)) {
                if (phoneNumberValid.equals(phoneNumber1)) {
                    code = 0;
                }
                phoneNumberValid = phoneNumber1;
                mPresenter.validPhoneIsExit(phoneNumberValid);
            } else {
                ToastUitl.show("请输入正确的手机号码", Toast.LENGTH_SHORT);
            }
        } else {
            ToastUitl.show("请输入手机号码", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 登录
     */
    private void doLogin() {
        if (isPswLogin == 1) {
            userNum = etUserLoginNumber.getText().toString();
            psw2 = etUserLoginPsw.getText().toString();
            if ("".equals(userNum) || userNum == null) {
                ToastUitl.showShort("请输入用户名");
                return;
            }
            if ("".equals(psw2) || psw2 == null) {
                ToastUitl.showShort("请输入密码");
                return;
            }
            mPresenter.getLogin(userNum, psw2, 2);
        } else {
            phoneNumberValid = etPhoneLoginNumber2.getText().toString();
            validCode = viLoginValidCode.getTextString();
            if ("".equals(phoneNumberValid) || phoneNumberValid == null) {
                ToastUitl.showShort("请输入手机号码");
                return;
            }
            if (RegularExpression.isCellphone(phoneNumberValid) == false) {
                ToastUitl.showShort("请输入正确的手机号码");
                return;
            }
            if ("".equals(validCode) || validCode == null) {
                ToastUitl.showShort("请输入验证码");
                return;
            }
            type = 0;
            mPresenter.validPhoneIsExit(phoneNumberValid);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
        if (validDialog != null) {
            validDialog.dismiss();
        }
        stopService(mCodeTimerServiceIntent);
        stopService(mVoiseTimerServiceIntent);
        unregisterReceiver(mCodeTimerReceiver);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        /*Utils.getConnect();*/
    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
