package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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
import com.hr.ui.ui.login.contract.RegisterContract;
import com.hr.ui.ui.login.model.RegisterModel;
import com.hr.ui.ui.login.presenter.RegisterPresenter;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.utils.CodeTimer;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.TimeCount;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.service.CodeTimerService;

import java.security.spec.PSSParameterSpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/5.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_phoneRegisterNumber)
    EditText etPhoneRegisterNumber;
    @BindView(R.id.et_phoneRegisterValidCode)
    EditText etPhoneRegisterValidCode;
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
    private SharedPreferencesUtils sUtils;
    private PopupWindow popupWindow;
    private String autoCode;
    private Intent mCodeTimerServiceIntent;
    public static final String CODE = "code";
    private ImageView ivAutoCode;
    private EditText etAutoCode;
    private int code;
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
       this.code=code;
       sUtils.setIntValue("code",code);
        tvPhoneRegisterGetValidCode.setEnabled(false);
        startService(mCodeTimerServiceIntent);//启动服务
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }

    @Override
    public void sendAutoCode(String autoCode) {
        ivAutoCode.setImageBitmap(EncryptUtils.stringtoBitmap(autoCode));
    }

    @Override
    public void sendRegisterSuccess(int userId) {
       MainActivity.startAction(this,userId);
       finish();
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
        sUtils=new SharedPreferencesUtils(this);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.register);
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
    }

    @OnClick({R.id.tv_phoneRegisterGetValidCode, R.id.btn_phoneRegisterOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phoneRegisterGetValidCode:
                String phoneNumber = etPhoneRegisterNumber.getText().toString();
                if (!"".equals(phoneNumber) && phoneNumber != null) {
                    if (RegularExpression.isCellphone(phoneNumber)){
                            if(sUtils.getIntValue("code",0)>=1){
                                mPresenter.getAutoCode();
                                initPopWindow();
                            } else {
                                 mPresenter.getValidCode(phoneNumber, "", 0,Constants.VALIDCODE_REGISTER_YTPE);
                            }
                    } else {
                        ToastUitl.show("请输入正确的手机号码", Toast.LENGTH_SHORT);
                    }
                } else {
                    ToastUitl.show("请输入手机号码", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.btn_phoneRegisterOK:
                doRegister();
                break;
        }
    }

    private void doRegister() {
        String phoneNumber=etPhoneRegisterNumber.getText().toString();
        String validCode=etPhoneRegisterValidCode.getText().toString();
        String password=etPhoneRegisterPsw.getText().toString();

        if ("".equals(phoneNumber) || phoneNumber == null) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if (RegularExpression.isCellphone(phoneNumber)==false){
            ToastUitl.showShort("请输入正确的手机号码");
            return;
        }
        if("".equals(validCode)||validCode==null){
            ToastUitl.showShort("请输入验证码");
            return;
        }
        if("".equals(password)||password==null){
            ToastUitl.showShort("请输入密码");
            return;
        }
        if(password.length()<6||password.length()>16){
            ToastUitl.showShort("请输入长度为6-16位的密码");
            return;
        }
        mPresenter.getRegister(phoneNumber,validCode,password);
    }

    /**
     * 图形验证码界面
     */
    public  void initPopWindow(){
        final View popView = LayoutInflater.from(this).inflate(R.layout.layout_autocode, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(true);
        ivAutoCode=popView.findViewById(R.id.vc_image);
       TextView tvReflesh=popView.findViewById(R.id.vc_refresh);
         etAutoCode=popView.findViewById(R.id.vc_code);
        RelativeLayout rlConfirm=popView.findViewById(R.id.rl__item_autocode_confirm);
        LinearLayout llClose=popView.findViewById(R.id.ll_autoCodeClose);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autoCodeText=etAutoCode.getText().toString();
                if(autoCodeText!=null&&!"".equals(autoCodeText)) {
                    mPresenter.getValidCode(etPhoneRegisterNumber.getText().toString(), etAutoCode.getText().toString(),1,Constants.VALIDCODE_REGISTER_YTPE);
                }else{
                    ToastUitl.show("请填写图形验证码",Toast.LENGTH_SHORT);
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
                tvPhoneRegisterGetValidCode.setEnabled(isEnable);
                tvPhoneRegisterGetValidCode.setText(message);
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
