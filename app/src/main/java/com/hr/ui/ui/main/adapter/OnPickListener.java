package com.hr.ui.ui.main.adapter;

import com.hr.ui.bean.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
}
