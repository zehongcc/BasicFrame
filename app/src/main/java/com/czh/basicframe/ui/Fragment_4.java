package com.czh.basicframe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binioter.guideview.Component;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.calendar.CCalendarView;
import com.czh.basicframe.ui.login.Activity_Login;
import com.czh.basicframe.utils.DialogUtils;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.widget.CLoadingView;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.file_tv1)
    TextView filePathTv1;
    @BindView(R.id.file_tv2)
    TextView filePathTv2;
    @BindView(R.id.file_tv3)
    TextView filePathTv3;
    @BindView(R.id.file_tv4)
    TextView filePathTv4;
    @BindView(R.id.show_btn)
    Button showBtn;
    @BindView(R.id.toActBtn)
    Button toActBtn;
    @BindView(R.id.outActBtn)
    Button outActBtn;
    @BindView(R.id.startAnim)
    Button startAnim;
    @BindView(R.id.cLoadingView)
    CLoadingView cLoadingView;

    private Disposable mDisposable;//观察者和被观察者订阅关系

    @Override
    protected int setLayout() {
        return R.layout.fragment_4;
    }

    @Override
    protected void initValue(Bundle bundle) {
        initRecyclerView();
    }

    private void initRecyclerView() {
    }


    @Override
    protected void main() {
        views.add(fileBtn1);
        views.add(fileBtn2);
        views.add(fileBtn3);
        views.add(fileBtn4);
    }

    @OnClick({R.id.file_btn1, R.id.file_btn2, R.id.file_btn3, R.id.file_btn4, R.id.show_btn, R.id.toActBtn, R.id.outActBtn,R.id.startAnim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.file_btn1:
                DialogUtils.getInstance().showTimeDialog(mContext, null,
                        false, new CCalendarView.OnSelectTimeListener() {
                    @Override
                    public void onSelect(String time) {
                    filePathTv1.setText(time);
                    }
                });
//                LogUtils.e(TAG, "  >>>>>> 订阅关系 <<<<<<  " + (mDisposable == null));
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
            case R.id.show_btn://显示指引蒙板
                showGuideView(views.get(currentIndex), currentIndex);
                break;
            case R.id.toActBtn://界面跳转效果
                Intent intent = new Intent(mContext, Activity_Login.class);
                startActivity(intent);
                mActivity.overridePendingTransition(R.anim.zoom_right_out, R.anim.zoom_right_in);
                break;
            case R.id.outActBtn:
                Intent intent1 = new Intent(mContext, Activity_Login.class);
                startActivity(intent1);
                break;
            case R.id.startAnim:
                cLoadingView.zoom();
                break;
        }
    }

    private Guide guide;
    private List<View> views = new ArrayList<>();
    int currentIndex = 0;

    public void showGuideView(View view, int i) {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(view)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                LogUtils.e(TAG, ">>>>>>>>>>> dismiss ");
                currentIndex = i + 1;
                if (currentIndex < views.size()) {
                    showGuideView(views.get(currentIndex), currentIndex);
                } else {
                    currentIndex = 0;
                }
            }
        });

        builder.addComponent(new SimpleComponent());
        guide = builder.createGuide();
        guide.show(getActivity());
    }


    public class SimpleComponent implements Component {

        @Override
        public View getView(LayoutInflater inflater) {

            LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layer_frends, null);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();
                }
            });
            return ll;
        }

        @Override
        public int getAnchor() {
            return Component.ANCHOR_BOTTOM;
        }

        @Override
        public int getFitPosition() {
            return Component.FIT_END;
        }

        @Override
        public int getXOffset() {
            //X轴偏移距离了 单位dp
            return 10;
        }

        @Override
        public int getYOffset() {
            //Y轴偏移距离  单位dp
            return 10;
        }
    }


    /**
     * flatMap不保证下游接收时间的顺序。 -- contactMap 能保证顺序输出
     */
    private void flatMapSample() {
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
                LogUtils.e(TAG, " >>>>>>> " + s);
            }
        });
    }

    /**
     * 压缩 ： 将多个被观察者压缩成一个返回。
     */
    private void zipSample() {
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
        });
        //BiFunction 一个基于多个输入值计算一个值的功能接口（回调）。
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e(TAG, " >>>>>>> " + s);
            }
        });
    }

}
