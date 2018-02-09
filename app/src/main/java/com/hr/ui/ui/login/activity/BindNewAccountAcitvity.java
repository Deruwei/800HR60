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
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
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
    @BindView(R.id.et_bindPhoneAccountValidCode)
    EditText etBindPhoneAccountValidCode;
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
    private SharedPreferencesUtils sUtils;
    private PopupWindow popupWindow;
    private String autoCode;
    private Intent mCodeTimerServiceIntent;
    public static final String CODE = "codeBind";
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
    private int userId;

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
        startService(mCodeTimerServiceIntent);//启动服务
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void sendAutoCode(String autoCode) {
        initPopWindow();
        ivAutoCode.setImageBitmap(EncryptUtils.stringtoBitmap(autoCode));

    }

    @Override
    public void sendRegisterSuccess(int userId) {
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
        MobclickAgent.onProfileSignIn("WB",userId+"");
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        LoginBean loginBean = new LoginBean();
        if ("QQ".equals(Constants.TYPE_THIRDPARTLOGIN)) {
            loginBean.setLoginType(2);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 2);
        } else {
            loginBean.setLoginType(3);
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 3);
        }
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
                    mPresenter.getAutoCode();
                } else {
                    mPresenter.getValidCode(phoneNumber, "", 0, Constants.VALIDCODE_REGISTER_YTPE);
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
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);
        onEditViewTextChangeAndFocusChange();
    }

    private void onEditViewTextChangeAndFocusChange() {
        etBindNewAccountNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivBindNewAccountNumberDelete.setVisibility(View.GONE);
                } else {
                    if (etBindNewAccountNumber.getText().toString() != null && !"".equals(etBindNewAccountNumber.getText().toString())) {
                        ivBindNewAccountNumberDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etBindNewAccountPsw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivBindNewAccountPswDelete.setVisibility(View.GONE);
                } else {
                    if (etBindNewAccountPsw.getText().toString() != null && !"".equals(etBindNewAccountPsw.getText().toString())) {
                        ivBindNewAccountPswDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etBindPhoneAccountValidCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivBindNewAccountGetValidCodeDelete.setVisibility(View.GONE);
                } else {
                    if (etBindPhoneAccountValidCode.getText().toString() != null && !"".equals(etBindPhoneAccountValidCode.getText().toString())) {
                        ivBindNewAccountGetValidCodeDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etBindNewAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivBindNewAccountNumberDelete.setVisibility(View.VISIBLE);
                } else {
                    ivBindNewAccountNumberDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etBindNewAccountPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivBindNewAccountPswDelete.setVisibility(View.VISIBLE);
                } else {
                    ivBindNewAccountPswDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etBindPhoneAccountValidCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivBindNewAccountGetValidCodeDelete.setVisibility(View.VISIBLE);
                } else {
                    ivBindNewAccountGetValidCodeDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tv_bindNewAccountGetValidCode,R.id.tv_bindNewAccountPhone, R.id.rl_bindNewAccountHiddenPsw, R.id.iv_bindNewAccountNumberDelete, R.id.iv_bindNewAccountPswDelete, R.id.iv_bindNewAccountGetValidCodeDelete, R.id.btn_bindNewAccountOK, R.id.tv_bindNewAccountFindPsw})
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
                etBindPhoneAccountValidCode.setText("");
                break;
            case R.id.tv_bindNewAccountGetValidCode:
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

    private void doRegister() {
        phoneNumber = etBindNewAccountNumber.getText().toString();
        validCode = etBindPhoneAccountValidCode.getText().toString();
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
            }
        }
    };

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
                    mPresenter.getValidCode(etBindNewAccountNumber.getText().toString(), etAutoCode.getText().toString(), 1, Constants.VALIDCODE_REGISTER_YTPE);
                } else {
                    ToastUitl.show("请填写图形验证码", Toast.LENGTH_SHORT);
                }
            }
        });
        tvReflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAutoCode();
            }
        });
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_register, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mCodeTimerServiceIntent);
        unregisterReceiver(mCodeTimerReceiver);
    }
}
