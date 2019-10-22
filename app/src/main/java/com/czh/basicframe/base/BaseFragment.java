package com.czh.basicframe.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.czh.basicframe.R;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * author  : czh
 * create Date : 2019/10/22  9:37
 * 详情 :
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int setLayout();

    protected abstract void initValue(Bundle bundle);

    protected abstract void main();

    protected PermissionUtils permissionUtils;

    protected Context mContext;

    protected Activity mActivity;

    protected View mView;

    protected Unbinder unbinder;

    protected  ToastUtils toast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(setLayout(), container, false);
        mView = view;
        unbinder = ButterKnife.bind(this, view);
        init();
        initValue(savedInstanceState);
        main();
        return view;
    }

    private void init() {
        permissionUtils = PermissionUtils.getInstance();
        mContext = getContext();
        toast = ToastUtils.getInstance();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限回调
        PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onFragmentEventBus(EventBean object) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
