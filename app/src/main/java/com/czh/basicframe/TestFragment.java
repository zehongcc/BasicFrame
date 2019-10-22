package com.czh.basicframe;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.EventConfig;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.PermissionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * author  : czh
 * create Date : 2019/10/22  10:09
 * 详情 :
 */
public class TestFragment extends BaseFragment implements PermissionUtils.OnPermissionCallBack {

    Button clickBtn;
    @BindView(R.id.onClickBtn)
    Button onClickBtn;
    @BindView(R.id.content_tv)
    TextView contentTv;

    @Override
    protected int setLayout() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initValue(Bundle bundle) {
    }

    private void request(){
        permissionUtils.checkPermissions(mActivity, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO}, 120, TestFragment.this);
    }

    @Override
    protected void main() {

    }

    @Override
    public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
        LogUtils.d("Fragment >>> 请求权限" + isSuccess + " , 请求码：" + requestCode);
    }

    @Override
    protected void onFragmentEventBus(final EventBean object) {
        super.onFragmentEventBus(object);
        LogUtils.e(">>>>>>>>>>>>>> fragment >>>>>>>>>>>>> " + object.getTag() + " , " + ((String) object.getObject()));
        if (object.getTag() == EventConfig.TEST_CODE){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contentTv.setText(((String) object.getObject()));
                }
            });
        }
    }

    @OnClick(R.id.onClickBtn)
    public void onViewClicked() {
        EventBus.getDefault().post(new EventBean(EventConfig.TEST_CODE_2,"这是Fragment发给Activity的消息!"));
    }
}
