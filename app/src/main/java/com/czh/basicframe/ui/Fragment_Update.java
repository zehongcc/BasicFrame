package com.czh.basicframe.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.widget.NinePicImageView;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.nicePicImageView)
    NinePicImageView ninePicImageView;
    @BindView(R.id.recyclerView)
    RecyclerView nineRecyclerView;

    @Override
    protected int setLayout() {
        return R.layout.fragment_update;
    }

    @Override
    protected void initValue(Bundle bundle) {

    }

    @Override
    protected void main() {

        nineRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        nineRecyclerView.setAdapter(new TestAdapter(mContext));
    }

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @OnClick(R.id.btn1)
    public void onViewClicked() {
//        PermissionUtils.getInstance().checkPermissions(mActivity, permissions, 111, (isSuccess, requestCode) -> {
//            if (isSuccess){
//                toast.shortToast("开始更新");
//            }
//        });
        List<String> test = new ArrayList<>();
        test.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1671398372,3381203579&fm=26&gp=0.jpg");
        test.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3998889803,535011003&fm=26&gp=0.jpg");
        test.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1302601015,3873955939&fm=26&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3620678974,412273927&fm=26&gp=0.jpg");
        test.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1485081754,2164381578&fm=26&gp=0.jpg");
        test.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3687293927,3730859401&fm=26&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2638329283,1152876418&fm=11&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3551856573,95373944&fm=26&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2106686232,3200710859&fm=26&gp=0.jpg");
        test.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1671398372,3381203579&fm=26&gp=0.jpg");
        ninePicImageView.setUrls(test);
    }
}
