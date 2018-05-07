package com.hr.ui.ui.resume.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/3.
 */

public class PreviewResumeActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_previewResumePhoto)
    CircleImageView ivPreviewResumePhoto;
    @BindView(R.id.iv_personalIcon)
    ImageView ivPersonalIcon;
    @BindView(R.id.tv_previewResumePersonName)
    TextView tvPreviewResumePersonName;
    @BindView(R.id.ll_previewResumePersonName)
    LinearLayout llPreviewResumePersonName;
    @BindView(R.id.tv_previewResumePersonSex)
    TextView tvPreviewResumePersonSex;
    @BindView(R.id.ll_previewResumePersonSex)
    LinearLayout llPreviewResumePersonSex;
    @BindView(R.id.tv_previewResumePersonBirth)
    TextView tvPreviewResumePersonBirth;
    @BindView(R.id.ll_previewResumePersonBirth)
    LinearLayout llPreviewResumePersonBirth;
    @BindView(R.id.tv_previewResumePersonLivePlace)
    TextView tvPreviewResumePersonLivePlace;
    @BindView(R.id.ll_previewResumePersonLivePlace)
    LinearLayout llPreviewResumePersonLivePlace;
    @BindView(R.id.tv_previewResumePersonStartToWork)
    TextView tvPreviewResumePersonStartToWork;
    @BindView(R.id.ll_previewResumePersonStartToWork)
    LinearLayout llPreviewResumePersonStartToWork;
    @BindView(R.id.tv_previewResumePersonFuncLevel)
    TextView tvPreviewResumePersonFuncLevel;
    @BindView(R.id.ll_previewResumePersonFuncLevel)
    LinearLayout llPreviewResumePersonFuncLevel;
    @BindView(R.id.tv_previewResumePersonPhone)
    TextView tvPreviewResumePersonPhone;
    @BindView(R.id.ll_previewResumePersonPhone)
    LinearLayout llPreviewResumePersonPhone;
    @BindView(R.id.tv_previewResumePersonEmail)
    TextView tvPreviewResumePersonEmail;
    @BindView(R.id.ll_previewResumePersonEmail)
    LinearLayout llPreviewResumePersonEmail;
    @BindView(R.id.ll_previewResumePerson)
    LinearLayout llPreviewResumePerson;
    @BindView(R.id.iv_jobOrderIcon)
    ImageView ivJobOrderIcon;
    @BindView(R.id.tv_previewResumeJobOrderWorkType)
    TextView tvPreviewResumeJobOrderWorkType;
    @BindView(R.id.ll_previewResumeJobOrderWorkType)
    LinearLayout llPreviewResumeJobOrderWorkType;
    @BindView(R.id.tv_previewResumeJobOrderWorkPlace)
    TextView tvPreviewResumeJobOrderWorkPlace;
    @BindView(R.id.ll_previewResumeJobOrderWorkPlace)
    LinearLayout llPreviewResumeJobOrderWorkPlace;
    @BindView(R.id.tv_previewResumeJobOrderExpertSalary)
    TextView tvPreviewResumeJobOrderExpertSalary;
    @BindView(R.id.ll_previewResumeJobOrderExpertSalary)
    LinearLayout llPreviewResumeJobOrderExpertSalary;
    @BindView(R.id.tv_previewResumeJobOrderWorkStyle)
    TextView tvPreviewResumeJobOrderWorkStyle;
    @BindView(R.id.ll_previewResumeJobOrderWorkStyle)
    LinearLayout llPreviewResumeJobOrderWorkStyle;
    @BindView(R.id.ll_previewResume_IndustryJobOrder)
    LinearLayout llPreviewResumeIndustryJobOrder;
    @BindView(R.id.iv_educationIcon)
    ImageView ivEducationIcon;
    @BindView(R.id.ll_previewResume_education)
    LinearLayout llPreviewResumeEducation;
    @BindView(R.id.iv_workExpIcon)
    ImageView ivWorkExpIcon;
    @BindView(R.id.ll_previewResume_workExp)
    LinearLayout llPreviewResumeWorkExp;
    @BindView(R.id.iv_languageSkillIcon)
    ImageView ivLanguageSkillIcon;
    @BindView(R.id.ll_previewResume_languageSkill)
    LinearLayout llPreviewResumeLanguageSkill;
    @BindView(R.id.iv_introductionIcon)
    ImageView ivIntroductionIcon;
    @BindView(R.id.tv_previewResume_introduction)
    TextView tvPreviewResumeIntroduction;
    @BindView(R.id.iv_projectExpIcon)
    ImageView ivProjectExpIcon;
    @BindView(R.id.ll_previewResume_projectExp)
    LinearLayout llPreviewResumeProjectExp;
    @BindView(R.id.iv_professionSkillIcon)
    ImageView ivProfessionSkillIcon;
    @BindView(R.id.ll_previewResume_professionSkill)
    LinearLayout llPreviewResumeProfessionSkill;
    @BindView(R.id.iv_plantExpIcon)
    ImageView ivPlantExpIcon;
    @BindView(R.id.ll_previewResume_plantExp)
    LinearLayout llPreviewResumePlantExp;
    @BindView(R.id.tv_previewResume_workExpTag)
    TextView tvPreviewResumeWorkExpTag;
    private ResumeBean.ResumeInfoBean resumeInfoBean;
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
    private SharedPreferencesUtils sUtils;
    private String resumeType;

    public static void startAction(Activity activity, ResumeBean.ResumeInfoBean resumeInfoBean) {
        Intent intent = new Intent(activity, PreviewResumeActivity.class);
        intent.putExtra("resumeData", (Serializable) resumeInfoBean);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_previewresume;
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
        tvToolbarTitle.setText(R.string.resumePreview);
        resumeType = sUtils.getStringValue(Constants.RESUME_TYPE, "");
        if("1".equals(resumeType)){
            tvPreviewResumeWorkExpTag.setText(getString(R.string.workExp));
        }else{
            tvPreviewResumeWorkExpTag.setText(getString(R.string.internshipExperience));
        }
        MobclickAgent.onEvent(this,"v6_scan_previewResume");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resumeInfoBean = (ResumeBean.ResumeInfoBean) getIntent().getSerializableExtra("resumeData");
        if (resumeInfoBean != null) {
            assessInfoBean = resumeInfoBean.getAssess_info();
            orderInfoBean = resumeInfoBean.getOrder_info();
            titleInfoBean = resumeInfoBean.getTitle_info();
            baseInfoBean = resumeInfoBean.getBase_info();
            educationListBeanList = resumeInfoBean.getEducation_list();
            experienceListBeanList = resumeInfoBean.getExperience_list();
            languageListBeanList = resumeInfoBean.getLanguage_list();
            plantListBeanList = resumeInfoBean.getPlant_list();
            skillListBeanList = resumeInfoBean.getSkill_list();
            projectListBeanList = resumeInfoBean.getProject_list();
            initBaseInfo();
            initJobOrder();
            initWorkExp();
            initEdu();
            initLanguage();
            initIntroduction();
            initProjectExp();
            initProfessionSkill();
            initPlantExp();
        }

    }

    private void initPlantExp() {
        if (plantListBeanList != null && plantListBeanList.size() != 0) {
            for (int i = 0; i < plantListBeanList.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_plantexp, null);
                // initview
                TextView tvTime = view.findViewById(R.id.tv_previewResumePlantExpTime);
                TextView tvInstruction = view.findViewById(R.id.tv_previewResumePlantExpInstruction);
                TextView tvClass = view.findViewById(R.id.tv_previewResumePlantExpClass);
                TextView tvPlace = view.findViewById(R.id.tv_previewResumePlantExpPlace);
                TextView tvVerti = view.findViewById(R.id.tv_previewResumePlantExpVerti);
                TextView tvDes = view.findViewById(R.id.tv_previewResumePlantExpDes);
                LinearLayout llPlantPlace=view.findViewById(R.id.ll_previewResumePlantPlace);
                LinearLayout llPlantCer=view.findViewById(R.id.ll_previewResumePlantCer);
                String endTime= plantListBeanList.get(i).getToyear() + "年" + plantListBeanList.get(i).getTomonth() + "月";
                if(endTime.equals("0年0月")){
                    endTime="至今";
                }
                tvTime.setText(plantListBeanList.get(i).getFromyear() + "年" + plantListBeanList.get(i).getFrommonth() + "月" + "—" +endTime);
                tvInstruction.setText(plantListBeanList.get(i).getInstitution());
                tvClass.setText(plantListBeanList.get(i).getCourse());
                if(plantListBeanList.get(i).getPlace()!=null&&!"".equals(plantListBeanList.get(i).getPlace())&&!"0".equals(plantListBeanList.get(i).getPlace())) {
                    llPlantPlace.setVisibility(View.VISIBLE);
                    tvPlace.setText(FromStringToArrayList.getInstance().getCityListName(plantListBeanList.get(i).getPlace()));
                }else{
                    llPlantPlace.setVisibility(View.GONE);
                }
                if(!"".equals(plantListBeanList.get(i).getCertification())&&plantListBeanList.get(i).getCertification()!=null) {
                    llPlantCer.setVisibility(View.VISIBLE);
                    tvVerti.setText(plantListBeanList.get(i).getCertification());
                }else{
                    llPlantCer.setVisibility(View.GONE);
                }
                tvDes.setText(plantListBeanList.get(i).getTraindetail());
                llPreviewResumePlantExp.addView(view);
            }
        }
    }

    private void initProfessionSkill() {
        if (skillListBeanList != null && skillListBeanList.size() != 0) {
            for (int i = 0; i < skillListBeanList.size(); i++) {
                View view=null;
                if(skillListBeanList.get(i).getSkilltitle().length()>12){
                    view = LayoutInflater.from(this).inflate(R.layout.item_professionskill2, null);
                }else {
                    view = LayoutInflater.from(this).inflate(R.layout.item_professionskill, null);
                }
                // initview
                TextView tvSkillName = view.findViewById(R.id.tv_previewResumeProfessionSkillName);
                TextView tvUseTime = view.findViewById(R.id.tv_previewResumeProfessionSkillUseTime);
                TextView tvSkillLevel = view.findViewById(R.id.tv_previewResumeProfessionSkillLevel);
                tvSkillName.setText(skillListBeanList.get(i).getSkilltitle());
                tvUseTime.setText(skillListBeanList.get(i).getUsetime() + "个月");
                tvSkillLevel.setText(ResumeInfoIDToString.getSkillLevel(this, skillListBeanList.get(i).getAbility(), true));
                llPreviewResumeProfessionSkill.addView(view);
            }
        }
    }

    private void initProjectExp() {
        if (projectListBeanList != null && projectListBeanList.size() != 0) {
            for (int i = 0; i < projectListBeanList.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_projectexp, null);
                // initview
                TextView tvTime = view.findViewById(R.id.tv_previewResumeProjectExpTime);
                TextView tvProjectName = view.findViewById(R.id.tv_previewResumeProjectExpName);
                TextView tvProjectPosition = view.findViewById(R.id.tv_previewResumeProjectExpPosition);
                TextView tvProjectDes = view.findViewById(R.id.tv_previewResumeProjectExpDes);
                TextView tvPositionResponsibility = view.findViewById(R.id.tv_previewResumeProjectExpResponsibility);
                String startTime=projectListBeanList.get(i).getFromyear() + "年" + projectListBeanList.get(i).getFrommonth() + "月";
                String endTime=projectListBeanList.get(i).getToyear() + "年" + projectListBeanList.get(i).getTomonth() + "月";
                if("0年0月".equals(endTime)){
                    endTime="至今";
                }
                tvTime.setText(startTime + "—" +endTime );
                tvProjectName.setText(projectListBeanList.get(i).getProjectname());
                tvProjectPosition.setText(projectListBeanList.get(i).getPosition());
                tvProjectDes.setText(projectListBeanList.get(i).getProjectdesc());
                tvPositionResponsibility.setText(projectListBeanList.get(i).getResponsibility());
                llPreviewResumeProjectExp.addView(view);
            }
        }
    }

    private void initIntroduction() {
        if (assessInfoBean != null && assessInfoBean.size() != 0) {
            tvPreviewResumeIntroduction.setText(assessInfoBean.get(0).getIntroduction());
        } else {
            tvPreviewResumeIntroduction.setVisibility(View.GONE);
        }
    }

    private void initLanguage() {
        if (languageListBeanList != null && languageListBeanList.size() != 0) {
            for (int i = 0; i < languageListBeanList.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_languageskill, null);
                // initview
                TextView tvLanguageName = view.findViewById(R.id.tv_previewResumeLanguageName);
                TextView tvLanguageSpeak = view.findViewById(R.id.tv_previewResumeSpeak);
                TextView tvLanguageWrite = view.findViewById(R.id.tv_previewResumeWrite);
                tvLanguageName.setText(ResumeInfoIDToString.getLanguageTpye(this, languageListBeanList.get(i).getLangname(), true));
                tvLanguageSpeak.setText("听说：" + ResumeInfoIDToString.getLanguageReadLevel(this, languageListBeanList.get(i).getSpeak_level(), true));
                tvLanguageWrite.setText("读写：" + ResumeInfoIDToString.getLanguageReadLevel(this, languageListBeanList.get(i).getRead_level(), true));
                llPreviewResumeLanguageSkill.addView(view);
            }
        }
    }

    private void initEdu() {
        if (educationListBeanList != null && educationListBeanList.size() != 0) {
            for (int i = 0; i < educationListBeanList.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_education, null);
                // initview
                TextView tvEducationTime = view.findViewById(R.id.tv_previewResumeEduTime);
                TextView tvSchoolName = view.findViewById(R.id.tv_previewResumeEduSchool);
                TextView tvdegree = view.findViewById(R.id.tv_previewResumeEduDegree);
                TextView tvMajor = view.findViewById(R.id.tv_previewResumeEduEducation);
                TextView tvDes = view.findViewById(R.id.tv_previewResumeEduDes);
                LinearLayout llprofessionDes=view.findViewById(R.id.ll_previewResumeProfessionDes);
                tvEducationTime.setText(educationListBeanList.get(i).getFromyear() + "年" + educationListBeanList.get(i).getFrommonth() + "月" + "—" + educationListBeanList.get(i).getToyear() + "年" + educationListBeanList.get(i).getTomonth() + "月");
                tvSchoolName.setText(educationListBeanList.get(i).getSchoolname());
                tvdegree.setText(ResumeInfoIDToString.getEducationDegree(this, educationListBeanList.get(i).getDegree(), true));
                tvMajor.setText(educationListBeanList.get(i).getMoremajor());
                if(educationListBeanList.get(i).getEdudetail()!=null&&!"".equals(educationListBeanList.get(i).getEdudetail())) {
                    llprofessionDes.setVisibility(View.VISIBLE);
                    tvDes.setText(educationListBeanList.get(i).getEdudetail());
                }else{
                    llprofessionDes.setVisibility(View.GONE);
                }
                llPreviewResumeEducation.addView(view);
            }
        }
    }

    private void initWorkExp() {
        if (experienceListBeanList != null && experienceListBeanList.size() != 0) {
            for (int i = 0; i < experienceListBeanList.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_workexp, null);
                // initview
                TextView tvTime = view.findViewById(R.id.tv_previewResumeWorkExpTime);
                TextView tvCompanyName = view.findViewById(R.id.tv_previewResumeWorkExpCompanyName);
                TextView tvPosition = view.findViewById(R.id.tv_previewResumeWorkExpPosition);
                TextView tvPlace = view.findViewById(R.id.tv_previewResumeWorkExpWorkPlace);
                TextView tvSalary = view.findViewById(R.id.tv_previewResumePositionSalary);
                TextView tvDes = view.findViewById(R.id.tv_previewResumePositionResponsibilityDes);
                TextView tvWorkPlaceTag = view.findViewById(R.id.tv_previewResumeWorkExpWorkPlaceTag);
                if ("0".equals(experienceListBeanList.get(i).getToyear()) && "0".equals(experienceListBeanList.get(i).getTomonth())) {
                    tvTime.setText(experienceListBeanList.get(i).getFromyear() + "年" + experienceListBeanList.get(i).getFrommonth() + "月" + "—" + "至今");
                } else {
                    tvTime.setText(experienceListBeanList.get(i).getFromyear() + "年" + experienceListBeanList.get(i).getFrommonth() + "月" + "—" + experienceListBeanList.get(i).getToyear() + "年" + experienceListBeanList.get(i).getTomonth() + "月");
                }
                if ("1".equals(resumeType)) {
                    tvWorkPlaceTag.setText(getString(R.string.workPlace));
                } else {
                    tvWorkPlaceTag.setText(getString(R.string.internshipPlace));
                }
                tvCompanyName.setText(experienceListBeanList.get(i).getCompany());
                tvPosition.setText(experienceListBeanList.get(i).getPosition());
                tvPlace.setText(FromStringToArrayList.getInstance().getCityListName(experienceListBeanList.get(i).getCompanyaddress()));
                tvSalary.setText(experienceListBeanList.get(i).getSalary());
                tvDes.setText(experienceListBeanList.get(i).getResponsiblity());
                llPreviewResumeWorkExp.addView(view);
            }
        }
    }

    private void initJobOrder() {
        tvPreviewResumeJobOrderWorkType.setText(ResumeInfoIDToString.getWorkType(this, orderInfoBean.get(0).getJobtype(), true));
        tvPreviewResumeJobOrderWorkStyle.setText(ResumeInfoIDToString.getCurrentState(this, orderInfoBean.get(0).getCurrent_workstate(), true));
        tvPreviewResumeJobOrderExpertSalary.setText(orderInfoBean.get(0).getOrder_salary());
        tvPreviewResumeJobOrderWorkPlace.setText(FromStringToArrayList.getInstance().getCityListName(orderInfoBean.get(0).getWorkarea()));
        List<ResumeBean.ResumeInfoBean.OrderInfoBean.OrderIndustryBean> industryListBean = orderInfoBean.get(0).getOrder_industry();
        llPreviewResumeIndustryJobOrder.removeAllViewsInLayout();
        for (int i = 0; i < industryListBean.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_joborder, null);
            // initview
            TextView tvIndustry = view.findViewById(R.id.tv_previewResumeJobOrderIndustryName);
            TextView tvFeild = view.findViewById(R.id.tv_previewResumeExpectField);
            TextView tvPosition = view.findViewById(R.id.tv_previewResumeExpectPosition);
            tvIndustry.setText(ResumeInfoIDToString.getIndustry(this, industryListBean.get(i).getIndustry(), true));
            tvFeild.setText(FromStringToArrayList.getInstance().getExpectFieldName(industryListBean.get(i).getIndustry(), industryListBean.get(i).getLingyu()));
            tvPosition.setText(FromStringToArrayList.getInstance().getExpectPositionString(industryListBean.get(i).getIndustry(), industryListBean.get(i).getFunc()));
            llPreviewResumeIndustryJobOrder.addView(view);
        }
    }

    private void initBaseInfo() {
        tvPreviewResumePersonName.setText(baseInfoBean.get(0).getName());
        tvPreviewResumePersonSex.setText(ResumeInfoIDToString.getSexName(this, baseInfoBean.get(0).getSex()));
        tvPreviewResumePersonBirth.setText(baseInfoBean.get(0).getYear() + "-" + baseInfoBean.get(0).getMonth() + "-" + baseInfoBean.get(0).getDay());
        tvPreviewResumePersonLivePlace.setText(FromStringToArrayList.getInstance().getCityListName(baseInfoBean.get(0).getLocation()));
        if ("-1".equals(baseInfoBean.get(0).getWork_beginyear())) {
            tvPreviewResumePersonStartToWork.setText("无工作经验");
        } else {
            tvPreviewResumePersonStartToWork.setText(baseInfoBean.get(0).getWork_beginyear() + "年");
        }
        tvPreviewResumePersonFuncLevel.setText(ResumeInfoIDToString.getZhiCheng(this, baseInfoBean.get(0).getPost_rank(), true) + "职称");
        tvPreviewResumePersonPhone.setText(baseInfoBean.get(0).getYdphone());
        tvPreviewResumePersonEmail.setText(baseInfoBean.get(0).getEmailaddress());
        if(baseInfoBean.get(0).getPic_filekey()!=null&&!"".equals(baseInfoBean.get(0).getPic_filekey())) {
            Glide.with(this).load(Constants.IMAGE_BASEPATH + baseInfoBean.get(0).getPic_filekey()).fitCenter().into(ivPreviewResumePhoto);
        }else{
            Glide.with(this).load(R.mipmap.persondefault).fitCenter().into(ivPreviewResumePhoto);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
