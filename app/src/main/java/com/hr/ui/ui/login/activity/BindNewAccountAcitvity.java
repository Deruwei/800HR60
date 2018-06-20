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
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.db.ThirdPartDao;
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
import com.service.CodeTimerService;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/8.
 */

public class BindNewAccountAcitvity extends BaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_bindNewAccountNumber)
    EditText etBindNewAccountNumber;
    @BindView(R.id.et_bindNewAccountPsw)
    EditText etBindNewAccountPsw;
    @BindView(R.id.tv_bindNewAccountGetValidCode)
    TextView tvBindNewAccountGetValidCode;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_bindNewAccountNumberIcon)
    ImageView ivBindNewAccountNumberIcon;
    @BindView(R.id.iv_bindNewAccountNumberDelete)
    ImageView ivBindNewAccountNumberDelete;
    @BindView(R.id.iv_bindNewAccountValidCodeIcon)
    ImageView ivBindNewAccountValidCodeIcon;
    @BindView(R.id.iv_bindNewAccountGetValidCodeDelete)
    ImageView ivBindNewAccountGetValidCodeDelete;
    @BindView(R.id.View_validCode3)
    View ViewValidCode3;
    @BindView(R.id.iv_bindNewAccountPswIcon)
    ImageView ivBindNewAccountPswIcon;
    @BindView(R.id.iv_bindNewAccountPswDelete)
    ImageView ivBindNewAccountPswDelete;
    @BindView(R.id.btn_bindNewAccountOK)
    Button btnBindNewAccountOK;
    @BindView(R.id.tv_bindNewAccountFindPsw)
    TextView tvBindNewAccountFindPsw;
    @BindView(R.id.iv_bindNewAccountHiddenPsw)
    ImageView ivBindNewAccountHiddenPsw;
    @BindView(R.id.rl_bindNewAccountHiddenPsw)
    RelativeLayout rlBindNewAccountHiddenPsw;
    @BindView(R.id.tv_bindNewAccountPhone)
    TextView tvBindNewAccountPhone;
    @BindView(R.id.cl_bindNewPhone)
    ConstraintLayout clBindNewPhone;
    @BindView(R.id.tv_bindPhoneAccountValidCode)
    EditText tvBindPhoneAccountValidCode;
    @BindView(R.id.vi_bindPhoneAccountValidCode)
    VerificationCodeEditText viBindPhoneAccountValidCode;
    @BindView(R.id.rl_bindNewAccountGetValidCodeDelete)
    RelativeLayout rlBindNewAccountGetValidCodeDelete;
    @BindView(R.id.tv_bindNewAccountOr)
    TextView tvBindNewAccountOr;
    @BindView(R.id.tv_bindNewAccountVoice)
    TextView tvBindNewAccountVoice;
    private SharedPreferencesUtils sUtils;
    private PopupWindow popupWindow;
    private String autoCode;
    private Intent mCodeTimerServiceIntent,mCodeTimerServiceVoiceIntent;
    public static final String CODE = "codeBind",VOICECODE="codeVoiceBind";
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private String uid;
    private int type;
    private String validCode;
    private boolean isHidden = true;
    private int code;
    private String phoneNumber, password;
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId, validType = 1;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, BindNewAccountAcitvity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bindnewaccount;
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
        tvToolbarTitle.setText(R.string.bindNewAccount);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    public void sendValidCode(int code) {
        this.code = code;
        sUtils.setIntValue("code", code);
        tvBindNewAccountGetValidCode.setEnabled(false);
        tvBindNewAccountVoice.setEnabled(false);
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
    public void sendAutoCode(String autoCode) {
        this.autoCode = autoCode;
        ivAutoCode.setImageBitmap(EncryptUtils.stringtoBitmap(autoCode));
    }

    @Override
    public void sendRegisterSuccess(int userId) {
        MobclickAgent.onEvent(this, "v6_register_thirdPart");
        ThirdLoginBean thirdPartBean = new ThirdLoginBean();
        List<ThirdLoginBean> thirdPartBeanList = ThirdPartDao.queryThirdPart(Constants.TYPE_THIRDPARTLOGIN);
        // List<ThirdLoginBean> thirdPartBeanList= HRApplication.getDaoSession().getThirdLoginBeanDao().queryBuilder().where(ThirdPartBeanDao.Properties.Type.eq(Constants.TYPE_THIRDPARTLOGIN)).list();
        for (int i = 0; i < thirdPartBeanList.size(); i++) {
            if (thirdPartBeanList.get(i).getType().equals(Constants.TYPE_THIRDPARTLOGIN)) {
                thirdPartBean = thirdPartBeanList.get(i);
                uid = thirdPartBean.getUId();
                thirdPartBean.setSUId(userId + "");
                break;
            }
        }
        // System.out.println("hello" + thirdPartBean.toString());
        mPresenter.getThidBinding(thirdPartBean, phoneNumber, password, 0);
    }

    @Override
    public void bindingSuccess(int userId) {
        MobclickAgent.onEvent(this, "v6_login_thirdPart");
        MobclickAgent.onProfileSignIn("WB", userId + "");
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        LoginBean loginBean = new LoginBean();
        if ("qq".equals(Constants.TYPE_THIRDPARTLOGIN)) {
            loginBean.setLoginType(2);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 2);
        } else {
            loginBean.setLoginType(3);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 3);
        }
        sUtils.setStringValue(Constants.USERID, userId + "");
        loginBean.setName(phoneNumber);
        loginBean.setPassword(password);
        loginBean.setThirdPartUid(uid);
        loginBean.setThirdPartLoginType(Constants.TYPE_THIRDPARTLOGIN);
        loginBean.setThirdPartSUid(userId + "");
        LoginDBUtils.insertData(loginBean);
        this.userId = userId;
        mPresenter.getResumeList();
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
        etAutoCode.setText("");
        mPresenter.getAutoCode();
    }

    @Override
    public void phoneIsExit(String flag) {
        if ("1".equals(flag)) {
            ToastUitl.showShort(R.string.error_327);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //验证码计时器服务
        mCodeTimerServiceIntent = new Intent(this, CodeTimerService.class);
        mCodeTimerServiceIntent.setAction(CODE);

        mCodeTimerServiceVoiceIntent=new Intent(this,CodeTimerService.class);
        mCodeTimerServiceVoiceIntent.setAction(VOICECODE);
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);

        IntentFilter filterVoice=new IntentFilter(VOICECODE);
        registerReceiver(mCodeTimerReceiver,filterVoice);
        onEditViewTextChangeAndFocusChange();
    }

    private void onEditViewTextChangeAndFocusChange() {
        Utils.setEditViewTextChangeAndFocus(etBindNewAccountNumber,ivBindNewAccountNumberDelete);
        Utils.setEditViewTextChangeAndFocus(etBindNewAccountPsw,ivBindNewAccountPswDelete);
        viBindPhoneAccountValidCode.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivBindNewAccountGetValidCodeDelete.setVisibility(View.VISIBLE);
                }else{
                    ivBindNewAccountGetValidCodeDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onInputCompleted(CharSequence s) {
                validCode=s.toString();
            }
        });
    }

    @OnClick({R.id.tv_bindNewAccountGetValidCode, R.id.tv_bindNewAccountPhone,R.id.tv_bindNewAccountVoice, R.id.rl_bindNewAccountHiddenPsw, R.id.iv_bindNewAccountNumberDelete, R.id.iv_bindNewAccountPswDelete, R.id.iv_bindNewAccountGetValidCodeDelete, R.id.btn_bindNewAccountOK, R.id.tv_bindNewAccountFindPsw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bindNewAccountHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivBindNewAccountHiddenPsw.setImageResource(R.mipmap.see);
                    etBindNewAccountPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivBindNewAccountHiddenPsw.setImageResource(R.mipmap.hidden);
                    etBindNewAccountPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etBindNewAccountPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etBindNewAccountPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_bindNewAccountNumberDelete:
                etBindNewAccountNumber.setText("");
                break;
            case R.id.iv_bindNewAccountPswDelete:
                etBindNewAccountPsw.setText("");
                break;
            case R.id.iv_bindNewAccountGetValidCodeDelete:
                viBindPhoneAccountValidCode.setText("");
                break;
            case R.id.tv_bindNewAccountGetValidCode:
               validType=1;
               doSendValidCode();
                break;
            case R.id.tv_bindNewAccountVoice:
                validType=2;
                doSendValidCode();
                break;
            case R.id.btn_bindNewAccountOK:
                type = 0;
                doRegister();
                break;
            case R.id.tv_bindNewAccountPhone:
                BindPhoneLoginActivity.startAction(this);
                break;
            case R.id.tv_bindNewAccountFindPsw:
                bindUserLoginActivity.startAction(this);
                break;
        }
    }

    private void doSendValidCode() {
        tvBindPhoneAccountValidCode.setVisibility(View.GONE);
        viBindPhoneAccountValidCode.setVisibility(View.VISIBLE);
        type = 1;
        String phoneNumber1 = etBindNewAccountNumber.getText().toString();
        if (!"".equals(phoneNumber1) && phoneNumber1 != null) {
            if (RegularExpression.isCellphone(phoneNumber1)) {
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
        phoneNumber = etBindNewAccountNumber.getText().toString();
        validCode = viBindPhoneAccountValidCode.getText().toString();
        password = etBindNewAccountPsw.getText().toString();

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
                tvBindNewAccountGetValidCode.setEnabled(isEnable);
                tvBindNewAccountGetValidCode.setText(message);
            }else if(VOICECODE.equals(action)){
                boolean isEnable = intent.getBooleanExtra(CodeTimer.IS_ENABLE, false);
                String message = intent.getStringExtra(CodeTimer.MESSAGE);
                tvBindNewAccountGetValidCode.setEnabled(isEnable);
                tvBindNewAccountVoice.setEnabled(isEnable);
                tvBindNewAccountVoice.setText(message);
            }
        }
    };

    /**
     * 图形验证码界面
     */
    public void initPopWindow() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.layout_autocode, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        ivAutoCode = popView.findViewById(R.id.vc_image);
        TextView tvReflesh = popView.findViewById(R.id.vc_refresh);
        etAutoCode = popView.findViewById(R.id.vc_code);
        RelativeLayout rlConfirm = popView.findViewById(R.id.rl__item_autocode_confirm);
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autoCodeText = etAutoCode.getText().toString();
                if (autoCodeText != null && !"".equals(autoCodeText)) {
                    mPresenter.getValidCode(etBindNewAccountNumber.getText().toString(), etAutoCode.getText().toString(), 1, Constants.VALIDCODE_REGISTER_YTPE, validType);
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
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_register, null);
        popupWindow.showAtLocation(clBindNewPhone, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mCodeTimerServiceIntent);
        stopService(mCodeTimerServiceVoiceIntent);
        unregisterReceiver(mCodeTimerReceiver);
    }
}
