package com.dengzh.sample.module.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import com.dengzh.core.net.BaseRespEntity;
import com.dengzh.core.net.HttpObserver;
import com.dengzh.core.net.RetrofitManager;
import com.dengzh.core.utils.LogUtil;
import com.dengzh.core.utils.RxUtil;
import com.dengzh.sample.R;
import com.dengzh.sample.bean.User;
import com.dengzh.sample.module.base.BaseActivity;
import com.dengzh.sample.mvp.model.modelImp.GithubModel;
import com.dengzh.sample.mvp.presenter.GithubPresenter;
import com.dengzh.sample.mvp.view.IGithubView;
import com.dengzh.sample.net.api.GitHubAPI;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dengzh on 2017/9/8 0008.
 * retrofit 网络库简单使用例子
 */

public class GitHubActivity extends BaseActivity<GithubPresenter,GithubModel> implements IGithubView {

    @BindView(R.id.contentTv)
    TextView contentTv;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_retrofit;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        toolbar.setTitle("Retrofit网络请求");
    }

    @Override
    protected void initData() {
        mPresenter.onHandleQuery("baiiu");
        //aTv.setText("123");
    }

    private void testRetrofit(){

        //2.创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //.client(okHttpClient)
                //设置baseUrl,注意，baseUrl必须后缀"/"
                .baseUrl("https://api.github.com/")
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //获取GitHub的API
        GitHubAPI gitHubAPI = retrofit.create(GitHubAPI.class);
        //3.异步调用
        Call<User> userCall = gitHubAPI.userInfo("baiiu");
        userCall.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() == null) {
                    try {
                        LogUtil.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.e(TAG, response.body().toString());
                    contentTv.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (call.isCanceled()) {
                    LogUtil.e(TAG, "取消call");
                } else {
                    LogUtil.e(TAG, t.toString());
                }
            }
        });
        //取消调用
        //userCall.cancel();
    }

    private void testRxJava() {
        //1.增加日志信息
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        //2.创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //设置baseUrl,注意，baseUrl必须后缀"/"
                .baseUrl("https://api.github.com/")
                //addConverterFactory提供Gson支持，可以添加多种序列化Factory，
                // 但是GsonConverterFactory必须放在最后,否则会抛出异常。
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //获取GitHub的API
        GitHubAPI gitHubAPI = retrofit.create(GitHubAPI.class);
        //RxJava
        Observable<User> observable = gitHubAPI.userInfoByRxJava("baiiu");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        LogUtil.e(TAG, user.toString());
                        contentTv.setText(user.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 所有的请求可以以此例子做修改
     */
    private void testRxJava2(){
        RetrofitManager.getInstance()
                .createReq(GitHubAPI.class)
                .userInfoByRxJava2("baiiu") //到此是找到Observable对象
                .compose(RxUtil.<BaseRespEntity<User>>rxSchedulerHelper()) //此处封装访问在io线程，回调在主线程
                .subscribe(new HttpObserver<BaseRespEntity<User>>() {  //访问回调方法
                    @Override
                    protected void onSuccees(BaseRespEntity<User> t) throws Exception {
                        User user = t.getData();
                        LogUtil.e(TAG, user.toString());
                        contentTv.setText(user.toString());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

//
//        //flatMap 请求A->等到A数据，整理A数据，请求B->等到B数据
//        RetrofitManager.getInstance()
//                .createReq(GitHubAPI.class)
//                .userInfoByRxJava2("baiiu") //到此是找到Observable对象
//                .compose(RxUtil.<BaseRespEntity<User>>rxSchedulerHelper()) //此处封装访问在io线程，回调在主线程
//                .observeOn(Schedulers.io()) //多个observeOn，都会执行，比如此处首先回到主线程(此例子此处没做操作)，
//                //再到子线程，做了新的网络请求，下面flatMap提供了新的Observable
//                .flatMap(new Function<BaseRespEntity<User>, ObservableSource<User>>() {
//                    @Override
//                    public ObservableSource<User> apply(@NonNull BaseRespEntity<User> userBaseRespEntity) throws Exception {
//                        return RetrofitManager.getInstance()
//                                .createReq(GitHubAPI.class)
//                                .userInfoByRxJava("baiiu");
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpObserver<User>(){
//
//                    @Override
//                    protected void onSuccees(User user) throws Exception {
//
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showContent(String text) {
        contentTv.setText(text);
        LogUtil.e(TAG, text);
    }

}
