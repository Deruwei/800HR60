package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.City;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.HotCity;
import com.hr.ui.bean.LocateState;
import com.hr.ui.bean.LocatedCity;
import com.hr.ui.db.CityUtils;
import com.hr.ui.ui.main.adapter.CityListAdapter;
import com.hr.ui.ui.main.adapter.InnerListener;
import com.hr.ui.ui.main.adapter.OnPickListener;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.decoration.DividerItemDecoration;
import com.hr.ui.utils.decoration.SectionItemDecoration;
import com.hr.ui.view.CityLetterIndexView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectCityNewActivity extends BaseNoConnectNetworkAcitivty implements CityLetterIndexView.OnIndexTouchedChangedListener, InnerListener, TextWatcher {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_selectCity)
    EditText etSelectCity;
    @BindView(R.id.iv_selectCityNewDelete)
    ImageView ivSelectCityNewDelete;
    @BindView(R.id.tv_selectCityNewSearch)
    ImageView tvSelectCityNewSearch;
    @BindView(R.id.rv_selectCityNew)
    RecyclerView rvSelectCityNew;
    @BindView(R.id.cliv_selectCityNew)
    CityLetterIndexView clivSelectCityNew;
    @BindView(R.id.tv_selectCityNewIndex)
    TextView tvSelectCityNewIndex;
    @BindView(R.id.rl_selectCityNewSearch)
    RelativeLayout rlSelectCityNewSearch;
    @BindView(R.id.cp_no_result_icon)
    ImageView cpNoResultIcon;
    @BindView(R.id.cp_no_result_text)
    TextView cpNoResultText;
    @BindView(R.id.cp_empty_view)
    LinearLayout cpEmptyView;
    private List<City> mTotalCityList = new ArrayList<>();
    private List<HotCity> mHotCityList = new ArrayList<>();
    private List<City> allCItyList = new ArrayList<>();
    private List<City> mResults=new ArrayList<>();
    private LocatedCity mLocatedCity;
    private int locateState;
    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private OnPickListener mOnPickListener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_selectcity_new;
    }

    public static void startAction(Activity activity, List<CityBean> selectCityList) {
        Intent intent = new Intent(activity, SelectCityNewActivity.class);
        intent.putExtra("selectCityList", (Serializable) selectCityList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.selectCity);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTotalCityList = FromStringToArrayList.getInstance().getCityList2();
        initLocatedCity();
        for (int i = 0; i < 4; i++) {
            HotCity hotCity = new HotCity(mTotalCityList.get(i).getName(), "", mTotalCityList.get(i).getCode());
            mHotCityList.add(hotCity);
        }
        mTotalCityList = FromStringToArrayList.getInstance().sortCityList(mTotalCityList);
        mTotalCityList.add(0, new LocatedCity("定位城市","未知","0"));
        mTotalCityList.add(1, new HotCity("热门城市", "未知", "0"));
        allCItyList = mTotalCityList;
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSelectCityNew.setLayoutManager(mLayoutManager);
        rvSelectCityNew.setHasFixedSize(true);
        rvSelectCityNew.addItemDecoration(new SectionItemDecoration(this, allCItyList), 0);
        //rvSelectCityNew.addItemDecoration(new DividerItemDecoration(this), 1);
        mAdapter = new CityListAdapter(this, allCItyList, mHotCityList, locateState,rvSelectCityNew);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        rvSelectCityNew.setAdapter(mAdapter);
        rvSelectCityNew.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //确保定位城市能正常刷新
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.refreshLocationItem();
                }
            }
        });
        clivSelectCityNew.setOverlayTextView(tvSelectCityNewIndex)
                .setOnIndexChangedListener(this);

        etSelectCity.addTextChangedListener(this);
    }

    private void initLocatedCity() {
        if (mLocatedCity == null) {
            mLocatedCity = new LocatedCity(getString(R.string.locating), "未知", "0");
            locateState = LocateState.FAILURE;
        } else {
            locateState = LocateState.SUCCESS;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        mAdapter.scrollToSection(index);
    }

    public void locationChanged(LocatedCity location, int state) {
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
        if (mOnPickListener != null) {
            mOnPickListener.onPick(position, data);
        }
    }

    @Override
    public void locate() {
        if (mOnPickListener != null) {
            mOnPickListener.onLocate();
        }
    }

    public void setOnPickListener(OnPickListener listener) {
        this.mOnPickListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)) {
            ivSelectCityNewDelete.setVisibility(View.GONE);
            cpEmptyView.setVisibility(View.GONE);
            mResults = allCItyList;
            ((SectionItemDecoration) (rvSelectCityNew.getItemDecorationAt(0))).setData(mResults);
            mAdapter.updateData(mResults);
        } else {
            ivSelectCityNewDelete.setVisibility(View.VISIBLE);
            //开始数据库查找
            mResults = CityUtils.queryDataById(etSelectCity.getText().toString());
            ((SectionItemDecoration) (rvSelectCityNew.getItemDecorationAt(0))).setData(mResults);
            if (mResults == null || mResults.isEmpty()) {
                cpEmptyView.setVisibility(View.VISIBLE);
            } else {
                cpEmptyView.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        rvSelectCityNew.scrollToPosition(0);
    }

    @OnClick({R.id.iv_selectCityNewDelete, R.id.tv_selectCityNewSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_selectCityNewDelete:
                etSelectCity.setText("");
                break;
            case R.id.tv_selectCityNewSearch:
                break;
        }
    }
}
