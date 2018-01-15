package com.hr.ui.ui.message.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;

import java.util.List;

/**
 * Created by wdr on 2018/1/15.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;
    private List<Integer> bgId;
    private Context context;
    public MyFragmentPagerAdapter(FragmentManager fm,Context context,List<Fragment> fragments, List<String> titles,List<Integer> bgId){
        super(fm);
        this.fragments=fragments;
        this.context=context;
        this.bgId=bgId;
        this.titles=titles;

    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles != null ? titles.get(position) : null;
    }
    public View getTabView(int position){
        //首先为子tab布置一个布局
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab,null);
        TextView tv = (TextView) v.findViewById(R.id.tv_textDot);
        tv.setText(titles.get(position));
        ImageView iv = (ImageView) v.findViewById(R.id.iv_redDot);
        iv.setImageResource(bgId.get(position));
        return v;
    }
    //防止fragment自动销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
