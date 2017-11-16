package com.dengzh.sample.mvp.presenter;

import com.dengzh.sample.module.retrofit.GitHubActivity;
import com.dengzh.sample.bean.User;
import com.dengzh.core.mvp.BasePresenter;
import com.dengzh.sample.mvp.model.modelImp.GithubModel;
import com.dengzh.core.net.HttpObserver;
import com.dengzh.sample.mvp.model.modelInter.IGithubModel;
import com.dengzh.sample.mvp.view.IGithubView;

/**
 * Created by dengzh on 2017/9/12 0012.
 * Presenter 两个泛型做接口声明即可
 */

public class GithubPresenter extends BasePresenter<IGithubView,IGithubModel> {

    public void onHandleQuery(String user){
        mModel.requestUserInfo(user)
                .compose(mView.<User>bindToLifecycle())
                .subscribe(new HttpObserver<User>() {  //访问回调方法
                    @Override
                    protected void onSuccees(User t) throws Exception {
                        mView.showContent(t.toString());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //对比requestUserInfo  生命周期传给下层了
    public void onHandleQuery3(String user){
        mModel.requestUserInfo3(user,mView.<User>bindToLifecycle())
                .subscribe(new HttpObserver<User>() {  //访问回调方法
                    @Override
                    protected void onSuccees(User t) throws Exception {
                        mView.showContent(t.toString());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //接口方法不大好
    public void onHandleQuery2(String user){
        mModel.requestUserInfo2(user,mView.<User>bindToLifecycle(), new HttpObserver<User>() {
            @Override
            protected void onSuccees(User user) throws Exception {
                mView.showContent(user.toString());
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

            }
        });
    }

}
