package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.PersonalInformationContract;
import com.hr.ui.ui.main.modle.PersonalInformationModel;
import com.hr.ui.ui.main.presenter.PersonalInformationPresenter;
import com.hr.ui.utils.Base64;
import com.hr.ui.utils.ClickUtils;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyCustomDatePicker;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyTextView;
import com.hr.ui.view.RoundImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    CircleImageView ivPersonalInformationImage;
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
    @BindView(R.id.view8)
    RelativeLayout view8;
    @BindView(R.id.rl_workTime)
    RelativeLayout rlWorkTime;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.cl_personalInfo)
    ConstraintLayout clPersonalInfo;
    private SharedPreferencesUtils sUtis;
    private int stopType,startType;
    private CustomDatePicker customDatePickerJobTitle, datePickerSex, datePickerStartJobTime;
    private MyCustomDatePicker datePickerBirth;
    private String jobTitleId, sex, bitrh, cityId, workTime, phoneNumber;
    public static PersonalInformationActivity instance;
    private String type;//简历类型 学生 已工作
    public static final String TAG = PersonalInformationActivity.class.getSimpleName();
    private MyDialog myDialog;
    private PopupWindow popupWindow;
    public static final int REQUEST_CODE_SELECT = 0x10;
    public static final int IMAGE_PICKER = 0x20;
    private String imagePath;
    private String content,workYear;
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
        MobclickAgent.onEvent(this,"v6_edit_resumePersonInfo");
        if (stopType == 1) {
            MobclickAgent.onEvent(this,"v6_resume_complete");
            MainActivity.startAction(this, 0);
            AppManager.getAppManager().finishAllActivity();
        } else {
            EducationActivity.startAction(this);
        }
    }

    @Override
    public void uploadImageSuccess(String path) {
        sUtis.setStringValue(Constants.PERSONIMAGE,path);
        PersonalInformationData personalInformationData = new PersonalInformationData();
        personalInformationData.setImageUrl(path);
        personalInformationData.setName(etName.getText().toString());
        personalInformationData.setSex(sex);
        personalInformationData.setBirth(tvBirth.getText().toString());
        personalInformationData.setLivePlace(cityId);
        if ("1".equals(type)) {
            workTime=workTime;
        } else {
            workTime="-1";
        }
        personalInformationData.setWorkTime(workTime);
        personalInformationData.setPositionTitle(jobTitleId);
        personalInformationData.setPhoneNumber(phoneNumber);
        personalInformationData.setEmail(etEmail.getText().toString());
        mPresenter.sendPersonalInformationToResume(personalInformationData);
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
        sUtis = new SharedPreferencesUtils(this);
        stopType = sUtis.getIntValue(Constants.RESUME_STOPTYPE, 0);
        startType=sUtis.getIntValue(Constants.RESUME_STARTTYPE,0);
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
                exitOrFinishActivity();
            }
        });
        type = sUtis.getStringValue(Constants.RESUME_TYPE, "");
        phoneNumber = sUtis.getStringValue(Constants.USERPHONE, "");
        if (type != null && !"".equals(type)) {
            if ("1".equals(type)) {
                view8.setVisibility(View.VISIBLE);
            } else if ("2".equals(type)) {
                view8.setVisibility(View.GONE);
            }
        }
        if (stopType == 1) {
            btnNext.setText(R.string.complete);
        }
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
                if (s.length() > 0) {
                    ivNameDelete.setVisibility(View.VISIBLE);
                } else {
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
                if (s.length() > 0) {
                    tvEmailDelete.setVisibility(View.VISIBLE);
                } else {
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
                if (s.length() > 0) {
                    tvBirthSelect.setImageResource(R.mipmap.right_arrow);
                } else {
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
                if (s.length() > 0) {
                    ivSexSelect.setImageResource(R.mipmap.right_arrow);
                } else {
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
                if (s.length() > 0) {
                    tvLivePlaceSelect.setImageResource(R.mipmap.right_arrow);
                } else {
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
                if (s.length() > 0) {
                    tvWorkTimeSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvWorkTimeSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivNameDelete.setVisibility(View.GONE);
                }else{
                    if(etName.getText().toString()!=null&&!"".equals(etName.getText().toString())){
                        ivNameDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    tvEmailDelete.setVisibility(View.GONE);
                }else{
                    if(etEmail.getText().toString()!=null&&!"".equals(etEmail.getText().toString())){
                        tvEmailDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tvPositionTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvPositionTitleSelect.setImageResource(R.mipmap.right_arrow);
                } else {
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
        instance = this;
        initDialog();
    }

    private void initDialog() {
        customDatePickerJobTitle = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                jobTitleId = ResumeInfoIDToString.getZhiChengId(HRApplication.getAppContext(), time);
                tvPositionTitle.setText(time);
            }
        }, getResources().getStringArray(R.array.array_zhicheng_zh));
        datePickerSex = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                sex = ResumeInfoIDToString.getSexId(HRApplication.getAppContext(), time);
                tvSex.setText(time);
            }
        }, getResources().getStringArray(R.array.array_sex_zh));

        datePickerBirth = new MyCustomDatePicker(this, new MyCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvBirth.setText(time);
                bitrh = time;
                sUtis.setStringValue(Constants.BIRTHYEAR,bitrh.substring(0,bitrh.indexOf("-")));
                workYear=bitrh.substring(0,bitrh.indexOf("-"));
                initWorkDialog();
            }
        });
    }

    private void initWorkDialog() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");//格式为 2013年9月3日 14:44
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        int endYear = Integer.parseInt(formatter.format(curDate))+1;
        int workSize=0;
        if(workYear!=null&&!"".equals(workYear)) {
             workSize =endYear- Integer.parseInt(workYear)-18+1;
        }else {
            workSize=60;
        }
        String[] s = new String[workSize];
        s[0]="无工作经验";
        for (int i = 1; i < workSize; i++) {
            s[i] = (endYear - i) + "";
        }
        datePickerStartJobTime = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvWorkTime.setText(time);
                if("无工作经验".equals(time)) {
                    workTime="-1";
                }else {
                    workTime = time;
                }
            }
        }, s);
    }

    @OnClick({R.id.rl_sex,R.id.iv_personalInformationImage, R.id.rl_birth, R.id.tv_emailDelete, R.id.rl_livePlace, R.id.rl_workTime, R.id.rl_email, R.id.iv_nameDelete, R.id.rl_positionTitle, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_personalInformationImage:
                takePhoto();
                break;
            case R.id.rl_sex:
                setFocus();
                datePickerSex.show(tvSex.getText().toString());
                break;
            case R.id.rl_birth:
                setFocus();
                String s = tvBirth.getText().toString();
                datePickerBirth.show(s, 3);
                break;
            case R.id.rl_livePlace:
                setFocus();
                SelectCityActivity.startAction(this, 1, TAG);
                break;
            case R.id.rl_workTime:
                setFocus();
                if(workYear!=null&&!"".equals(workYear)) {
                    datePickerStartJobTime.show(tvWorkTime.getText().toString());
                }else{
                    ToastUitl.showShort("请选择出生日期");
                }
                break;
            case R.id.rl_email:
                break;
            case R.id.rl_positionTitle:
                setFocus();
                customDatePickerJobTitle.show(tvPositionTitle.getText().toString());
                break;
            case R.id.btn_next:
                if(!ClickUtils.isFastClick()) {
                    doSendPersonalInformation();
                }
                break;
            case R.id.iv_nameDelete:
                etName.setText("");
                break;
            case R.id.tv_emailDelete:
                etEmail.setText("");
                break;
        }
    }
    private void  setFocus(){
        clPersonalInfo.setFocusableInTouchMode(true);
        clPersonalInfo.setFocusable(true);
        clPersonalInfo.requestFocus();
        clPersonalInfo.findFocus();
    }
    private void doSendPersonalInformation() {
        if (etName.getText().toString() == null || "".equals(etName.getText().toString())) {
            ToastUitl.showShort("请填写姓名");
            return;
        }
        if ("".equals(sex)||sex==null) {
            ToastUitl.showShort("请选择性别");
            return;
        }
        if ("".equals(bitrh) || bitrh == null) {
            ToastUitl.showShort("请选择出生日期");
            return;
        }
        if ("".equals(cityId) || cityId == null) {
            ToastUitl.showShort("请选择现居住地");
            return;
        }
        if (etEmail.getText().toString() == null || "".equals(etEmail.getText().toString())) {
            ToastUitl.showShort("请填写电子邮箱");
            return;
        }
        if (RegularExpression.isEmail(etEmail.getText().toString()) == false) {
            ToastUitl.showShort("请填写正确的邮箱");
            return;
        }
        if ("".equals(jobTitleId) || jobTitleId == null) {
            ToastUitl.showShort("请选择现有职称");
            return;
        }
        if(content!=null&&!"".equals(content)) {
            mPresenter.upLoadImage(content);
        }else {
            PersonalInformationData personalInformationData = new PersonalInformationData();
            personalInformationData.setName(etName.getText().toString());
            personalInformationData.setSex(sex);
            personalInformationData.setBirth(bitrh);
            sUtis.setStringValue(Constants.BIRTHYEAR,bitrh.substring(0,bitrh.indexOf("-")));
            personalInformationData.setLivePlace(cityId);
            personalInformationData.setEmail(etEmail.getText().toString());
            if ("1".equals(type)) {
                personalInformationData.setWorkTime(workTime);
            } else {
                workTime="-1";
                personalInformationData.setWorkTime("-1");
            }
            personalInformationData.setPhoneNumber(phoneNumber);
            personalInformationData.setPositionTitle(jobTitleId);
            // Log.i("手机号码", phoneNumber);
            mPresenter.sendPersonalInformationToResume(personalInformationData);
        }
    }

    public void setSelectCity(CityBean cityBean) {
        if (tvLivePlace != null&&!"".equals(cityBean.getName())&&cityBean.getName()!=null) {
            tvLivePlace.setText(cityBean.getName());
        }
        cityId = cityBean.getId();
    }

    private void exitOrFinishActivity() {
        if (startType == 1) {
            myDialog = new MyDialog(this, 2);
            myDialog.setMessage(getString(R.string.exitWarning));
            myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    myDialog.dismiss();
                    SplashActivity.startAction(PersonalInformationActivity.this,1);
                    SharedPreferencesUtils sUtils = new SharedPreferencesUtils(HRApplication.getAppContext());
                    sUtils.setIntValue(Constants.ISAUTOLOGIN, 0);
                    AppManager.getAppManager().finishAllActivity();
                }
            });
            myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitOrFinishActivity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    private void takePhoto() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.layout_takephoto, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        TextView tvTakePhoto = popView.findViewById(R.id.tv_takePhoto);
        TextView tvSelectPicture = popView.findViewById(R.id.tv_selectPicture);
        TextView tvCancel = popView.findViewById(R.id.tv_cancelSelect);
        FrameLayout rl_takePhoto=popView.findViewById(R.id.rl_popTakePhoto);
        rl_takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
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
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.KITKAT) {
            //  大于等于24即为4.4及以上执行内容
            // 设置背景颜色变暗
        } else {
            //  低于19即为4.4以下执行内容
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        popupWindow.setOutsideTouchable(true);
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInformationActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                /*CompanyDetailActivity.startAction(getActivity());*/
            }
        });
        tvSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInformationActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                /*CompanyDetailActivity.startAction(getActivity());*/
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                /*CompanyDetailActivity.startAction(getActivity());*/
            }
        });
        View rootview = LayoutInflater.from(PersonalInformationActivity.this).inflate(R.layout.fragment_resume, null);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //dp转px工具
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagePath = images.get(0).path;
                uploadImage();
            } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagePath = images.get(0).path;
                uploadImage();
            } else {
                ToastUitl.showShort("没有数据");
            }
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    private void uploadImage() {
        File file = new File(imagePath);
        if (file == null || !file.exists()) {
            ToastUitl.showShort("文件不存在");
            return;// 文件不存在
        } else {
            Glide.with(this).load(imagePath).fitCenter().into(ivPersonalInformationImage);
            // System.out.println(imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] bt = stream.toByteArray();
            String bt1 = Base64.encodeToString(bt, Base64.DEFAULT);
            content = URLEncoder.encode(bt1);
        }
    }
}
