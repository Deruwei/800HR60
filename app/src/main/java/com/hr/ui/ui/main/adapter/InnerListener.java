package com.hr.ui.ui.main.adapter;


import com.hr.ui.bean.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
