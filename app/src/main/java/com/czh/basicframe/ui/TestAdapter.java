package com.czh.basicframe.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.czh.basicframe.R;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.widget.NinePicImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * create by Chen
 * create date : 2019/11/23
 * desc :
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestNineHolder> {

    private final String TAG = "TestAdapter";
    private Context context;

    private List<String> test = new ArrayList<>();

    public TestAdapter(Context context) {
        this.context = context;
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
    }


    @NonNull
    @Override
    public TestNineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_test, parent, false);
        return new TestNineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestNineHolder holder, int position) {
        List<String> newLists = new ArrayList<>();
        for (int i = 0; i < position + 1; i++) {
            newLists.add(test.get(i));
        }
        setHeader(newLists, holder.nineIv, position);
    }

    //设置头像-- 头像地址转成文件
    private void setHeader(final List<String> urls, final NinePicImageView ninePicImageView, int position) {
        long sTime = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<File> files = new ArrayList<>();
                for (int i = 0; i < urls.size(); i++) {
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(urls.get(i))
                            .submit();
                    try {
                        final File imageFile = target.get();
                        files.add(imageFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //文件转完 -- 在主线程更新
                android.os.Message message = new android.os.Message();
                message.what = 1;
                message.obj = new MessageBean(files, ninePicImageView);
                handler.sendMessage(message);
                long eTime = System.currentTimeMillis();
                LogUtils.e(TAG, ">>> " + position + " >> " + (eTime - sTime) + "ms");
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    MessageBean msgBean = (MessageBean) msg.obj;
                    List<File> list = msgBean.getList();
                    NinePicImageView view = msgBean.getView();
                    view.setUrls(list);
                    break;
            }
        }
    };


    class MessageBean {
        private List<File> list;
        private NinePicImageView view;

        public MessageBean(List<File> list, NinePicImageView view) {
            this.list = list;
            this.view = view;
        }

        public List<File> getList() {
            return list;
        }

        public NinePicImageView getView() {
            return view;
        }
    }


    @Override
    public int getItemCount() {
        return 9;
    }

    class TestNineHolder extends RecyclerView.ViewHolder {
        private NinePicImageView nineIv;

        public TestNineHolder(View itemView) {
            super(itemView);
            nineIv = itemView.findViewById(R.id.item_nine_iv);
        }
    }
}
