package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.ui.main.contract.PersonalInformationContract;
import com.hr.ui.ui.main.modle.PersonalInformationModel;
import com.hr.ui.ui.main.presenter.PersonalInformationPresenter;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyCustomDatePicker;
import com.hr.ui.view.MyTextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/13.
 */

public class PersonalInformationActivity extends BaseActivity<PersonalInformationPresenter, PersonalInformationModel> implements PersonalInformationContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_personalInformationImage)
    ImageView ivPersonalInformationImage;
    @BindView(R.id.tv_nameTag)
    MyTextView tvNameTag;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_nameDelete)
    ImageView ivNameDelete;
    @BindView(R.id.tv_sexTag)
    MyTextView tvSexTag;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.iv_sexSelect)
    ImageView ivSexSelect;
    @BindView(R.id.tv_birthTag)
    MyTextView tvBirthTag;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_birthSelect)
    ImageView tvBirthSelect;
    @BindView(R.id.tv_livePlaceTag)
    MyTextView tvLivePlaceTag;
    @BindView(R.id.tv_livePlace)
    TextView tvLivePlace;
    @BindView(R.id.tv_livePlaceSelect)
    ImageView tvLivePlaceSelect;
    @BindView(R.id.tv_workTimeTag)
    MyTextView tvWorkTimeTag;
    @BindView(R.id.tv_workTime)
    TextView tvWorkTime;
    @BindView(R.id.tv_workTimeSelect)
    ImageView tvWorkTimeSelect;
    @BindView(R.id.tv_emailTag)
    MyTextView tvEmailTag;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.tv_emailDelete)
    ImageView tvEmailDelete;
    @BindView(R.id.tv_positionTitleTag)
    MyTextView tvPositionTitleTag;
    @BindView(R.id.tv_positionTitle)
    TextView tvPositionTitle;
    @BindView(R.id.tv_positionTitleSelect)
    ImageView tvPositionTitleSelect;
    @BindView(R.id.btn_next)
    Button btnNext;
    private CustomDatePicker customDatePickerJobTitle,datePickerSex,datePickerStartJobTime;
    private MyCustomDatePicker datePickerBirth;
    private String jobTitleId,sex,bitrh,cityId,workTime;
    public static PersonalInformationActivity instance;
    public static final String TAG=PersonalInformationActivity.class.getSimpleName();

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, PersonalInformationActivity.class);
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
    public void sendInformationSuccess() {
        EducationActivity.startAction(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personalinformation;
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
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNameTag.setTitleWidth(tvBirthTag);
        tvSexTag.setTitleWidth(tvBirthTag);
        tvBirthTag.setTitleWidth(tvBirthTag);
        tvEmailTag.setTitleWidth(tvBirthTag);
        tvLivePlaceTag.setTitleWidth(tvBirthTag);
        tvPositionTitleTag.setTitleWidth(tvBirthTag);
        tvWorkTimeTag.setTitleWidth(tvBirthTag);
        ivNameDelete.setVisibility(View.GONE);
        tvEmailDelete.setVisibility(View.GONE);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>0){
                        ivNameDelete.setVisibility(View.VISIBLE);
                    }else{
                        ivNameDelete.setVisibility(View.GONE);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    tvEmailDelete.setVisibility(View.VISIBLE);
                }else{
                    tvEmailDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    tvBirthSelect.setImageResource(R.mipmap.right_arrow);
                }else{
                    tvBirthSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvSex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivSexSelect.setImageResource(R.mipmap.right_arrow);
                }else{
                    ivSexSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvLivePlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    tvLivePlaceSelect.setImageResource(R.mipmap.right_arrow);
                }else{
                    tvLivePlaceSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvWorkTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    tvWorkTimeSelect.setImageResource(R.mipmap.right_arrow);
                }else{
                    tvWorkTimeSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvPositionTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    tvPositionTitleSelect.setImageResource(R.mipmap.right_arrow);
                }else{
                    tvPositionTitleSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance=this;
        initDialog();
    }

    private void initDialog() {
        customDatePickerJobTitle=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                jobTitleId= ResumeInfoIDToString.getZhiChengId(HRApplication.getAppContext(),time);
                tvPositionTitle.setText(time);
            }
        },getResources().getStringArray(R.array.array_zhicheng_zh));
        datePickerSex=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
              sex=ResumeInfoIDToString.getSexId(HRApplication.getAppContext(),time);
              tvSex.setText(time);
            }
        },getResources().getStringArray(R.array.array_sex_zh));

        datePickerBirth=new MyCustomDatePicker(this, new MyCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvBirth.setText(time);
                bitrh=time;
            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");//格式为 2013年9月3日 14:44
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        int endYear=Integer.parseInt(formatter.format(curDate)) ;
        String[] s=new String[70];
        for(int i=0;i<70;i++){
            s[i]=(endYear-i)+"";
        }
        datePickerStartJobTime=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvWorkTime.setText(time);
                workTime=time;
            }
        },s);
    }

    @OnClick({R.id.rl_sex, R.id.rl_birth,R.id.tv_emailDelete, R.id.rl_livePlace, R.id.rl_workTime, R.id.rl_email,R.id.iv_nameDelete, R.id.rl_positionTitle,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_sex:
                datePickerSex.show(tvSex.getText().toString());
                break;
            case R.id.rl_birth:
                String s=tvBirth.getText().toString();
                datePickerBirth.show(s,3);
                break;
            case R.id.rl_livePlace:
                SelectCityActivity.startAction(this,1,TAG);
                break;
            case R.id.rl_workTime:
                datePickerStartJobTime.show(tvWorkTime.getText().toString());
                break;
            case R.id.rl_email:
                break;
            case R.id.rl_positionTitle:
                customDatePickerJobTitle.show(tvPositionTitle.getText().toString());
                break;
            case R.id.btn_next:
                EducationActivity.startAction(this);
                //doSendPersonalInformation();
                break;
            case R.id.iv_nameDelete:
                etName.setText("");
                break;
            case R.id.tv_emailDelete:
                etEmail.setText("");
                break;
        }
    }

    private void doSendPersonalInformation() {
        if(etName.getText().toString()==null||"".equals(etName.getText().toString())){
            ToastUitl.showShort("请填写姓名");
            return;
        }
        if("".equals(sex)){
            ToastUitl.showShort("请选择性别");
            return;
        }
        if("".equals(bitrh)||bitrh==null){
            ToastUitl.showShort("请选择出生日期");
            return;
        }
        if("".equals(cityId)||cityId==null){
            ToastUitl.showShort("请选择现居住地");
            return;
        }
        if(etEmail.getText().toString()==null||"".equals(etEmail.getText().toString())){
            ToastUitl.showShort("请填写电子邮箱");
            return;
        }
        if(RegularExpression.isEmail(etEmail.getText().toString())==false){
            ToastUitl.showShort("请填写正确的邮箱");
            return;
        }
        if("".equals(jobTitleId)||jobTitleId==null){
            ToastUitl.showShort("请选择现有职称");
            return;
        }
        PersonalInformationData personalInformationData=new PersonalInformationData();
        personalInformationData.setName(etName.getText().toString());
        personalInformationData.setSex(sex);
        personalInformationData.setBirth(bitrh);
        personalInformationData.setLivePlace(cityId);
        personalInformationData.setEmail(etEmail.getText().toString());
        personalInformationData.setWorkTime(workTime);
        personalInformationData.setPhoneNumber("17319277396");
        personalInformationData.setPositionTitle(jobTitleId);
        mPresenter.sendPersonalInformationToResume(personalInformationData);
    }

    public void setSelectCity(CityBean cityBean){
        if(tvLivePlace!=null) {
            tvLivePlace.setText(cityBean.getName());
        }
        cityId=cityBean.getId();
    }
}
