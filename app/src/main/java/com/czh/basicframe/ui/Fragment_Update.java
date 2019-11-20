package com.czh.basicframe.ui;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.utils.PermissionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * create by Chen
 * create date : 2019/11/20
 * desc :
 */
public class Fragment_Update extends BaseFragment {

    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected int setLayout() {
        return R.layout.fragment_update;
    }

    @Override
    protected void initValue(Bundle bundle) {

    }

    @Override
    protected void main() {

    }

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @OnClick(R.id.btn1)
    public void onViewClicked() {
        PermissionUtils.getInstance().checkPermissions(mActivity, permissions, 111, (isSuccess, requestCode) -> {
            if (isSuccess){
                toast.shortToast("开始更新");
            }
        });
    }
}
