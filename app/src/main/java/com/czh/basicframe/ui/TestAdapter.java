package com.czh.basicframe.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.czh.basicframe.R;
import com.czh.basicframe.widget.NinePicImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Chen
 * create date : 2019/11/23
 * desc :
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestNineHolder> {

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
        for (int i = 0; i < position+1; i++) {
            newLists.add(test.get(i));
        }
        holder.nineIv.setUrls(newLists);
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
