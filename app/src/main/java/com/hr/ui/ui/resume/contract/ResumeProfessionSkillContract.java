package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.ProfessionSkillData;
import com.hr.ui.bean.ResumeProfessionSkillBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/2.
 */

public interface ResumeProfessionSkillContract {
    interface Model extends BaseModel{
        Observable<ResumeProfessionSkillBean> getSkill(String skillId);
        Observable<ResponseBody> deleteSkill(String skillid);
        Observable<ResponseBody> addOrReplaceSkill(ProfessionSkillData skillData);
    }
    interface View extends BaseView{
        void getSkillSuccess(ResumeProfessionSkillBean.SkillListBean skillBean);
        void deleteSkillSuccess();
        void addOrReplaceSkillSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getSkill(String skillId);
        public abstract void deleteSkill(String skillId);
        public abstract  void addOrReplaceSkill(ProfessionSkillData skillData);
    }
}
