package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.Base3Activity;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.CompanyRegisterBean;
import com.hr.ui.ui.login.contract.CompanyRegisterContract;
import com.hr.ui.ui.login.model.CompanyRegisterModel;
import com.hr.ui.ui.login.presenter.CompanyRegisterPresenter;
import com.hr.ui.ui.me.activity.VersionActivity;
import com.hr.ui.utils.CodeUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.view.CustomDatePicker;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/2/6.
 */

public class CompanyRegisterActivity extends Base3Activity<CompanyRegisterPresenter, CompanyRegisterModel> implements CompanyRegisterContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_comregister_comname)
    EditText etComregisterComname;
    @BindView(R.id.et_comregister_comphone)
    EditText etComregisterComphone;
    @BindView(R.id.et_comregister_comcontacts)
    EditText etComregisterComcontacts;
    @BindView(R.id.et_comregister_comnemail)
    EditText etComregisterComnemail;
    @BindView(R.id.et_comregister_HowToGet)
    TextView etComregisterHowToGet;
    @BindView(R.id.et_comregister_username)
    EditText etComregisterUsername;
    @BindView(R.id.et_comregister_password)
    EditText etComregisterPassword;
    @BindView(R.id.et_comregister_passwordSure)
    EditText etComregisterPasswordSure;
    @BindView(R.id.et_comregister_aotoPSW)
    EditText etComregisterAotoPSW;
    @BindView(R.id.vc_comRegImage)
    ImageView vcComRegImage;
    @BindView(R.id.vc_comRegRefresh)
    TextView vcComRegRefresh;
    @BindView(R.id.ll_aotucode)
    LinearLayout llAotucode;
    @BindView(R.id.iv_registcom_check)
    ImageView ivRegistcomCheck;
    @BindView(R.id.tv_registcom_agreement)
    TextView tvRegistcomAgreement;
    @BindView(R.id.rl_comregistr_save)
    RelativeLayout rlComregistrSave;
    @BindView(R.id.tv_company_register_phone)
    TextView tvCompanyRegisterPhone;
    @BindView(R.id.tv_howtoget)
    TextView tvHowtoget;
    @BindView(R.id.iv_comregister_comnameDelete)
    ImageView ivComregisterComnameDelete;
    @BindView(R.id.iv_comregister_comphoneDelete)
    ImageView ivComregisterComphoneDelete;
    @BindView(R.id.iv_comregister_comcontactsDelete)
    ImageView ivComregisterComcontactsDelete;
    @BindView(R.id.iv_comregister_comnemailDelete)
    ImageView ivComregisterComnemailDelete;
    @BindView(R.id.iv_comregister_usernameDelete)
    ImageView ivComregisterUsernameDelete;
    @BindView(R.id.iv_comregister_passwordDelete)
    ImageView ivComregisterPasswordDelete;
    @BindView(R.id.iv_comregister_passwordSureDelete)
    ImageView ivComregisterPasswordSureDelete;
    @BindView(R.id.iv_comregister_aotoPSWDelete)
    ImageView ivComregisterAotoPSWDelete;
    @BindView(R.id.iv_comregister_passwordHiddenPsw)
    ImageView ivComregisterPasswordHiddenPsw;
    @BindView(R.id.iv_comregister_passwordSureHiddenPsw)
    ImageView ivComregisterPasswordSureHiddenPsw;
    @BindView(R.id.ll_registerCompany)
    LinearLayout llRegisterCompany;
    @BindView(R.id.tv_comregister_industry)
    TextView tvComregisterIndustry;
    @BindView(R.id.iv_comregister_industryDelete)
    ImageView ivComregisterIndustryDelete;
    @BindView(R.id.rl_comregister_industry)
    RelativeLayout rlComregisterIndustry;
    private View howToGet;
    private PopupWindow popupWindow;
    private String howtoknow;
    private String getCode, industryId;
    private boolean isCheck = true;
    private boolean isHiddenPsw, isHiddenPswSure;
    private CustomDatePicker datePickerInsdustry;
    private List<CityBean> industryList = new ArrayList<>();

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, CompanyRegisterActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_regist;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        MobclickAgent.onEvent(this,"v6_scanCompanyRegister");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.companyRegister);
        vcComRegImage.setImageBitmap(CodeUtils.getInstance().getBitmap());
        getCode = CodeUtils.getInstance().getCode(); // 获取显示的验证码
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        Utils.getConnect();
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        textChangeAndFocusChange();
        initDialog();
    }

    private void initDialog() {
        industryList = FromStringToArrayList.getInstance().getIndustryList();
        datePickerInsdustry = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvComregisterIndustry.setText(time);
                for (int i = 0; i < industryList.size(); i++) {
                    if (industryList.get(i).getName().equals(time)) {
                        industryId = industryList.get(i).getId();

                        break;
                    }
                }
                String name=Utils.getIndustryNUumber(Integer.parseInt(industryId));
                tvCompanyRegisterPhone.setText(name);
            }
        }, getResources().getStringArray(R.array.array_industry_zh2));
    }

    private void textChangeAndFocusChange() {
        Utils.setEditViewTextChangeAndFocus(etComregisterUsername, ivComregisterUsernameDelete);
        Utils.setEditViewTextChangeAndFocus(etComregisterAotoPSW, ivComregisterAotoPSWDelete);
        Utils.setEditViewTextChangeAndFocus(etComregisterComcontacts, ivComregisterComcontactsDelete);
        Utils.setEditViewTextChangeAndFocus(etComregisterPassword, ivComregisterPasswordDelete);
        Utils.setEditViewTextChangeAndFocus(etComregisterPasswordSure, ivComregisterPasswordSureDelete);
        Utils.setEditViewTextChangeAndFocus(etComregisterComname, ivComregisterComnameDelete);
        Utils.setEditViewTextChangeAndFocus(etComregisterComphone, ivComregisterComphoneDelete);
        Utils.setEditViewTextChangeAndFocus(etComregisterComnemail, ivComregisterComnemailDelete);
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
    public void registerCompanySuccess() {
        ToastUitl.showShort("企业注册成功");
        MobclickAgent.onEvent(this,"v6_registerCompany");
        finish();
    }

    @OnClick({R.id.et_comregister_HowToGet,R.id.tv_company_register_phone, R.id.rl_comregister_industry, R.id.iv_comregister_comnameDelete, R.id.iv_comregister_comphoneDelete, R.id.iv_comregister_comcontactsDelete, R.id.iv_comregister_comnemailDelete, R.id.iv_comregister_usernameDelete, R.id.iv_comregister_passwordDelete, R.id.iv_comregister_passwordHiddenPsw, R.id.iv_comregister_passwordSureDelete, R.id.iv_comregister_passwordSureHiddenPsw, R.id.iv_comregister_aotoPSWDelete, R.id.ll_aotucode, R.id.iv_registcom_check, R.id.tv_registcom_agreement, R.id.rl_comregistr_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_company_register_phone:
                MobclickAgent.onEvent(this,"v6_callPhone");
                break;
            case R.id.rl_comregister_industry:
                datePickerInsdustry.show(tvComregisterIndustry.getText().toString());
                break;
            case R.id.et_comregister_HowToGet:
                setFocus();
                initHowToGet();
                break;
            case R.id.ll_aotucode:
                vcComRegImage.setImageBitmap(CodeUtils.getInstance().getBitmap());
                getCode = CodeUtils.getInstance().getCode();
                break;
            case R.id.iv_registcom_check:
                if (isCheck == false) {
                    ivRegistcomCheck.setImageResource(R.mipmap.lv);
                } else {
                    ivRegistcomCheck.setImageResource(R.mipmap.hui);
                }
                isCheck = !isCheck;
                break;
            case R.id.tv_registcom_agreement:
                VersionActivity.startAction(this,"http://m.800hr.com/ent/register/agreement.php?app=true");
                break;
            case R.id.rl_comregistr_save:
                doRegister();
                break;
            case R.id.iv_comregister_comnameDelete:
                etComregisterComname.setText("");
                break;
            case R.id.iv_comregister_comphoneDelete:
                etComregisterComphone.setText("");
                break;
            case R.id.iv_comregister_comcontactsDelete:
                etComregisterComcontacts.setText("");
                break;
            case R.id.iv_comregister_comnemailDelete:
                etComregisterComnemail.setText("");
                break;
            case R.id.iv_comregister_usernameDelete:
                etComregisterUsername.setText("");
                break;
            case R.id.iv_comregister_passwordDelete:
                etComregisterPassword.setText("");
                break;
            case R.id.iv_comregister_passwordHiddenPsw:
                if (isHiddenPsw) {
                    //设置EditText文本为可见的
                    ivComregisterPasswordHiddenPsw.setImageResource(R.mipmap.see);
                    etComregisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivComregisterPasswordHiddenPsw.setImageResource(R.mipmap.hidden);
                    etComregisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHiddenPsw = !isHiddenPsw;
                etComregisterPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etComregisterPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_comregister_passwordSureDelete:
                etComregisterPasswordSure.setText("");
                break;
            case R.id.iv_comregister_passwordSureHiddenPsw:
                if (isHiddenPswSure) {
                    //设置EditText文本为可见的
                    ivComregisterPasswordSureHiddenPsw.setImageResource(R.mipmap.see);
                    etComregisterPasswordSure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivComregisterPasswordSureHiddenPsw.setImageResource(R.mipmap.hidden);
                    etComregisterPasswordSure.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHiddenPswSure = !isHiddenPswSure;
                etComregisterPasswordSure.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence2 = etComregisterPasswordSure.getText();
                if (charSequence2 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence2;
                    Selection.setSelection(spanText, charSequence2.length());
                }
                break;
            case R.id.iv_comregister_aotoPSWDelete:
                etComregisterAotoPSW.setText("");
                break;
        }
    }

    private void setFocus() {
        llRegisterCompany.setFocusableInTouchMode(true);
        llRegisterCompany.setFocusable(true);
        llRegisterCompany.requestFocus();
        llRegisterCompany.findFocus();
    }

    private void doRegister() {
        if(industryId==null||"".equals(industryId)){
            ToastUitl.showShort("请选择行业");
            return;
        }
        if (etComregisterComname.getText().toString() == null || "".equals(etComregisterComname.getText().toString())) {
            ToastUitl.showShort("请填写公司名称");
            return;
        }
        if (etComregisterComphone.getText().toString() == null || "".equals(etComregisterComphone.getText().toString())) {
            ToastUitl.showShort("请填写联系电话");
            return;
        }
        if (RegularExpression.isNumber(etComregisterComphone.getText().toString()) == false) {
            ToastUitl.showShort("请填写正确的联系电话");
            return;
        }
        if (etComregisterComcontacts.getText().toString() == null || "".equals(etComregisterComcontacts.getText().toString())) {
            ToastUitl.showShort("请填写联系人");
            return;
        }
        if (etComregisterComnemail.getText().toString() == null || "".equals(etComregisterComnemail.getText().toString())) {
            ToastUitl.showShort("请填写电子邮箱");
            return;
        }
        if(RegularExpression.isEmail(etComregisterComnemail.getText().toString())==false){
            ToastUitl.showShort("请填写正确的电子邮箱");
            return;
        }
        if ("".equals(howtoknow) || howtoknow == null) {
            ToastUitl.showShort("请选择你获取软件的渠道");
            return;
        }
        if (etComregisterUsername.getText().toString() == null || "".equals(etComregisterUsername.getText().toString())) {
            ToastUitl.showShort("请填写用户名");
            return;
        }
        if(RegularExpression.isUseName(etComregisterUsername.getText().toString())==false){
            ToastUitl.showShort("用户名应为4-25位字母、数字、下划线");
            return;
        }
        if ("".equals(etComregisterPassword.getText().toString()) || etComregisterPassword.getText().toString() == null) {
            ToastUitl.showShort("请填写登录密码");
            return;
        }
        if(RegularExpression.isPsw(etComregisterPassword.getText().toString())==false){
            ToastUitl.showShort("登录密码应为6-25位字母与数字的组合");
            return;
        }
        if ("".equals(etComregisterPasswordSure.getText().toString()) || etComregisterPasswordSure.getText().toString() == null) {
            ToastUitl.showShort("请填写确认密码");
            return;
        }
        if(RegularExpression.isPsw(etComregisterPasswordSure.getText().toString())==false){
            ToastUitl.showShort("确认密码应为6-25位字母与数字的组合");
            return;
        }
        if (!etComregisterPasswordSure.getText().toString().equals(etComregisterPassword.getText().toString())) {
            ToastUitl.showShort("登录密码和确认密码不一致");
            return;
        }
        if (etComregisterAotoPSW.getText().toString() == null || "".equals(etComregisterAotoPSW.getText().toString())) {
            ToastUitl.showShort("请填写验证码");
            return;
        }
        if (!etComregisterAotoPSW.getText().toString().equalsIgnoreCase(getCode)) {
            ToastUitl.showShort("请填写正确的验证码");
            return;
        }
        if (isCheck == false) {
            ToastUitl.showShort("请同意英才网联企业会员注册协议");
            return;
        }
        CompanyRegisterBean companyRegisterBean = new CompanyRegisterBean();
        companyRegisterBean.setSiteCode(industryId);
        companyRegisterBean.setEmail(etComregisterComnemail.getText().toString());
        companyRegisterBean.setHowToknow(howtoknow);
        companyRegisterBean.setUserName(etComregisterUsername.getText().toString());
        companyRegisterBean.setLinkMan(etComregisterComcontacts.getText().toString());
        companyRegisterBean.setPassword(etComregisterPassword.getText().toString());
        companyRegisterBean.setPasswordRe(etComregisterPasswordSure.getText().toString());
        companyRegisterBean.setEnterpriseName(etComregisterComname.getText().toString());
        companyRegisterBean.setPhone(etComregisterComphone.getText().toString());
        mPresenter.registerCompany(companyRegisterBean);
    }

    private void initHowToGet() {

        howToGet = LayoutInflater.from(this).inflate(R.layout.howtoget, null);
        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(howToGet);
        ViewGroup.LayoutParams params = etComregisterHowToGet.getLayoutParams();
        int wid = params.width;
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(wid);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
        //设置弹出窗体需要软键盘，
        int[] location = new int[2];
        tvHowtoget.getLocationOnScreen(location);
        //popupWindow.showAsDropDown(etComregisterHowToGet, width - etComregisterHowToGet.getWidth(), 0);
        popupWindow.showAtLocation(tvHowtoget, Gravity.NO_GRAVITY, location[0] + tvHowtoget.getWidth(), location[1] + tvHowtoget.getHeight());
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 设置弹窗外可点击，默认为false
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final TextView tv_pleaseSelect = (TextView) howToGet.findViewById(R.id.tv_pleaseSelect);
        final TextView tv_sellerIntro = (TextView) howToGet.findViewById(R.id.tv_sellerIntro);
        final TextView tv_friendIntro = (TextView) howToGet.findViewById(R.id.tv_friendIntro);
        final TextView tv_internetInro = (TextView) howToGet.findViewById(R.id.tv_internetIntro);
        final TextView tv_otherIntro = (TextView) howToGet.findViewById(R.id.tv_otherIntro);
        tv_friendIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howtoknow = "2";
                etComregisterHowToGet.setText(tv_friendIntro.getText().toString().trim());
                popupWindow.dismiss();
            }
        });
        tv_internetInro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howtoknow = "3";
                etComregisterHowToGet.setText(tv_internetInro.getText().toString().trim());
                popupWindow.dismiss();
            }
        });
        tv_otherIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howtoknow = "4";
                etComregisterHowToGet.setText(tv_otherIntro.getText().toString().trim());
                popupWindow.dismiss();
            }
        });
        tv_pleaseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howtoknow = "";
                etComregisterHowToGet.setText(tv_pleaseSelect.getText().toString().trim());
                popupWindow.dismiss();
            }
        });
        tv_sellerIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howtoknow = "1";
                etComregisterHowToGet.setText(tv_sellerIntro.getText().toString().trim());
                popupWindow.dismiss();
            }
        });
    }
}
