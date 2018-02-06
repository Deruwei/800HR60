package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.me.contract.ValidPhoneContract;
import com.hr.ui.ui.me.model.ValidPhoneModel;
import com.hr.ui.ui.me.presenter.ValidPhonePresenter;
import com.hr.ui.ui.resume.activity.ResumePersonalInfoActivity;
import com.hr.ui.utils.CodeTimer;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.service.CodeTimerService;

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
    @BindView(R.id.et_validPhoneValidCode)
    EditText etValidPhoneValidCode;
    @BindView(R.id.tv_validPhoneValidCode)
    TextView tvValidPhoneValidCode;
    @BindView(R.id.view_getValidCodeValid)
    View viewGetValidCodeValid;
    @BindView(R.id.btn_validPhoneOK)
    Button btnValidPhoneOK;
    @BindView(R.id.tv_validPhoneChangePhone)
    TextView tvValidPhoneChangePhone;
    private String phoneNumber;
    private SharedPreferencesUtils sUtils;
    private PopupWindow popupWindow;
    private int code;
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private Intent mCodeTimerServiceIntent;
    public static final String CODE = "codeValidPhone";

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
        ResumePersonalInfoActivity.instance.setValid(phoneNumber);
        finish();
    }

    @Override
    public void getValidCodeSuccess(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        tvValidPhoneValidCode.setEnabled(false);
        startService(mCodeTimerServiceIntent);//启动服务
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void getCaptchaSuccess(String autoCode) {
        ivAutoCode.setImageBitmap(EncryptUtils.stringtoBitmap(autoCode));
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
        sUtils=new SharedPreferencesUtils(this);
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
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);
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
                tvValidPhoneValidCode.setEnabled(isEnable);
                tvValidPhoneValidCode.setText(message);
            }
        }
    };

    /**
     * 图形验证码界面   String phoneNumber, String type,int way, String captcha
     */
    public void initPopWindow() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.layout_autocode, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(true);
        ivAutoCode = popView.findViewById(R.id.vc_image);
        TextView tvReflesh = popView.findViewById(R.id.vc_refresh);
        etAutoCode = popView.findViewById(R.id.vc_code);
        RelativeLayout rlConfirm = popView.findViewById(R.id.rl__item_autocode_confirm);
        LinearLayout llClose = popView.findViewById(R.id.ll_autoCodeClose);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autoCodeText = etAutoCode.getText().toString();
                if (autoCodeText != null && !"".equals(autoCodeText)) {
                    mPresenter.getValidCode(phoneNumber, Constants.VALIDCODE_RESETORVALIDPHONE_YTPE, 1, autoCodeText);
                } else {
                    ToastUitl.show("请填写图形验证码", Toast.LENGTH_SHORT);
                }
            }
        });
        tvReflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCaptcha();
            }
        });
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_register, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    @OnClick({R.id.tv_validPhoneValidCode, R.id.btn_validPhoneOK, R.id.tv_validPhoneChangePhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_validPhoneValidCode:
                if (!"".equals(phoneNumber) && phoneNumber != null) {
                    if (RegularExpression.isCellphone(phoneNumber)) {
                        if (sUtils.getIntValue("code", 0) >= 1) {
                            mPresenter.getCaptcha();
                            initPopWindow();
                        } else {
                            mPresenter.getValidCode(phoneNumber, Constants.VALIDCODE_RESETPHONEORPSW_YTPE, 1,"");
                        }
                    } else {
                        ToastUitl.show("请输入正确的手机号码", Toast.LENGTH_SHORT);
                    }
                } else {
                    ToastUitl.show("请输入手机号码", Toast.LENGTH_SHORT);
                }
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

    private void doValidCode() {
        if(etValidPhoneValidCode.getText().toString()==null||"".equals(etValidPhoneValidCode.getText().toString())){
            ToastUitl.showShort("请填写手机验证码");
            return;
        }
        mPresenter.validPhone(phoneNumber,etValidPhoneValidCode.getText().toString());
    }
}
