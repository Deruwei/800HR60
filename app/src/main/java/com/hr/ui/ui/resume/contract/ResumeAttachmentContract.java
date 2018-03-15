package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;

/**
 * Created by wdr on 2018/3/7.
 */

public interface ResumeAttachmentContract {
    interface Model extends BaseModel{

    }
    interface View extends BaseView{
        void uploadResumeAttachmentSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{

    }
}
