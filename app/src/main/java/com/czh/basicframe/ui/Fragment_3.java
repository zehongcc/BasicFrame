package com.czh.basicframe.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.widget.NinePicImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create by Chen
 * create date : 2019/11/20
 * desc :
 */
public class Fragment_3 extends BaseFragment {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.nicePicImageView)
    NinePicImageView ninePicImageView;
    @BindView(R.id.recyclerView)
    RecyclerView nineRecyclerView;

    @Override
    protected int setLayout() {
        return R.layout.fragment_3;
    }

    @Override
    protected void initValue(Bundle bundle) {

    }

    @Override
    protected void main() {
    }


    @OnClick(R.id.btn1)
    public void onViewClicked() {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<File> files = new ArrayList<>();
                for (int i = 0; i < test.size(); i++) {
                    FutureTarget<File> target = Glide.with(mContext)
                            .asFile()
                            .load(test.get(i))
                            .submit();
                    try {
                        final File imageFile = target.get();
                        files.add(imageFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //文件转完 -- 在主线程更新
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ninePicImageView.setUrls(files);
                    }
                });
            }
        }).start();

        ///
        nineRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        nineRecyclerView.setAdapter(new TestAdapter(mContext));
    }
}
