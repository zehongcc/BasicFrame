package com.czh.basicframe;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.EventConfig;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.PermissionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.content_tv)
    TextView contentTv;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    private TestFragment testFragment;

    @Override
    protected void init(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        testFragment = new TestFragment();
        transaction.add(R.id.frameLayout, testFragment);
        transaction.commit();
    }

    @Override
    protected void main() {
        //
    }

    //请求权限
    public void toRequest() {
        permissionUtils.checkPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO}, 110, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                LogUtils.d("请求权限" + isSuccess + " , 请求码：" + requestCode);
            }
        });
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                toRequest();
                break;
            case R.id.btn2:
                EventBus.getDefault().postSticky(new EventBean(EventConfig.TEST_CODE, "这是一条activity发送到fragment的内容!"));
                break;
            case R.id.btn3:
                break;
        }
    }

    @Override
    public void onEventBus(EventBean object) {
        super.onEventBus(object);
        if (object == null) return;
        String content = (String) object.getObject();
        int tag = object.getTag();
        LogUtils.e("<<<<<<<<<<<<< activity >>>>>>>>>> " + tag + " , " + tag);
        if (tag == EventConfig.TEST_CODE_2) {
            contentTv.setText(content);
        } else if (tag == EventConfig.TEST_CODE) {
            contentTv.setText("");
            testFragment.onFragmentEventBus(object);
        }
    }
}
