package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
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
import com.service.CodeTimerService;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/6.
 */

public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {


    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.view_loginLine1)
    View viewLoginLine1;
    @BindView(R.id.rb_phoneLogin)
    RadioButton rbPhoneLogin;
    @BindView(R.id.rb_userLogin)
    RadioButton rbUserLogin;
    @BindView(R.id.rb_validCodeLogin)
    RadioButton rbValidCodeLogin;
    @BindView(R.id.view_loginLine2)
    View viewLoginLine2;
    @BindView(R.id.rl_loginTitle)
    RelativeLayout rlLoginTitle;
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
    @BindView(R.id.ll_pswLogin)
    LinearLayout llPswLogin;
    @BindView(R.id.iv_phoneLoginNumberIcon2)
    ImageView ivPhoneLoginNumberIcon2;
    @BindView(R.id.et_phoneLoginNumber2)
    EditText etPhoneLoginNumber2;
    @BindView(R.id.iv_phoneLoginPhoneDelete2)
    ImageView ivPhoneLoginPhoneDelete2;
    @BindView(R.id.iv_phoneLoginValidIcon)
    ImageView ivPhoneLoginValidIcon;
    @BindView(R.id.et_phoneLoginValid)
    EditText etPhoneLoginValid;
    @BindView(R.id.iv_phoneLoginValidDelete)
    ImageView ivPhoneLoginValidDelete;
    @BindView(R.id.tv_sendValidCode)
    TextView tvSendValidCode;
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
    @BindView(R.id.btn_phoneLoginOK)
    Button btnPhoneLoginOK;
    @BindView(R.id.tv_phoneLoginAccountLogin)
    TextView tvPhoneLoginAccountLogin;
    @BindView(R.id.tv_phoneLoginFindPsw)
    TextView tvPhoneLoginFindPsw;
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
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private boolean isHidden = true,isHiddleUser=true;
    private String phoneNum,userNum, phoneNumberValid = "", psw,psw2, validCode;
    private SharedPreferencesUtils sUtils;
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId, code, type;
    private Intent mCodeTimerServiceIntent;
    public static final String CODE = "codeValidLogin";
    private PopupWindow popupWindow;
    private MyDialog dialog;
    private int isPswLogin = 0; //0代表手机登录 1代表密码登录  2 代表验证码邓丽

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
        if (isPswLogin==0) {
            MobclickAgent.onEvent(this, "v6_login_phone");
            sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 0);
        }else if(isPswLogin==1){
            MobclickAgent.onEvent(this,"v6_login_user");
            sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 1);
        } else {
            sUtils.setIntValue(Constants.ISAUTOLOGIN, 0);
            MobclickAgent.onEvent(this,"v6_login_validCode");
        }
        sUtils.setStringValue(Constants.USERPHONE, phoneNum);
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
    public void bindingSuccess(int userId) {

    }


    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne(this, multipleResumeBean, userId, imageIds, mPresenter);
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
        etAutoCode.setText("");
        mPresenter.getAutoCode();
    }

    @Override
    public void phoneIsExit(String flag) {
        if ("0".equals(flag)) {
            dialog=new MyDialog(this,2);
            dialog.setMessage(getString(R.string.error_phoneNotExit2));
            dialog.setYesOnclickListener("去注册", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    RegisterActivity.startAction(LoginActivity.this,phoneNumberValid);
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
                    mPresenter.getValidCode(phoneNumberValid, "", 0, Constants.VALIDCODE_LOGIN_YTPE);
                }
            }
        }
    }

    /**
     * 图形验证码界面
     */
    public void initPopWindow() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.layout_autocode, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        ivAutoCode = popView.findViewById(R.id.vc_image);
        TextView tvReflesh = popView.findViewById(R.id.vc_refresh);
        etAutoCode = popView.findViewById(R.id.vc_code);
        RelativeLayout rlConfirm = popView.findViewById(R.id.rl__item_autocode_confirm);
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autoCodeText = etAutoCode.getText().toString();
                if (autoCodeText != null && !"".equals(autoCodeText)) {
                    mPresenter.getValidCode(etPhoneLoginNumber2.getText().toString(), autoCodeText, 1, Constants.VALIDCODE_LOGIN_YTPE);
                } else {
                    ToastUitl.show("请填写图形验证码", Toast.LENGTH_SHORT);
                }
            }
        });

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            //  大于等于19即为4.4及以上执行内容
            // 设置背景颜色变暗
        } else {
            //  低于19即为4.4以下执行内容
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        tvReflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAutoCode();
            }
        });
        popupWindow.showAtLocation(clPhoneLogin, Gravity.CENTER, 0, 0);
    }

    @Override
    public void sendValidCode(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        tvSendValidCode.setEnabled(false);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            startForegroundService(mCodeTimerServiceIntent);
        } else {
            startService(mCodeTimerServiceIntent);
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void sendAutoCode(String autoCode) {
        ivAutoCode.setImageBitmap(EncryptUtils.stringtoBitmap(autoCode));
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
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.userLogin);
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
        //验证码计时器服务
        mCodeTimerServiceIntent = new Intent(this, CodeTimerService.class);
        mCodeTimerServiceIntent.setAction(CODE);
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);
        setFocusChangeAndEditViewTextChange();
    }

    /**
     * 验证码倒计时的广播
     */
    private BroadcastReceiver mCodeTimerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                boolean isEnable = intent.getBooleanExtra(CodeTimer.IS_ENABLE, false);
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvSendValidCode.setEnabled(isEnable);
                tvSendValidCode.setText(message);
            }
        }
    };

    private void setFocusChangeAndEditViewTextChange() {
        Utils.setEditViewTextChangeAndFocus(etPhoneLoginPsw, ivPhoneLoginPswDelete);
        Utils.setEditViewTextChangeAndFocus(etPhoneLoginNumber, ivPhoneLoginPhoneDelete);
        Utils.setEditViewTextChangeAndFocus(etPhoneLoginNumber2, ivPhoneLoginPhoneDelete2);
        Utils.setEditViewTextChangeAndFocus(etPhoneLoginValid, ivPhoneLoginValidDelete);
        Utils.setEditViewTextChangeAndFocus(etUserLoginNumber,ivUserLoginNumberDelete);
        Utils.setEditViewTextChangeAndFocus(etUserLoginPsw,ivUserLoginPswDelete);
    }

    @OnClick({R.id.rl_phoneLoginHiddenPsw,R.id.rl_userLoginHiddenPsw,R.id.iv_userLoginNumberDelete,R.id.iv_userLoginPswDelete, R.id.rb_phoneLogin,R.id.rb_userLogin, R.id.tv_sendValidCode, R.id.iv_phoneLoginPhoneDelete2, R.id.iv_phoneLoginValidDelete, R.id.rb_validCodeLogin, R.id.iv_phoneLoginPhoneDelete, R.id.iv_phoneLoginPswDelete, R.id.btn_phoneLoginOK, R.id.tv_phoneLoginAccountLogin, R.id.tv_phoneLoginFindPsw, R.id.iv_phoneLoginQQ, R.id.iv_phoneLoginWeChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.rb_phoneLogin:
                isPswLogin = 0;
                llPswLogin.setVisibility(View.VISIBLE);
                llValidLogin.setVisibility(View.GONE);
                rbPhoneLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                rbValidCodeLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                rbUserLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                llUserLoginMiddle.setVisibility(View.GONE);
               /* viewPswLogin.setVisibility(View.VISIBLE);
                viewValidLogin.setVisibility(View.GONE);*/
                break;
            case R.id.rb_validCodeLogin:
                isPswLogin = 2;
                llPswLogin.setVisibility(View.GONE);
                rbPhoneLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                rbValidCodeLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                rbUserLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                llValidLogin.setVisibility(View.VISIBLE);
                llUserLoginMiddle.setVisibility(View.GONE);
                /*viewPswLogin.setVisibility(View.GONE);
                viewValidLogin.setVisibility(View.VISIBLE);*/
                break;
            case R.id.rb_userLogin:
                isPswLogin=1;
                llPswLogin.setVisibility(View.GONE);
                rbPhoneLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                rbValidCodeLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                rbUserLogin.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                llValidLogin.setVisibility(View.GONE);
                llUserLoginMiddle.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_phoneLoginHiddenPsw:
                if (isHiddleUser) {
                    //设置EditText文本为可见的
                    ivPhoneLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etPhoneLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivPhoneLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etPhoneLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHiddleUser = !isHiddleUser;
                etPhoneLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence1 = etPhoneLoginPsw.getText();
                if (charSequence1 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence1;
                    Selection.setSelection(spanText, charSequence1.length());
                }
                break;
            case R.id.btn_phoneLoginOK:
                doLogin();
                //MainActivity.startAction(this,0);
                break;
            case R.id.iv_phoneLoginPhoneDelete:
                etPhoneLoginNumber.setText("");
                break;
            case R.id.iv_phoneLoginPhoneDelete2:
                etPhoneLoginNumber2.setText("");
            case R.id.iv_phoneLoginValidDelete:
                etPhoneLoginValid.setText("");
            case R.id.iv_phoneLoginPswDelete:
                etPhoneLoginPsw.setText("");
                break;
            case R.id.tv_sendValidCode:
                type = 1;
                String phoneNumber1 = etPhoneLoginNumber2.getText().toString();
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
                break;
            case R.id.tv_phoneLoginAccountLogin:
                UserLoginActivity.startAction(this);
                break;
            case R.id.tv_phoneLoginFindPsw:
                if(isPswLogin==1){
                    FindUserPasswordActivity.startAction(this);
                }else {
                    FindPhonePasswordActivity.startAction(this);
                }
                break;
            case R.id.iv_phoneLoginQQ:
                ThirdPartLoginUtils.LoginQQ(mPresenter);
                break;
            case R.id.iv_phoneLoginWeChat:
                ThirdPartLoginUtils.LoginWeChat(mPresenter);
                break;
        }
    }

    /**
     * 登录
     */
    private void doLogin() {
        if (isPswLogin==0) {
            phoneNum = etPhoneLoginNumber.getText().toString();
            psw = etPhoneLoginPsw.getText().toString();
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
      /*  if (psw.length() < 6 || psw.length() > 25) {
            ToastUitl.showShort("请输入6-25位长度密码");
            return;
        }*/
            mPresenter.getLogin(phoneNum, psw, 1);
        }else if(isPswLogin==1){
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
            validCode = etPhoneLoginValid.getText().toString();
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
        if(dialog!=null){
            dialog.dismiss();
        }
        stopService(mCodeTimerServiceIntent);
        unregisterReceiver(mCodeTimerReceiver);
    }
}
