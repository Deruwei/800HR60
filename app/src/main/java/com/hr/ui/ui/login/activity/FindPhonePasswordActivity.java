package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.hr.ui.ui.login.contract.FindPasswordContract;
import com.hr.ui.ui.login.model.FindPasswordModel;
import com.hr.ui.ui.login.presenter.FindPasswordPresenter;
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
 * Created by wdr on 2017/12/6.
 */

public class FindPhonePasswordActivity extends BaseActivity<FindPasswordPresenter, FindPasswordModel> implements FindPasswordContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_findPhonePswNumber)
    EditText etFindPhonePswNumber;
    @BindView(R.id.et_findPhonePswValidCode)
    EditText etFindPhonePswValidCode;
    @BindView(R.id.et_findPhonePswNew)
    EditText etFindPhonePswNew;
    @BindView(R.id.tv_findPhonePswValidCode)
    TextView tvFindPhonePswValidCode;
    @BindView(R.id.iv_findPhonePswHiddenPsw)
    ImageView ivFindPhonePswHiddenPsw;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_findPhonePswNumberIcon)
    ImageView ivFindPhonePswNumberIcon;
    @BindView(R.id.iv_findPhonePswNumberDelete)
    ImageView ivFindPhonePswNumberDelete;
    @BindView(R.id.iv_findPhonePswValidCodeIcon)
    ImageView ivFindPhonePswValidCodeIcon;
    @BindView(R.id.iv_findPhonePswValidCodeDelete)
    ImageView ivFindPhonePswValidCodeDelete;
    @BindView(R.id.view_getValidCode2)
    View viewGetValidCode2;
    @BindView(R.id.iv_findPhonePswNewIcon)
    ImageView ivFindPhonePswNewIcon;
    @BindView(R.id.iv_findPhonePswDelete)
    ImageView ivFindPhonePswDelete;
    @BindView(R.id.rl_findPhonePswHiddenPsw)
    RelativeLayout rlFindPhonePswHiddenPsw;
    @BindView(R.id.btn_findPhonePswOK)
    Button btnFindPhonePswOK;
    //密码是否隐藏
    private boolean isHidden = true;
    private int code;
    private PopupWindow popupWindow;
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private Intent mCodeTimerServiceIntent;
    public static final String CODE = "code";
    private SharedPreferencesUtils sUtils;
    private int type;//0 表示修改密码  1表示获取验证码
    private String phoneNumber,password,validCode;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, FindPhonePasswordActivity.class);
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
    public int getLayoutId() {
        return R.layout.activity_findphonepsw;
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
        tvToolbarTitle.setText(R.string.findPsw);
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
        onEditViewTextChangeAndFocusChange();
    }

    private void onEditViewTextChangeAndFocusChange() {
        etFindPhonePswValidCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivFindPhonePswValidCodeDelete.setVisibility(View.GONE);
                }else{
                    if(etFindPhonePswValidCode.getText().toString()!=null&&!"".equals(etFindPhonePswValidCode.getText().toString())){
                        ivFindPhonePswValidCodeDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etFindPhonePswNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivFindPhonePswNumberDelete.setVisibility(View.GONE);
                }else{
                    if(etFindPhonePswNumber.getText().toString()!=null&&!"".equals(etFindPhonePswNumber.getText().toString())){
                        ivFindPhonePswNumberDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etFindPhonePswNew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivFindPhonePswDelete.setVisibility(View.GONE);
                }else{
                    if(etFindPhonePswNew.getText().toString()!=null&&!"".equals(etFindPhonePswNew.getText().toString())){
                        ivFindPhonePswDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etFindPhonePswNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivFindPhonePswDelete.setVisibility(View.VISIBLE);
                }else{
                    ivFindPhonePswDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFindPhonePswNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivFindPhonePswNumberDelete.setVisibility(View.VISIBLE);
                }else{
                    ivFindPhonePswNumberDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFindPhonePswValidCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivFindPhonePswValidCodeDelete.setVisibility(View.VISIBLE);
                }else{
                    ivFindPhonePswValidCodeDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tv_findPhonePswValidCode, R.id.iv_findPhonePswDelete, R.id.iv_findPhonePswNumberDelete, R.id.iv_findPhonePswValidCodeDelete, R.id.btn_findPhonePswOK, R.id.rl_findPhonePswHiddenPsw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_findPhonePswDelete:
                etFindPhonePswNew.setText("");
                break;
            case R.id.iv_findPhonePswNumberDelete:
                etFindPhonePswNumber.setText("");
                break;
            case R.id.iv_findPhonePswValidCodeDelete:
                etFindPhonePswValidCode.setText("");
                break;
            case R.id.tv_findPhonePswValidCode:
                type=1;
               phoneNumber = etFindPhonePswNumber.getText().toString();
                if (!"".equals(phoneNumber) && phoneNumber != null) {
                    if (RegularExpression.isCellphone(phoneNumber)) {
                       mPresenter.validPhoneIsExit(phoneNumber);
                    } else {
                        ToastUitl.show("请输入正确的手机号码", Toast.LENGTH_SHORT);
                    }
                } else {
                    ToastUitl.show("请输入手机号码", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.btn_findPhonePswOK:
                type=0;
                doFindPsw();
                break;
            case R.id.rl_findPhonePswHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivFindPhonePswHiddenPsw.setImageResource(R.mipmap.see);
                    etFindPhonePswNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivFindPhonePswHiddenPsw.setImageResource(R.mipmap.hidden);
                    etFindPhonePswNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etFindPhonePswNew.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etFindPhonePswNew.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
        }
    }

    private void doFindPsw() {
         phoneNumber = etFindPhonePswNumber.getText().toString();
         validCode = etFindPhonePswValidCode.getText().toString();
         password = etFindPhonePswNew.getText().toString();

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
        mPresenter.validPhoneIsExit(phoneNumber);
    }

    @Override
    public void getValidCodeSuccess(int tokenTimes) {
        this.code = tokenTimes;
        sUtils.setIntValue("code", tokenTimes);
        tvFindPhonePswValidCode.setEnabled(false);
        startService(mCodeTimerServiceIntent);//启动服务
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void resetPasswordSuccess() {
        finish();
    }

    @Override
    public void sendAutoCode(String autoCode) {
        initPopWindow();
        ivAutoCode.setImageBitmap(EncryptUtils.stringtoBitmap(autoCode));
    }

    @Override
    public void setNeedAotuCode() {
        mPresenter.getAutoCode();
    }

    @Override
    public void phoneisExit(String flag) {
        if("0".equals(flag)){
            ToastUitl.showShort(R.string.error_phoneNotExit);
            return;
        }else{
            if(type==0){
                mPresenter.resetPhonePsw(phoneNumber, validCode, password);
            }else{
                if (sUtils.getIntValue("code", 0) >= 0) {
                    mPresenter.getAutoCode();
                    //Log.i("1", "2");
                } else {
                    mPresenter.getValidCode(phoneNumber, "", 0, Constants.VALIDCODE_FINDPSW_YTPE);
                    // Log.i("1", "3");
                }
            }
        }
    }

    /**
     * 图形验证码界面
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
                    mPresenter.getValidCode(etFindPhonePswNumber.getText().toString(), autoCodeText, 1, Constants.VALIDCODE_FINDPSW_YTPE);
                } else {
                    ToastUitl.show("请填写图形验证码", Toast.LENGTH_SHORT);
                }
            }
        });
        tvReflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPresenter.;
                mPresenter.getAutoCode();
            }
        });
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_register, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
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
                tvFindPhonePswValidCode.setEnabled(isEnable);
                tvFindPhonePswValidCode.setText(message);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mCodeTimerServiceIntent);
        unregisterReceiver(mCodeTimerReceiver);
    }
}
