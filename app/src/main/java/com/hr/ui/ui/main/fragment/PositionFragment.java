package com.hr.ui.ui.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.EvenList;
import com.hr.ui.bean.EventJobOrderResume;
import com.hr.ui.ui.main.adapter.MySelectPositionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectPositionRightAdapter;
import com.hr.ui.utils.PopupWindowPositonClassView;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.view.MyFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PositionFragment extends BaseFragment {
    @BindView(R.id.lv_chooseJobTitleMaxTerm)
    ListView lvChooseJobTitleMaxTerm;
    @BindView(R.id.lv_chooseJobTitleMinTerm)
    ListView lvChooseJobTitleMinTerm;
    @BindView(R.id.tv_chosenJobTitle)
    TextView tvChosenJobTitle;
    @BindView(R.id.tv_chosenJobTitleNum)
    TextView tvChosenJobTitleNum;
    @BindView(R.id.tv_chosenJobTitleNumType)
    TextView tvChosenJobTitleNumType;
    @BindView(R.id.fl_chooseJobContent)
    MyFlowLayout flChooseJobContent;
    @BindView(R.id.rl_chosenJob)
    RelativeLayout rlChosenJob;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.ll_chooseJob)
    LinearLayout llChooseJob;
    Unbinder unbinder;
    @BindView(R.id.cl_chooseJob)
    FrameLayout clChooseJob;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_selectNum)
    TextView tvSelectNum;
    @BindView(R.id.cv_selectNum)
    CardView cvSelectNum;
    private String indutryId;
    private List<CityBean> selectPositionList = new ArrayList<>();
    private List<CityBean> selectRealPositionList = new ArrayList<>();
    private List<CityBean> positonLeftList = new ArrayList<>();
    private List<CityBean> positionRightList = new ArrayList<>();
    private int sum, currentJobPosition;
    private List<CityBean> selectPositionClassList = new ArrayList<>();
    private MySelectPositionLeftAdapter leftAdapter;
    private MySelectPositionRightAdapter rightAdapter;
    private boolean isHidden;

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_choosejob;
    }

    @Override
    public void initPresenter() {

    }

    public static PositionFragment newInstance() {
        PositionFragment navigationFragment = new PositionFragment();
        return navigationFragment;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        indutryId = getArguments().getString("industryId");
        selectRealPositionList = (List<CityBean>) getArguments().getSerializable("selectPosition");
        int position = getArguments().getInt("position");
        if (position == 100) {
            llChooseJob.setVisibility(View.VISIBLE);
        } else {
            llChooseJob.setVisibility(View.GONE);
        }
        setAdapter();

    }

    private void setAdapter() {
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
                        } else {
                            if (positionList2.get(j).getId().equals(selectRealPositionList.get(i).getId())) {
                                positionList2.get(j).setCheck(true);
                                selectPositionList.add(positionList2.get(j));
                                continue;
                            }
                        }
                    }
                }
            }
        }
        initChooseJobView(indutryId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMethod(EventJobOrderResume eventJobOrderResume) {
        switch (eventJobOrderResume.getType()) {
            case 2:
                indutryId = eventJobOrderResume.getIndustryId();
                selectRealPositionList = eventJobOrderResume.getSelectList();
                positionRightList.clear();
                positonLeftList.clear();
                flChooseJobContent.removeAllViewsInLayout();
                setAdapter();
                break;
        }
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
        final LinearLayout ll = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.item_textview_selected, null, false);
        ll.setLayoutParams(params);
        TextView tv = ll.findViewById(R.id.item_select);
        if (cityBean.getId().contains("|")) {
            if (cityBean.getName().contains("(")) {
                tv.setText(cityBean.getName());
            } else {
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
                flChooseJobContent.removeView(flChooseJobContent.findViewWithTag(cityBean.getId()));
                sum = selectPositionList.size();
                getRightDataFresh();
                if (sum == 0) {
                    rlChosenJob.setVisibility(View.GONE);
                }
                setNum();
                EventBus.getDefault().post(new EventJobOrderResume(5, indutryId, selectPositionList));
            }
        });
        ll.setTag(cityBean.getId());
        flChooseJobContent.addView(ll);
        setNum();
    }

    private void initChooseJobView(final String industryId) {
        final List<CityBean> PositionList = FromStringToArrayList.getInstance().getExpectPosition(industryId);
        List<CityBean> PositionList1 = FromStringToArrayList.getInstance().getExpectPosition(industryId);
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
            rlChosenJob.setVisibility(View.GONE);
            tvSelectNum.setText("无");
        }
        final Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
        lvChooseJobTitleMaxTerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        lvChooseJobTitleMinTerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sum = selectPositionList.size();
                currentJobPosition = position;
                if (positionRightList.get(position).isCheck() == false) {
                    if (Utils.checkMedicinePositionClass(positionRightList.get(position)) == true) {
                        PopupWindowPositonClassView viewPositionClass = new PopupWindowPositonClassView(new PopupWindow(), getActivity(), clChooseJob);
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
                        } else {
                            if (positionRightList.get(position).getId().equals(selectPositionList.get(i).getId())) {
                                removeView(selectPositionList.get(i));
                                selectPositionList.remove(selectPositionList.get(i));
                                break;
                            }
                        }
                    }
                    setNum();
                }
                rightAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new EventJobOrderResume(5, industryId, selectPositionList));
            }
        });
    }

    private void doAddJobView(int position, List<CityBean> selectPositionClassList) {
        sum = selectPositionList.size();
        if (sum == 0) {
            rlChosenJob.setVisibility(View.VISIBLE);
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
                if (Utils.checkMedicinePositionClass2(positionRightList.get(position)) == true) {
                    cityBean.setId(positionRightList.get(position).getId() + "|" + "10500");
                    cityBean.setName(positionRightList.get(position).getName());
                    cityBean.setCheck(true);
                    selectPositionList.add(cityBean);
                } else {
                    cityBean = positionRightList.get(position);
                    selectPositionList.add(positionRightList.get(position));
                }
            }
            addView(cityBean);
            positionRightList.get(position).setCheck(true);
        }
        rightAdapter.notifyDataSetChanged();
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
        flChooseJobContent.removeView(flChooseJobContent.findViewWithTag(cityBean.getId()));
        sum = selectPositionList.size();
        setNum();
    }

    private void setNum() {
        sum = selectPositionList.size();
        if (sum == 0) {
            rlChosenJob.setVisibility(View.GONE);
            isHidden=true;
        } else {
            if(isHidden){
                rlChosenJob.setVisibility(View.GONE);
                isHidden=true;
            }else {
                rlChosenJob.setVisibility(View.VISIBLE);
                isHidden=false;
            }
        }
        tvChosenJobTitleNum.setText(sum + "");
        if(sum==0){
            tvSelectNum.setText("无");
        }else {
            tvSelectNum.setText(sum + "/5");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    leftAdapter = new MySelectPositionLeftAdapter(positonLeftList);
                    lvChooseJobTitleMaxTerm.setAdapter(leftAdapter);
                    break;
                case 2:
                    rightAdapter = new MySelectPositionRightAdapter(positionRightList);
                    lvChooseJobTitleMinTerm.setAdapter(rightAdapter);
                    break;
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMethod2(EvenList evenList) {
        switch (evenList.getType()) {
            case 6:
                this.selectPositionClassList = evenList.getList();
                doAddJobView(currentJobPosition, selectPositionClassList);
                selectPositionClassList.clear();
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
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.btn_cancel,R.id.cv_selectNum})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_cancel:
                if (!"".equals(selectPositionList) && selectPositionList != null && selectPositionList.size() != 0) {
                    EventBus.getDefault().post(new EventJobOrderResume(8));
                } else {
                    ToastUitl.showShort("请选择职位");
                }
                break;
            case R.id.cv_selectNum:
                if(isHidden){
                    if(selectPositionList.size()>0){
                        rlChosenJob.setVisibility(View.VISIBLE);
                        isHidden=!isHidden;
                    }
                }else {
                    rlChosenJob.setVisibility(View.GONE);
                    isHidden=!isHidden;
                }

                break;
        }

    }
}
