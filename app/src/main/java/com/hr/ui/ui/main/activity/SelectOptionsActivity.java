package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.hr.ui.bean.EvenList;
import com.hr.ui.bean.EventHomeBean;
import com.hr.ui.bean.EventJobOrderResume;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.adapter.MySelectPositionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectPositionRightAdapter;
import com.hr.ui.ui.main.adapter.SelectIndustryAdapter;
import com.hr.ui.ui.main.fragment.FunctionFragment;
import com.hr.ui.ui.main.fragment.HomeFragment;
import com.hr.ui.ui.main.fragment.IndustryFragment;
import com.hr.ui.ui.main.fragment.PositionFragment;
import com.hr.ui.utils.PopupWindowPositonClassView;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyDrawLayout2;
import com.hr.ui.view.MyFlowLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_selectIndustry)
    TextView tvSelectIndustry;
    @BindView(R.id.iv_selectIndustry)
    ImageView ivSelectIndustry;
    @BindView(R.id.rl_selectIndustry)
    RelativeLayout rlSelectIndustry;
    @BindView(R.id.tv_selectTerritory)
    TextView tvSelectTerritory;
    @BindView(R.id.iv_selectTerritory)
    ImageView ivSelectTerritory;
    @BindView(R.id.rl_selectTerritory)
    RelativeLayout rlSelectTerritory;
    @BindView(R.id.tv_selectJob)
    TextView tvSelectJob;
    @BindView(R.id.iv_selectJob)
    ImageView ivSelectJob;
    @BindView(R.id.rl_selectJob)
    RelativeLayout rlSelectJob;
    @BindView(R.id.ll_selectJobOrder)
    LinearLayout llSelectJobOrder;
    @BindView(R.id.view_selectJobOrder)
    View viewSelectJobOrder;
    @BindView(R.id.cl_selectOptions)
    ConstraintLayout clSelectOptions;
    private String indutryId, industryIdFir; //选择的行业id
    private List<String> industryIds;
    private List<CityBean> selectRealFuncList = new ArrayList<>();
    private List<CityBean> selectRealPositionList = new ArrayList<>();
    private int  type,updatePositionNum,mIndex;
    private SharedPreferencesUtils sUtis;
    private List<Fragment> fragmentList;
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
        EventBus.getDefault().register(this);
        sUtis = new SharedPreferencesUtils(this);
        industryIdFir = getIntent().getStringExtra("industryId");
        indutryId = industryIdFir;
        updatePositionNum = getIntent().getIntExtra("num", 100);
        selectRealPositionList = (List<CityBean>) getIntent().getSerializableExtra("position");
        selectRealFuncList = (List<CityBean>) getIntent().getSerializableExtra("func");
        industryIds = (List<String>) getIntent().getSerializableExtra("industryIds");
        type = getIntent().getIntExtra("type", 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.selectedExpectPosition);
        tvToolbarSave.setText(R.string.save);
        if(updatePositionNum==100) {
            tvToolbarSave.setVisibility(View.GONE);
        }else{
            tvToolbarSave.setVisibility(View.VISIBLE);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initFragment();
    }

    private void initFragment() {
        fragmentList=new ArrayList<>();
        IndustryFragment industryFragment=IndustryFragment.newInstance();
        Bundle bundle=new Bundle();
        bundle.putString("industryId",indutryId);
        bundle.putStringArrayList("industryIds", (ArrayList<String>) industryIds);
        bundle.putInt("position",updatePositionNum);
        industryFragment.setArguments(bundle);

        FunctionFragment functionFragment=FunctionFragment.newInstance();
        bundle=new Bundle();
        bundle.putString("industryId",indutryId);
        bundle.putSerializable("selectFunction", (Serializable) selectRealFuncList);
        functionFragment.setArguments(bundle);

        PositionFragment positionFragment=PositionFragment.newInstance();
        bundle=new Bundle();
        bundle.putString("industryId",indutryId);
        bundle.putSerializable("selectPosition", (Serializable) selectRealPositionList);
        positionFragment.setArguments(bundle);

        fragmentList.add(industryFragment);
        fragmentList.add(functionFragment);
        fragmentList.add(positionFragment);
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.ll_resumeJobOrder, industryFragment).commit();
        //默认设置为第0个
        setIndexSelected(0);
        setTitleColorAndIcon(1);
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(fragmentList.get(mIndex));
        //判断是否添加
        if (!fragmentList.get(index).isAdded()) {
            ft.add(R.id.ll_resumeJobOrder, fragmentList.get(index)).show(fragmentList.get(index));
        } else {
            ft.show(fragmentList.get(index));
        }
        ft.commitAllowingStateLoss();
        //再次赋值
        mIndex = index;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_selectIndustry, R.id.tv_toolbarSave, R.id.rl_selectTerritory, R.id.rl_selectJob})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_toolbarSave:
                doSave();
                break;
            case R.id.rl_selectIndustry:
                setIndexSelected(0);
                setTitleColorAndIcon(1);
                EventBus.getDefault().post(new EventJobOrderResume(0,indutryId,industryIds,updatePositionNum));
                break;
            case R.id.rl_selectTerritory:
                if (indutryId != null && !"".equals(indutryId)) {
                    goToFunction();
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
            case R.id.rl_selectJob:
                if (indutryId != null && !"".equals(indutryId)) {
                    goToPosition();
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
        }
    }

    private void goToPosition() {
        EventBus.getDefault().post(new EventJobOrderResume(2,indutryId,selectRealPositionList));
        Bundle bundle=new Bundle();
        bundle=new Bundle();
        bundle.putString("industryId",indutryId);
        bundle.putInt("position",updatePositionNum);
        bundle.putSerializable("selectPosition", (Serializable) selectRealPositionList);
        fragmentList.get(2).setArguments(bundle);
        setIndexSelected(2);
        setTitleColorAndIcon(3);
    }

    private void goToFunction() {
        if (FromStringToArrayList.getInstance().getIndustryIsHaveField(indutryId)) {
            Bundle bundle = new Bundle();
            bundle.putString("industryId", indutryId);
            fragmentList.get(1).setArguments(bundle);
            bundle.putInt("position", updatePositionNum);
            bundle.putSerializable("selectFunction", (Serializable) selectRealFuncList);
            EventBus.getDefault().post(new EventJobOrderResume(1, indutryId, selectRealFuncList));
            setIndexSelected(1);
            setTitleColorAndIcon(2);
        }else{
            goToPosition();
        }
    }

    private void doSave() {
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
                EventBus.getDefault().post(new EvenList(4, updatePositionNum, indutryId, selectRealFuncList, selectRealPositionList));
            } else {
                if (selectRealPositionList == null || "".equals(selectRealPositionList) || selectRealPositionList.size() == 0) {
                    ToastUitl.showShort("请选择职位");
                    return;
                }
                EventBus.getDefault().post(new EvenList(4, updatePositionNum, indutryId, selectRealFuncList, selectRealPositionList));
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
                    EventBus.getDefault().post(new EvenList(5, indutryId, selectRealFuncList, selectRealPositionList));
                } else {
                    if (indutryId == null && "".equals(indutryId)) {
                        ToastUitl.showShort("请选择行业");
                        return;
                    }
                    if (selectRealPositionList == null || "".equals(selectRealPositionList) || selectRealPositionList.size() == 0) {
                        ToastUitl.showShort("请选择职位");
                        return;
                    }
                    EventBus.getDefault().post(new EvenList(5, indutryId, selectRealFuncList, selectRealPositionList));
                }
            }
        }
        sUtis.setBooleanValue(Constants.IS_NEEDSAVEWARNNING, true);
        finish();
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
     * 3.IndustryFragment传递过来的数据
     * 4.FunctionFragment传递过来的数据
     * 5.PositionFragment传递过来的数据
     * 6.7.添加数据是执行下一步
     * 8.添加数据保存
     * @param eventJobOrderResume
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMethod(EventJobOrderResume eventJobOrderResume){
        switch (eventJobOrderResume.getType()){
            case 3:
                String industryId=eventJobOrderResume.getIndustryId();
                if(indutryId==null||"".equals(indutryId)||!indutryId.equals(industryId)){
                    selectRealFuncList=new ArrayList<>();
                    selectRealPositionList=new ArrayList<>();
                }
                indutryId=industryId;
                industryIdFir=industryId;

                if(FromStringToArrayList.getInstance().getIndustryIsHaveField(indutryId)==true){
                    tvSelectTerritory.setPaintFlags(0); //中间横线
                    tvSelectTerritory.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                    rlSelectTerritory.setClickable(true);
                }else{
                    tvSelectTerritory.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);//中间横线
                    rlSelectTerritory.setClickable(false);
                }
                break;
            case 4:
                selectRealFuncList=eventJobOrderResume.getSelectList();
                break;
            case 5:
                selectRealPositionList=eventJobOrderResume.getSelectList();
                break;
            case 6:
                goToFunction();
                break;
            case 7:
                goToPosition();
                break;
            case 8:
                doSave();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
