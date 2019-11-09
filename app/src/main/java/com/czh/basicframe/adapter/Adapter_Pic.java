package com.czh.basicframe.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.czh.basicframe.R;
import com.czh.basicframe.utils.DensityUtil;
import com.czh.basicframe.utils.GlideUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author  : czh
 * create Date : 2019/8/16  15:33
 * 详情 :  图片选择
 */
public class Adapter_Pic extends RecyclerView.Adapter<Adapter_Pic.PicHolder> implements View.OnClickListener {

    private Context mContext;

    private List<File> listFile;

    private List<FileJsonBean> fileJsonList;

    private int mWidth;

    private OnPicClickListener listener;

    private boolean isAddMore;//是否添加更多


    public Adapter_Pic(Activity activity) {
        this.mContext = activity;
        this.listFile = new ArrayList<>();
        this.fileJsonList = new ArrayList<>();
        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        mWidth = (width - DensityUtil.dip2px(activity, 5) * 4) / 3;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_pic, parent, false);
        PicHolder holder = new PicHolder(view);
        //设置图片高度
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = mWidth;
        params.height = mWidth;
//        params.setMargins(DensityUtil.dip2px(mContext, 10), 0, DensityUtil.dip2px(mContext, 10), 0);
        holder.picItemLayout.setLayoutParams(params);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicHolder holder, int position) {
        if (position == listFile.size()) {
            holder.delIv.setVisibility(View.GONE);
            holder.picIv.setOnClickListener(this);
            holder.picIv.setImageResource(R.drawable.icon_picture);
            holder.picIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            if (isAddMore) {
                holder.delIv.setVisibility(View.VISIBLE);
                holder.picIv.setOnClickListener(null);
            } else {
                holder.delIv.setVisibility(View.GONE);
                holder.picIv.setOnClickListener(this);
            }
            holder.picIv.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.picIv.setImageResource(R.color.color_bg);
            GlideUtils.load(listFile.get(position),holder.picIv, R.color.color_bg);
            holder.delIv.setOnClickListener(v -> {
                //删除
                deletePic(position);
            });
        }
        holder.picIv.setTag(R.string.item_tag_one, position);
    }

    //删除
    private void deletePic(int position) {
        List<File> newFileList = new ArrayList<>();
        for (int i = 0; i < listFile.size(); i++) {
            if (i != position) {
                newFileList.add(listFile.get(i));
            }
        }
        listFile.clear();
        listFile.addAll(newFileList);
        //清除上传的数据
        List<FileJsonBean> newFileJsonList = new ArrayList<>() ;
        for (int i = 0; i < fileJsonList.size(); i++) {
            if (i != position){
                newFileJsonList.add(fileJsonList.get(i));
            }
        }
        fileJsonList.clear();
        fileJsonList.addAll(newFileJsonList) ;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isAddMore) {
            return listFile.size() + 1;
        } else {
            return 1;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_iv:
                if (listener != null) {
                    listener.onAdd(v, (Integer) v.getTag(R.string.item_tag_one));
                }
                break;
        }
    }

    public void setIsAddMore(boolean b) {
        this.isAddMore = b;
    }

    /**
     * 清除数据
     */
    public void clear() {
        this.listFile.clear();
        this.fileJsonList.clear();
        notifyDataSetChanged();
    }

    public interface OnPicClickListener {
        void onAdd(View view, int position);
    }

    //设置点击监听
    public void setOnPicListener(OnPicClickListener listener) {
        this.listener = listener;
    }

    //添加文件
    public void addFile(File fileBean, FileJsonBean fileJsonBean) {
        if (!isAddMore) {
            listFile.clear();
            fileJsonList.clear();
        }
        this.listFile.add(fileBean);
        this.fileJsonList.add(fileJsonBean);
        notifyDataSetChanged();
    }

    //获取json
    public String getFileJson() {
        String json = null;
        json = new Gson().toJson(fileJsonList);
        return json;
    }

    public List<FileJsonBean> getFileList() {
        return fileJsonList;
    }


    class PicHolder extends RecyclerView.ViewHolder {
        private ImageView picIv, delIv;
        private RelativeLayout picItemLayout;

        public PicHolder(View itemView) {
            super(itemView);
            delIv = itemView.findViewById(R.id.item_del_iv);
            picIv = itemView.findViewById(R.id.item_iv);
            picItemLayout = itemView.findViewById(R.id.item_pic_layout);
        }
    }
}
