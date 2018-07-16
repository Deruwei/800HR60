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
import com.hr.ui.constants.Constants;
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

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/2/6.
 */

public class ValidPhoneActivity extends BaseActivity<ValidPhonePresenter, ValidPhoneModel> implements ValidPhoneContract.View {
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
    @BindView(R.id.tv_validPhoneNumber)
    TextView tvValidPhoneNumber;
    @BindView(R.id.iv_validPhoneValidCodeIcon)
    ImageView ivValidPhoneValidCodeIcon;
    @BindView(R.id.view_getValidCodeValid)
    View viewGetValidCodeValid;
    @BindView(R.id.btn_validPhoneOK)
    Button btnValidPhoneOK;
    @BindView(R.id.tv_validPhoneChangePhone)
    TextView tvValidPhoneChangePhone;
    @BindView(R.id.cl_validPhone)
    ConstraintLayout clValidPhone;
    @BindView(R.id.tv_validPhoneValidCode)
    TextView tvValidPhoneValidCode;
    @BindView(R.id.vi_validPhoneValidCode)
    VerificationCodeEditText viValidPhoneValidCode;
    @BindView(R.id.iv_validPhoneValidCodeDelete)
    ImageView ivValidPhoneValidCodeDelete;
    @BindView(R.id.rl_validPhoneValidCodeDelete)
    RelativeLayout rlValidPhoneValidCodeDelete;
    @BindView(R.id.tv_validPhoneGetValidCode)
    TextView tvValidPhoneGetValidCode;
    @BindView(R.id.tv_validPhoneOr)
    TextView tvValidPhoneOr;
    @BindView(R.id.tv_validPhoneVoice)
    TextView tvValidPhoneVoice;
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
    private Intent mCodeTimerServiceIntent, mCodeTimerServiceVoiceIntent;
    public static final String CODE = "codeValidPhone", VOICECODE = "codeVoiceValidPhone";
    private ValidDialog validDialog;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String phoneNumber) {
        Intent intent = new Intent(activity, ValidPhoneActivity.class);
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
        EventBus.getDefault().post(new EventString(phoneNumber, "validCode"));
        finish();
    }

    @Override
    public void getValidCodeSuccess(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        tvValidPhoneValidCode.setEnabled(false);
        ivValidPhoneVoice.setEnabled(false);
        if (validType == 1) {
            ToastUitl.showShort(R.string.validAlreadySend);
            ivValidPhoneVoice.setImageResource(R.mipmap.voice_grey);
            tvValidPhoneGetValidCode.setTextColor(ContextCompat.getColor(ValidPhoneActivity.this,R.color.color_999));
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceIntent);
            } else {
                startService(mCodeTimerServiceIntent);
            }
        } else {
            ToastUitl.showShort(R.string.voiceAlreadySend);
            tvValidPhoneGetValidCode.setTextColor(ContextCompat.getColor(ValidPhoneActivity.this,R.color.color_999));
            tvValidPhoneVoice.setTextColor(ContextCompat.getColor(ValidPhoneActivity.this,R.color.color_999));
            ivValidPhoneVoice.setVisibility(View.GONE);
            tvValidPhoneVoice.setVisibility(View.VISIBLE);
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
        return R.layout.activity_validphone;
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
        tvValidPhoneNumber.setText(phoneNumber);
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
        setEditViewChangeTextAndFocus();
    }

    private void setEditViewChangeTextAndFocus() {
        viValidPhoneValidCode.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivValidPhoneValidCodeDelete.setVisibility(View.VISIBLE);
                } else {
                    ivValidPhoneValidCodeDelete.setVisibility(View.GONE);
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
            if(isEnable) {
                ivValidPhoneVoice.setVisibility(View.VISIBLE);
                tvValidPhoneVoice.setVisibility(View.GONE);
                ivValidPhoneVoice.setImageResource(R.mipmap.voice_orange);
                tvValidPhoneVoice.setTextColor(ContextCompat.getColor(ValidPhoneActivity.this,R.color.new_main));
                tvValidPhoneGetValidCode.setTextColor(ContextCompat.getColor(ValidPhoneActivity.this,R.color.new_main));
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

    @OnClick({R.id.tv_validPhoneGetValidCode,R.id.tv_validPhoneValidCode, R.id.iv_validPhoneVoice, R.id.iv_changePhoneValidCodeDelete, R.id.btn_validPhoneOK, R.id.tv_validPhoneChangePhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_validPhoneValidCode:
                tvValidPhoneValidCode.setVisibility(View.GONE);
                viValidPhoneValidCode.setVisibility(View.VISIBLE);
                Utils.setIM(viValidPhoneValidCode,this);
                break;
            case R.id.iv_changePhoneValidCodeDelete:
                viValidPhoneValidCode.setText("");
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
            case R.id.tv_validPhoneChangePhone:
                ChangePhoneActivity.startAction(this);
                finish();
                break;
        }
    }

    private void doSendValidCode() {
        tvValidPhoneValidCode.setVisibility(View.GONE);
        viValidPhoneValidCode.setVisibility(View.VISIBLE);
        Utils.setIM(viValidPhoneValidCode,this);
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
        if (viValidPhoneValidCode.getTextString() == null || "".equals(viValidPhoneValidCode.getTextString())) {
            ToastUitl.showShort("请输入手机验证码");
            return;
        }
        mPresenter.validPhone(phoneNumber, viValidPhoneValidCode.getTextString());
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
