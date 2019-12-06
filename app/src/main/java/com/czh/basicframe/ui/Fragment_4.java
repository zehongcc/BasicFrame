package com.czh.basicframe.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.module.fragment4.Adpater_File;
import com.czh.basicframe.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * create by Chen
 * create date : 2019/12/4
 * desc :
 */
public class Fragment_4 extends BaseFragment {
    @BindView(R.id.file_btn1)
    Button fileBtn1;
    @BindView(R.id.file_btn2)
    Button fileBtn2;
    @BindView(R.id.file_btn3)
    Button fileBtn3;
    @BindView(R.id.file_btn4)
    Button fileBtn4;
    @BindView(R.id.file_recyclerView)
    RecyclerView fileRecyclerView;
    @BindView(R.id.file_tv1)
    TextView filePathTv1;
    @BindView(R.id.file_tv2)
    TextView filePathTv2;
    @BindView(R.id.file_tv3)
    TextView filePathTv3;
    @BindView(R.id.file_tv4)
    TextView filePathTv4;

    private Disposable mDisposable;//观察者和被观察者订阅关系

    @Override
    protected int setLayout() {
        return R.layout.fragment_4;
    }

    @Override
    protected void initValue(Bundle bundle) {
        initRecyclerView();
    }

    private Adpater_File adapter;

    private void initRecyclerView() {
        fileRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new Adpater_File(mContext);
        fileRecyclerView.setAdapter(adapter);
    }


    @Override
    protected void main() {

    }

    @OnClick({R.id.file_btn1, R.id.file_btn2, R.id.file_btn3, R.id.file_btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.file_btn1:
                LogUtils.e(TAG, "  >>>>>> 订阅关系 <<<<<<  " + (mDisposable == null));
                break;
            case R.id.file_btn2:
                //创建被观察的对象
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        LogUtils.d(TAG, "observableEmitter's thread=" + Thread.currentThread().getId() + ",string=true");
                        emitter.onNext("true");
                        LogUtils.d(TAG, "observableEmitter's thread=" + Thread.currentThread().getId() + ",string=false");
                        emitter.onNext("false");
                        LogUtils.d(TAG, "observableEmitter's thread=" + Thread.currentThread().getId() + ",onComplete");
                        emitter.onComplete();
                    }
                })//1.指定了subscribe方法执行的线程，并进行第一次下游线程的切换，将其切换到新的子线程。
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        //将上游指定类型String转换成指定类型Boolean
                        .map(new Function<String, Boolean>() {
                            @Override
                            public Boolean apply(String s) throws Exception {
                                LogUtils.d(TAG, "apply's thread=" + Thread.currentThread().getId() + ",s=" + s);
                                return "true".equals(s);
                            }
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtils.d(TAG, "Observer's thread=" + Thread.currentThread().getId() + ",boolean=" + aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

                break;
            case R.id.file_btn3:
                flatMapSample();
                break;
            case R.id.file_btn4:
                zipSample();
                break;
        }
    }

    /**
     * flatMap不保证下游接收时间的顺序。 -- contactMap 能保证顺序输出
     */
    private void flatMapSample(){
        Observable<Integer> sourceVable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
            }
        });
        Observable<String> observable = sourceVable.flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.fromArray("a value of " + integer + " , b value of " + integer);
            }
        });
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e(TAG," >>>>>>> "+s);
            }
        });
    }

    /**
     * 压缩 ： 将多个被观察者压缩成一个返回。
     */
    private void zipSample(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
            }
        });
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
            }
        }) ;
        //BiFunction 一个基于多个输入值计算一个值的功能接口（回调）。
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer+s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e(TAG," >>>>>>> "+s);
            }
        });
    }

}
