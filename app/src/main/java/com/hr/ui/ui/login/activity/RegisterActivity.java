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
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.Base3Activity;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.contract.RegisterContract;
import com.hr.ui.ui.login.model.RegisterModel;
import com.hr.ui.ui.login.presenter.RegisterPresenter;
import com.hr.ui.utils.CodeTimer;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
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
 * Created by wdr on 2017/12/5.
 */

public class RegisterActivity extends Base3Activity<RegisterPresenter, RegisterModel> implements RegisterContract.View {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_phoneRegisterNumber)
    EditText etPhoneRegisterNumber;
    @BindView(R.id.iv_phoneRegisterPswIcon)
    ImageView ivPhoneRegisterPswIcon;
    @BindView(R.id.et_phoneRegisterPsw)
    EditText etPhoneRegisterPsw;
    @BindView(R.id.btn_phoneRegisterOK)
    Button btnPhoneRegisterOK;
    public static final String TAG = RegisterActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_phoneRegisterGetValidCode)
    TextView tvPhoneRegisterGetValidCode;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_phoneRegisterNumberIcon)
    ImageView ivPhoneRegisterNumberIcon;
    @BindView(R.id.iv_phoneRegisterPhoneDelete)
    ImageView ivPhoneRegisterPhoneDelete;
    @BindView(R.id.iv_phoneRegisterValidCodeIcon)
    ImageView ivPhoneRegisterValidCodeIcon;
    @BindView(R.id.iv_phoneRegisterValidCodeDelete)
    ImageView ivPhoneRegisterValidCodeDelete;
    @BindView(R.id.View_lineValidCode)
    View ViewLineValidCode;
    @BindView(R.id.iv_phoneRegisterPswDelete)
    ImageView ivPhoneRegisterPswDelete;
    @BindView(R.id.tv_registerHasAccount)
    TextView tvRegisterHasAccount;
    @BindView(R.id.iv_phoneRegisterHiddenPsw)
    ImageView ivPhoneRegisterHiddenPsw;
    @BindView(R.id.cl_register)
    ConstraintLayout clRegister;
    @BindView(R.id.vi_phoneRegisterValidCodeInput)
    VerificationCodeEditText viPhoneRegisterValidCodeInput;
    @BindView(R.id.tv_phoneRegisterValidCode)
    TextView tvPhoneRegisterValidCode;
    @BindView(R.id.rl_phoneRegisterValidCodeDelete)
    RelativeLayout rlPhoneRegisterValidCodeDelete;
    @BindView(R.id.tv_phoneRegisterOr)
    TextView tvPhoneRegisterOr;
    @BindView(R.id.tv_phoneRegisterVoice)
    TextView tvPhoneRegisterVoice;
    @BindView(R.id.iv_phoneRegisterVoice)
    ImageView ivPhoneRegisterVoice;
    @BindView(R.id.fl_phoneRegisterVoice)
    FrameLayout flPhoneRegisterVoice;
    private PopupWindow popupWindow;
    private String autoCode;
    private ValidDialog validDialog;
    private Intent mCodeTimerServiceIntent, mCodeTimerServiceVoiceIntent;
    public static final String CODE = "code", VOICECODE = "codeVoiceRegister";
    private SharedPreferencesUtils sUtils;
    private int code;
    private String validCode;
    private int type; //0代表的是注册  1，代表的是获取验证码
    private String phoneNumber = "", password;
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId, validType = 1;
    private boolean isHidden = true;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String phoneNumber) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        intent.putExtra("phone", phoneNumber);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void showLoading(String title) {
        Log.i(TAG, title);
    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void sendValidCode(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        Utils.setIM(viPhoneRegisterValidCodeInput,this);
        ivPhoneRegisterVoice.setEnabled(false);
        tvPhoneRegisterGetValidCode.setEnabled(false);
        if (validType == 1) {
            ToastUitl.showShort(R.string.validAlreadySend);
            ivPhoneRegisterVoice.setImageResource(R.mipmap.voice_grey);
            tvPhoneRegisterGetValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceIntent);
            } else {
                startService(mCodeTimerServiceIntent);
            }
        } else {
            ToastUitl.showShort(R.string.voiceAlreadySend);
            tvPhoneRegisterGetValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            tvPhoneRegisterVoice.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            ivPhoneRegisterVoice.setVisibility(View.GONE);
            tvPhoneRegisterVoice.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceVoiceIntent);
            } else {
                startService(mCodeTimerServiceVoiceIntent);
            }
        }
        if (validDialog != null) {
            validDialog.dismiss();
        }
    }

    @Override
    public void sendAutoCode(String autoCode) {
        this.autoCode = autoCode;
        if (validDialog != null) {
            validDialog.setImageBitMap(EncryptUtils.stringtoBitmap(autoCode));
        }
    }

    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne2(this, multipleResumeBean, userId, imageIds, mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean, this, titles);
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
        if ("1".equals(flag)) {
            ToastUitl.showShort(R.string.error_327);
            return;
        } else {
            if (type == 0) {
                mPresenter.getRegister(phoneNumber, validCode, password);
            } else {
                if (sUtils.getIntValue("code", 0) >= 1) {
                    initPopWindow();
                    mPresenter.getAutoCode();

                } else {
                    mPresenter.getValidCode(phoneNumber, "", 0, Constants.VALIDCODE_REGISTER_YTPE, validType);
                }
            }
        }
    }


    @Override
    public void sendRegisterSuccess(int userId) {
        MobclickAgent.onEvent(mContext, "v6_register_phone");
        MobclickAgent.onEvent(this, "v6_login_phone");
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        sUtils.setIntValue(Constants.AUTOLOGINTYPE, 0);
        sUtils.setStringValue(Constants.USERPHONE, phoneNumber);
        this.userId = userId;
        sUtils.setStringValue(Constants.USERID, userId + "");
        mPresenter.getResumeList();
    }

    @Override
    public void bindingSuccess(int userId) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
        phoneNumber = getIntent().getStringExtra("phone");
        if (phoneNumber != null && !"".equals(phoneNumber)) {
            etPhoneRegisterNumber.setText(phoneNumber);
        } else {
            phoneNumber = "";
        }
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.register);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viPhoneRegisterValidCodeInput.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivPhoneRegisterValidCodeDelete.setVisibility(View.VISIBLE);
                } else {
                    ivPhoneRegisterValidCodeDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onInputCompleted(CharSequence s) {

            }
        });
       /* myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        viPhoneRegisterValidCodeInput.setOnLongClickLitener(new VerificationCodeEditText.OnLongClickListener() {
            @Override
            public void onLongClick() {
                //ToastUitl.showShort("你好");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(viPhoneRegisterValidCodeInput.getWindowToken(), 0);
                initPopupWindow();
            }
        });*/
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //短信验证码计时器服务
        mCodeTimerServiceIntent = new Intent(this, CodeTimerService.class);
        mCodeTimerServiceIntent.setAction(CODE);

        //语音验证码计时器服务
        mCodeTimerServiceVoiceIntent = new Intent(this, CodeTimerService.class);
        mCodeTimerServiceVoiceIntent.setAction(VOICECODE);
        //注册接收短信验证码和语音验证码计时器信息的广播
        IntentFilter filterVoice = new IntentFilter(VOICECODE);
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);
        registerReceiver(mCodeTimerReceiver, filterVoice);
        onEditViewTextChangeAndFocusChange();
    }

    private void onEditViewTextChangeAndFocusChange() {
        Utils.setEditViewTextChangeAndFocus(etPhoneRegisterNumber, ivPhoneRegisterPhoneDelete);
        Utils.setEditViewTextChangeAndFocus(etPhoneRegisterPsw, ivPhoneRegisterPswDelete);
    }

    @OnClick({R.id.tv_phoneRegisterGetValidCode,R.id.tv_phoneRegisterValidCode, R.id.btn_phoneRegisterOK, R.id.iv_phoneRegisterVoice, R.id.iv_phoneRegisterHiddenPsw, R.id.iv_phoneRegisterPhoneDelete, R.id.iv_phoneRegisterPswDelete, R.id.iv_phoneRegisterValidCodeDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phoneRegisterValidCode:
                tvPhoneRegisterValidCode.setVisibility(View.GONE);
                viPhoneRegisterValidCodeInput.setVisibility(View.VISIBLE);
                Utils.setIM(viPhoneRegisterValidCodeInput,this);
                break;
            case R.id.iv_phoneRegisterHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivPhoneRegisterHiddenPsw.setImageResource(R.mipmap.see);
                    etPhoneRegisterPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivPhoneRegisterHiddenPsw.setImageResource(R.mipmap.hidden);
                    etPhoneRegisterPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etPhoneRegisterPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etPhoneRegisterPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_phoneRegisterPhoneDelete:
                etPhoneRegisterNumber.setText("");
                break;
            case R.id.iv_phoneRegisterVoice:
                validType = 2;
                doSendValidCode();
                break;
            case R.id.iv_phoneRegisterPswDelete:
                etPhoneRegisterPsw.setText("");
                break;
            case R.id.iv_phoneRegisterValidCodeDelete:
                viPhoneRegisterValidCodeInput.setValue("");
                break;
            case R.id.tv_phoneRegisterGetValidCode:
                validType = 1;
                doSendValidCode();
                break;
            case R.id.btn_phoneRegisterOK:
                type = 0;
                doRegister();
                break;
        }
    }
    private void doSendValidCode() {
        type = 1;
        viPhoneRegisterValidCodeInput.setVisibility(View.VISIBLE);
        tvPhoneRegisterValidCode.setVisibility(View.GONE);
        String phoneNumber1 = etPhoneRegisterNumber.getText().toString();
        if (!"".equals(phoneNumber1) && phoneNumber1 != null) {
            if (RegularExpression.isCellphone(phoneNumber1)) {
                if (phoneNumber.equals(phoneNumber1)) {
                    code = 0;
                }
                phoneNumber = phoneNumber1;
                mPresenter.validPhoneIsExit(phoneNumber);
            } else {
                ToastUitl.show("请输入正确的手机号码", Toast.LENGTH_SHORT);
            }
        } else {
            ToastUitl.show("请输入手机号码", Toast.LENGTH_SHORT);
        }
    }

    private void doRegister() {
        phoneNumber = etPhoneRegisterNumber.getText().toString();
        validCode = viPhoneRegisterValidCodeInput.getTextString();
        password = etPhoneRegisterPsw.getText().toString();

        if ("".equals(phoneNumber) || phoneNumber == null) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if (RegularExpression.isCellphone(phoneNumber) == false) {
            ToastUitl.showShort("请输入正确的手机号码");
            return;
        }
        if ("".equals(validCode) || validCode == null) {
            ToastUitl.showShort("请输入验证码");
            return;
        }
        if ("".equals(password) || password == null) {
            ToastUitl.showShort("请输入密码");
            return;
        }
        if (password.length() < 6 || password.length() > 25) {
            ToastUitl.showShort("请输入长度为6-25位的密码");
            return;
        }
        type = 0;
        mPresenter.validPhoneIsExit(phoneNumber);

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
                    mPresenter.getValidCode(etPhoneRegisterNumber.getText().toString(), autoCode, 1, Constants.VALIDCODE_REGISTER_YTPE, validType);
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

    /**
     * 验证码倒计时的广播
     */
    private BroadcastReceiver mCodeTimerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean isEnable = intent.getBooleanExtra(CodeTimer.IS_ENABLE, false);
            if(isEnable){
                ivPhoneRegisterVoice.setImageResource(R.mipmap.voice_orange);
                ivPhoneRegisterVoice.setVisibility(View.VISIBLE);
                tvPhoneRegisterGetValidCode.setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.new_main));
                tvPhoneRegisterVoice.setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.new_main));
                tvPhoneRegisterVoice.setVisibility(View.GONE);
            }
            if (CODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvPhoneRegisterGetValidCode.setEnabled(isEnable);
                ivPhoneRegisterVoice.setEnabled(isEnable);
                tvPhoneRegisterGetValidCode.setText(message);
            } else if (VOICECODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvPhoneRegisterGetValidCode.setEnabled(isEnable);
                ivPhoneRegisterVoice.setEnabled(isEnable);
                tvPhoneRegisterVoice.setText(message);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mCodeTimerServiceIntent);
        stopService(mCodeTimerServiceVoiceIntent);
        unregisterReceiver(mCodeTimerReceiver);
        if (validDialog != null) {
            validDialog.dismiss();
        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        /* Utils.getConnect();*/
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @OnClick(R.id.tv_registerHasAccount)
    public void onViewClicked() {
        LoginActivity.startAction(this);
        finish();
    }
}
