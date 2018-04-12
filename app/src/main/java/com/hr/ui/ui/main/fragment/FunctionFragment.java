package com.hr.ui.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class FunctionFragment extends BaseFragment {
    @BindView(R.id.rv_chooseTerritory)
    RecyclerView rvChooseTerritory;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.ll_chooseTerritory)
    LinearLayout llChooseTerritory;
    Unbinder unbinder;
    private List<CityBean> selectFuncList = new ArrayList<>();
    private String industryId;
    private SelectIndustryAdapter territoryAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_chooseterritory;
    }

    @Override
    public void initPresenter() {
    }

    public static FunctionFragment newInstance() {
        FunctionFragment navigationFragment = new FunctionFragment();
        return navigationFragment;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        int position=getArguments().getInt("position");
        if(position==100){
            llChooseTerritory.setVisibility(View.VISIBLE);
        }else{
            llChooseTerritory.setVisibility(View.GONE);
        }
        industryId = getArguments().getString("industryId");
        selectFuncList = (List<CityBean>) getArguments().getSerializable("selectFunction");
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvChooseTerritory.setLayoutManager(manager);
        setAdapter();
    }

    private void setAdapter() {
        final List<CityBean> terrirotyList = FromStringToArrayList.getInstance().getExpectField(industryId);
        if (selectFuncList != null) {
            for (int i = 0; i < selectFuncList.size(); i++) {
                for (int j = 0; j < terrirotyList.size(); j++) {
                    if (terrirotyList.get(j).getId().equals(selectFuncList.get(i).getId())) {
                        terrirotyList.get(j).setCheck(true);
                        continue;
                    }
                }
            }
        }
        territoryAdapter = new SelectIndustryAdapter(terrirotyList, getActivity(), 1);
        rvChooseTerritory.setAdapter(territoryAdapter);
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
                EventBus.getDefault().post(new EventJobOrderResume(4, industryId, selectFuncList));
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMethod(EventJobOrderResume eventJobOrderResume) {
        switch (eventJobOrderResume.getType()) {
            case 1:
                industryId = eventJobOrderResume.getIndustryId();
                selectFuncList = eventJobOrderResume.getSelectList();
                setAdapter();
                break;
        }
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

    @OnClick(R.id.btn_cancel)
    public void onViewClicked() {
        if(!"".equals(selectFuncList)&&selectFuncList!=null&&selectFuncList.size()!=0){
            EventBus.getDefault().post(new EventJobOrderResume(7));
        }else{
            ToastUitl.showShort("请选择领域");
        }
    }
}
