package com.dengzh.shop.utils.rx;

import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dengzh on 2017/9/11 0011.
 * rx工具类
 */

public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T,T> rxSchedulerHelper(){  //compose简化线程
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理，且绑定生命周期
     * @param life
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T,T> rxSchedulerHelper(final LifecycleTransformer<T> life){  //compose简化线程
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(life);
            }
        };
    }

}
