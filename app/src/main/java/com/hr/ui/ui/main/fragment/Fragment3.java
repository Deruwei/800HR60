package com.hr.ui.ui.main.fragment;

import android.os.Bundle;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.constants.Constants;

/**
 * Created by wdr on 2017/11/22.
 */

public class Fragment3 extends BaseFragment{
    public static Fragment3 newInstance(String s) {
        Fragment3 navigationFragment = new Fragment3();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_fragment1;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }
}
