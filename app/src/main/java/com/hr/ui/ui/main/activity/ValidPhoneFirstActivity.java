package com.hr.ui.ui.main.activity;

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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
    private String phoneNumber;
    private SharedPreferencesUtils sUtils;
    private PopupWindow popupWindow;
    private int code, validType = 1;
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private Intent mCodeTimerServiceIntent,mCodeTimerServiceVoiceIntent;
    public static final String CODE = "codeValidPhone",VOICECODE="codeVoiceFirst";

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
        tvValidPhoneVoice.setEnabled(false);
        if(validType==1) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceIntent);
            } else {
                startService(mCodeTimerServiceIntent);
            }
        }else{
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(mCodeTimerServiceVoiceIntent);
            } else {
                startService(mCodeTimerServiceVoiceIntent);
            }
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void getCaptchaSuccess(String autoCode) {
        ivAutoCode.setImageBitmap(EncryptUtils.stringtoBitmap(autoCode));
    }

    @Override
    public void getValidCodeFailt() {
        etAutoCode.setText("");
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
                mPresenter.getValidCode(phoneNumber, Constants.VALIDCODE_RESETPHONEORPSW_YTPE, 1, "", validType);
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

        mCodeTimerServiceVoiceIntent=new Intent(this,CodeTimerService.class);
        mCodeTimerServiceVoiceIntent.setAction(VOICECODE);
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);

        IntentFilter filterVoice=new IntentFilter(VOICECODE);
        registerReceiver(mCodeTimerReceiver,filterVoice);
        editTextChangeAndFucos();
    }

    private void editTextChangeAndFucos() {
        Utils.setEditViewTextChangeAndFocus(etValidPhoneNumber, ivValidPhoneNumberDelete);
        viValidPhoneValidCode.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivValidPhoneNumberDelete.setVisibility(android.view.View.VISIBLE);
                }else{
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
            if (CODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                boolean isEnable = intent.getBooleanExtra(CodeTimer.IS_ENABLE, false);
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvValidPhoneGetValidCode.setEnabled(isEnable);
                tvValidPhoneVoice.setEnabled(isEnable);
                tvValidPhoneGetValidCode.setText(message);
            }else if(VOICECODE.equals(action)){
                boolean isEnable = intent.getBooleanExtra(CodeTimer.IS_ENABLE, false);
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvValidPhoneGetValidCode.setEnabled(isEnable);
                tvValidPhoneVoice.setEnabled(isEnable);
                tvValidPhoneVoice.setText(message);
            }
        }
    };

    /**
     * 图形验证码界面   String phoneNumber, String type,int way, String captcha
     */
    public void initPopWindow() {
        final android.view.View popView = LayoutInflater.from(this).inflate(R.layout.layout_autocode, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        ivAutoCode = popView.findViewById(R.id.vc_image);
        TextView tvReflesh = popView.findViewById(R.id.vc_refresh);
        etAutoCode = popView.findViewById(R.id.vc_code);
        RelativeLayout rlConfirm = popView.findViewById(R.id.rl__item_autocode_confirm);
        rlConfirm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String autoCodeText = etAutoCode.getText().toString();
                if (autoCodeText != null && !"".equals(autoCodeText)) {
                    mPresenter.getValidCode(phoneNumber, Constants.VALIDCODE_RESETORVALIDPHONE_YTPE, 1, autoCodeText, validType);
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
        tvReflesh.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                mPresenter.getCaptcha();
            }
        });
        android.view.View rootview = LayoutInflater.from(this).inflate(R.layout.activity_register, null);
        popupWindow.showAtLocation(clValidPhone, Gravity.CENTER, 0, 0);
    }

    @OnClick({R.id.tv_validPhoneGetValidCode,R.id.tv_validPhoneVoice, R.id.btn_validPhoneOK, R.id.iv_validPhoneNumberDelete, R.id.iv_validPhoneValidCodeDelete})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.tv_validPhoneGetValidCode:
                validType=1;
                doSendValidCode();
                break;
            case R.id.tv_validPhoneVoice:
                validType=2;
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
        phoneNumber = viValidPhoneValidCode.getText().toString();
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
        if (viValidPhoneValidCode.getText().toString() == null || "".equals(viValidPhoneValidCode.getText().toString())) {
            ToastUitl.showShort("请填写手机验证码");
            return;
        }
        mPresenter.validPhone(phoneNumber, viValidPhoneValidCode.getText().toString());
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
    }
}
