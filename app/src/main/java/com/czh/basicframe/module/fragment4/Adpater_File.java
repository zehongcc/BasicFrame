package com.czh.basicframe.module.fragment4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.czh.basicframe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Chen
 * create date : 2019/12/4
 * desc :
 */
public class Adpater_File extends RecyclerView.Adapter<Adpater_File.FileHolder> {

    private Context context ;
    private List<String> mList ;

    public Adpater_File(Context context) {
        this.context = context ;
        this.mList = new ArrayList<>() ;
    }


    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adpater_file,parent,false) ;
        FileHolder holder = new FileHolder(view) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, int position) {
        holder.nameTv.setText(mList.get(position));
    }

    public void setDatas(String[] strs){
        this.mList.clear();
        if (strs != null && strs.length > 0){
            for (int i = 0; i < strs.length; i++) {
                mList.add(strs[i]) ;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class FileHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        public FileHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.item_fileName_tv);
        }
    }
}
