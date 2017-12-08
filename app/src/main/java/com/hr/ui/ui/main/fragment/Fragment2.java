package com.hr.ui.ui.main.fragment;

import android.os.Bundle;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.constants.Constants;

/**
 * Created by wdr on 2017/11/22.
 */

public class Fragment2 extends BaseFragment {
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_fragment1;
    }

    @Override
    public void initPresenter() {

    }
    public static Fragment2 newInstance(String s) {
        Fragment2 navigationFragment = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }
    @Override
    protected void initView() {

    }
}
