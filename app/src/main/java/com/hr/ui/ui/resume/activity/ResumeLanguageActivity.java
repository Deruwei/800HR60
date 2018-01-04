package com.hr.ui.ui.resume.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.LanguageLevelData;
import com.hr.ui.bean.ResumeLanguageBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.resume.contract.ResumeLanguageContract;
import com.hr.ui.ui.resume.model.ResumeLanguageModel;
import com.hr.ui.ui.resume.presenter.ResumeLanguagePresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CustomDatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeLanguageActivity extends BaseActivity<ResumeLanguagePresenter, ResumeLanguageModel> implements ResumeLanguageContract.View {

    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeLanguageSkillLanguageTag)
    TextView tvResumeLanguageSkillLanguageTag;
    @BindView(R.id.et_resumeLanguageSkillLanguage)
    TextView etResumeLanguageSkillLanguage;
    @BindView(R.id.iv_resumeLanguageSkillLanguageSelect)
    ImageView ivResumeLanguageSkillLanguageSelect;
    @BindView(R.id.tv_resumeLanguageSkillListenSpeakTag)
    TextView tvResumeLanguageSkillListenSpeakTag;
    @BindView(R.id.et_resumeLanguageSkillListenSpeak)
    TextView etResumeLanguageSkillListenSpeak;
    @BindView(R.id.iv_resumeLanguageSkillListenSpeakSelect)
    ImageView ivResumeLanguageSkillListenSpeakSelect;
    @BindView(R.id.tv_resumeLanguageSkillReadWriteTag)
    TextView tvResumeLanguageSkillReadWriteTag;
    @BindView(R.id.et_resumeLanguageSkillReadWrite)
    TextView etResumeLanguageSkillReadWrite;
    @BindView(R.id.iv_resumeLanguageSkillReadWriteSelect)
    ImageView ivResumeLanguageSkillReadWriteSelect;
    @BindView(R.id.btn_resumeLanguageSkillOK)
    Button btnResumeLanguageSkillOK;
    @BindView(R.id.tv_resumeLanguageSkillDelete)
    TextView tvResumeLanguageSkillDelete;
    @BindView(R.id.rl_resumeLanguageSkillLanguage)
    RelativeLayout rlResumeLanguageSkillLanguage;
    @BindView(R.id.rl_resumeLanguageSkillListenSpeak)
    RelativeLayout rlResumeLanguageSkillListenSpeak;
    @BindView(R.id.rl_resumeLanguageSkillReadWrite)
    RelativeLayout rlResumeLanguageSkillReadWrite;
    private String languageId,speakId,writeId;
    private CustomDatePicker datePickerLanguageName,datePickerSpeak,datePickerWrite;
    private SharedPreferencesUtils sUtils;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeLanguageActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,String languageId) {
        Intent intent = new Intent(activity,ResumeLanguageActivity.class);
        intent.putExtra("languageId",languageId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
    public void getLanguageSuccess(ResumeLanguageBean.LanguageListBean languageBean) {
        initUi(languageBean);
    }

    private void initUi(ResumeLanguageBean.LanguageListBean languageBean) {
        etResumeLanguageSkillLanguage.setText(ResumeInfoIDToString.getLanguageTpye(this,languageBean.getLangname(),true));
        languageId=languageBean.getLangname();
        speakId=languageBean.getSpeak_level();
        writeId=languageBean.getRead_level();
        etResumeLanguageSkillListenSpeak.setText(ResumeInfoIDToString.getLanguageReadLevel(this,languageBean.getSpeak_level(),true));
        etResumeLanguageSkillReadWrite.setText(ResumeInfoIDToString.getLanguageReadLevel(this,writeId,true));
    }

    @Override
    public void deleteLanguageSuccess() {
        ToastUitl.showShort(R.string.deleteSuccess);
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        finish();
    }

    @Override
    public void addOrReplaceLanguageSuccess() {
        ToastUitl.showShort(R.string.saveSuccess);
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resumelanguageskill;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        sUtils=new SharedPreferencesUtils(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        languageId = getIntent().getStringExtra("languageId");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.languageSkill);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(!"".equals(languageId )&&languageId !=null){
            mPresenter.getLanguage(languageId);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initDialog();
    }

    private void initDialog() {
        datePickerLanguageName=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumeLanguageSkillLanguage.setText(time);
                languageId= ResumeInfoIDToString.getLanguageTpyeId(ResumeLanguageActivity.this,time);
            }
        },getResources().getStringArray(R.array.array_language_type_zh));
        datePickerSpeak=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumeLanguageSkillListenSpeak.setText(time);
                speakId=ResumeInfoIDToString.getLanguageSpeakLevelId(ResumeLanguageActivity.this,time);
            }
        },getResources().getStringArray(R.array.array_language_speaklevel_zh));
        datePickerWrite=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumeLanguageSkillReadWrite.setText(time);
                writeId=ResumeInfoIDToString.getLanguageSpeakLevelId(ResumeLanguageActivity.this,time);
            }
        },getResources().getStringArray(R.array.array_language_readlevel_zh));
    }

    @OnClick({R.id.rl_resumeLanguageSkillLanguage, R.id.rl_resumeLanguageSkillListenSpeak, R.id.rl_resumeLanguageSkillReadWrite, R.id.btn_resumeLanguageSkillOK, R.id.tv_resumeLanguageSkillDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_resumeLanguageSkillLanguage:
                datePickerLanguageName.show(etResumeLanguageSkillLanguage.getText().toString());
                break;
            case R.id.rl_resumeLanguageSkillListenSpeak:
                datePickerSpeak.show(etResumeLanguageSkillListenSpeak.getText().toString());
                break;
            case R.id.rl_resumeLanguageSkillReadWrite:
                datePickerWrite.show(etResumeLanguageSkillReadWrite.getText().toString());
                break;
            case R.id.btn_resumeLanguageSkillOK:
                doAddOrReplaceLanguage();
                break;
            case R.id.tv_resumeLanguageSkillDelete:
                if(!"".equals(languageId)&&languageId!=null) {
                    mPresenter.deleteLanguage(languageId);
                }else{
                    ToastUitl.showShort("请选择语言");
                    return;
                }
                break;
        }
    }

    private void doAddOrReplaceLanguage() {
        if(languageId==null||"".equals(languageId)){
            ToastUitl.showShort("请选择语言");
            return;
        }
        if(speakId==null||"".equals(speakId)){
            ToastUitl.showShort("请选择听说");
            return;
        }
        if("".equals(writeId)||writeId==null){
            ToastUitl.showShort("请选择读写");
            return;
        }
        LanguageLevelData languageLevelData=new LanguageLevelData();
        languageLevelData.setLanguageId(languageId);
        languageLevelData.setReadLevel(writeId);
        languageLevelData.setSpeakLevel(speakId);
        mPresenter.addOrReplace(languageLevelData);
    }
}
