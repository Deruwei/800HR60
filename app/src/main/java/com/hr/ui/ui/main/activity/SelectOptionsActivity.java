package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.adapter.MySelectPositionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectPositionRightAdapter;
import com.hr.ui.ui.main.adapter.SelectIndustryAdapter;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.utils.PopupWindowPositonClassView;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/11/28.
 */

public class SelectOptionsActivity extends BaseActivity {

    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_selectIndustry)
    TextView tvSelectIndustry;
    @BindView(R.id.tv_selectTerritory)
    TextView tvSelectTerritory;
    @BindView(R.id.tv_selectJob)
    TextView tvSelectJob;
    @BindView(R.id.ll_selectJobOrder)
    LinearLayout llSelectJobOrder;
    @BindView(R.id.view_selectJobOrder)
    View viewSelectJobOrder;
    @BindView(R.id.tv_industryName)
    TextView tvIndustryName;
    @BindView(R.id.ll_industryItems)
    LinearLayout llIndustryItems;
    @BindView(R.id.rl_industry)
    RelativeLayout rlIndustry;
    @BindView(R.id.tv_territoryName)
    TextView tvTerritoryName;
    @BindView(R.id.gl_territoryItems)
    MyFlowLayout glTerritoryItems;
    @BindView(R.id.rl_territory)
    RelativeLayout rlTerritory;
    @BindView(R.id.tv_JobName)
    TextView tvJobName;
    @BindView(R.id.gl_jobItems)
    MyFlowLayout glJobItems;
    @BindView(R.id.rl_job)
    RelativeLayout rlJob;
    @BindView(R.id.iv_selectIndustry)
    ImageView ivSelectIndustry;
    @BindView(R.id.rl_selectIndustry)
    RelativeLayout rlSelectIndustry;
    @BindView(R.id.iv_selectTerritory)
    ImageView ivSelectTerritory;
    @BindView(R.id.rl_selectTerritory)
    RelativeLayout rlSelectTerritory;
    @BindView(R.id.iv_selectJob)
    ImageView ivSelectJob;
    @BindView(R.id.rl_selectJob)
    RelativeLayout rlSelectJob;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.btn_selectJobOrderSave)
    Button btnSelectJobOrderSave;
    @BindView(R.id.tv_selectJobOrderDelete)
    TextView tvSelectJobOrderDelete;
    @BindView(R.id.ll_selectBottom)
    LinearLayout llSelectBottom;
    @BindView(R.id.cl_selectOptions)
    ConstraintLayout clSelectOptions;
    private int num = 2;//每行的个数
    private PopupWindow popupWindowIndustry, popupWindowTerritory, popupWindowJob;
    private boolean isShowIndustry, isShowTerritory, isShowJob;
    private Button btnConfirm, btnCancel;
    private String indutryId, industryIdFir; //选择的行业id
    private MyFlowLayout flChooseContent;
    private RelativeLayout rlChooseJobContent;
    private LinearLayout llChooseJob;
    private TextView tvChooseJobNum;
    private ListView lvChooseJobLeft, lvChooseJobRight;
    private List<String> industryIds;
    private List<CityBean> selectFuncList = new ArrayList<>();//选择的领域集合
    private List<CityBean> selectRealFuncList = new ArrayList<>();
    private List<CityBean> PositionList = new ArrayList<>();//所有的职位集合
    private List<CityBean> PositionList1 = new ArrayList<>();//所有的职位集合
    private List<CityBean> positonLeftList, positionRightList;//listview左右的职位集合
    private List<CityBean> selectPositionList = new ArrayList<>();//选择的职位集合
    private List<CityBean> selectRealPositionList = new ArrayList<>();
    private List<CityBean> selectPositionClassList = new ArrayList<>();
    private int sum, type;//职位信息选择的个数
    private MySelectPositionLeftAdapter leftAdapter;
    private MySelectPositionRightAdapter rightAdapter;
    private int updatePositionNum;
    private SharedPreferencesUtils sUtis;
    private String canSelectOther;
    public static SelectOptionsActivity instance;
    private PopupWindow popupWindowPositonClass;
    private int currentJobPosition;
    private MyDialog dialog,dialogIndustry;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, List<String> industryIds, int i, String industry, List<CityBean> funList, List<CityBean> positionList) {
        Intent intent = new Intent(activity, SelectOptionsActivity.class);
        intent.putExtra("num", i);
        intent.putExtra("industryIds", (Serializable) industryIds);
        intent.putExtra("industryId", industry);
        intent.putExtra("func", (Serializable) funList);
        intent.putExtra("position", (Serializable) positionList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, List<String> industryIds, int i, int type) {
        Intent intent = new Intent(activity, SelectOptionsActivity.class);
        intent.putExtra("num", i);
        intent.putExtra("industryIds", (Serializable) industryIds);
        intent.putExtra("type", type);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_selectjoborder;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        instance = this;
        sUtis = new SharedPreferencesUtils(this);
        industryIdFir = getIntent().getStringExtra("industryId");
        indutryId = industryIdFir;
        updatePositionNum = getIntent().getIntExtra("num", 100);
        selectRealPositionList = (List<CityBean>) getIntent().getSerializableExtra("position");
        selectRealFuncList = (List<CityBean>) getIntent().getSerializableExtra("func");
        industryIds = (List<String>) getIntent().getSerializableExtra("industryIds");
        //Log.i("你好2",industryIds.toString());
        type = getIntent().getIntExtra("type", 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.selectedExpectPosition);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setUI();
    }

    private void setUI() {
        llIndustryItems.removeAllViewsInLayout();
        glTerritoryItems.removeAllViewsInLayout();
        glJobItems.removeAllViewsInLayout();
        if (indutryId != null && !"".equals(indutryId)) {
            rlIndustry.setVisibility(View.VISIBLE);
            addIndustryView();
            if (industryIds.size() == 1) {
                tvSelectJobOrderDelete.setVisibility(View.GONE);
            }
        } else {
            rlIndustry.setVisibility(View.GONE);
            tvSelectJobOrderDelete.setVisibility(View.GONE);
        }
        if (selectRealFuncList != null && !"".equals(selectRealFuncList) && selectRealFuncList.size() != 0 && !"".equals(selectRealFuncList.get(0).getId())) {
            rlTerritory.setVisibility(View.VISIBLE);
            addTerritoryView(selectRealFuncList);
        } else {
            rlTerritory.setVisibility(View.GONE);
        }
        if (selectRealPositionList != null && !"".equals(selectRealPositionList) && selectRealPositionList.size() != 0) {
            rlJob.setVisibility(View.VISIBLE);
            //Log.i("现在的数据",selectRealPositionList.toString());
            addJobView(selectRealPositionList);
        } else {
            rlJob.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance = this;
    }

    public void setPositionlassList(List<CityBean> selectPositionList) {
        this.selectPositionClassList = selectPositionList;
        doAddJobView(currentJobPosition, selectPositionClassList);
        selectPositionClassList.clear();
    }
    private void doDelete() {
        dialog=new MyDialog(this,2);
        dialog.setMessage(getString(R.string.sureDeleteJobOrder));
        dialog.setYesOnclickListener(getString(R.string.sure), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                ResumeJobOrderActivity.instance.deleteJobOrderInfo(updatePositionNum);
                dialog.dismiss();
                finish();
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
    @OnClick({R.id.rl_selectIndustry, R.id.btn_selectJobOrderSave, R.id.tv_selectJobOrderDelete, R.id.rl_selectTerritory, R.id.rl_selectJob})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_selectJobOrderSave:
                if (updatePositionNum != 100) {
                    if (FromStringToArrayList.getInstance().getIndustryIsHaveField(indutryId) == true) {
                        //Log.i("当前的行业id",indutryId+"-----");
                        if ("".equals(selectRealFuncList) || selectRealFuncList == null || selectRealFuncList.size() == 0 || "".equals(selectRealFuncList.get(0).getId())) {
                            ToastUitl.showShort("请选择领域");
                            return;
                        }
                        if (selectRealPositionList == null || "".equals(selectRealPositionList) || selectRealPositionList.size() == 0) {
                            ToastUitl.showShort("请选择职位");
                            return;
                        }
                        //Log.i("当前的行业",selectRealFuncList.toString());
                        ResumeJobOrderActivity.instance.updateJobOrderInfo(updatePositionNum, indutryId, selectRealFuncList, selectRealPositionList);
                    } else {
                        if (selectRealPositionList == null || "".equals(selectRealPositionList) || selectRealPositionList.size() == 0) {
                            ToastUitl.showShort("请选择职位");
                            return;
                        }
                        ResumeJobOrderActivity.instance.updateJobOrderInfo(updatePositionNum, indutryId, selectRealFuncList, selectRealPositionList);
                    }


                } else {
                    if (type == 1) {
                        if (FromStringToArrayList.getInstance().getIndustryIsHaveField(indutryId) == true) {
                            if (indutryId == null && "".equals(indutryId)) {
                                ToastUitl.showShort("请选择行业");
                                return;
                            }
                            if ("".equals(selectRealFuncList) || selectRealFuncList == null || selectRealFuncList.size() == 0) {
                                ToastUitl.showShort("请选择领域");
                                return;
                            }
                            if (selectRealPositionList == null || "".equals(selectRealPositionList) || selectRealPositionList.size() == 0) {
                                ToastUitl.showShort("请选择职位");
                                return;
                            }
                            ResumeJobOrderActivity.instance.addJobOrderInfo(indutryId, selectRealFuncList, selectRealPositionList);
                        } else {
                            if (indutryId == null && "".equals(indutryId)) {
                                ToastUitl.showShort("请选择行业");
                                return;
                            }
                            if (selectRealPositionList == null || "".equals(selectRealPositionList) || selectRealPositionList.size() == 0) {
                                ToastUitl.showShort("请选择职位");
                                return;
                            }
                            ResumeJobOrderActivity.instance.addJobOrderInfo(indutryId, selectRealFuncList, selectRealPositionList);
                        }
                    }
                }
                sUtis.setBooleanValue(Constants.IS_NEEDSAVEWARNNING, true);
                finish();
                break;
            case R.id.tv_selectJobOrderDelete:
                doDelete();
                break;
            case R.id.rl_selectIndustry:
                if (isShowIndustry == false) {
                    initIndustryPopupWindow();
                    if (popupWindowTerritory != null && popupWindowTerritory.isShowing() == true) {
                        popupWindowTerritory.dismiss();
                        isShowTerritory = !isShowTerritory;
                    }
                    if (popupWindowJob != null && popupWindowJob.isShowing() == true) {
                        popupWindowJob.dismiss();
                        isShowJob = !isShowJob;
                    }
                } else {
                    if (popupWindowIndustry != null) {
                        popupWindowIndustry.dismiss();
                    }
                    setTitleColorAndIcon(4);
                }
                isShowIndustry = !isShowIndustry;
                break;
            case R.id.rl_selectTerritory:
                if (indutryId != null&&!"".equals(indutryId)) {
                    if (FromStringToArrayList.getInstance().getIndustryIsHaveField(indutryId)) {
                        if (isShowTerritory == false) {
                            initTerritoryPopupWindow(indutryId);
                            if (popupWindowIndustry != null && popupWindowIndustry.isShowing() == true) {
                                popupWindowIndustry.dismiss();
                                isShowIndustry = !isShowIndustry;
                            }
                            if (popupWindowJob != null && popupWindowJob.isShowing() == true) {
                                popupWindowJob.dismiss();
                                isShowJob = !isShowJob;
                            }
                        } else {
                            if (popupWindowTerritory != null) {
                                popupWindowTerritory.dismiss();
                            }
                            setTitleColorAndIcon(4);
                        }
                        isShowTerritory = !isShowTerritory;
                    }
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
            case R.id.rl_selectJob:
                if (indutryId != null&&!"".equals(indutryId)) {
                    if (isShowJob == false) {
                        initJobPopupWindow();
                        if (popupWindowIndustry != null && popupWindowIndustry.isShowing() == true) {
                            popupWindowIndustry.dismiss();
                            isShowIndustry = !isShowIndustry;
                        }
                        if (popupWindowTerritory != null && popupWindowTerritory.isShowing() == true) {
                            popupWindowTerritory.dismiss();
                            isShowTerritory = !isShowTerritory;
                        }
                    } else {
                        if (popupWindowJob != null) {
                            popupWindowJob.dismiss();
                        }
                        setTitleColorAndIcon(4);
                    }
                    isShowJob = !isShowJob;
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
        }
    }

    /**
     * 添加行业视图
     */
    private void addIndustryView() {

        LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_industry, null, false);
        TextView tv_territory = ll.findViewById(R.id.tv_industryName);
        ImageView ivIndustryIcon = ll.findViewById(R.id.iv_industryImg);
        tv_territory.setText(ResumeInfoIDToString.getIndustry(this, indutryId, true));
        ivIndustryIcon.setImageDrawable(FromStringToArrayList.getInstance().getIndustryIcon(indutryId));
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogIndustry=new MyDialog(SelectOptionsActivity.this,2);
                dialogIndustry.setMessage("确定要删除["+FromStringToArrayList.getInstance().getIndustryName(indutryId)+"]行业吗？");
                dialogIndustry.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialogIndustry.dismiss();
                    }
                });
                dialogIndustry.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        llIndustryItems.removeView(llIndustryItems.findViewWithTag(indutryId));
                        industryIds.remove(indutryId);
                        indutryId = "";
                        selectRealFuncList=new ArrayList<>();
                        selectRealPositionList=new ArrayList<>();
                        glJobItems.removeAllViewsInLayout();
                        glTerritoryItems.removeAllViewsInLayout();
                        if (llIndustryItems.getChildCount() == 0) {
                            rlIndustry.setVisibility(View.GONE);
                        }
                        rlJob.setVisibility(View.GONE);
                        rlTerritory.setVisibility(View.GONE);
                        dialogIndustry.dismiss();
                    }
                });
                dialogIndustry.show();
            }
        });
        ll.setTag(indutryId);
        llIndustryItems.addView(ll);
    }

    /**
     * 添加领域视图
     *
     * @param funcList
     */
    private void addTerritoryView(final List<CityBean> funcList) {
        for (int i = 0; i < funcList.size(); i++) {
            rlTerritory.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            params.topMargin = 12;
            params.bottomMargin = 12;
            params.rightMargin = 15;
            final LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                    R.layout.item_textview_selected, null, false);
            ll.setLayoutParams(params);
            TextView tv = ll.findViewById(R.id.item_select);
            tv.setText(funcList.get(i).getName());
            final CityBean cityBean = funcList.get(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 /*   selectPositionList.remove(cityBean);*/
                    glTerritoryItems.removeView(glTerritoryItems.findViewWithTag(cityBean.getId()));
                    selectRealFuncList.remove(cityBean);
                    if (glTerritoryItems.getChildCount() == 0) {
                        rlTerritory.setVisibility(View.GONE);
                    }
                }
            });
            ll.setTag(funcList.get(i).getId());
            glTerritoryItems.addView(ll);
        }
    }

    /**
     * 添加职位视图
     *
     * @param selectPositionList
     */
    private void addJobView(final List<CityBean> selectPositionList) {
        for (int i = 0; i < selectPositionList.size(); i++) {
            rlJob.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            params.topMargin = 12;
            params.bottomMargin = 12;
            params.rightMargin = 15;
            final LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                    R.layout.item_textview_selected, null, false);
            ll.setLayoutParams(params);
            TextView tv = ll.findViewById(R.id.item_select);
            if (selectPositionList.get(i).getId().contains("|")) {
                if(selectPositionList.get(i).getName().contains("(")){
                    tv.setText(selectPositionList.get(i).getName());
                }else {
                    if(Utils.checkMedicinePositionClass2(selectPositionList.get(i))){
                        tv.setText(selectPositionList.get(i).getName() + "(" + "行政后勤" + ")");
                    }else {
                        tv.setText(selectPositionList.get(i).getName() + "(" + Utils.getPositionClassName(selectPositionList.get(i).getId().substring(selectPositionList.get(i).getId().indexOf("|") + 1)) + ")");
                    }
                }
            } else {
                tv.setText(selectPositionList.get(i).getName());
            }
            final int finalI = i;
            final CityBean cityBean = selectPositionList.get(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 /*   selectPositionList.remove(cityBean);*/
                    glJobItems.removeView(glJobItems.findViewWithTag(cityBean.getId()));
                    selectRealPositionList.remove(cityBean);
                    if (glJobItems.getChildCount() == 0) {
                        rlJob.setVisibility(View.GONE);
                    }
                }
            });
            ll.setTag(selectPositionList.get(i).getId());
            glJobItems.addView(ll);
        }
    }

    /**
     * 设置点击上面菜单的颜色以及图标的变化  1选择行业  2选择领域   3选择职位  4关闭页面
     *
     * @param style
     */
    private void setTitleColorAndIcon(int style) {
        if (style == 1) {
            tvSelectIndustry.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
            tvSelectTerritory.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            tvSelectJob.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            ivSelectIndustry.setImageResource(R.mipmap.up);
            ivSelectTerritory.setImageResource(R.mipmap.down);
            ivSelectJob.setImageResource(R.mipmap.down);
        } else if (style == 2) {
            tvSelectIndustry.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            tvSelectTerritory.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
            tvSelectJob.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            ivSelectIndustry.setImageResource(R.mipmap.down);
            ivSelectTerritory.setImageResource(R.mipmap.up);
            ivSelectJob.setImageResource(R.mipmap.down);
        } else if (style == 3) {
            tvSelectIndustry.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            tvSelectTerritory.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            tvSelectJob.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
            ivSelectIndustry.setImageResource(R.mipmap.down);
            ivSelectTerritory.setImageResource(R.mipmap.down);
            ivSelectJob.setImageResource(R.mipmap.up);
        } else if (style == 4) {
            tvSelectIndustry.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            tvSelectTerritory.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            tvSelectJob.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            ivSelectIndustry.setImageResource(R.mipmap.down);
            ivSelectTerritory.setImageResource(R.mipmap.down);
            ivSelectJob.setImageResource(R.mipmap.down);
        }
    }

    /**
     * 弹出选择行业的界面
     */
    private void initIndustryPopupWindow() {

        setTitleColorAndIcon(1);
        View viewIndustry = getLayoutInflater().inflate(R.layout.layout_chooseindustry, null);
        popupWindowIndustry = new PopupWindow(viewIndustry, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int[] location = new int[2];
        viewSelectJobOrder.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        popupWindowIndustry.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindowIndustry.setHeight(wm.getDefaultDisplay().getHeight() - y);
        RecyclerView rvIndustry = viewIndustry.findViewById(R.id.rv_chooseIndustry);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvIndustry.setLayoutManager(manager);
        final List<CityBean> industryList = FromStringToArrayList.getInstance().getIndustryList();
        if (indutryId != null && !"".equals(indutryId)) {
            for (int i = 0; i < industryList.size(); i++) {
                if (indutryId.equals(industryList.get(i).getId())) {
                    industryList.get(i).setCheck(true);
                }
            }
        }
        final SelectIndustryAdapter selectIndustryAdapter;
        //Log.i("你好2",industryList.toString());
        if (updatePositionNum != 100) {
            //Log.i("你好2","你好1");
            selectIndustryAdapter = new SelectIndustryAdapter(industryList, industryIdFir, SelectOptionsActivity.this, "1", industryIds);
        } else {
            //Log.i("你好2","你好2");
            selectIndustryAdapter = new SelectIndustryAdapter(industryList, industryIdFir, SelectOptionsActivity.this, "2", industryIds);
        }
        rvIndustry.setAdapter(selectIndustryAdapter);
        selectIndustryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (industryIdFir != null) {
                    for (int i = 0; i < industryIds.size(); i++) {
                        if (industryIdFir.equals(industryIds.get(i))) {
                            industryIds.remove(industryIds.get(i));
                            break;
                        }
                    }
                }
                if (!industryList.get(position).getId().equals(indutryId)) {
                    if (selectRealFuncList != null) {
                        selectRealFuncList.clear();
                    }
                    if (selectRealPositionList != null) {
                        selectRealPositionList.clear();
                    }
                    setUI();
                }
                indutryId = industryList.get(position).getId();
                if (FromStringToArrayList.getInstance().getIndustryIsHaveField(industryList.get(position).getId())) {
                    initTerritoryPopupWindow(indutryId);
                    tvSelectTerritory.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
                    tvSelectTerritory.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                    isShowTerritory = !isShowTerritory;
                } else {
                    tvSelectTerritory.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                    tvSelectTerritory.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    initJobPopupWindow();
                    isShowJob = !isShowJob;
                }
                selectIndustryAdapter.notifyDataSetChanged();
                popupWindowIndustry.dismiss();
                isShowIndustry = !isShowIndustry;
            }
        });
        popupWindowIndustry.setFocusable(false);
        popupWindowIndustry.setAnimationStyle(R.style.style_pop_animation);
        popupWindowIndustry.showAtLocation(viewSelectJobOrder, Gravity.NO_GRAVITY, 0, y);
    }

    /**
     * t弹出选择领域的界面
     *
     * @param industryId
     */
    private void initTerritoryPopupWindow(String industryId) {
        selectFuncList.clear();
        Log.i("当前的id",industryId+"");
        final List<CityBean> terrirotyList2 = FromStringToArrayList.getInstance().getExpectField(industryId);
        if (selectRealFuncList != null && !"".equals(selectRealFuncList)) {
            for (int i = 0; i < selectRealFuncList.size(); i++) {
                for (int j = 0; j < terrirotyList2.size(); j++) {
                    if (terrirotyList2.get(j).getId().equals(selectRealFuncList.get(i).getId())) {
                        terrirotyList2.get(j).setCheck(true);
                        selectFuncList.add(terrirotyList2.get(j));
                    }
                }
            }
        }
        setTitleColorAndIcon(2);
        View viewTerritory = getLayoutInflater().inflate(R.layout.layout_chooseterritory, null);
        popupWindowTerritory = new PopupWindow(viewTerritory, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int[] location = new int[2];
        viewSelectJobOrder.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        final List<CityBean> terrirotyList = FromStringToArrayList.getInstance().getExpectField(industryId);
        for (int i = 0; i < selectFuncList.size(); i++) {
            for (int j = 0; j < terrirotyList.size(); j++) {
                if (terrirotyList.get(j).getId().equals(selectFuncList.get(i).getId())) {
                    terrirotyList.get(j).setCheck(true);
                    continue;
                }
            }
        }
        RecyclerView rvTerritory = viewTerritory.findViewById(R.id.rv_chooseTerritory);
        Button btnOK = viewTerritory.findViewById(R.id.btn_confirm);
        Button btnCancle = viewTerritory.findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowTerritory.dismiss();
                isShowTerritory = !isShowTerritory;
                setTitleColorAndIcon(4);
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectFuncList == null || selectFuncList.size() == 0) {
                    ToastUitl.showShort("请选择领域");
                    return;
                } else {
                    popupWindowTerritory.dismiss();
                    isShowTerritory = !isShowTerritory;
                    setTitleColorAndIcon(4);
                    if (selectRealFuncList != null) {
                        selectRealFuncList.clear();
                    } else {
                        selectRealFuncList = new ArrayList<>();
                    }
                    for (int i = 0; i < selectFuncList.size(); i++) {
                        for (int j = 0; j < terrirotyList.size(); j++) {
                            if (terrirotyList.get(j).getId().equals(selectFuncList.get(i).getId())) {
                                selectRealFuncList.add(terrirotyList.get(j));
                                continue;
                            }
                        }
                    }
                    setUI();
                    initJobPopupWindow();
                    isShowJob = !isShowJob;
                }
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvTerritory.setLayoutManager(manager);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final SelectIndustryAdapter territoryAdapter = new SelectIndustryAdapter(terrirotyList, SelectOptionsActivity.this, 1);
        rvTerritory.setAdapter(territoryAdapter);
        territoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {

                if (terrirotyList.get(position).isCheck() == false) {
                    if (selectFuncList.size() >= 5) {
                        ToastUitl.showShort("最多只能选择5个领域");
                        return;
                    }
                    terrirotyList.get(position).setCheck(true);
                    selectFuncList.add(terrirotyList.get(position));
                } else {
                    for (int i = 0; i < selectFuncList.size(); i++) {
                        if (selectFuncList.get(i).getId().equals(terrirotyList.get(position).getId())) {
                            selectFuncList.remove(selectFuncList.get(i));
                        }
                    }
                    terrirotyList.get(position).setCheck(false);
                }
                territoryAdapter.notifyDataSetChanged();
            }
        });
        popupWindowTerritory.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindowTerritory.setHeight(wm.getDefaultDisplay().getHeight() - y);
        popupWindowTerritory.setFocusable(false);
        popupWindowTerritory.setAnimationStyle(R.style.style_pop_animation);
        popupWindowTerritory.showAtLocation(viewSelectJobOrder, Gravity.NO_GRAVITY, 0, y);
    }

    /**
     * 弹出选择职位的界面
     */
    private void initJobPopupWindow() {
        List<CityBean> positionList2 = FromStringToArrayList.getInstance().getExpectPosition(indutryId);
        //Log.i("当前的数据",selectRealPositionList.toString());
        selectPositionList = new ArrayList<>();
        if (selectRealPositionList != null && !"".equals(selectRealPositionList)) {
            for (int i = 0; i < selectRealPositionList.size(); i++) {
                for (int j = 0; j < positionList2.size(); j++) {
                    if ("14".equals(indutryId) && Utils.checkMedicinePositionClass(selectRealPositionList.get(i)) == true && selectRealPositionList.get(i).getId().contains("|")) {
                        if (positionList2.get(j).getId().equals(selectRealPositionList.get(i).getId().substring(0, selectRealPositionList.get(i).getId().indexOf("|")))) {
                            selectPositionList.add(selectRealPositionList.get(i));
                            continue;
                        }
                    } else {
                        if ("14".equals(indutryId) && Utils.checkMedicinePositionClass2(selectRealPositionList.get(i)) == true && selectRealPositionList.get(i).getId().contains("|")) {
                            if (positionList2.get(j).getId().equals(selectRealPositionList.get(i).getId().substring(0, selectRealPositionList.get(i).getId().indexOf("|")))) {
                                selectPositionList.add(selectRealPositionList.get(i));
                                continue;
                            }
                        }else {
                            if (positionList2.get(j).getId().equals(selectRealPositionList.get(i).getId())) {
                                positionList2.get(j).setCheck(true);
                                selectPositionList.add(positionList2.get(j));
                                continue;
                            }
                        }
                    }
                    //Log.i("现在的数据2", selectPositionList.toString());
                }
            }
        }
        setTitleColorAndIcon(3);
        View viewJob = getLayoutInflater().inflate(R.layout.layout_choosejob, null);
        popupWindowJob = new PopupWindow(viewJob, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rlChooseJobContent = viewJob.findViewById(R.id.rl_chosenJob);
        tvChooseJobNum = viewJob.findViewById(R.id.tv_chosenJobTitleNum);
        flChooseContent = viewJob.findViewById(R.id.fl_chooseJobContent);
        btnCancel = viewJob.findViewById(R.id.btn_cancel);
        btnConfirm = viewJob.findViewById(R.id.btn_confirm);
        llChooseJob = viewJob.findViewById(R.id.ll_chooseJob);
        lvChooseJobLeft = viewJob.findViewById(R.id.lv_chooseJobTitleMaxTerm);
        lvChooseJobRight = viewJob.findViewById(R.id.lv_chooseJobTitleMinTerm);
        int[] location = new int[2];
        viewSelectJobOrder.getLocationOnScreen(location);
        initChooseJobView(indutryId);
        int x = location[0];
        int y = location[1];
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowJob.dismiss();
                isShowJob = !isShowJob;
                setTitleColorAndIcon(4);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPositionList == null || selectPositionList.size() == 0) {
                    ToastUitl.showShort("请选择职位");
                    return;
                } else {
                    popupWindowJob.dismiss();
                    isShowJob = !isShowJob;
                    setTitleColorAndIcon(4);
                    if (selectRealPositionList != null) {
                        selectRealPositionList.clear();
                    } else {
                        selectRealPositionList = new ArrayList<>();
                    }

                    for (int i = 0; i < selectPositionList.size(); i++) {
                        CityBean cityBean = new CityBean();
                        cityBean.setId(selectPositionList.get(i).getId());
                        cityBean.setName(selectPositionList.get(i).getName());
                        cityBean.setCheck(true);
                        selectRealPositionList.add(cityBean);
                    }
                    setUI();
                }
            }
        });
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        popupWindowJob.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindowJob.setHeight(wm.getDefaultDisplay().getHeight() - y);
        popupWindowJob.setFocusable(false);
        popupWindowJob.setAnimationStyle(R.style.style_pop_animation);
        popupWindowJob.showAtLocation(viewSelectJobOrder, Gravity.NO_GRAVITY, 0, y);
    }

    private void initChooseJobView(String industryId) {
        PositionList = FromStringToArrayList.getInstance().getExpectPosition(industryId);
        PositionList1=FromStringToArrayList.getInstance().getExpectPosition(industryId);
        //System.out.println(positonBeanList.toString());
        positonLeftList = new ArrayList<>();
        for (int i = 0; i < PositionList1.size(); i++) {
            if (PositionList1.get(i).getId().endsWith("000")) {
                positonLeftList.add(PositionList1.get(i));
            }
        }
        //Log.i("现在的数据2",selectPositionList.toString());
        if (selectPositionList != null && selectPositionList.size() != 0) {
            for (int i = 0; i < selectPositionList.size(); i++) {
                addView(selectPositionList.get(i));
            }
        } else {
            rlChooseJobContent.setVisibility(View.GONE);
        }
        final Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
        lvChooseJobLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (positonLeftList.get(position).isCheck() == false) {
                    for (int i = 0; i < positonLeftList.size(); i++) {
                        positonLeftList.get(i).setCheck(false);
                    }
                    positonLeftList.get(position).setCheck(true);
                } else {
                    positonLeftList.get(position).setCheck(false);
                }
                positionRightList = new ArrayList<>();
                for (int i = 0; i < PositionList.size(); i++) {
                    if (positonLeftList.get(position).getId().substring(0, 3).equals(PositionList.get(i).getId().substring(0, 3))) {
                        PositionList.get(i).setCheck(false);
                        positionRightList.add(PositionList.get(i));
                    }
                }
                if (selectPositionList != null && selectPositionList.size() != 0) {
                    for (int i = 0; i < positionRightList.size(); i++) {
                        for (int j = 0; j < selectPositionList.size(); j++) {
                            if (selectPositionList.get(j).getId().contains("|")) {
                                //Log.i("数据",selectPositionList.get(j).toString());
                                if (positionRightList.get(i).getId().equals(selectPositionList.get(j).getId().substring(0, selectPositionList.get(j).getId().indexOf("|")))) {
                                    positionRightList.get(i).setCheck(true);
                                }
                            } else {
                                if (positionRightList.get(i).getId().equals(selectPositionList.get(j).getId())) {
                                    positionRightList.get(i).setCheck(true);
                                }
                            }
                        }
                    }
                }
                Message message1 = Message.obtain();
                message1.what = 2;
                handler.sendMessage(message1);
                leftAdapter.notifyDataSetChanged();
            }
        });
        lvChooseJobRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sum = selectPositionList.size();
                currentJobPosition = position;
                if (positionRightList.get(position).isCheck() == false) {
                    if (Utils.checkMedicinePositionClass(positionRightList.get(position)) == true) {
                        PopupWindowPositonClassView viewPositionClass = new PopupWindowPositonClassView(popupWindowPositonClass, SelectOptionsActivity.this, clSelectOptions);
                    } else {
                        doAddJobView(position, selectPositionClassList);
                    }
                } else {
                    positionRightList.get(position).setCheck(false);
                    for (int i = 0; i < selectPositionList.size(); i++) {
                        if (selectPositionList.get(i).getId().contains("|")) {
                            if (positionRightList.get(position).getId().equals(selectPositionList.get(i).getId().substring(0, selectPositionList.get(i).getId().indexOf("|")))) {
                                removeView(selectPositionList.get(i));
                                selectPositionList.remove(selectPositionList.get(i));
                                break;
                            }
                        }else {
                            if(positionRightList.get(position).getId().equals(selectPositionList.get(i).getId())) {
                                removeView(selectPositionList.get(i));
                                selectPositionList.remove(selectPositionList.get(i));
                                break;
                            }
                        }
                    }
                    setNum();
                }
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

    private void doAddJobView(int position, List<CityBean> selectPositionClassList) {
        sum = selectPositionList.size();
        if (sum == 0) {
            rlChooseJobContent.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            for (int i = 0; i < positionRightList.size(); i++) {
                positionRightList.get(i).setCheck(false);
            }
            List<CityBean> ints = new ArrayList<>();
            for (int i = 0; i < selectPositionList.size(); i++) {
                if (selectPositionList.get(i).getId().substring(0, 3).equals(positionRightList.get(position).getId().substring(0, 3))) {
                    removeView(selectPositionList.get(i));
                    selectPositionList.get(i).setId(selectPositionList.get(i).getId());
                    ints.add(selectPositionList.get(i));
                }
            }
            selectPositionList.removeAll(ints);
        } else {
            if (positionRightList.get(0).isCheck() == true) {
                for (int i = 0; i < selectPositionList.size(); i++) {
                    if (selectPositionList.get(i).getId().contains("|")) {
                        if (selectPositionList.get(i).getId().substring(0, selectPositionList.get(i).getId().indexOf("|")).equals(positionRightList.get(0).getId())) {
                            removeView(selectPositionList.get(i));
                            selectPositionList.remove(i);
                            break;
                        }
                    } else {
                        if (selectPositionList.get(i).getId().equals(positionRightList.get(0).getId())) {
                            removeView(selectPositionList.get(i));
                            selectPositionList.remove(i);
                            break;
                        }
                    }
                }
                positionRightList.get(0).setCheck(false);
            }
        }
        sum = selectPositionList.size();
        if (sum >= 5) {
            ToastUitl.showShort("最多只能选择5个职位");
            return;
        } else {
            CityBean cityBean = new CityBean();
            if (selectPositionClassList != null && selectPositionClassList.size() != 0) {
                cityBean.setId(positionRightList.get(position).getId() + "|" + selectPositionClassList.get(0).getId());
                cityBean.setName(positionRightList.get(position).getName());
                cityBean.setCheck(true);
                selectPositionList.add(cityBean);
            } else {
                if(Utils.checkMedicinePositionClass2(positionRightList.get(position))==true){
                        cityBean.setId(positionRightList.get(position).getId() + "|" + "10500");
                        cityBean.setName(positionRightList.get(position).getName());
                        cityBean.setCheck(true);
                        selectPositionList.add(cityBean);
                }else {
                    cityBean = positionRightList.get(position);
                    selectPositionList.add(positionRightList.get(position));
                }
            }
            addView(cityBean);
            positionRightList.get(position).setCheck(true);
        }
        rightAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }
    public void setPositionClassList(List<CityBean> selectPositionList) {
        this.selectPositionClassList = selectPositionList;
        doAddJobView(currentJobPosition, selectPositionClassList);
        selectPositionClassList.clear();
    }
    /**
     * 添加已选职位视图
     *
     * @param cityBean
     */
    private void addView(final CityBean cityBean) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 15;
        params.topMargin = 12;
        params.bottomMargin = 12;
        params.rightMargin = 15;
        final LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.item_textview_selected, null, false);
        ll.setLayoutParams(params);
        TextView tv = ll.findViewById(R.id.item_select);
        if (cityBean.getId().contains("|")) {
            if(cityBean.getName().contains("(")){
                tv.setText(cityBean.getName());
            }else {
                if (Utils.checkMedicinePositionClass2(cityBean) == true) {
                    tv.setText(cityBean.getName() + "(" + "行政后勤" + ")");
                } else {
                    tv.setText(cityBean.getName() + "(" + Utils.getPositionClassName(cityBean.getId().substring(cityBean.getId().indexOf("|") + 1)) + ")");

                }
            }
        } else {
            tv.setText(cityBean.getName());
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPositionList.remove(cityBean);
                flChooseContent.removeView(flChooseContent.findViewWithTag(cityBean.getId()));
                sum = selectPositionList.size();
                getRightDataFresh();
                if (sum == 0) {
                    rlChooseJobContent.setVisibility(View.GONE);
                }
                setNum();
            }
        });
        ll.setTag(cityBean.getId());
        flChooseContent.addView(ll);
        setNum();
    }

    private void getRightDataFresh() {
        if (positionRightList != null && positionRightList.size() != 0) {
            for (int i = 0; i < positionRightList.size(); i++) {
                positionRightList.get(i).setCheck(false);
                for (int j = 0; j < selectPositionList.size(); j++) {
                    if (positionRightList.get(i).getId().equals(selectPositionList.get(j).getId())) {
                        positionRightList.get(i).setCheck(true);
                    }
                }
            }
            rightAdapter.notifyDataSetChanged();
        }
    }

    private void removeView(CityBean cityBean) {
        flChooseContent.removeView(flChooseContent.findViewWithTag(cityBean.getId()));
        sum = selectPositionList.size();
        if (sum == 0) {
            rlChooseJobContent.setVisibility(View.GONE);
        }
    }

    private void setNum() {
        sum = selectPositionList.size();
        tvChooseJobNum.setText(sum + "");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    leftAdapter = new MySelectPositionLeftAdapter(positonLeftList);
                    lvChooseJobLeft.setAdapter(leftAdapter);
                    break;
                case 2:
                    rightAdapter = new MySelectPositionRightAdapter(positionRightList);
                    lvChooseJobRight.setAdapter(rightAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (popupWindowTerritory != null) {
            popupWindowTerritory.dismiss();
        }
        if (popupWindowJob != null) {
            popupWindowJob.dismiss();
        }
        if (popupWindowIndustry != null) {
            popupWindowIndustry.dismiss();
        }
        instance = null;
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
