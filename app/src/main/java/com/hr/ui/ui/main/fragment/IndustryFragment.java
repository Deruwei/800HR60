package com.hr.ui.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.EventJobOrderResume;
import com.hr.ui.ui.main.adapter.SelectIndustryAdapter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 求职意向中选择行业Fragment的功能点：
 * 1.当求职意向是添加新求职意向的时候，求职意向中选择的行业在Fragment中全部设为不可以选择
 * 2.当求职意向是修改求职意向的时候，求职意向中药修改的行业在Fragment中默认选择，其他不适修改的已选择行业设为不可选择
 * 3.当是添加的时候在下面显示下一步的按钮，点击之后如果行业有领域则跳到选择领域，没有则跳到选择职位
 * 4.当是修改的时候，点击行业，需要手动选择跳到选择领域或者选择职位页面
 */
public class IndustryFragment extends BaseFragment {
    @BindView(R.id.rv_chooseIndustry)
    RecyclerView rvChooseIndustry;
    Unbinder unbinder;
    @BindView(R.id.btn_industryNext)
    Button btnIndustryNext;
    @BindView(R.id.ll_chooseIndustryNext)
    LinearLayout llChooseIndustryNext;
    private String indutryId;//行业的id
    private int updatePositionNum;//求职意向要修改的位置，是添加的话是100
    private List<CityBean> industryList;//行业信息的list集合
    private List<String> industryIds = new ArrayList<>();//求职意向中选择职位的id的list集合

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_chooseindustry;
    }

    @Override
    public void initPresenter() {

    }

    public static IndustryFragment newInstance() {
        IndustryFragment navigationFragment = new IndustryFragment();
        return navigationFragment;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        EventBus.getDefault().register(this);
        indutryId = bundle.getString("industryId");
        industryIds = bundle.getStringArrayList("industryIds");
        updatePositionNum = bundle.getInt("position");
        if (updatePositionNum == 100) {
            llChooseIndustryNext.setVisibility(View.VISIBLE);
        } else {
            llChooseIndustryNext.setVisibility(View.GONE);
        }
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvChooseIndustry.setLayoutManager(manager);
        setAdapter();
    }

    private void setAdapter() {
        industryList = FromStringToArrayList.getInstance().getIndustryList();
        if (indutryId != null && !"".equals(indutryId)) {
            for (int i = 0; i < industryList.size(); i++) {
                if (indutryId.equals(industryList.get(i).getId())) {
                    industryList.get(i).setCheck(true);
                }
            }
        }
        final SelectIndustryAdapter selectIndustryAdapter;
       // Log.i("你好2",industryIds);
        if (updatePositionNum != 100) {
            //Log.i("你好2","你好1");
            selectIndustryAdapter = new SelectIndustryAdapter(industryList, indutryId, getActivity(), "1", industryIds);
        } else {
            //Log.i("你好2","你好2");
            selectIndustryAdapter = new SelectIndustryAdapter(industryList, indutryId, getActivity(), "2", industryIds);
        }
        rvChooseIndustry.setAdapter(selectIndustryAdapter);
        selectIndustryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (indutryId != null) {
                    for (int i = 0; i < industryIds.size(); i++) {
                        if (indutryId.equals(industryIds.get(i))) {
                            industryIds.remove(industryIds.get(i));
                            break;
                        }
                    }
                }
                for (int i = 0; i < industryList.size(); i++) {
                    industryList.get(i).setCheck(false);
                }
                indutryId = industryList.get(position).getId();
                industryList.get(position).setCheck(true);
                selectIndustryAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new EventJobOrderResume(3, indutryId));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMethod(EventJobOrderResume eventJobOrderResume) {
        switch (eventJobOrderResume.getType()) {
            case 0:
                updatePositionNum = eventJobOrderResume.getUpdateNum();
                industryIds = eventJobOrderResume.getIndustryIds();
                indutryId = eventJobOrderResume.getIndustryId();
                setAdapter();
                break;
        }

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

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
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick(R.id.btn_industryNext)
    public void onViewClicked() {
        if(!"".equals(indutryId)&&indutryId!=null){
            EventBus.getDefault().post(new EventJobOrderResume(6));
        }else{
            ToastUitl.showShort("请选择行业");
        }
    }
}
