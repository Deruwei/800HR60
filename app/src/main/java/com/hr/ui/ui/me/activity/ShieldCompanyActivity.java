package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.QueryShieldCompanyBean;
import com.hr.ui.bean.ShieldCompanyBean;
import com.hr.ui.ui.job.activity.CompanyPageActivity;
import com.hr.ui.ui.me.adapter.MyShieldCompanyAdapter;
import com.hr.ui.ui.me.adapter.MyShieldCompanyDataAdapter;
import com.hr.ui.ui.me.contract.ShieldCompanyContract;
import com.hr.ui.ui.me.model.ShieldCompanyModel;
import com.hr.ui.ui.me.presenter.ShieldCompanyPresenter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/16.
 */

public class ShieldCompanyActivity extends BaseActivity<ShieldCompanyPresenter, ShieldCompanyModel> implements ShieldCompanyContract.View {

    @BindView(R.id.iv_toolbarShieldCompanySearchBack)
    ImageView ivToolbarShieldCompanySearchBack;
    @BindView(R.id.iv_mainSearch)
    ImageView ivMainSearch;
    @BindView(R.id.et_toolbarShieldCompanySearch)
    EditText etToolbarShieldCompanySearch;
    @BindView(R.id.iv_toolbarShieldCompanySearchDelete)
    ImageView ivToolbarShieldCompanySearchDelete;
    @BindView(R.id.rl_mainSearch)
    RelativeLayout rlMainSearch;
    @BindView(R.id.tv_toolbarShieldCompanySearch)
    TextView tvToolbarShieldCompanySearch;
    @BindView(R.id.rv_shieldCompany)
    XRecyclerView rvShieldCompany;
    @BindView(R.id.rv_shieldCompanyQuery)
    XRecyclerView rvShieldCompanyQuery;
    @BindView(R.id.tv_shieldCompanyTitle)
    TextView tvShieldCompanyTitle;
    @BindView(R.id.iv_noContent)
    ImageView ivNoContent;
    @BindView(R.id.tv_noData)
    TextView tvNoData;
    @BindView(R.id.iv_noDataSearchIcon)
    ImageView ivNoDataSearchIcon;
    @BindView(R.id.iv_noDataSearch)
    RelativeLayout ivNoDataSearch;
    @BindView(R.id.rl_emptyView)
    RelativeLayout rlEmptyView;
    @BindView(R.id.tv_shieldCompanyAllShield)
    TextView tvShieldCompanyAll;
    @BindView(R.id.tv_shieldCompanyAllShieldNum)
    TextView tvShieldCompanyAllShieldNum;
    private MyShieldCompanyAdapter adapter;
    private MyShieldCompanyDataAdapter shieldCompanyDataAdapter;
    private List<QueryShieldCompanyBean.EnteListBean> enteListBeanList = new ArrayList<>();
    public List<ShieldCompanyBean.EliminateListBean> eliminateListBeanList = new ArrayList<>();
    private int type = 1;//1代表显示已屏蔽企业  2标识搜索结果
    private int sheldNum;
    private int position;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ShieldCompanyActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shieldcompany;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.getShieldCompanyData(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvShieldCompany.setLayoutManager(linearLayoutManager);
        /*Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvCollection.addItemDecoration(rvCollection.new DividerItemDecoration(dividerDrawable));*/
        rvShieldCompany.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvShieldCompany.setNestedScrollingEnabled(false);
        rvShieldCompany.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        adapter = new MyShieldCompanyAdapter();
        shieldCompanyDataAdapter = new MyShieldCompanyDataAdapter();
        tvNoData.setVisibility(View.GONE);
        rvShieldCompany.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mPresenter.getShieldCompanyData(false);
                        shieldCompanyDataAdapter.notifyDataSetChanged();
                        rvShieldCompany.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        rvShieldCompany.setNoMore(true);
                    }
                }, 1000);
            }
        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rvShieldCompanyQuery.setLayoutManager(linearLayoutManager2);
        /*Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvCollection.addItemDecoration(rvCollection.new DividerItemDecoration(dividerDrawable));*/
        rvShieldCompanyQuery.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvShieldCompanyQuery.setNestedScrollingEnabled(false);
        rvShieldCompanyQuery.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        adapter = new MyShieldCompanyAdapter();
        shieldCompanyDataAdapter = new MyShieldCompanyDataAdapter();
        rvShieldCompanyQuery.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mPresenter.queryShieldCompanyDataByKeyword(etToolbarShieldCompanySearch.getText().toString());
                        adapter.notifyDataSetChanged();
                        rvShieldCompanyQuery.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        rvShieldCompanyQuery.setNoMore(true);
                    }
                }, 1000);
            }
        });
        ivToolbarShieldCompanySearchDelete.setVisibility(View.GONE);
        etToolbarShieldCompanySearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivToolbarShieldCompanySearchDelete.setVisibility(View.GONE);
                } else {
                    if (etToolbarShieldCompanySearch.getText().toString() != null && etToolbarShieldCompanySearch.getText().toString().length() > 0) {
                        ivToolbarShieldCompanySearchDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etToolbarShieldCompanySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivToolbarShieldCompanySearchDelete.setVisibility(View.VISIBLE);
                } else {
                    ivToolbarShieldCompanySearchDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etToolbarShieldCompanySearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(type!=1){
                        tvToolbarShieldCompanySearch.setText(getString(R.string.search));
                        type=1;
                    }
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toolbarShieldCompanySearchBack, R.id.tv_shieldCompanyAllShield, R.id.iv_toolbarShieldCompanySearchDelete, R.id.tv_toolbarShieldCompanySearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shieldCompanyAllShield:
                etToolbarShieldCompanySearch.setEnabled(true);
                mPresenter.setShiledCompany(etToolbarShieldCompanySearch.getText().toString());
                break;
            case R.id.iv_toolbarShieldCompanySearchBack:
                if(type!=1){
                    etToolbarShieldCompanySearch.setEnabled(true);
                    rvShieldCompany.setVisibility(View.VISIBLE);
                    tvShieldCompanyAll.setVisibility(View.GONE);
                    rvShieldCompanyQuery.setVisibility(View.GONE);
                    tvToolbarShieldCompanySearch.setText(getString(R.string.search));
                    tvShieldCompanyAllShieldNum.setVisibility(View.GONE);
                    tvShieldCompanyTitle.setText(getString(R.string.shieldCompany));
                    type = 1;
                    mPresenter.getShieldCompanyData(false);
                }else {
                    finish();
                }
                break;
            case R.id.iv_toolbarShieldCompanySearchDelete:
                etToolbarShieldCompanySearch.setText("");
                break;
            case R.id.tv_toolbarShieldCompanySearch:
                if (type == 1) {
                    if (etToolbarShieldCompanySearch.getText().toString() == null || "".equals(etToolbarShieldCompanySearch.getText().toString())) {
                        ToastUitl.showShort("请输入关键词");
                        return;
                    }
                    if(sheldNum>=20){
                        ToastUitl.showShort("最多添加20个关键词，请删除一部分关键词重新添加");
                        return;
                    }
                    setFocus();
                    etToolbarShieldCompanySearch.setEnabled(true);
                    rvShieldCompany.setVisibility(View.GONE);
                    rvShieldCompanyQuery.setVisibility(View.VISIBLE);
                    tvShieldCompanyAllShieldNum.setVisibility(View.VISIBLE);
                    mPresenter.queryShieldCompanyDataByKeyword(etToolbarShieldCompanySearch.getText().toString());
                } else {
                    etToolbarShieldCompanySearch.setEnabled(true);
                    rvShieldCompany.setVisibility(View.VISIBLE);
                    tvShieldCompanyAll.setVisibility(View.GONE);
                    rvShieldCompanyQuery.setVisibility(View.GONE);
                    tvToolbarShieldCompanySearch.setText(getString(R.string.search));
                    tvShieldCompanyAllShieldNum.setVisibility(View.GONE);
                    tvShieldCompanyTitle.setText(getString(R.string.shieldCompany));
                    type = 1;
                    mPresenter.getShieldCompanyData(false);
                }
                break;
        }
    }

    private void setFocus() {
        rlMainSearch.setFocusable(true);
        rlMainSearch.setFocusableInTouchMode(true);
        rlMainSearch.requestFocus();
        rlMainSearch.findFocus();
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
    public void getShieldCompanyDataSuccess(List<ShieldCompanyBean.EliminateListBean> eliminateListBeans) {
        eliminateListBeanList.clear();
        if (eliminateListBeans != null && !"".equals(eliminateListBeans) && eliminateListBeans.size() != 0) {
            rvShieldCompany.setVisibility(View.VISIBLE);
            sheldNum=eliminateListBeans.size();
            eliminateListBeanList.addAll(eliminateListBeans);
            shieldCompanyDataAdapter = new MyShieldCompanyDataAdapter();
            shieldCompanyDataAdapter.setFavouriteListBeanList(eliminateListBeanList);
            rvShieldCompany.setAdapter(shieldCompanyDataAdapter);
            rlEmptyView.setVisibility(View.GONE);
        } else {
            sheldNum=0;
            rlEmptyView.setVisibility(View.VISIBLE);
            rvShieldCompany.setVisibility(View.GONE);
        }

       /* shieldCompanyDataAdapter.setClickCallBack(new MyShieldCompanyDataAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                CompanyPageActivity.startAction(ShieldCompanyActivity.this, eliminateListBeanList.get(pos).getEliminate_id());
            }
        });*//**/
        shieldCompanyDataAdapter.setOnViewClick(new MyShieldCompanyDataAdapter.OnViewClick() {
            @Override
            public void onViewClick(TextView btn, int position1) {
                mPresenter.deleteShieldCompany(eliminateListBeanList.get(position1).getId());
                position=position1;
            }
        });
    }

    @Override
    public void queryShieldCompanyDataByKeyWordSuccess(List<QueryShieldCompanyBean.EnteListBean> enteListBeans,String total) {
        enteListBeanList.clear();
        enteListBeanList.addAll(enteListBeans);
        tvToolbarShieldCompanySearch.setText(getString(R.string.cancel));
        tvShieldCompanyAll.setVisibility(View.VISIBLE);
        tvShieldCompanyTitle.setText(getString(R.string.searchResult));
        tvShieldCompanyAllShieldNum.setText("("+total+")");
        type = 2;
        if (enteListBeans != null && enteListBeans.size() != 0) {
            rlEmptyView.setVisibility(View.GONE);
            rvShieldCompanyQuery.setVisibility(View.VISIBLE);
            initUI();
        } else {
            rlEmptyView.setVisibility(View.VISIBLE);
            rvShieldCompanyQuery.setVisibility(View.GONE);
        }
    }

    private void initUI() {
        adapter = new MyShieldCompanyAdapter();
        if (!"".equals(enteListBeanList) && enteListBeanList != null && enteListBeanList.size() != 0) {
            adapter.setFavouriteListBeanList(enteListBeanList);
            rvShieldCompanyQuery.setAdapter(adapter);
            rlEmptyView.setVisibility(View.GONE);
        } else {
            rlEmptyView.setVisibility(View.VISIBLE);
            ivNoDataSearch.setVisibility(View.GONE);
        }

        adapter.setClickCallBack(new MyShieldCompanyAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                CompanyPageActivity.startAction(ShieldCompanyActivity.this, enteListBeanList.get(pos).getEnterprise_id());
            }
        });
        adapter.setOnViewClick(new MyShieldCompanyAdapter.OnViewClick() {
            @Override
            public void onViewClick(TextView btn, int position1) {
                if(sheldNum>=20){
                    ToastUitl.showShort("最多添加20个关键词，请删除一部分关键词重新添加");
                    return;
                }else {
                    mPresenter.setShiledCompany(enteListBeanList.get(position1).getEnterprise_name());
                    position = position1;
                }
            }
        });
    }

    @Override
    public void setShieldCompanySuccess() {
        ToastUitl.showShort("屏蔽成功");
        etToolbarShieldCompanySearch.setEnabled(true);
        rvShieldCompany.setVisibility(View.VISIBLE);
        tvShieldCompanyAll.setVisibility(View.GONE);
        rvShieldCompanyQuery.setVisibility(View.GONE);
        tvToolbarShieldCompanySearch.setText(getString(R.string.search));
        tvShieldCompanyAllShieldNum.setVisibility(View.GONE);
        tvShieldCompanyTitle.setText(getString(R.string.shieldCompany));
        type = 1;
        mPresenter.getShieldCompanyData(false);
    }

    @Override
    public void deleteShieldCompany() {
        ToastUitl.showShort("取消屏蔽成功");
        sheldNum--;
        shieldCompanyDataAdapter.doDelete(position+1);
    }
}
