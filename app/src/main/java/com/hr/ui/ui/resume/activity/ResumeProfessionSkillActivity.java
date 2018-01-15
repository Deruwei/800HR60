package com.hr.ui.ui.resume.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.ProfessionSkillData;
import com.hr.ui.bean.ResumeProfessionSkillBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.resume.contract.ResumeProfessionSkillContract;
import com.hr.ui.ui.resume.model.ResumeProfessionSkillModel;
import com.hr.ui.ui.resume.presenter.ResumeProfessionSkillPresenter;
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

public class ResumeProfessionSkillActivity extends BaseActivity<ResumeProfessionSkillPresenter, ResumeProfessionSkillModel> implements ResumeProfessionSkillContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeProfessionSkillNameTag)
    TextView tvResumeProfessionSkillNameTag;
    @BindView(R.id.et_resumeProjectExpName)
    EditText etResumeProjectExpName;
    @BindView(R.id.iv_resumeProfessionSkillNameDelete)
    ImageView ivResumeProfessionSkillNameDelete;
    @BindView(R.id.tv_resumeProfessionSkillUseTimeTag)
    TextView tvResumeProfessionSkillUseTimeTag;
    @BindView(R.id.et_resumeProjectExpUseTime)
    EditText etResumeProjectExpUseTime;
    @BindView(R.id.iv_resumeProfessionSkillUseTimeSelect)
    ImageView ivResumeProfessionSkillUseTimeSelect;
    @BindView(R.id.tv_resumeProfessionSkillLevelTag)
    TextView tvResumeProfessionSkillLevelTag;
    @BindView(R.id.et_resumeProjectExpLevel)
    TextView etResumeProjectExpLevel;
    @BindView(R.id.iv_resumeProfessionSkillLevelDelete)
    ImageView ivResumeProfessionSkillLevelDelete;
    @BindView(R.id.btn_resumeProfessionSkillOK)
    Button btnResumeProfessionSkillOK;
    @BindView(R.id.tv_resumeProfessionSkillDelete)
    TextView tvResumeProfessionSkillDelete;
    @BindView(R.id.rl_resumeProfessionSkillLevel)
    RelativeLayout rlResumeProfessionSkillLevel;
    @BindView(R.id.cl_resumeProfessionSkill)
    ConstraintLayout clResumeProfessionSkill;
    private CustomDatePicker datePickerSkillLevel;
    private String skillLevelId;
    private String skillId;
    private SharedPreferencesUtils sUtils;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeProfessionSkillActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String skillId) {
        Intent intent = new Intent(activity, ResumeProfessionSkillActivity.class);
        intent.putExtra("skillId", skillId);
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
    public void getSkillSuccess(ResumeProfessionSkillBean.SkillListBean skillBean) {
        initUI(skillBean);
    }

    private void initUI(ResumeProfessionSkillBean.SkillListBean skillBean) {
        etResumeProjectExpName.setText(skillBean.getSkilltitle());
        etResumeProjectExpUseTime.setText(skillBean.getUsetime());
        etResumeProjectExpLevel.setText(ResumeInfoIDToString.getSkillLevel(this, skillBean.getAbility(), true));
        skillLevelId = skillBean.getAbility();
        ivResumeProfessionSkillNameDelete.setVisibility(View.GONE);
        ivResumeProfessionSkillUseTimeSelect.setVisibility(View.GONE);
    }

    @Override
    public void deleteSkillSuccess() {
        ToastUitl.showShort(R.string.deleteSuccess);
        sUtils.setBooleanValue(Constants.IS_FERSH, true);
        finish();
    }

    @Override
    public void addOrReplaceSkillSuccess() {
        ToastUitl.showShort(R.string.saveSuccess);
        sUtils.setBooleanValue(Constants.IS_FERSH, true);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resumeprofessionskill;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        sUtils = new SharedPreferencesUtils(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        skillId = getIntent().getStringExtra("skillId");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.professionSkill);
        ivResumeProfessionSkillNameDelete.setVisibility(View.GONE);
        ivResumeProfessionSkillUseTimeSelect.setVisibility(View.GONE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!"".equals(skillId) && skillId != null) {
            mPresenter.getSkill(skillId);
        }else{
            tvResumeProfessionSkillDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initDlalog();
        textChanged();
    }
    private void setFocus(){
        clResumeProfessionSkill.setFocusableInTouchMode(true);
        clResumeProfessionSkill.setFocusable(true);
        clResumeProfessionSkill.requestFocus();
        clResumeProfessionSkill.findFocus();
    }
    private void textChanged() {
        etResumeProjectExpName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeProfessionSkillNameDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeProjectExpName.getText().toString() != null && !"".equals(etResumeProjectExpName.getText().toString())) {
                        ivResumeProfessionSkillNameDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeProjectExpName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeProfessionSkillNameDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeProfessionSkillNameDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResumeProjectExpUseTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeProfessionSkillUseTimeSelect.setVisibility(View.GONE);
                } else {
                    if (etResumeProjectExpUseTime.getText().toString() != null && !"".equals(etResumeProjectExpUseTime.getText().toString())) {
                        ivResumeProfessionSkillUseTimeSelect.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeProjectExpLevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeProfessionSkillUseTimeSelect.setVisibility(View.VISIBLE);
                } else {
                    ivResumeProfessionSkillUseTimeSelect.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initDlalog() {
        datePickerSkillLevel = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etResumeProjectExpLevel.setText(time);
                skillLevelId = ResumeInfoIDToString.getSkillLevelId(ResumeProfessionSkillActivity.this, time);
            }
        }, getResources().getStringArray(R.array.array_skilllevel_zh));
    }

    @OnClick({R.id.iv_resumeProfessionSkillNameDelete, R.id.iv_resumeProfessionSkillUseTimeSelect, R.id.rl_resumeProfessionSkillLevel, R.id.btn_resumeProfessionSkillOK, R.id.tv_resumeProfessionSkillDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_resumeProfessionSkillNameDelete:
                etResumeProjectExpName.setText("");
                break;
            case R.id.iv_resumeProfessionSkillUseTimeSelect:
                etResumeProjectExpUseTime.setText("");
                break;
            case R.id.rl_resumeProfessionSkillLevel:
                setFocus();
                datePickerSkillLevel.show(etResumeProjectExpLevel.getText().toString());
                break;
            case R.id.btn_resumeProfessionSkillOK:
                doAddOrReplaceProfessionSkill();
                break;
            case R.id.tv_resumeProfessionSkillDelete:
                mPresenter.deleteSkill(skillId);
                break;
        }
    }

    private void doAddOrReplaceProfessionSkill() {
        if (etResumeProjectExpName.getText().toString() == null || "".equals(etResumeProjectExpName.getText().toString())) {
            ToastUitl.showShort("请填写技能名称");
            return;
        }
        if (etResumeProjectExpUseTime.getText().toString() == null || "".equals(etResumeProjectExpUseTime.getText().toString())) {
            ToastUitl.showShort("请填写使用时间");
            return;
        }
        if (skillLevelId == null || "".equals(skillLevelId)) {
            ToastUitl.showShort("请选择专业水平");
            return;
        }
        ProfessionSkillData professionSkillData = new ProfessionSkillData();
        if (skillId != null && !"".equals(skillId)) {
            professionSkillData.setSkillId(skillId);
        }
        professionSkillData.setSkillName(etResumeProjectExpName.getText().toString());
        professionSkillData.setSkillLevel(skillLevelId);
        professionSkillData.setSkillUseTime(etResumeProjectExpUseTime.getText().toString());
        mPresenter.addOrReplaceSkill(professionSkillData);
    }
}
