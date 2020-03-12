package com.czh.basicframe.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.utils.DensityUtil;

import java.util.List;

/**
 * author  : czh
 * create Date : 2019/8/20  13:58
 * 详情 :
 */
public class CCalendarAdapter extends RecyclerView.Adapter<CCalendarAdapter.CCalendarHolder> implements View.OnClickListener {
    private Context context;
    private List<CCalendarBean> list;
    private int mItemHeight;
    private OnClickItemListener onClickItemListener;

    /**
     * 外部设置控件的宽高
     */
    private int mWidth;
    private int mHeight;

    public CCalendarAdapter(Context context, List<CCalendarBean> list, int mItemHeight) {
        this.context = context;
        this.list = list;
        this.mItemHeight = mItemHeight;
    }

    public CCalendarAdapter(Context context, List<CCalendarBean> list, int width ,int height) {
        this.context = context;
        this.list = list;
        this.mWidth = width ;
        this.mHeight = height ;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onItemClick(View view, int position, Object o);
    }


    @NonNull
    @Override
    public CCalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ccalendaradapter, parent, false);
        CCalendarHolder holder = new CCalendarHolder(view);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5));
        if (mItemHeight != 0) {
            params.width = mItemHeight;
            params.height = mItemHeight;
        }
        //根据外部设置的宽高来设置
        if (mWidth != 0 && mHeight != 0) {
            params.width = mWidth;
            params.height = mHeight;
        }
        holder.itemTv.setLayoutParams(params);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CCalendarHolder holder, int position) {
        CCalendarBean bean = list.get(position);
        holder.itemTv.setText(bean.getDay());
        //是否可以选择
        boolean dayClickable = bean.isDayClickable();
        if (dayClickable){
            holder.itemTv.setOnClickListener(this);
            //是否已经选中
            if (bean.isSelect()) {
                holder.itemTv.setTextColor(context.getResources().getColor(R.color.color_white));
                holder.itemTv.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
            } else {
                holder.itemTv.setTextColor(context.getResources().getColor(R.color.color_line));
                holder.itemTv.setBackgroundColor(context.getResources().getColor(R.color.color_white));
            }
        }else {
            holder.itemTv.setTextColor(context.getResources().getColor(R.color.color_line));
            holder.itemTv.setBackgroundColor(context.getResources().getColor(R.color.color_bg));
            holder.itemTv.setOnClickListener(null);
        }
        holder.itemTv.setTag(R.string.item_tag_one, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_tv:
                int position = (int) v.getTag(R.string.item_tag_one);
                CCalendarBean bean = list.get(position);
                if (bean.isEnable()) {
                    changeSelect(position);
                    if (onClickItemListener != null) {
                        onClickItemListener.onItemClick(v, position, bean.getDay());
                    }
                }
                break;
        }
    }

    //改变选择
    private void changeSelect(int position) {
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setSelect(true);
            } else {
                list.get(i).setSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    public String getDay() {
        String day = "1";
        for (int i = 0; i < list.size(); i++) {
            boolean select = list.get(i).isSelect();
            if (select) {
                day = list.get(i).getDay();
                break;
            }
        }
        return Integer.valueOf(day) > 9 ? day : "0" + day;
    }


    class CCalendarHolder extends RecyclerView.ViewHolder {
        private TextView itemTv;

        public CCalendarHolder(View itemView) {
            super(itemView);
            itemTv = itemView.findViewById(R.id.item_tv);
        }
    }
}
