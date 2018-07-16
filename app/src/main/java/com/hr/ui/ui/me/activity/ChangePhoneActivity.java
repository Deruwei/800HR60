package com.hr.ui.ui.me.activity;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.EventString;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.ui.me.contract.ChangePhoneContract;
import com.hr.ui.ui.me.model.ChangePhoneModel;
import com.hr.ui.ui.me.presenter.ChangePhonePresenter;
import com.hr.ui.ui.resume.activity.ResumePersonalInfoActivity;
import com.hr.ui.utils.CodeTimer;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.VerificationAction;
import com.hr.ui.view.VerificationCodeEditText;
import com.hr.ui.view.dialog.ValidDialog;
import com.service.CodeTimerService;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/18.
 */

public class ChangePhoneActivity extends BaseActivity<ChangePhonePresenter, ChangePhoneModel> implements ChangePhoneContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_changePhoneNumberIcon)
    ImageView ivChangePhoneNumberIcon;
    @BindView(R.id.et_changePhoneNumber)
    EditText etChangePhoneNumber;
    @BindView(R.id.iv_changePhoneValidCodeIcon)
    ImageView ivChangePhoneValidCodeIcon;
    @BindView(R.id.view_getValidCode2)
    View viewGetValidCode2;
    @BindView(R.id.btn_changePhoneOK)
    Button btnChangePhoneOK;
    @BindView(R.id.iv_changePhoneNumberDelete)
    ImageView ivChangePhoneNumberDelete;
    @BindView(R.id.iv_changePhoneValidCodeDelete)
    ImageView ivChangePhoneValidCodeDelete;
    @BindView(R.id.iv_changePhonePswIcon)
    ImageView ivChangePhonePswIcon;
    @BindView(R.id.et_changePhonePsw)
    EditText etChangePhonePsw;
    @BindView(R.id.iv_changePhonePswDelete)
    ImageView ivChangePhonePswDelete;
    @BindView(R.id.view_getValidCode3)
    View viewGetValidCode3;
    @BindView(R.id.cl_changePhone)
    ConstraintLayout clChangePhone;
    @BindView(R.id.iv_changePhonePswHidden)
    ImageView ivChangePhonePswHidden;
    @BindView(R.id.tv_changePhoneValidCode)
    TextView tvChangePhoneValidCode;
    @BindView(R.id.vi_changePhoneNumber)
    VerificationCodeEditText viChangePhoneNumber;
    @BindView(R.id.rl_changePhoneValidCodeDelete)
    RelativeLayout rlChangePhoneValidCodeDelete;
    @BindView(R.id.tv_changePhoneGetValidCode)
    TextView tvChangePhoneGetValidCode;
    @BindView(R.id.tv_changePhoneOr)
    TextView tvChangePhoneOr;
    @BindView(R.id.tv_changePhoneVoice)
    TextView tvChangePhoneVoice;
    @BindView(R.id.iv_changePhoneVoice)
    ImageView ivChangePhoneVoice;
    @BindView(R.id.fl_changePhoneVoice)
    FrameLayout flChangePhoneVoice;
    private boolean isHidden = true;
    private String chaptcha;
    private String autoCode;
    private Intent mCodeTimerServiceIntent, mCodeTimerServiceVoiceIntent;
    public static final String CODE = "codeChangePhone", VOICECODE = "codeVoiceChange";
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private SharedPreferencesUtils sUtils;
    private int code, validType = 1;
    private String Tag;
    private PopupWindow popupWindow;
    private ValidDialog validDialog;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ChangePhoneActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    public static void startAction(Activity activity, String tag) {
        Intent intent = new Intent(activity, ChangePhoneActivity.class);
        intent.putExtra("tag", tag);
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
    public void changePhoneSuccess() {
        ToastUitl.showShort("更改手机成功");
        int autoLoginType = sUtils.getIntValue(Constants.AUTOLOGINTYPE, 5);
        LoginBean loginBean = LoginDBUtils.queryDataById(autoLoginType + "");
        // Log.i("现在的数据", loginBean.getLoginType() + "");
        if (loginBean.getLoginType() == 0) {
            loginBean.setName(etChangePhoneNumber.getText().toString());
            LoginDBUtils.insertOrReplaceData(loginBean);
        }

        finish();
        if (ResumePersonalInfoActivity.TAG.equals(Tag)) {
            // ResumePersonalInfoActivity.instance.setValid(etChangePhoneNumber.getText().toString());
            EventBus.getDefault().post(new EventString(etChangePhoneNumber.getText().toString(), "validCode"));
        }

    }

    @Override
    public void getValidCodeSuccess(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        tvChangePhoneGetValidCode.setEnabled(false);
        ivChangePhoneVoice.setEnabled(false);
        if (validType == 1) {
            ToastUitl.showShort(R.string.validAlreadySend);
            ivChangePhoneVoice.setImageResource(R.mipmap.voice_grey);
            tvChangePhoneGetValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceIntent);
            } else {
                startService(mCodeTimerServiceIntent);
            }
        } else {
            ToastUitl.showShort(R.string.voiceAlreadySend);
            tvChangePhoneGetValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            tvChangePhoneVoice.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            ivChangePhoneVoice.setVisibility(View.GONE);
            tvChangePhoneVoice.setVisibility(View.VISIBLE);
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
    public void getCaptchaSuccess(String autoCode) {
        this.autoCode = autoCode;
        if (validDialog != null) {
            validDialog.setImageBitMap(EncryptUtils.stringtoBitmap(autoCode));
        }
    }

    @Override
    public void phoneIsExit(String flag) {
        if ("1".equals(flag)) {
            ToastUitl.showShort(R.string.error_327);
            return;
        } else {
            if (sUtils.getIntValue("code", 0) >= 1) {
                mPresenter.getCaptcha();
                initPopWindow();
            } else {
                mPresenter.getValidCode(etChangePhoneNumber.getText().toString(), Constants.VALIDCODE_RESETORVALIDPHONE_YTPE, 1, "", validType);
            }
        }
    }

    @Override
    public void getValidCodeFailt() {
        if (validDialog != null) {
            validDialog.setText("");
        }
        mPresenter.getCaptcha();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_changephone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        Tag = getIntent().getStringExtra("tag");
        sUtils = new SharedPreferencesUtils(this);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.changeTel);
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
        mCodeTimerServiceIntent = new Intent(this, CodeTimerService.class);
        mCodeTimerServiceIntent.setAction(CODE);

        mCodeTimerServiceVoiceIntent = new Intent(this, CodeTimerService.class);
        mCodeTimerServiceVoiceIntent.setAction(VOICECODE);
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);

        IntentFilter filterVoice = new IntentFilter(VOICECODE);
        registerReceiver(mCodeTimerReceiver, filterVoice);
        editViewTextChangeAndFocos();
    }

    private void editViewTextChangeAndFocos() {
        Utils.setEditViewTextChangeAndFocus(etChangePhoneNumber, ivChangePhoneNumberDelete);
        Utils.setEditViewTextChangeAndFocus(etChangePhonePsw, ivChangePhonePswDelete);
        viChangePhoneNumber.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivChangePhoneValidCodeDelete.setVisibility(View.VISIBLE);
                } else {
                    ivChangePhoneValidCodeDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onInputCompleted(CharSequence s) {

            }
        });
    }

    @OnClick({R.id.tv_changePhoneGetValidCode, R.id.tv_changePhoneValidCode,R.id.iv_changePhoneVoice, R.id.btn_changePhoneOK, R.id.iv_changePhonePswHidden, R.id.iv_changePhoneNumberDelete, R.id.iv_changePhonePswDelete, R.id.iv_changePhoneValidCodeDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_changePhoneValidCode:
                tvChangePhoneValidCode.setVisibility(View.GONE);
                viChangePhoneNumber.setVisibility(View.VISIBLE);
                Utils.setIM(viChangePhoneNumber,this);
                break;
            case R.id.tv_changePhoneGetValidCode:
                validType = 1;
                doSendValidCode();
                break;
            case R.id.iv_changePhoneVoice:
                validType = 2;
                doSendValidCode();
                break;
            case R.id.btn_changePhoneOK:
                doChangePhone();
                break;
            case R.id.iv_changePhoneNumberDelete:
                etChangePhoneNumber.setText("");
                break;
            case R.id.iv_changePhonePswDelete:
                etChangePhonePsw.setText("");
                break;
            case R.id.iv_changePhoneValidCodeDelete:
                viChangePhoneNumber.setText("");
                break;
            case R.id.iv_changePhonePswHidden:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivChangePhonePswHidden.setImageResource(R.mipmap.see);
                    etChangePhonePsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivChangePhonePswHidden.setImageResource(R.mipmap.hidden);
                    etChangePhonePsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etChangePhonePsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etChangePhonePsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
        }
    }

    private void doSendValidCode() {
        tvChangePhoneValidCode.setVisibility(View.GONE);
        viChangePhoneNumber.setVisibility(View.VISIBLE);
        Utils.setIM(viChangePhoneNumber,this);
        String phoneNumber = etChangePhoneNumber.getText().toString();
        if (!"".equals(phoneNumber) && phoneNumber != null) {
            if (RegularExpression.isCellphone(phoneNumber)) {
                mPresenter.validPhoneIsExit(phoneNumber);
            } else {
                ToastUitl.show("请输入正确的手机号码", Toast.LENGTH_SHORT);
            }
        } else {
            ToastUitl.show("请输入手机号码", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 图形验证码界面   String phoneNumber, String type,int way, String captcha
     */
    public void initPopWindow() {
        if (validDialog != null) {
            validDialog.show();
        } else {
            validDialog = new ValidDialog(this);
            validDialog.setOnConfirmListener(new ValidDialog.OnConfirmListener() {
                @Override
                public void onConfirm(String autoCode) {
                    mPresenter.getValidCode(etChangePhoneNumber.getText().toString(), Constants.VALIDCODE_RESETORVALIDPHONE_YTPE, 1, autoCode, validType);
                }
            });
            validDialog.setOnRefleshClickListener(new ValidDialog.OnRefleshClickListener() {
                @Override
                public void doReflesh() {
                    mPresenter.getCaptcha();
                }
            });
            validDialog.show();
        }
    }

    private void doChangePhone() {
        /*int autoLoginType = sUtils.getIntValue(Constants.AUTOLOGINTYPE, 5);
        LoginBean loginBean = LoginDBUtils.queryDataById(autoLoginType + "");*/
        if (etChangePhoneNumber.getText().toString() == null || "".equals(etChangePhoneNumber.getText().toString())) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if (RegularExpression.isCellphone(etChangePhoneNumber.getText().toString()) == false) {
            ToastUitl.showShort("请输入正确的手机号码");
            return;
        }
        if (viChangePhoneNumber.getTextString() == null || "".equals(viChangePhoneNumber.getTextString())) {
            ToastUitl.showShort("请输入验证码");
            return;
        }
        mPresenter.changePhone(etChangePhoneNumber.getText().toString(), viChangePhoneNumber.getTextString());
    }

    /**
     * 验证码倒计时的广播
     */
    private BroadcastReceiver mCodeTimerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean isEnable = intent.getBooleanExtra(CodeTimer.IS_ENABLE, false);
            if(isEnable) {
                tvChangePhoneVoice.setVisibility(View.GONE);
                ivChangePhoneVoice.setVisibility(View.VISIBLE);
                ivChangePhoneVoice.setImageResource(R.mipmap.voice_orange);
                tvChangePhoneGetValidCode.setTextColor(ContextCompat.getColor(ChangePhoneActivity.this,R.color.new_main));
                tvChangePhoneVoice.setTextColor(ContextCompat.getColor(ChangePhoneActivity.this,R.color.new_main));
            }

            if (CODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvChangePhoneGetValidCode.setEnabled(isEnable);
                ivChangePhoneVoice.setEnabled(isEnable);
                tvChangePhoneGetValidCode.setText(message);
            } else if (VOICECODE.equals(action)) {
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvChangePhoneGetValidCode.setEnabled(isEnable);
                ivChangePhoneVoice.setEnabled(isEnable);
                tvChangePhoneVoice.setText(message);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (validDialog != null) {
            validDialog.dismiss();
        }
        stopService(mCodeTimerServiceIntent);
        stopService(mCodeTimerServiceVoiceIntent);
        unregisterReceiver(mCodeTimerReceiver);
    }
}
