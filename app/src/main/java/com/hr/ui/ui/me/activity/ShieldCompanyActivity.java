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
    private MyShieldCompanyAdapter adapter;
    private MyShieldCompanyDataAdapter shieldCompanyDataAdapter;
    private List<QueryShieldCompanyBean.EnteListBean> enteListBeanList = new ArrayList<>();
    public List<ShieldCompanyBean.EliminateListBean> eliminateListBeanList = new ArrayList<>();
    private int type=1;//1代表显示已屏蔽企业  2标识搜索结果

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
        mPresenter.getShieldCompanyData();
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
        rvShieldCompany.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mPresenter.getShieldCompanyData();
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toolbarShieldCompanySearchBack, R.id.iv_toolbarShieldCompanySearchDelete, R.id.tv_toolbarShieldCompanySearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbarShieldCompanySearchBack:
                finish();
                break;
            case R.id.iv_toolbarShieldCompanySearchDelete:
                etToolbarShieldCompanySearch.setText("");
                break;
            case R.id.tv_toolbarShieldCompanySearch:
                if(type==1) {

                    if(etToolbarShieldCompanySearch.getText().toString()==null||"".equals(etToolbarShieldCompanySearch.getText().toString())){
                        ToastUitl.showShort("请输入公司名");
                        return;
                    }
                    setFocus();
                    etToolbarShieldCompanySearch.setEnabled(false);
                    rvShieldCompany.setVisibility(View.GONE);
                    rvShieldCompanyQuery.setVisibility(View.VISIBLE);
                    mPresenter.queryShieldCompanyDataByKeyword(etToolbarShieldCompanySearch.getText().toString());
                }else{
                    etToolbarShieldCompanySearch.setEnabled(true);
                    rvShieldCompany.setVisibility(View.VISIBLE);
                    rvShieldCompanyQuery.setVisibility(View.GONE);
                    tvToolbarShieldCompanySearch.setText(getString(R.string.search));
                    tvShieldCompanyTitle.setText(getString(R.string.shieldCompany));
                    type=1;
                    mPresenter.getShieldCompanyData();
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
        eliminateListBeanList.addAll(eliminateListBeans);
        shieldCompanyDataAdapter = new MyShieldCompanyDataAdapter();
        shieldCompanyDataAdapter.setFavouriteListBeanList(eliminateListBeanList);
        rvShieldCompany.setAdapter(shieldCompanyDataAdapter);

       /* shieldCompanyDataAdapter.setClickCallBack(new MyShieldCompanyDataAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                CompanyPageActivity.startAction(ShieldCompanyActivity.this, eliminateListBeanList.get(pos).getEliminate_id());
            }
        });*//**/
        shieldCompanyDataAdapter.setOnViewClick(new MyShieldCompanyDataAdapter.OnViewClick() {
            @Override
            public void onViewClick(TextView btn, int position) {
                mPresenter.deleteShieldCompany(eliminateListBeanList.get(position).getId());
            }
        });
    }

    @Override
    public void queryShieldCompanyDataByKeyWordSuccess(List<QueryShieldCompanyBean.EnteListBean> enteListBeans) {
        enteListBeanList.addAll(enteListBeans);
        tvToolbarShieldCompanySearch.setText(getString(R.string.cancel));
        tvShieldCompanyTitle.setText(getString(R.string.searchResult));
        type=2;
        if (enteListBeans != null && enteListBeans.size() != 0) {
            initUI();
        }
    }
    private void initUI() {
        adapter = new MyShieldCompanyAdapter();
        adapter.setFavouriteListBeanList(enteListBeanList);
        rvShieldCompanyQuery.setAdapter(adapter);
        adapter.setClickCallBack(new MyShieldCompanyAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                CompanyPageActivity.startAction(ShieldCompanyActivity.this, enteListBeanList.get(pos).getEnterprise_id());
            }
        });
        adapter.setOnViewClick(new MyShieldCompanyAdapter.OnViewClick() {
            @Override
            public void onViewClick(TextView btn, int position) {
                mPresenter.setShiledCompany(enteListBeanList.get(position).getEnterprise_name());
            }
        });
    }

    @Override
    public void setShieldCompanySuccess() {
        ToastUitl.showShort("屏蔽成功");
    }

    @Override
    public void deleteShieldCompany() {
        ToastUitl.showShort("取消屏蔽成功");
    }
}
