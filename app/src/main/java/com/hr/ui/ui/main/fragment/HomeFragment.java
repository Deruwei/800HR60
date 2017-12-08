package com.hr.ui.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.adapter.ViewPagerAdapter;
import com.hr.ui.utils.BottomNavigationViewHelper;
import com.hr.ui.view.MyViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wdr on 2017/11/22.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.bnv_homeFragment)
    BottomNavigationView bnvHomeFragment;
    Unbinder unbinder;
    private MenuItem menuItem;
    @BindView(R.id.vp_homeFragment)
    MyViewPager vpHomeFragment;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3,fragment4;

    public static HomeFragment newInstance(String s) {
        HomeFragment navigationFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_homefragment;
    }

    @Override
    public void initPresenter() {

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        fragment1=new Fragment1();
        fragment2=new Fragment2();
        fragment3=new Fragment3();
        fragment4=new Fragment3();
        adapter.addFragment(fragment1);
        adapter.addFragment(fragment2);
        adapter.addFragment(fragment3);
        adapter.addFragment(fragment4);
        viewPager.setAdapter(adapter);
    }
    @Override
    protected void initView() {
        BottomNavigationViewHelper.disableShiftMode(bnvHomeFragment);
        bnvHomeFragment.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        vpHomeFragment.setCurrentItem(0);
                        break;
                    case R.id.message:
                        vpHomeFragment.setCurrentItem(1);
                        break;
                    case R.id.resume:
                        vpHomeFragment.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
        vpHomeFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bnvHomeFragment.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bnvHomeFragment.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(vpHomeFragment);
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
    }
}
