package com.hr.ui.ui.resume.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.ResumePersonalInfoBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.activity.SelectCityActivity;
import com.hr.ui.ui.resume.contract.ResumePersonalInfoContract;
import com.hr.ui.ui.resume.model.ResumePersonalInfoModel;
import com.hr.ui.ui.resume.presenter.ResumePersonalInfoPresenter;
import com.hr.ui.utils.Base64;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyCustomDatePicker;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/25.
 */

public class ResumePersonalInfoActivity extends BaseActivity<ResumePersonalInfoPresenter, ResumePersonalInfoModel> implements ResumePersonalInfoContract.View {

    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeName)
    TextView tvResumeName;
    @BindView(R.id.et_resumePersonName)
    EditText etResumePersonName;
    @BindView(R.id.iv_personNameDelete)
    ImageView ivPersonNameDelete;
    @BindView(R.id.tv_resumeSex)
    TextView tvResumeSex;
    @BindView(R.id.et_resumeSex)
    TextView etResumeSex;
    @BindView(R.id.iv_resumeSexSelect)
    ImageView ivResumeSexSelect;
    @BindView(R.id.tv_resumeBirth)
    TextView tvResumeBirth;
    @BindView(R.id.et_resumeBirth)
    TextView etResumeBirth;
    @BindView(R.id.iv_resumeBirthSelect)
    ImageView ivResumeBirthSelect;
    @BindView(R.id.tv_resumeLivePlace)
    TextView tvResumeLivePlace;
    @BindView(R.id.et_resumeLivePlace)
    TextView etResumeLivePlace;
    @BindView(R.id.iv_resumeLivePlaceSelect)
    ImageView ivResumeLivePlaceSelect;
    @BindView(R.id.tv_resumeWorkTime)
    TextView tvResumeWorkTime;
    @BindView(R.id.et_resumeWorkTime)
    TextView etResumeWorkTime;
    @BindView(R.id.iv_resumeWorkTimeSelect)
    ImageView ivResumeWorkTimeSelect;
    @BindView(R.id.tv_resumePhone)
    TextView tvResumePhone;
    @BindView(R.id.et_resumePhone)
    TextView etResumePhone;
    @BindView(R.id.iv_resumePhoneSelect)
    ImageView ivResumePhoneSelect;
    @BindView(R.id.tv_resumeEmail)
    TextView tvResumeEmail;
    @BindView(R.id.et_resumeEmail)
    EditText etResumeEmail;
    @BindView(R.id.iv_resumeEmailDelete)
    ImageView ivResumeEmailDelete;
    @BindView(R.id.btn_resumeSave)
    Button btnResumeSave;
    @BindView(R.id.et_resumePositionTitle)
    TextView etResumePositionTitle;
    @BindView(R.id.rl_resumePersonalMain)
    ConstraintLayout rlResumePersonalMain;
    @BindView(R.id.iv_ResumePersonPhoto)
    CircleImageView ivResumePersonPhoto;
    @BindView(R.id.rl_resumePersonalInfoImage)
    RelativeLayout rlResumePersonalInfoImage;
    @BindView(R.id.rl_resumePersonalInfoSex)
    RelativeLayout rlResumePersonalInfoSex;
    @BindView(R.id.rl_resumePersonalInfoBirth)
    RelativeLayout rlResumePersonalInfoBirth;
    @BindView(R.id.rl_resumePersonalInfoLivePlace)
    RelativeLayout rlResumePersonalInfoLivePlace;
    @BindView(R.id.rl_resumePersonalInfoWorkTime)
    RelativeLayout rlResumePersonalInfoWorkTime;
    @BindView(R.id.tv_resumePositionTitle)
    TextView tvResumePositionTitle;
    @BindView(R.id.iv_resumePositionTitleSelect)
    ImageView ivResumePositionTitleSelect;
    @BindView(R.id.rl_resumePersonalInfoPositionTitle)
    RelativeLayout rlResumePersonalInfoPositionTitle;
    @BindView(R.id.rl_resumePersonalInfoPhone)
    RelativeLayout rlResumePersonalInfoPhone;
    private String imageUrl, name, sexid, birth, liveplaceId, workTime, phoneNumber, email, positionTitleId;
    private CustomDatePicker datePickerSex, datePickerWorkTime, datePickerPositionTitle;
    private MyCustomDatePicker datePickerBirth;
    public static final String TAG = ResumePersonalInfoActivity.class.getSimpleName();
    public static ResumePersonalInfoActivity instance;
    private List<CityBean> cityBeanList = new ArrayList<>();
    private PopupWindow popupWindow;
    public static final int REQUEST_CODE_SELECT = 0x10;
    public static final int IMAGE_PICKER = 0x20;
    private String imagePath;
    private String content;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumePersonalInfoActivity.class);
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
    public void getPersonalInfoSuccess(ResumePersonalInfoBean resumePersonalInfoBean) {
        refreshPersonalInfoUI(resumePersonalInfoBean);
    }

    private void refreshPersonalInfoUI(ResumePersonalInfoBean resumePersonalInfoBean) {
        if (resumePersonalInfoBean.getBase_info() != null && resumePersonalInfoBean.getBase_info().size() != 0) {
            ResumePersonalInfoBean.BaseInfoBean baseInfoBean = resumePersonalInfoBean.getBase_info().get(0);
            etResumeBirth.setText(baseInfoBean.getYear() + "-" + baseInfoBean.getMonth() + "-" + baseInfoBean.getDay());
            birth=etResumeBirth.getText().toString();
            etResumeEmail.setText(baseInfoBean.getEmailaddress());
            etResumeLivePlace.setText(FromStringToArrayList.getInstance().getCityListName(baseInfoBean.getLocation()));
            etResumeSex.setText(ResumeInfoIDToString.getSexName(this, baseInfoBean.getSex()));
            sexid = baseInfoBean.getSex();
            liveplaceId = baseInfoBean.getLocation();
            cityBeanList.clear();
            CityBean cityBean = new CityBean();
            cityBean.setId(liveplaceId);
            cityBean.setName(FromStringToArrayList.getInstance().getCityListName(liveplaceId));
            cityBean.setCheck(true);
            cityBeanList.add(cityBean);
            if ("-1".equals(baseInfoBean.getWork_beginyear())) {
                etResumeWorkTime.setText("无工作经验");
                workTime = "-1";
            } else {
                etResumeWorkTime.setText(baseInfoBean.getWork_beginyear());
                workTime = baseInfoBean.getWork_beginyear();
            }
            imagePath=baseInfoBean.getPic_filekey();
            Glide.with(this).load(Constants.IMAGE_BASEPATH + imagePath).centerCrop().into(ivResumePersonPhoto);
            etResumePositionTitle.setText(ResumeInfoIDToString.getZhiCheng(this, baseInfoBean.getPost_rank(), true));
            positionTitleId = baseInfoBean.getPost_rank();
            etResumePersonName.setText(baseInfoBean.getName());
            etResumePhone.setText(baseInfoBean.getYdphone());
            phoneNumber=baseInfoBean.getYdphone();
        }
        ivPersonNameDelete.setVisibility(View.GONE);
        ivResumeEmailDelete.setVisibility(View.GONE);
    }

    @Override
    public void updatePersonalInfoSuccess() {
        ToastUitl.showShort("修改成功");
        finish();
    }

    @Override
    public void uploadImageSuccess(String imagePath) {
        PersonalInformationData personalInformationData = new PersonalInformationData();
        personalInformationData.setImageUrl(imagePath);
        personalInformationData.setName(etResumePersonName.getText().toString());
        personalInformationData.setSex(sexid);
        personalInformationData.setBirth(etResumeBirth.getText().toString());
        personalInformationData.setLivePlace(liveplaceId);
        personalInformationData.setWorkTime(workTime);
        personalInformationData.setPositionTitle(positionTitleId);
        personalInformationData.setPhoneNumber(phoneNumber);
        personalInformationData.setEmail(etResumeEmail.getText().toString());
        mPresenter.updatePersonalInfo(personalInformationData);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resumepersonalinfo;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        instance = this;
        mPresenter.getPersonalInfo();
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.personalInformation);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTextChangedListener();
    }

    private void setTextChangedListener() {
        etResumePersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivPersonNameDelete.setVisibility(View.VISIBLE);
                } else {
                    ivPersonNameDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResumePersonName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivPersonNameDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeBirth.getText().toString() != null && !"".equals(etResumePersonName.getText().toString())) {
                        ivPersonNameDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeEmailDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeEmailDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResumeEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //System.out.println("焦点失去");
                    ivResumeEmailDelete.setVisibility(View.GONE);
                } else {
                    //System.out.println("焦点得到");
                    if (etResumeEmail.getText().toString() != null && !"".equals(etResumeEmail.getText().toString())) {
                        ivResumeEmailDelete.setVisibility(View.VISIBLE);

                    }
                }
                etResumeEmail.postInvalidate();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initDialog();
    }

    private void initDialog() {
        datePickerBirth = new MyCustomDatePicker(this, new MyCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumeBirth.setText(time);
            }
        });
        datePickerPositionTitle = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumePositionTitle.setText(time);
                positionTitleId = ResumeInfoIDToString.getZhiChengId(ResumePersonalInfoActivity.this, time);
            }
        }, getResources().getStringArray(R.array.array_zhicheng_zh));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");//格式为 2013年9月3日 14:44
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        int endYear = Integer.parseInt(formatter.format(curDate)) + 1;
        String[] s = new String[71];
        s[0] = "无工作经验";
        for (int i = 1; i < 71; i++) {
            s[i] = (endYear - i) + "";
        }
        datePickerWorkTime = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumeWorkTime.setText(time);
                if ("无工作经验".equals(time)) {
                    workTime = "-1";
                } else {
                    workTime = time;
                }
            }
        }, s);
        datePickerSex = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumeSex.setText(time);
                sexid = ResumeInfoIDToString.getSexId(ResumePersonalInfoActivity.this, time);
            }
        }, getResources().getStringArray(R.array.array_sex_zh));
    }

    @OnClick({R.id.rl_resumePersonalInfoImage, R.id.rl_resumePersonalInfoPositionTitle, R.id.et_resumePersonName, R.id.et_resumeEmail, R.id.rl_resumePersonalMain, R.id.iv_personNameDelete, R.id.rl_resumePersonalInfoSex, R.id.rl_resumePersonalInfoBirth, R.id.rl_resumePersonalInfoLivePlace, R.id.rl_resumePersonalInfoWorkTime, R.id.rl_resumePersonalInfoPhone, R.id.iv_resumeEmailDelete, R.id.btn_resumeSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
          /*  case R.id.ll_shadowPersonal:
                llShadowPersonal.setVisibility(View.GONE);
                rlResumePersonalMain.setFocusable(true);
                rlResumePersonalMain.setFocusableInTouchMode(true);
                rlResumePersonalMain.requestFocus();
                rlResumePersonalMain.findFocus();
                break;*/
          /*  case R.id.et_resumePersonName:
               llShadowPersonal.setVisibility(View.VISIBLE);
                break;
            case R.id.et_resumeEmail:
                llShadowPersonal.setVisibility(View.VISIBLE);
                break;*/
            case R.id.rl_resumePersonalInfoPositionTitle:
                datePickerPositionTitle.show(etResumePositionTitle.getText().toString());
                break;
            case R.id.rl_resumePersonalInfoImage:
                setFocus();
                takePhoto();
                break;
            case R.id.iv_personNameDelete:
                etResumePersonName.setText("");
                break;
            case R.id.rl_resumePersonalInfoSex:
                setFocus();
                datePickerSex.show(etResumeSex.getText().toString());
                break;
            case R.id.rl_resumePersonalInfoBirth:
                setFocus();
                datePickerBirth.show(etResumeBirth.getText().toString(), 3);
                break;
            case R.id.rl_resumePersonalInfoLivePlace:
                setFocus();
                SelectCityActivity.startAction(this, 1, TAG, cityBeanList);
                break;
            case R.id.rl_resumePersonalInfoWorkTime:
                setFocus();
                datePickerWorkTime.show(etResumeWorkTime.getText().toString());
                break;
            case R.id.rl_resumePersonalInfoPhone:
                setFocus();
                break;
            case R.id.iv_resumeEmailDelete:
                etResumeEmail.setText("");
                break;
            case R.id.btn_resumeSave:
                doUpdateResume();
                break;
        }
    }

    private void takePhoto() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.layout_takephoto, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        TextView tvTakePhoto = popView.findViewById(R.id.tv_takePhoto);
        TextView tvSelectPicture = popView.findViewById(R.id.tv_selectPicture);
        TextView tvCancel = popView.findViewById(R.id.tv_cancelSelect);
        popupWindow.setOutsideTouchable(true);
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResumePersonalInfoActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                /*CompanyDetailActivity.startAction(getActivity());*/
            }
        });
        tvSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResumePersonalInfoActivity.this, ImageGridActivity.class);
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
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_resumepersonalinfo, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //dp转px工具
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void setFocus() {
        rlResumePersonalMain.setFocusable(true);
        rlResumePersonalMain.setFocusableInTouchMode(true);
        rlResumePersonalMain.requestFocus();
        rlResumePersonalMain.findFocus();
    }

    private void doUpdateResume() {
        if ("".equals(etResumePersonName.getText().toString()) || etResumeEmail.getText().toString() == null) {
            ToastUitl.showShort("请填写姓名");
            return;
        }
        if ("".equals(sexid) || sexid == null) {
            ToastUitl.showShort("请选择性别");
            return;
        }
        if ("".equals(birth) || birth == null) {
            ToastUitl.showShort("请选择出生日期");
            return;
        }
        if ("".equals(liveplaceId) || liveplaceId == null) {
            ToastUitl.showShort("请选择现居住地");
            return;
        }
        if ("".equals(workTime) || workTime == null) {
            ToastUitl.showShort("请选择工作时间");
            return;
        }
        phoneNumber=etResumePhone.getText().toString();
        if ("".equals(phoneNumber) || phoneNumber == null) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if ("".equals(etResumeEmail.getText().toString()) || etResumeEmail.getText().toString() == null) {
            ToastUitl.showShort("请输入电子邮箱");
            return;
        }
        if(content!=null&&!"".equals(content)) {
            mPresenter.upLoadImage(content);
        }else{
            PersonalInformationData personalInformationData = new PersonalInformationData();
            personalInformationData.setName(etResumePersonName.getText().toString());
            personalInformationData.setSex(sexid);
            personalInformationData.setBirth(etResumeBirth.getText().toString());
            personalInformationData.setLivePlace(liveplaceId);
            personalInformationData.setWorkTime(workTime);
            personalInformationData.setPositionTitle(positionTitleId);
            personalInformationData.setPhoneNumber(phoneNumber);
            personalInformationData.setEmail(etResumeEmail.getText().toString());
            mPresenter.updatePersonalInfo(personalInformationData);
        }
    }

    public void setSelectCity(CityBean cityBean) {
        etResumeLivePlace.setText(cityBean.getName());
        liveplaceId = cityBean.getId();
        cityBeanList.clear();
        cityBeanList.add(cityBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
    private void uploadImage(){
        File file = new File(imagePath);
        if (file == null || !file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;// 文件不存在
        } else {
            Glide.with(this).load(imagePath).centerCrop().into(ivResumePersonPhoto);
           // System.out.println(imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] bt = stream.toByteArray();
            String bt1 = Base64.encodeToString(bt,Base64.DEFAULT);
            content = URLEncoder.encode(bt1);
            //Log.i("数据",en);
        }
    }
}
