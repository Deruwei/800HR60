package com.hr.ui.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.adapter.MyRecommendJobAdapter;
import com.hr.ui.ui.main.modle.ResumeModel;
import com.hr.ui.ui.main.presenter.ResumePresenter;
import com.hr.ui.ui.resume.activity.PreviewResumeActivity;
import com.hr.ui.ui.resume.activity.ResumeEducationActivity;
import com.hr.ui.ui.resume.activity.ResumeIntroductionActivity;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.ui.resume.activity.ResumeLanguageActivity;
import com.hr.ui.ui.resume.activity.ResumePersonalInfoActivity;
import com.hr.ui.ui.resume.activity.ResumeProfessionSkillActivity;
import com.hr.ui.ui.resume.activity.ResumeProjectExpActivity;
import com.hr.ui.ui.resume.activity.ResumeTrainActivity;
import com.hr.ui.ui.resume.activity.ResumeWorkExpActivity;
import com.hr.ui.utils.Base64;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyResumeScoreProgressBar;
import com.hr.ui.view.SnailBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdr on 2017/11/22.
 */

public class ResumeFragment extends BaseFragment<ResumePresenter, ResumeModel> implements ResumeContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_resumePreview)
    TextView tvResumePreview;
    @BindView(R.id.ll_resumeTitleTop)
    RelativeLayout llResumeTitleTop;
    @BindView(R.id.rl_ResumePersonImg)
    RelativeLayout rlResumePersonImg;
    @BindView(R.id.sb_resume)
    SnailBar sbResume;
    @BindView(R.id.pb_resume)
    MyResumeScoreProgressBar pbResume;
    @BindView(R.id.tv_resumeJobOrder)
    TextView tvResumeJobOrder;
    @BindView(R.id.rl_resumeContent)
    LinearLayout rlResumeContent;
    Unbinder unbinder;
    @BindView(R.id.tv_resumePersonName)
    TextView tvResumePersonName;
    @BindView(R.id.tv_resumeJobState)
    TextView tvResumeJobState;
    @BindView(R.id.tv_resumePersonInfo)
    TextView tvResumePersonInfo;
    @BindView(R.id.tv_resumeEduInfo)
    TextView tvResumeEduInfo;
    @BindView(R.id.tv_resumePersonTel)
    TextView tvResumePersonTel;
    @BindView(R.id.tv_resumePersonEmail)
    TextView tvResumePersonEmail;
    @BindView(R.id.ll_resumeEduBGList)
    LinearLayout llResumeEduBGList;
    @BindView(R.id.tv_addResumeEduBG)
    TextView tvAddResumeEduBG;
    @BindView(R.id.ll_resumeWorkExpList)
    LinearLayout llResumeWorkExpList;
    @BindView(R.id.tv_resumeAddWorkExp)
    TextView tvResumeAddWorkExp;
    @BindView(R.id.ll_resumeProjectExpLIst)
    LinearLayout llResumeProjectExpLIst;
    @BindView(R.id.tv_addResumeProjectExp)
    TextView tvAddResumeProjectExp;
    @BindView(R.id.ll_resumeProfessionSkillList)
    LinearLayout llResumeProfessionSkillList;
    @BindView(R.id.tv_addResumeProfessionSkill)
    TextView tvAddResumeProfessionSkill;
    @BindView(R.id.ll_resumeLanguageSkillList)
    LinearLayout llResumeLanguageSkillList;
    @BindView(R.id.tv_addResumeLanguageSkill)
    TextView tvAddResumeLanguageSkill;
    @BindView(R.id.ll_resumeTrainExpList)
    LinearLayout llResumeTrainExpList;
    @BindView(R.id.tv_addResumeTrainExp)
    TextView tvAddResumeTrainExp;
    @BindView(R.id.ll_resumeIntroductionList)
    LinearLayout llResumeIntroductionList;
    @BindView(R.id.tv_addResumeIntroduction)
    TextView tvAddResumeIntroduction;
    @BindView(R.id.ll_resumeAttachmentList)
    LinearLayout llResumeAttachmentList;
    @BindView(R.id.tv_addResumeAttachment)
    TextView tvAddResumeAttachment;
    @BindView(R.id.iv_resumePersonImage)
    CircleImageView ivResumePersonImage;
    @BindView(R.id.iv_editResumePersonal)
    ImageView ivEditResumePersonal;
    @BindView(R.id.rl_resumeJobOrder)
    RelativeLayout rlResumeJobOrder;
    @BindView(R.id.rl_hideResume)
    RelativeLayout rlHideResume;
    @BindView(R.id.srl_resume)
    SwipeRefreshLayout srlResume;
    @BindView(R.id.iv_hideResume)
    Switch ivHideResume;
    @BindView(R.id.tv_resumeWorkExpName)
    TextView tvResumeWorkExpName;
    @BindView(R.id.tv_hideResumeTag)
    TextView tvHideResumeTag;
    @BindView(R.id.tv_refreshResume)
    TextView tvRefreshResume;
    @BindView(R.id.iv_resumeValid)
    ImageView ivResumeValid;
    @BindView(R.id.tv_resumeTips)
    TextView tvResumeTips;
    @BindView(R.id.iv_noNetError)
    ImageView ivNoNetError;
    @BindView(R.id.ll_netError)
    LinearLayout llNetError;
    @BindView(R.id.iv_updateResume)
    ImageView ivUpdateResume;
    private boolean isCheck;
    private List<ResumeBean.ResumeInfoBean.AssessInfoBean> assessInfoBean;
    private List<ResumeBean.ResumeInfoBean.OrderInfoBean> orderInfoBean;
    private List<ResumeBean.ResumeInfoBean.TitleInfoBean> titleInfoBean;
    private List<ResumeBean.ResumeInfoBean.BaseInfoBean> baseInfoBean;
    private List<ResumeBean.ResumeInfoBean.EducationListBean> educationListBeanList;
    private List<ResumeBean.ResumeInfoBean.ExperienceListBean> experienceListBeanList;
    private List<ResumeBean.ResumeInfoBean.LanguageListBean> languageListBeanList;
    private List<ResumeBean.ResumeInfoBean.PlantListBean> plantListBeanList;
    private List<ResumeBean.ResumeInfoBean.ProjectListBean> projectListBeanList;
    private List<ResumeBean.ResumeInfoBean.SkillListBean> skillListBeanList;
    private List<ResumeBean.ResumeInfoBean.SlaveListBean> slaveListBeanList;
    private Calendar calendar;
    private PopupWindow popupWindow;
    public static final int REQUEST_CODE_SELECT = 0x10;
    public static final int IMAGE_PICKER = 0x20;
    private String imagePath, resumeType;
    private ResumeBean.ResumeInfoBean resumeInfoBean;
    private boolean isFlesh, isCanFresh;
    private SharedPreferencesUtils sUtils;
    private int resumeId;
    private MyDialog dialog;
    public  static ResumeFragment instance;

    public static ResumeFragment newInstance(String s) {
        ResumeFragment navigationFragment = new ResumeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        instance=navigationFragment;
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_resume;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }


    @Override
    protected void initView() {
        sUtils = new SharedPreferencesUtils(getActivity());
      /*  mPresenter.getResumeList();*/
        // 设置进度条的颜色变化，最多可以设置4种颜色
        resumeId = sUtils.getIntValue(Constants.RESUME_ID, 0);
        srlResume.setColorSchemeResources(R.color.new_main);
        // 设置下拉监听，当用户下拉的时候会去执行回调
        srlResume.setOnRefreshListener(this);
        // 调整进度条距离屏幕顶部的距离
        srlResume.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        ivHideResume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (isCheck == false) {
                        mPresenter.setHide("2");
                    }
                } else {
                    mPresenter.setHide("0");
                }
            }
        });
        mPresenter.getResume(resumeId + "", true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onEvent(getActivity()," v6_scan_resume");
        isCanFresh = sUtils.getBooleanValue(Constants.IS_FERSH, false);
        if (isCanFresh == true) {
         /*   mPresenter.getResumeList();*/
            mPresenter.getResume(resumeId + "", false);
        }
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
    public void getResumeSuccess(ResumeBean resumeBean) {
        srlResume.setVisibility(View.VISIBLE);
        llNetError.setVisibility(View.GONE);
        /*srlResume.setRefreshing(false);*/
        if (isFlesh == true) {
            ToastUitl.showShort("刷新成功");
            MobclickAgent.onEvent(getActivity(),"v6_fresh_resume");
            isFlesh = false;
        }
        resumeType = resumeBean.getResume_info().getTitle_info().get(0).getResume_type();
        if ("1".equals(resumeType)) {
            tvResumeWorkExpName.setText(R.string.workExp);
            tvResumeAddWorkExp.setText(R.string.addWorkExp);
            rlHideResume.setVisibility(View.GONE);
        } else {
            tvResumeWorkExpName.setText(R.string.internshipExperience);
            tvResumeAddWorkExp.setText(R.string.addInternshipExp);
            rlHideResume.setVisibility(View.VISIBLE);
        }
        sUtils.setStringValue(Constants.RESUME_TYPE, resumeType);
        resumeInfoBean = resumeBean.getResume_info();
        orderInfoBean = resumeBean.getResume_info().getOrder_info();
        titleInfoBean = resumeBean.getResume_info().getTitle_info();
        baseInfoBean = resumeBean.getResume_info().getBase_info();
        assessInfoBean = resumeBean.getResume_info().getAssess_info();
        educationListBeanList = resumeBean.getResume_info().getEducation_list();
        experienceListBeanList = resumeBean.getResume_info().getExperience_list();
        projectListBeanList = resumeBean.getResume_info().getProject_list();
        skillListBeanList = resumeBean.getResume_info().getSkill_list();
        languageListBeanList = resumeBean.getResume_info().getLanguage_list();
        plantListBeanList = resumeBean.getResume_info().getPlant_list();
        slaveListBeanList = resumeBean.getResume_info().getSlave_list();
        initBaseInfo();
        initJobOrder();
        initTitleInfo();
        initEducation();
        initWorkExp();
        initProjectExp();
        initProfessionSkill();
        initLanguageSkill();
        initPlantExp();
        initIntroduction();
        initSlave();
    }

    private void initSlave() {
        llResumeAttachmentList.removeAllViewsInLayout();
        if (slaveListBeanList != null && slaveListBeanList.size() != 0) {
            llResumeAttachmentList.setVisibility(View.GONE);
            for (int i = 0; i < slaveListBeanList.size(); i++) {
                View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setText(slaveListBeanList.get(i).getAttachname());
                tvResumeName2.setText(slaveListBeanList.get(i).getAttachdescribe());
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ResumeProjectExpActivity.startAction(getActivity(), projectListBeanList.get(finalI).getProject_id());
                    }
                });
                llResumeAttachmentList.addView(plantView);
            }
        } else {
            llResumeAttachmentList.setVisibility(View.GONE);
        }
    }

    private void initIntroduction() {
        llResumeIntroductionList.removeAllViewsInLayout();
        if (assessInfoBean != null && assessInfoBean.size() != 0) {
            tvAddResumeIntroduction.setVisibility(View.GONE);
            for (int i = 0; i < assessInfoBean.size(); i++) {
                View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setSingleLine();
                tvResumeName.setText(assessInfoBean.get(i).getIntroduction());
                tvResumeName2.setVisibility(View.GONE);
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResumeIntroductionActivity.startAction(getActivity(), assessInfoBean.get(finalI).getIntroduction());
                        //ResumeProjectExpActivity.startAction(getActivity(), projectListBeanList.get(finalI).getProject_id());
                    }
                });
                llResumeIntroductionList.addView(plantView);
            }
        } else {
            tvAddResumeIntroduction.setVisibility(View.VISIBLE);
        }
    }

    private void initPlantExp() {
        llResumeTrainExpList.removeAllViewsInLayout();
        if (plantListBeanList != null && plantListBeanList.size() != 0) {
            llResumeTrainExpList.setVisibility(View.VISIBLE);

            for (int i = 0; i < plantListBeanList.size(); i++) {
                final View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setText(plantListBeanList.get(i).getInstitution());
                String endTimes = plantListBeanList.get(i).getToyear() + "." + plantListBeanList.get(i).getTomonth();
                if ("0.0".equals(endTimes)) {
                    endTimes = "至今";
                }
                tvResumeName2.setText(plantListBeanList.get(i).getFromyear() + "." + plantListBeanList.get(i).getFrommonth() + "-" + endTimes);
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResumeTrainActivity.startAction(getActivity(), plantListBeanList.get(finalI).getPlant_id());
                        //ResumeProjectExpActivity.startAction(getActivity(), projectListBeanList.get(finalI).getProject_id());
                    }
                });
                llResumeTrainExpList.addView(plantView);
            }
        } else {
            llResumeTrainExpList.setVisibility(View.GONE);
        }
    }

    private void initLanguageSkill() {
        llResumeLanguageSkillList.removeAllViewsInLayout();
        if (languageListBeanList != null && languageListBeanList.size() != 0) {
            llResumeLanguageSkillList.setVisibility(View.VISIBLE);
            for (int i = 0; i < languageListBeanList.size(); i++) {
                View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setText(ResumeInfoIDToString.getLanguageTpye(getActivity(), languageListBeanList.get(i).getLangname(), true));
                tvResumeName2.setText("听说：" + ResumeInfoIDToString.getLanguageReadLevel(getActivity(), languageListBeanList.get(i).getSpeak_level(), true) + "  读写：" + ResumeInfoIDToString.getLanguageReadLevel(getActivity(), languageListBeanList.get(i).getRead_level(), true));
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResumeLanguageActivity.startAction(getActivity(), languageListBeanList.get(finalI).getLangname());
                        //ResumeProjectExpActivity.startAction(getActivity(), projectListBeanList.get(finalI).getProject_id());
                    }
                });
                llResumeLanguageSkillList.addView(plantView);
            }
        } else {
            llResumeLanguageSkillList.setVisibility(View.GONE);
        }
    }

    private void initProfessionSkill() {
        llResumeProfessionSkillList.removeAllViewsInLayout();
        if (skillListBeanList != null && skillListBeanList.size() != 0) {
            llResumeProfessionSkillList.setVisibility(View.VISIBLE);

            for (int i = 0; i < skillListBeanList.size(); i++) {
                View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setText(skillListBeanList.get(i).getSkilltitle());
                tvResumeName2.setText(ResumeInfoIDToString.getSkillLevel(getActivity(), skillListBeanList.get(i).getAbility(), true));
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResumeProfessionSkillActivity.startAction(getActivity(), skillListBeanList.get(finalI).getSkill_id());
                        //ResumeProjectExpActivity.startAction(getActivity(), projectListBeanList.get(finalI).getProject_id());
                    }
                });
                llResumeProfessionSkillList.addView(plantView);
            }
        } else {
            llResumeProfessionSkillList.setVisibility(View.GONE);
        }
    }

    private void initProjectExp() {
        llResumeProjectExpLIst.removeAllViewsInLayout();
        if (projectListBeanList != null && projectListBeanList.size() != 0) {
            llResumeProjectExpLIst.setVisibility(View.VISIBLE);

            for (int i = 0; i < projectListBeanList.size(); i++) {
                View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setText(projectListBeanList.get(i).getProjectname());
                String startTime = projectListBeanList.get(i).getFromyear() + "." + projectListBeanList.get(i).getFrommonth();
                String endTime = projectListBeanList.get(i).getToyear() + "." + projectListBeanList.get(i).getTomonth();
                if ("0.0".equals(endTime)) {
                    endTime = "至今";
                }
                tvResumeName2.setText(startTime + "-" + endTime);
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResumeProjectExpActivity.startAction(getActivity(), projectListBeanList.get(finalI).getProject_id());
                    }
                });
                llResumeProjectExpLIst.addView(plantView);
            }
        } else {
            llResumeProjectExpLIst.setVisibility(View.GONE);
        }
    }

    @Override
    public void getResumeList(String resumeId) {
        // mPresenter.getResume(resumeId);
    }

    @Override
    public void uploadImageSuccess(final String path) {
        sUtils.setStringValue(Constants.PERSONIMAGE, path);
        resumeInfoBean.getBase_info().get(0).setPic_filekey(path);
        Glide.with(this).load(Constants.IMAGE_BASEPATH + path).fitCenter() .into(ivResumePersonImage);
        MainActivity.instance.setImage();
        HomeFragment.instance.setImage();
        MessageFragment.instance.setImage();
        // Log.i("okht","你好啊 "+Constants.IMAGE_BASEPATH + path);
    }

    @Override
    public void setHideSuccess() {
        if (ivHideResume.isChecked()) {
            ToastUitl.showShort("隐藏简历成功");
        } else {
            ToastUitl.showShort("取消隐藏简历成功");
        }
       /* open = !open;
        if (open == false) {
            ivHideResume.setChecked(false);
        } else {
            ivHideResume.setChecked(true);
        }*/
    }

    @Override
    public void updateSuccess() {
        MobclickAgent.onEvent(getActivity(),"v6_update_resume");
        ToastUitl.showShort("简历升级成功");
        mPresenter.getResume(resumeId + "", true);
    }

    @Override
    public void refreshResumeSuccess() {
        MobclickAgent.onEvent(getActivity(),"v6_fresh_resumeTime");
        MobclickAgent.onEvent(getActivity(),"v6_fresh_resume");
    }

    @Override
    public void netError() {
        srlResume.setVisibility(View.GONE);
        llNetError.setVisibility(View.VISIBLE);
    }

    /**
     * 个人信息
     */
    private void initBaseInfo() {
        if (baseInfoBean != null) {
            tvResumePersonName.setText(baseInfoBean.get(0).getName());
            calendar = Calendar.getInstance();
            // 年龄
            if ("2".equals(baseInfoBean.get(0).getYdphone_verify_status())) {
                ivResumeValid.setImageResource(R.mipmap.valid);
            } else {
                ivResumeValid.setImageResource(R.mipmap.novalid);
            }
            String yearString = baseInfoBean.get(0).getYear();
            sUtils.setStringValue(Constants.BIRTHYEAR, yearString);
            String monthString = baseInfoBean.get(0).getMonth();
            String dayString = baseInfoBean.get(0).getDay();
            int curYear = calendar.get(Calendar.YEAR);
            int yearStringInt = 0;
            if (yearString != null && yearString.length() > 0) {
                yearStringInt = Integer.parseInt(yearString);
            }
            int ageInt = curYear - yearStringInt;
            tvResumePersonInfo.setText(ResumeInfoIDToString.getSexName(getActivity(), baseInfoBean.get(0).getSex()) + "  " + ageInt + "岁  " + FromStringToArrayList.getInstance().getCityListName(baseInfoBean.get(0).getLocation()));
            tvResumePersonEmail.setText(baseInfoBean.get(0).getEmailaddress());
            tvResumePersonTel.setText(baseInfoBean.get(0).getYdphone());
            if (!"".equals(baseInfoBean.get(0).getPic_filekey()) && baseInfoBean.get(0).getPic_filekey() != null) {
                Glide.with(this).load(Constants.IMAGE_BASEPATH + baseInfoBean.get(0).getPic_filekey()) .fitCenter().into(ivResumePersonImage);
            }else{
                ivResumePersonImage.setImageResource(R.mipmap.persondefault);
            }
        }
    }

    /**
     * 简历信息
     */
    private void initTitleInfo() {
        if (titleInfoBean != null) {
            //Log.i("当前的数据", titleInfoBean.get(0).getFill_scale());
            sbResume.setMaxCount(100);
            if ("2".equals(titleInfoBean.get(0).getOpen())) {
                isCheck = true;
                ivHideResume.setChecked(true);
            } else {
                ivHideResume.setChecked(false);
            }
            if(isCanFresh==true){
                pbResume.setProgram2(Integer.parseInt(titleInfoBean.get(0).getFill_scale()));
                sbResume.setCurrentCount2(Integer.parseInt(titleInfoBean.get(0).getFill_scale()));
            }else {
                pbResume.setProgram(Integer.parseInt(titleInfoBean.get(0).getFill_scale()));
                sbResume.setCurrentCount(Integer.parseInt(titleInfoBean.get(0).getFill_scale()));
            }
        }
    }

    private void initJobOrder() {
        if (orderInfoBean != null) {
            tvResumeJobState.setText(ResumeInfoIDToString.getCurrentState(getActivity(), orderInfoBean.get(0).getCurrent_workstate(), true));
            tvResumeJobOrder.setText("[" + ResumeInfoIDToString.getIndustry(getActivity(), orderInfoBean.get(0).getOrder_industry().get(0).getIndustry(), true) + "]" + FromStringToArrayList.getInstance().getExpectPositionString(orderInfoBean.get(0).getOrder_industry().get(0).getIndustry(), orderInfoBean.get(0).getOrder_industry().get(0).getFunc()));
            sUtils.setStringValue(Constants.INDUSTRY_ID, orderInfoBean.get(0).getIndustry());
            if (isCanFresh == true) {
                sUtils.setBooleanValue(Constants.IS_FERSH, false);
                HomeFragment.instance.refresh(false);
            }
        }
    }

    private void initEducation() {
        llResumeEduBGList.removeAllViewsInLayout();
        if (educationListBeanList != null && educationListBeanList.size() != 0) {
            //学校+专业+学历的长度
            int length = educationListBeanList.get(0).getSchoolname().length() + educationListBeanList.get(0).getMoremajor().length() + ResumeInfoIDToString.getEducationDegree(getActivity(), educationListBeanList.get(0).getDegree(), true).length();
            String schoolName = educationListBeanList.get(0).getSchoolname();
            String moreMajor = educationListBeanList.get(0).getMoremajor();
            String degree = ResumeInfoIDToString.getEducationDegree(getActivity(), educationListBeanList.get(0).getDegree(), true);
            if (length > 18) {
                if (schoolName.length() > 8) {
                    schoolName = schoolName.substring(0, 7) + "...";
                }
                if (moreMajor.length() > 8) {
                    moreMajor = moreMajor.substring(0, 7) + "...";
                }
                tvResumeEduInfo.setText(schoolName + "  " + moreMajor + "  " + degree);
            } else {
                tvResumeEduInfo.setText(schoolName + "  " + moreMajor + "  " + degree);
            }
            llResumeEduBGList.setVisibility(View.VISIBLE);
            for (int i = 0; i < educationListBeanList.size(); i++) {
                View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setText(educationListBeanList.get(i).getSchoolname());
                tvResumeName2.setText(educationListBeanList.get(i).getFromyear() + "." + educationListBeanList.get(i).getFrommonth() + "-" + educationListBeanList.get(i).getToyear() + "." + educationListBeanList.get(i).getTomonth());
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResumeEducationActivity.startAction(getActivity(), educationListBeanList.get(finalI).getEducation_id(), educationListBeanList.size());
                    }
                });
                llResumeEduBGList.addView(plantView);
            }
        } else {
            llResumeEduBGList.setVisibility(View.GONE);
        }
    }

    private void initWorkExp() {
        llResumeWorkExpList.removeAllViewsInLayout();
        if (experienceListBeanList != null && experienceListBeanList.size() != 0) {
            llResumeWorkExpList.setVisibility(View.VISIBLE);
            for (int i = 0; i < experienceListBeanList.size(); i++) {
                View plantView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_resumeitem, null);
                // initview
                RelativeLayout rlResumeItem = plantView.findViewById(R.id.rl_ResumeItem);
                TextView tvResumeName = plantView.findViewById(R.id.tv_resumeItemName);
                TextView tvResumeName2 = plantView.findViewById(R.id.tv_resumeItemName2);
                tvResumeName.setText(experienceListBeanList.get(i).getCompany());
                String startTime = experienceListBeanList.get(i).getFromyear() + "." + experienceListBeanList.get(i).getFrommonth();
                String endTime = experienceListBeanList.get(i).getToyear() + "." + experienceListBeanList.get(i).getTomonth();
                if ("0.0".equals(endTime)) {
                    endTime = "至今";
                }
                tvResumeName2.setText(startTime + "-" + endTime);
                final int finalI = i;
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResumeWorkExpActivity.startAction(getActivity(), experienceListBeanList.get(finalI).getExperience_id(), experienceListBeanList.size());
                    }
                });
                llResumeWorkExpList.addView(plantView);
            }
        } else {
            llResumeWorkExpList.setVisibility(View.GONE);
        }
    }


    private void takePhoto() {
        final View popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_takephoto, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        TextView tvTakePhoto = popView.findViewById(R.id.tv_takePhoto);
        TextView tvSelectPicture = popView.findViewById(R.id.tv_selectPicture);
        TextView tvCancel = popView.findViewById(R.id.tv_cancelSelect);
        FrameLayout rl_takePhoto = popView.findViewById(R.id.rl_popTakePhoto);
        rl_takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
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
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                /*CompanyDetailActivity.startAction(getActivity());*/
            }
        });
        tvSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
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
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_resume, null);
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
            // System.out.println(imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] bt = stream.toByteArray();
            String bt1 = Base64.encodeToString(bt, Base64.DEFAULT);
            String content = URLEncoder.encode(bt1);
            //Log.i("数据",en);
            mPresenter.upLoadImage(content);
        }
    }

    @OnClick({R.id.tv_addResumeEduBG,R.id.iv_noNetError, R.id.tv_refreshResume, R.id.rl_hideResume, R.id.tv_resumeAddWorkExp, R.id.tv_resumePreview, R.id.tv_addResumeProjectExp, R.id.tv_addResumeProfessionSkill, R.id.tv_addResumeLanguageSkill, R.id.tv_addResumeTrainExp, R.id.tv_addResumeIntroduction, R.id.tv_addResumeAttachment, R.id.iv_resumePersonImage, R.id.rl_resumeJobOrder, R.id.iv_editResumePersonal, R.id.ll_resumeEduBGList, R.id.ll_resumeWorkExpList, R.id.ll_resumeProjectExpLIst, R.id.ll_resumeProfessionSkillList, R.id.ll_resumeLanguageSkillList, R.id.ll_resumeTrainExpList, R.id.ll_resumeIntroductionList, R.id.ll_resumeAttachmentList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_noNetError:
                mPresenter.getResume(resumeId+"",true);
                break;
            case R.id.tv_refreshResume:
                mPresenter.refreshResume(resumeId + "");
                break;
            case R.id.rl_hideResume:
                doUpdateResume();
                break;
            case R.id.iv_resumePersonImage:
                takePhoto();
                break;
            case R.id.tv_resumePreview:
                PreviewResumeActivity.startAction(getActivity(), resumeInfoBean);
                break;
            case R.id.rl_resumeJobOrder:
                ResumeJobOrderActivity.startAction(getActivity());
                break;
            case R.id.iv_editResumePersonal:
                ResumePersonalInfoActivity.startAction(getActivity());
                break;
            case R.id.ll_resumeEduBGList:
                ResumeEducationActivity.startAction(getActivity());
                break;
            case R.id.ll_resumeWorkExpList:
                ResumeWorkExpActivity.startAction(getActivity());
                break;
            case R.id.ll_resumeProjectExpLIst:
                break;
            case R.id.ll_resumeProfessionSkillList:
                break;
            case R.id.ll_resumeLanguageSkillList:
                break;
            case R.id.ll_resumeTrainExpList:
                break;
            case R.id.ll_resumeIntroductionList:
                break;
            case R.id.ll_resumeAttachmentList:
                break;
            case R.id.tv_addResumeEduBG:
                ResumeEducationActivity.startAction(getActivity());
                break;
            case R.id.tv_resumeAddWorkExp:
                ResumeWorkExpActivity.startAction(getActivity());
                break;
            case R.id.tv_addResumeProjectExp:
                ResumeProjectExpActivity.startAction(getActivity());
                break;
            case R.id.tv_addResumeProfessionSkill:
                ResumeProfessionSkillActivity.startAction(getActivity());
                break;
            case R.id.tv_addResumeLanguageSkill:
                ResumeLanguageActivity.startAction(getActivity());
                break;
            case R.id.tv_addResumeTrainExp:
                ResumeTrainActivity.startAction(getActivity());
                break;
            case R.id.tv_addResumeIntroduction:
                ResumeIntroductionActivity.startAction(getActivity());
                break;
            case R.id.tv_addResumeAttachment:
                break;
        }
    }

    private void doUpdateResume() {
        dialog = new MyDialog(getActivity(), 2);
        dialog.setMessage(getString(R.string.updateMessage));
        dialog.setYesOnclickListener(getString(R.string.sure), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                mPresenter.updateResume(resumeId + "");
                dialog.dismiss();
            }
        });
        dialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void refresh(){
        mPresenter.getResume(resumeId+"",false);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isFlesh = true;
                // 设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false。
                mPresenter.getResume(resumeId + "", false);
                if (srlResume != null) {
                    srlResume.setRefreshing(false);
                }
            }
        }, 2000);
    }
}
