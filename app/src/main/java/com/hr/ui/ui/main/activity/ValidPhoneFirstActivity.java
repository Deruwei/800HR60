package com.hr.ui.ui.main.activity;

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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.activity.PhoneLoginActivity;
import com.hr.ui.ui.me.contract.ValidPhoneContract;
import com.hr.ui.ui.me.model.ValidPhoneModel;
import com.hr.ui.ui.me.presenter.ValidPhonePresenter;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/3/16.
 */

public class ValidPhoneFirstActivity extends BaseActivity<ValidPhonePresenter, ValidPhoneModel> implements ValidPhoneContract, ValidPhoneContract.View {

    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_validPhoneNumberIcon)
    ImageView ivValidPhoneNumberIcon;
    @BindView(R.id.et_validPhoneNumber)
    EditText etValidPhoneNumber;
    @BindView(R.id.iv_validPhoneNumberDelete)
    ImageView ivValidPhoneNumberDelete;
    @BindView(R.id.iv_validPhoneValidCodeIcon)
    ImageView ivValidPhoneValidCodeIcon;
    @BindView(R.id.iv_validPhoneValidCodeDelete)
    ImageView ivValidPhoneValidCodeDelete;
    @BindView(R.id.view_getValidCodeValid)
    android.view.View viewGetValidCodeValid;
    @BindView(R.id.btn_validPhoneOK)
    Button btnValidPhoneOK;
    @BindView(R.id.cl_validPhone)
    ConstraintLayout clValidPhone;
    @BindView(R.id.tv_validPhoneValidCode)
    TextView tvValidPhoneValidCode;
    @BindView(R.id.vi_validPhoneValidCode)
    VerificationCodeEditText viValidPhoneValidCode;
    @BindView(R.id.rl_validPhoneValidCodeDelete)
    RelativeLayout rlValidPhoneValidCodeDelete;
    @BindView(R.id.tv_validPhoneGetValidCode)
    TextView tvValidPhoneGetValidCode;
    @BindView(R.id.tv_validPhoneOr)
    TextView tvValidPhoneOr;
    @BindView(R.id.tv_validPhoneVoice)
    TextView tvValidPhoneVoice;
    @BindView(R.id.ll_validPhone)
    LinearLayout llValidPhone;
    @BindView(R.id.iv_validPhoneVoice)
    ImageView ivValidPhoneVoice;
    @BindView(R.id.fl_validPhoneVoice)
    FrameLayout flValidPhoneVoice;
    private String phoneNumber;
    private SharedPreferencesUtils sUtils;
    private PopupWindow popupWindow;
    private int code, validType = 1;
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private ValidDialog validDialog;
    private Intent mCodeTimerServiceIntent, mCodeTimerServiceVoiceIntent;
    public static final String CODE = "codeValidPhone", VOICECODE = "codeVoiceFirst";

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String phoneNumber) {
        Intent intent = new Intent(activity, ValidPhoneFirstActivity.class);
        intent.putExtra("phone", phoneNumber);
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
    public void validPhoneSuccess() {
        ToastUitl.showShort("手机号码验证成功");
        //ResumePersonalInfoActivity.instance.setValid(phoneNumber);
        finish();
    }

    @Override
    public void getValidCodeSuccess(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        tvValidPhoneGetValidCode.setEnabled(false);
        ivValidPhoneVoice.setEnabled(false);
        if (validType == 1) {
            ToastUitl.showShort(R.string.validAlreadySend);
            ivValidPhoneVoice.setImageResource(R.mipmap.voice_grey);
            tvValidPhoneGetValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceIntent);
            } else {
                startService(mCodeTimerServiceIntent);
            }
        } else {
            ToastUitl.showShort(R.string.voiceAlreadySend);
            tvValidPhoneGetValidCode.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            tvValidPhoneVoice.setTextColor(ContextCompat.getColor(this,R.color.color_999));
            ivValidPhoneVoice.setVisibility(android.view.View.GONE);
            tvValidPhoneVoice.setVisibility(android.view.View.VISIBLE);
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
        if (validDialog != null) {
            validDialog.setImageBitMap(EncryptUtils.stringtoBitmap(autoCode));
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
    public void phoneIsExit(String flag) {
        if ("1".equals(flag)) {
            ToastUitl.showShort(R.string.error_327);
            return;
        } else {
            if (sUtils.getIntValue("code", 0) >= 1) {
                mPresenter.getCaptcha();
                initPopWindow();
            } else {
                mPresenter.getValidCode(phoneNumber, Constants.VALIDCODE_RESETORVALIDPHONE_YTPE, 1, "", validType);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_validphone_fisrt;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils = new SharedPreferencesUtils(this);
        phoneNumber = getIntent().getStringExtra("phone");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.validPhone);
        etValidPhoneNumber.setText(phoneNumber);
        toolBar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                PhoneLoginActivity.startAction(ValidPhoneFirstActivity.this);
                SharedPreferencesUtils sUtils = new SharedPreferencesUtils(HRApplication.getAppContext());
                sUtils.setIntValue(Constants.ISAUTOLOGIN, 0);
                AppManager.getAppManager().finishAllActivity();
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
        editTextChangeAndFucos();
    }

    private void editTextChangeAndFucos() {
        Utils.setEditViewTextChangeAndFocus(etValidPhoneNumber, ivValidPhoneNumberDelete);
        viValidPhoneValidCode.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivValidPhoneNumberDelete.setVisibility(android.view.View.VISIBLE);
                } else {
                    ivValidPhoneValidCodeDelete.setVisibility(android.view.View.GONE);
                }
            }

            @Override
            public void onInputCompleted(CharSequence s) {

            }
        });
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
                ivValidPhoneVoice.setVisibility(android.view.View.VISIBLE);
                tvValidPhoneVoice.setVisibility(android.view.View.GONE);
                ivValidPhoneVoice.setImageResource(R.mipmap.voice_orange);
                tvValidPhoneGetValidCode.setTextColor(ContextCompat.getColor(ValidPhoneFirstActivity.this,R.color.new_main));
                tvValidPhoneVoice.setTextColor(ContextCompat.getColor(ValidPhoneFirstActivity.this,R.color.new_main));
            }
            if (CODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvValidPhoneGetValidCode.setEnabled(isEnable);
                ivValidPhoneVoice.setEnabled(isEnable);
                tvValidPhoneGetValidCode.setText(message);
            } else if (VOICECODE.equals(action)) {
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvValidPhoneGetValidCode.setEnabled(isEnable);
                ivValidPhoneVoice.setEnabled(isEnable);
                tvValidPhoneVoice.setText(message);
            }
        }
    };

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
                    mPresenter.getValidCode(phoneNumber, Constants.VALIDCODE_RESETORVALIDPHONE_YTPE, 1, autoCode, validType);
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

    @OnClick({R.id.tv_validPhoneGetValidCode,R.id.tv_validPhoneValidCode, R.id.iv_validPhoneVoice, R.id.btn_validPhoneOK, R.id.iv_validPhoneNumberDelete, R.id.iv_validPhoneValidCodeDelete})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.tv_validPhoneValidCode:
                tvValidPhoneValidCode.setVisibility(android.view.View.GONE);
                viValidPhoneValidCode.setVisibility(android.view.View.VISIBLE);
                Utils.setIM(viValidPhoneValidCode,this);
                break;
            case R.id.tv_validPhoneGetValidCode:
                validType = 1;
                doSendValidCode();
                break;
            case R.id.iv_validPhoneVoice:
                validType = 2;
                doSendValidCode();
                break;
            case R.id.btn_validPhoneOK:
                doValidCode();
                break;
            case R.id.iv_validPhoneNumberDelete:
                etValidPhoneNumber.setText("");
                break;
            case R.id.iv_validPhoneValidCodeDelete:
                viValidPhoneValidCode.setText("");
                break;
        }
    }

    private void doSendValidCode() {
        viValidPhoneValidCode.setVisibility(android.view.View.VISIBLE);
        tvValidPhoneValidCode.setVisibility(android.view.View.GONE);
        Utils.setIM(viValidPhoneValidCode,this);
        phoneNumber = etValidPhoneNumber.getText().toString();
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

    private void doValidCode() {
        phoneNumber=etValidPhoneNumber.getText().toString();
        if(phoneNumber==null||"".equals(phoneNumber)){
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if(!RegularExpression.isCellphone(phoneNumber)){
            ToastUitl.showShort("请输入正确的手机号码");
            return;
        }
        if (viValidPhoneValidCode.getTextString() == null || "".equals(viValidPhoneValidCode.getTextString())) {
            ToastUitl.showShort("请输入验证码");
            return;
        }
        mPresenter.validPhone(phoneNumber, viValidPhoneValidCode.getTextString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

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
}
