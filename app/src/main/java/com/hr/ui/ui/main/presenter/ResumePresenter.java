package com.hr.ui.ui.main.presenter;

import android.util.MutableInt;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.ui.main.activity.MultipleResumeActivity;
import com.hr.ui.ui.main.fragment.ResumeContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import java.io.IOException;

/**
 * Created by wdr on 2017/12/22.
 */

public class ResumePresenter extends ResumeContract.Presenter {
    @Override
    public void getResume(String resumeId) {
        mRxManage.add(mModel.getResume(resumeId).subscribe(new RxSubscriber<ResumeBean>(mContext,false) {
            @Override
            protected void _onNext(ResumeBean resumeBean) throws IOException {
                if(resumeBean.getError_code()==0){
                    mView.getResumeSuccess(resumeBean);
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) resumeBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void getResumeList() {
        mRxManage.add(mModel.getResumeList().subscribe(new RxSubscriber<MultipleResumeBean>(mContext,false) {
            @Override
            protected void _onNext(MultipleResumeBean multipleResumeBean)  {
                String resumeId="";
                if("0".equals(multipleResumeBean.getError_code())){
                    if(multipleResumeBean.getResume_list()!=null&&multipleResumeBean.getResume_list().size()!=0) {
                        for (int i = 0; i < multipleResumeBean.getResume_list().size(); i++) {
                            if("1".equals(multipleResumeBean.getResume_list().get(i).getImportant())){
                                resumeId=multipleResumeBean.getResume_list().get(i).getResume_id();
                            }
                        }
                    }
                    mView.getResumeList(resumeId);
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(multipleResumeBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
